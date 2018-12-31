package cn.com.wangxuefeng.iyou;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class DynamicDataGet {
    private static final String  DYNAMIC_URL = "https://api.wangxuefeng.com.cn/weekdynamic/get/all?";
    public List<Dynamic> getDynamicData(int page) {
        List<Dynamic> dynamicList = new ArrayList<Dynamic>();
        try{
            String urlString = DYNAMIC_URL + "page=" + page;
            Log.i("DataGet",urlString);
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            InputStream in = connection.getInputStream();
            StringBuffer stringBuffer = new StringBuffer();
            int c = 0;
            while((c = in.read()) != -1){
                stringBuffer.append((char) c);
            }
            Log.i("DataGet",stringBuffer.toString());
            DynamicParse dynamicParse = new DynamicParse();
            dynamicParse.parse(stringBuffer.toString());
            dynamicList = dynamicParse.getDynamicList();
            return dynamicList;
        }catch (IOException e){
            e.printStackTrace();
        }
        return dynamicList;
    }
}
