package cn.com.wangxuefeng.iyou;

import java.util.List;

public class Dynamic {
    private int id;
    private User author;
    private String publishTime;
    private String textContent;
    private List<String> imgs;
    private List<User> liker;
    private long viewCount;

    public Dynamic() {
    }

    public Dynamic(int id, User author, String publishTime, String textContent, List<String> imgs, List<User> liker, long viewCount) {
        this.id = id;
        this.author = author;
        this.publishTime = publishTime;
        this.textContent = textContent;
        this.imgs = imgs;
        this.liker = liker;
        this.viewCount = viewCount;
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
}
