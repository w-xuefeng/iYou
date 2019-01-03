package cn.com.wangxuefeng.iyou;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

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
                String textContent = currentDynamic.getString(DYNAMIC_CONTENT);
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
                dynamicList.add(new Dynamic(id, author, publisherTime, textContent, imgArray, liker, viewCount));
            }
        }catch (JSONException e){
            e.printStackTrace();
        }
        return dynamicList;
    }
 }
