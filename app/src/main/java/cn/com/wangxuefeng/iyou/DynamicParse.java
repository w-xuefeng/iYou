package cn.com.wangxuefeng.iyou;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class DynamicParse {
    private static final String BASE_URL = "https://api.wangxuefeng.com.cn/static/assets";
    private List<Dynamic> dynamicList;
    public DynamicParse() {
    }
    public void parse(String dynamicInfor){
        try{
            JSONArray dynamicArray = new JSONArray(dynamicInfor);
            for (int i = 0; i < dynamicArray.length(); i++) {
                JSONObject currentDynamic = dynamicArray.getJSONObject(i);
                int id = currentDynamic.getInt("id");
                User author = new User(currentDynamic.getString("publisherstuid"), currentDynamic.getString("publisher"), currentDynamic.getString(BASE_URL + "head"));
                String publisherTime = currentDynamic.getString("publishdate");
                String textContent = currentDynamic.getString("content");
                long viewCount = currentDynamic.getLong("viewcount");
                List<String> imgs = new ArrayList<String>();
                JSONArray imgsUrl = currentDynamic.getJSONArray("imgs");
                if (imgsUrl.length() > 0) {
                    for(int k = 0; k < imgsUrl.length(); k++){
                        imgs.add(imgsUrl.getString(k));
                    }
                }
                List<User> liker  = new ArrayList<User>();
                JSONArray likeUser = currentDynamic.getJSONArray("linker");
                if (likeUser.length() > 0) {
                    for(int j = 0; j < likeUser.length(); j++){
                        JSONObject currentLiker = likeUser.getJSONObject(j);
                        liker.add(new User(currentLiker.getString("stuid"), currentLiker.getString("name"),currentLiker.getString(BASE_URL + "head")));
                    }
                }
                this.dynamicList.add(new Dynamic(id, author, publisherTime, textContent, imgs, liker, viewCount));
            }
        }catch (JSONException e){
            e.printStackTrace();
        }
    }

    public List<Dynamic> getDynamicList() {
        return dynamicList;
    }

 }
