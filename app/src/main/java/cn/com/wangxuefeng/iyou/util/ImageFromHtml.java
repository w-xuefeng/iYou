package cn.com.wangxuefeng.iyou.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ImageFromHtml {

    /**
     * 格式化html标签
     * @param content
     * @return
     */
    public static String addSpecialCharToString(String content) {
        content = content.replace("<", "##<");
        content = content.replace(">", ">##");
        content = content.replace("####", "##");
        return content;
    }

    /**
     * 正则获取img标签中的图片的URL
     * @param imgHtml
     * @return
     */
    public static String regGetImageUrl(String imgHtml) {
        Pattern pat = Pattern.compile("(.src=)(.+?)(style=.)");
        Matcher m = pat.matcher(imgHtml);
        if(m.find()){
            return m.group(2);
        }
        return null;
    }
}
