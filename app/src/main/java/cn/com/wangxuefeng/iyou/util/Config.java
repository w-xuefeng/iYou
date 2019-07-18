package cn.com.wangxuefeng.iyou.util;

public class Config {
    public static String BASE_URL = "https://api.wangxuefeng.com.cn";
    public static String ASSET_BASE_URL = "https://api.wangxuefeng.com.cn/static/assets";
    public static String DYNAMIC_URL = "/weekdynamic/get/all";
    public static String LOGIN_URL = "/session/post/stuid";

    public static String API(String url) {
        return BASE_URL + url;
    }

    public static String UPIMG(String url) {
        return ASSET_BASE_URL + url;
    }


    public static String LoginDataSP = "LoggedInUser";
}
