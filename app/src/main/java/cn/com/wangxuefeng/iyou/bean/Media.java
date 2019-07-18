package cn.com.wangxuefeng.iyou.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Media implements Serializable {
    private long pid;
    private long sid = 1;
    private long cid = 0;
    private long usn = 0;
    private String time = "";
    private String videoName;      // 从服务器返回包中获取的视频名称
    private String fileExt;        // 从服务器返回包中获取的文件拓展名
    private long fileLen = 0;      // 文件总长度
    private long filePos = 0;      // 当前文件下载位置

    private String videoTitle ;          // 影片名称
    private String videoType;     // 影片类型
    private String createTime;    // 创建时间
    private String keyword;       // 关键字
    private String starring;      // 主演
    private String director;      // 导演
    private String viewCount;     // 点击率
    private String timeLength;    // 片长
    private String codeRate;      // 码率
    private String introduction;  // 内容介绍

    private String cover;  // 视频海报

    private List<MediaSp> videoSp = new ArrayList<>(); // 视频分集

    public Media() {
    }

    public Media(long pid) {
        this.pid = pid;
    }

    public Media(long pid, long sid) {
        this.pid = pid;
        this.sid = sid;
    }

    public long getPid() {
        return pid;
    }

    public void setPid(long pid) {
        this.pid = pid;
    }

    public long getSid() {
        return sid;
    }

    public void setSid(long sid) {
        this.sid = sid;
    }

    public long getCid() {
        return cid;
    }

    public void setCid(long cid) {
        this.cid = cid;
    }

    public long getUsn() {
        return usn;
    }

    public void setUsn(long usn) {
        this.usn = usn;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getVideoName() {
        return videoName;
    }

    public void setVideoName(String videoName) {
        this.videoName = videoName;
    }

    public String getFileExt() {
        return fileExt;
    }

    public void setFileExt(String fileExt) {
        this.fileExt = fileExt;
    }

    public long getFileLen() {
        return fileLen;
    }

    public void setFileLen(long fileLen) {
        this.fileLen = fileLen;
    }

    public long getFilePos() {
        return filePos;
    }

    public void setFilePos(long filePos) {
        this.filePos = filePos;
    }

    public String getVideoTitle() {
        return videoTitle;
    }

    public void setVideoTitle(String videoTitle) {
        this.videoTitle = videoTitle;
    }

    public String getVideoType() {
        return videoType;
    }

    public void setVideoType(String videoType) {
        this.videoType = videoType;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getStarring() {
        return starring;
    }

    public void setStarring(String starring) {
        this.starring = starring;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getViewCount() {
        return viewCount;
    }

    public void setViewCount(String viewCount) {
        this.viewCount = viewCount;
    }

    public String getTimeLength() {
        return timeLength;
    }

    public void setTimeLength(String timeLength) {
        this.timeLength = timeLength;
    }

    public String getCodeRate() {
        return codeRate;
    }

    public void setCodeRate(String codeRate) {
        this.codeRate = codeRate;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public List<MediaSp> getVideoSp() {
        return videoSp;
    }

    public void setVideoSp(List<MediaSp> videoSp) {
        this.videoSp = videoSp;
    }
}
