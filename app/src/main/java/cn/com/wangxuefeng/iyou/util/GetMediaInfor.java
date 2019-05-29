package cn.com.wangxuefeng.iyou.util;

import android.content.Context;
import android.os.AsyncTask;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.com.wangxuefeng.iyou.R;
import cn.com.wangxuefeng.iyou.bean.Media;

public class GetMediaInfor extends AsyncTask<Object, Integer, List<List<Object>>> {

    EventEmitter eventEmitter = new EventEmitter();
    Context context;
    String VODCookie = null;
    Map<String, String> VODHeaders = new HashMap<>();

    public GetMediaInfor(Context context) {
        this.context = context;
        if(VODConfig.VODCookie != null){
            VODCookie = VODConfig.VODCookie;
        }
    }

    public GetMediaInfor(Context context, String cookie) {
        setCookie(cookie);
        this.context = context;
    }

    public GetMediaInfor(Context context, String cookie, EventEmitter eventEmitter) {
        setCookie(cookie);
        this.eventEmitter = eventEmitter;
        this.context = context;
    }

    public GetMediaInfor on(String eventName, IEventHandler eventHandler) {
        this.eventEmitter.on(eventName, eventHandler);
        return this;
    }


    // 方法1：onPreExecute（）
    // 作用：执行 线程任务前的操作
    @Override
    protected void onPreExecute() {
        // 执行前显示提示
    }


    // 方法2：doInBackground（）
    // 作用：接收输入参数、执行任务中的耗时操作、返回 线程任务执行的结果
    // 此处通过计算从而模拟“加载进度”的情况
    @Override
    protected List<List<Object>> doInBackground(Object... params) {
        List<List<Object>> res = new ArrayList<>();
        for (Object pid : params) {
            res.add(getByPid((long) pid));
        }
        return res;
    }

    // 方法3：onProgressUpdate（）
    // 作用：在主线程 显示线程任务执行的进度
    @Override
    protected void onProgressUpdate(Integer... progresses) {}

    // 方法4：onPostExecute（）
    // 作用：接收线程任务执行结果、将执行结果显示到UI组件
    @Override
    protected void onPostExecute(List<List<Object>> result) {
        List<Object> res = new ArrayList<>();
        for (int i = 0; i < result.size(); i++) {
            List<Object> current = result.get(i);
            String state = (String) current.get(0);
            if (state.equals("error")) {
                this.eventEmitter.emit("error", current);
            } else if (state.equals("data")){
                res.add(current.get(1));
            }
        }
        this.eventEmitter.emit("success", res);
    }

    // 方法5：onCancelled()
    // 作用：将异步任务设置为：取消状态
    @Override
    protected void onCancelled() {}

    public String getTableInfor(Document document, int column) {
        try {
            String res = new String(
                    document.select(VODConfig.TableInfoCSSSelector)
                            .get(column)
                            .select(VODConfig.SubTableInfoCSSSelector)
                            .get(VODConfig.SubTableInfoValueIndex)
                            .text()
                            .getBytes(document.charset()), VODConfig.InforValueCharset);
            return  res.trim().equals("") ? "暂未填写" : res;
        } catch (UnsupportedEncodingException e) {
            return context.getString(R.string.err_vod_UnsupportedEncoding);
        } catch (IndexOutOfBoundsException e) {
            return context.getString(R.string.err_vod_CookieUnavailable);
        }
    }

    public List<Object> getByPid(long pid) {
        Media media = new Media();
        media.setCover(VODConfig.getImageUrl(pid));
        media.setPid(pid);
        media.setIntroduction("");
        if(VODConfig.VODCookie != null){
            VODCookie = VODConfig.VODCookie;
        }
        try {
            if(VODCookie == null){ getCookie(); }
            Document document = getInforByPid(pid);
            if("relogin".equals(document.text().trim())){
                List<Object> res = retryGet(pid, 3);
                String state = (String) res.get(0);
                if(state.equals("error")){
                    return res;
                } else if (state.equals("data")){
                    document = (Document) res.get(1);
                }
            }
            media.setVideoTitle(getTableInfor(document, 0));
            media.setVideoType(getTableInfor(document, 2));
            media.setCreateTime(getTableInfor(document, 4));
            media.setKeyword(getTableInfor(document, 6));
            media.setStarring(getTableInfor(document, 8));
            media.setDirector(getTableInfor(document, 10));
            media.setViewCount(getTableInfor(document, 12));
            media.setTimeLength(getTableInfor(document, 14));
            media.setCodeRate(getTableInfor(document, 16));
            media.setIntroduction(new String(document
                    .select(VODConfig.IntroductionCSSSelector)
                    .get(VODConfig.IntroductionValueIndex)
                    .text()
                    .getBytes(document.charset()), VODConfig.InforValueCharset));
        } catch (IOException | IndexOutOfBoundsException e) {
            e.printStackTrace();
            List<Object> error = new ArrayList<>();
            error.add("error");
            error.add(context.getString(R.string.err_vod_req));
            return error;
        } catch (Exception e) {
            e.printStackTrace();
            List<Object> error = new ArrayList<>();
            error.add("error");
            error.add(context.getString(R.string.err_vod_req));
            return error;
        }
        List<Object> resData = new ArrayList<>();
        resData.add("data");
        resData.add(media);
        return resData;
    }
    public GetMediaInfor setCookie(String cookie) {
        this.VODCookie = cookie;
        return this;
    }

    public GetMediaInfor header(Map<String, String> header) {
        this.VODHeaders = header;
        return this;
    }

    private List<Object> getCookie() throws Exception {
        List<String> reCookie = GetCookie.get("http://" + VODConfig.IPAddress + VODConfig.CookieAddress2, "http://" + VODConfig.IPAddress + VODConfig.usrMenuAddress);
        if(reCookie != null && reCookie.get(0) != null){
            VODCookie = reCookie.get(0).split(";")[0].split("=")[1];
            List<Object> data = new ArrayList<>();
            data.add("error");
            data.add(VODCookie);
            return data;
        } else {
            List<Object> error = new ArrayList<>();
            error.add("error");
            error.add(context.getString(R.string.err_vod_NotCookie));
            return error;
        }
    }

    private Document getInforByPid(long pid) throws IOException {
        return Jsoup.connect(VODConfig.getInforUrl(pid))
                .headers(VODHeaders)
                .cookie(VODConfig.CookieName, VODCookie)
                .get();
    }

    private List<Object> retryGet(long pid, int retryCount) throws Exception {
        getCookie();
        Document document = getInforByPid(pid);
        List<Object> res = new ArrayList<>();
        int count = retryCount > 0 && retryCount <= 5 ? retryCount : 3;
        if("relogin".equals(document.text().trim())){
            for (int i = 0; i < count ;i++) {
                getCookie();
                document = getInforByPid(pid);
                if("relogin" != (document.text().trim())){
                    res.add("data");
                    res.add(document);
                    return res;
                }
            }
            res.add("error");
            res.add(context.getString(R.string.err_vod_CookieUnavailable));
        } else {
            res.add("data");
            res.add(document);
        }
        return res;
    }
}
