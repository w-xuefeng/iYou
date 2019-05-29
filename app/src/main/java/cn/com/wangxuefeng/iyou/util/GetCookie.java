package cn.com.wangxuefeng.iyou.util;

import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class GetCookie {
    public static List<String> get(String path, String linkPath) throws Exception {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(path)
                .build();
        Call call = client.newCall(request);
        Response response = call.execute();
        int code = response.code();
        if (code == 200) {
            ResponseBody body = response.body();
            Headers headers=response.headers();
            List<String> cookies = headers.values("Set-Cookie");
            String []defaultCookie = cookies.get(0).split(";")[0].split("=");
            Log.i("Cookie", cookies.get(0).split(";")[0]);
            linkCookie(linkPath, defaultCookie[0], defaultCookie[1]);
            VODConfig.VODCookie = defaultCookie[1];
            return cookies;
        }
        return null;
    }

    public static void linkCookie(String linkPath, String cookieName,String cookie) throws IOException {
        Document document = Jsoup.connect(linkPath).cookie(cookieName, cookie).get();
    }
}