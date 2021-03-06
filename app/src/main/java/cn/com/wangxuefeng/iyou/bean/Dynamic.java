package cn.com.wangxuefeng.iyou.bean;

import java.util.List;

public class Dynamic {
    private int id;
    private User author;
    private String publishTime;
    private String textContent;
    private String htmlContent;
    private String videoUrl;
    private List<String> imgs;
    private List<User> liker;
    private long viewCount;

    public Dynamic() {
    }

    public Dynamic(int id, User author, String publishTime, String textContent, String htmlContent, List<String> imgs, List<User> liker, long viewCount, String videoUrl) {
        this.id = id;
        this.author = author;
        this.publishTime = publishTime;
        this.textContent = textContent;
        this.htmlContent = htmlContent;
        this.imgs = imgs;
        this.liker = liker;
        this.viewCount = viewCount;
        this.videoUrl = videoUrl;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public String getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(String publishTime) {
        this.publishTime = publishTime;
    }

    public String getTextContent() {
        return textContent;
    }

    public void setTextContent(String textContent) {
        this.textContent = textContent;
    }

    public List<String> getImgs() {
        return imgs;
    }

    public void setImgs(List<String> imgs) {
        this.imgs = imgs;
    }


    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public List<User> getLiker() {
        return liker;
    }

    public void setLiker(List<User> liker) {
        this.liker = liker;
    }

    public long getViewCount() {
        return viewCount;
    }

    public void setViewCount(long viewCount) {
        this.viewCount = viewCount;
    }

    public void setHtmlContent(String htmlContent) {
        this.htmlContent = htmlContent;
    }
    public String getHtmlContent() {
        return htmlContent;
    }
}
