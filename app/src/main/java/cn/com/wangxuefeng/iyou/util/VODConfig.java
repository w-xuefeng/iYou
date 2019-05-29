package cn.com.wangxuefeng.iyou.util;

public class VODConfig {

    public static String VODCookie = null;

    public static final String IPAddress = "202.113.80.83";

    public static final int Port = 1680;

    public static final String CookieName = "JSESSIONID";

    public static final String lastVideoPidPageCSSSelector = "div.tw a img";

    public static final String TableInfoCSSSelector = "table.td14 tbody tr";

    public static final String SubTableInfoCSSSelector = "td";

    public static final int SubTableInfoValueIndex = 1;

    public static final String IntroductionCSSSelector = "td.line";

    public static final int IntroductionValueIndex = 2;

    public static final String InforValueCharset = "GB2312";

    public static final String CookieAddress = "/vod/usrlogin.jsp";

    public static final String CookieAddress2 = "/vod/usrlogin2.jsp";

    public static final String IndexAddress = "/vod/vodhome.jsp";

    public static final String usrMenuAddress = "/vod/usrMenu.jsp";

    public static String getInforUrl(long pid) {
        return "http://" + IPAddress + "/vod/proginfo.jsp?ProgId=" + pid;
    }

    public static String getImageUrl(long pid) {
        return "http://" + IPAddress +":" + Port + "/boful_app/boful.jpg?type=1&id=" + pid;
    }
}
