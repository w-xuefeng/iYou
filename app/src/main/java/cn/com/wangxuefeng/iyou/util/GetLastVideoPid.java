package cn.com.wangxuefeng.iyou.util;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.util.ArrayList;
import java.util.List;

import cn.com.wangxuefeng.iyou.R;

public class GetLastVideoPid extends AsyncTask<Void, Integer, List<Object>>{

    String VODCookie = null;
    EventEmitter eventEmitter = new EventEmitter();
    Context context;

    public GetLastVideoPid(Context context){
        this.context = context;
        if(VODConfig.VODCookie != null){
            VODCookie = VODConfig.VODCookie;
        }
    }

    @Override
    protected List<Object> doInBackground(Void... voids) {
        return getLastVideoPid();
    }

    @Override
    protected void onPostExecute(List<Object> res) {
        String state = (String) res.get(0);
        if(state.equals("data")){
            this.eventEmitter.emit("success", res);
        } else if (state.equals("error")){
            this.eventEmitter.emit("error", res);
        }
    }

    public GetLastVideoPid on(String eventName, IEventHandler eventHandler) {
        this.eventEmitter.on(eventName, eventHandler);
        return this;
    }

    private List<Object> getLastVideoPid() {
        List<String> reCookie = null;
        try {
            if(VODCookie == null){
                reCookie = GetCookie.get("http://" + VODConfig.IPAddress + VODConfig.CookieAddress2, "http://" + VODConfig.IPAddress + VODConfig.usrMenuAddress);
                if(reCookie != null && reCookie.get(0) != null){
                    VODCookie = reCookie.get(0).split(";")[0].split("=")[1];
                } else {
                    return cookieErrorHandle();
                }
            }
            Document document = Jsoup.connect("http://" + VODConfig.IPAddress + VODConfig.IndexAddress).cookie(VODConfig.CookieName, VODCookie).get();
            if("relogin".equals(document.text().trim())){
                Log.i("ERROR", "cookie 不可用");
                return cookieErrorHandle();
            } else {
                String url = document.select(VODConfig.lastVideoPidPageCSSSelector)
                        .get(0)
                        .attr("src");
                String pid = url.split("id=")[1];
                List<Object> data = new ArrayList<>();
                data.add("data");
                long temp = Long.parseLong(pid);
                data.add(Long.parseLong(pid));
                return data;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return cookieErrorHandle();
        }
    }

    private List<Object> cookieErrorHandle(){
        List<Object> error = new ArrayList<>();
        error.add("error");
        error.add(context.getString(R.string.err_vod_NotCookie));
        return error;
    }
}
