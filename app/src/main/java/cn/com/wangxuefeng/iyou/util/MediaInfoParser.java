package cn.com.wangxuefeng.iyou.util;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.regex.Pattern;

import cn.com.wangxuefeng.iyou.bean.Media;

public class MediaInfoParser {
    private long cid = 0;
    private long usn = 0;
    private long fileLen = 0;
    private String time;
    private String videoName;
    private String fileExt;
    private Media media = new Media();

    public MediaInfoParser(byte[] info) throws UnsupportedEncodingException {
        int i = 15;
        while (i > 11) {
            this.cid <<= 8;
            this.cid += info[i];
            i--;
        }
        i = 19;
        while (i > 15) {
            this.usn <<= 8;
            this.usn += info[i];
            i--;
        }
        i = 31;
        while (i > 23) {
            this.fileLen <<= 8;
            this.fileLen += info[i];
            i--;
        }
        this.time = this.replaceSpace(this.iconvDecode(Arrays.copyOfRange(info,52,72 ),"GB2312"));
        this.videoName = this.replaceSpace(this.iconvDecode(Arrays.copyOfRange(info,80,160 ),"GB2312"));
        this.fileExt = this.replaceSpace(this.iconvDecode(Arrays.copyOfRange(info,160,168 ),"GB2312"));

        this.media.setCid(this.cid);
        this.media.setUsn(this.usn);
        this.media.setFileLen(this.fileLen);
        this.media.setTime(this.time);
        this.media.setFileExt(this.fileExt);
        this.media.setVideoName(this.videoName);
    }

    private String iconvDecode(byte[] info, String codeType) throws UnsupportedEncodingException {
        return new String(info, codeType);
    }

    private String replaceSpace(String str) {
        return Pattern.compile("\b\u0000\b").matcher(str).replaceAll("");
    }

    public long getCid() {
        return cid;
    }

    public long getUsn() {
        return usn;
    }

    public long getFileLen() {
        return fileLen;
    }

    public String getTime() {
        return time;
    }

    public String getVideoName() {
        return videoName;
    }

    public String getFileExt() {
        return fileExt;
    }

    public Media getMedia() {
        return media;
    }
}
