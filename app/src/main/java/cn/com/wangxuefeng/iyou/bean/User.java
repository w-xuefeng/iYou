package cn.com.wangxuefeng.iyou.bean;

public class User {
    private int id;
    private String stuid;
    private String name;
    private String head;
    private String email;
    private int online;
    private int sex;
    private String birthday;
    private String college;
    private String majorclass;
    private String department;
    private int position;
    private String qq;
    private String phone;
    private int utype;
    private String loginip;
    private int ifkey;
    private String registerdat;
    private String token;
    private String xgtoken;
    private Duty duty;
    private int ulevel;
    private int signcount;
    private String wxid;
    private String photo;
    private String positionName;

    public User() {
    }

    public User(int id, String stuid, String name, String head, String email, int online, int sex, String birthday, String college, String majorclass, String department, int position, String qq, String phone, int utype, String loginip, int ifkey, String registerdat, String token, String xgtoken, Duty duty, int ulevel, int signcount, String wxid, String photo, String positionName) {
        this.id = id;
        this.stuid = stuid;
        this.name = name;
        this.head = head;
        this.email = email;
        this.online = online;
        this.sex = sex;
        this.birthday = birthday;
        this.college = college;
        this.majorclass = majorclass;
        this.department = department;
        this.position = position;
        this.qq = qq;
        this.phone = phone;
        this.utype = utype;
        this.loginip = loginip;
        this.ifkey = ifkey;
        this.registerdat = registerdat;
        this.token = token;
        this.xgtoken = xgtoken;
        this.duty = duty;
        this.ulevel = ulevel;
        this.signcount = signcount;
        this.wxid = wxid;
        this.photo = photo;
        this.positionName = positionName;
    }

    public User(String stuid, String name, String head) {
        this.stuid = stuid;
        this.name = name;
        this.head = head;
    }

    public User(String stuid, String name, String head, String email) {
        this.stuid = stuid;
        this.name = name;
        this.head = head;
        this.email = email;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOnline() {
        return online;
    }

    public void setOnline(int online) {
        this.online = online;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getCollege() {
        return college;
    }

    public void setCollege(String college) {
        this.college = college;
    }

    public String getMajorclass() {
        return majorclass;
    }

    public void setMajorclass(String majorclass) {
        this.majorclass = majorclass;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getUtype() {
        return utype;
    }

    public void setUtype(int utype) {
        this.utype = utype;
    }

    public String getLoginip() {
        return loginip;
    }

    public void setLoginip(String loginip) {
        this.loginip = loginip;
    }

    public int getIfkey() {
        return ifkey;
    }

    public void setIfkey(int ifkey) {
        this.ifkey = ifkey;
    }

    public String getRegisterdat() {
        return registerdat;
    }

    public void setRegisterdat(String registerdat) {
        this.registerdat = registerdat;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getXgtoken() {
        return xgtoken;
    }

    public void setXgtoken(String xgtoken) {
        this.xgtoken = xgtoken;
    }

    public Duty getDuty() {
        return duty;
    }

    public void setDuty(Duty duty) {
        this.duty = duty;
    }

    public int getUlevel() {
        return ulevel;
    }

    public void setUlevel(int ulevel) {
        this.ulevel = ulevel;
    }

    public int getSigncount() {
        return signcount;
    }

    public void setSigncount(int signcount) {
        this.signcount = signcount;
    }

    public String getWxid() {
        return wxid;
    }

    public void setWxid(String wxid) {
        this.wxid = wxid;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getPositionName() {
        return positionName;
    }

    public void setPositionName(String positionName) {
        this.positionName = positionName;
    }
}
