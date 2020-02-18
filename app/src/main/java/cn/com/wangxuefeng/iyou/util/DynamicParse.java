package cn.com.wangxuefeng.iyou.util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

import cn.com.wangxuefeng.iyou.bean.Dynamic;
import cn.com.wangxuefeng.iyou.bean.User;

public class DynamicParse {
    private static final String BASE_URL = "https://api.wangxuefeng.com.cn/static/assets";
    private static final String DYNAMIC_ID = "id";
    private static final String PUBLISH_ID = "publisherstuid";
    private static final String HEAD = "head";
    private static final String PUBLISHER = "publisher";
    private static final String PUBLISH_DATE = "publishdate";
    private static final String DYNAMIC_CONTENT = "content";
    private static final String VIEW_COUNT = "viewcount";
    private static final String DYNAMIC_IMG = "imgs";
    private static final String DYNAMIC_LIKER = "liker";
    private static final String LINKER_ID = "stuid";
    private static final String LINKER_NAME = "name";

    public DynamicParse() {}
    public List<Dynamic> parse(String dynamic){
        List<Dynamic> dynamicList = new ArrayList<>();
        try{
            JSONArray dynamicArray = new JSONArray(dynamic);
            for (int i = 0; i < dynamicArray.length(); i++) {
                JSONObject currentDynamic = dynamicArray.getJSONObject(i);
                int id = currentDynamic.getInt(DYNAMIC_ID);
                User author = new User(currentDynamic.getString(PUBLISH_ID), currentDynamic.getString(PUBLISHER), BASE_URL + currentDynamic.getString(HEAD));
                String publisherTime = currentDynamic.getString(PUBLISH_DATE);
                String htmlContent = currentDynamic.getString(DYNAMIC_CONTENT);
                String videoUrl = null;
                Document doc = Jsoup.parse(htmlContent);
                Elements videoIframe = doc.getElementsByTag("iframe");
                if (videoIframe.size() > 0) {
                    videoUrl = videoIframe.get(0).attr("src");
                }

                String textContent = doc.getElementById("TextContent").html();
                long viewCount = currentDynamic.getLong(VIEW_COUNT);
                List<String> imgArray = new ArrayList<>();
                JSONArray imgUrl = currentDynamic.getJSONArray(DYNAMIC_IMG);
                if (imgUrl.length() > 0) {
                    for(int k = 0; k < imgUrl.length(); k++){
                        imgArray.add(imgUrl.getString(k));
                    }
                }
                List<User> liker  = new ArrayList<>();
                JSONArray likeUser = currentDynamic.getJSONArray(DYNAMIC_LIKER);
                if (likeUser.length() > 0) {
                    for(int j = 0; j < likeUser.length(); j++){
                        JSONObject currentLiker = likeUser.getJSONObject(j);
                        liker.add(new User(currentLiker.getString(LINKER_ID), currentLiker.getString(LINKER_NAME), BASE_URL + currentLiker.getString(HEAD)));
                    }
                }
                dynamicList.add(new Dynamic(id, author, publisherTime, textContent, htmlContent, imgArray, liker, viewCount, videoUrl));
            }
        }catch (JSONException e){
            e.printStackTrace();
        }
        return dynamicList;
    }
 }
