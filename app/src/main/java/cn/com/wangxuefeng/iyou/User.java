package cn.com.wangxuefeng.iyou;

public class User {
    private String stuid;
    private String name;
    private String head;

    public User() {
    }

    public User(String stuid, String name, String head) {
        this.stuid = stuid;
        this.name = name;
        this.head = head;
    }

    public String getStuid() {
        return stuid;
    }

    public void setStuid(String stuid) {
        this.stuid = stuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }
}
