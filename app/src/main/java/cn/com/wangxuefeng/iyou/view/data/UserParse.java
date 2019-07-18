package cn.com.wangxuefeng.iyou.view.data;

import org.json.JSONException;
import org.json.JSONObject;
import cn.com.wangxuefeng.iyou.bean.Duty;
import cn.com.wangxuefeng.iyou.bean.User;
import cn.com.wangxuefeng.iyou.util.Config;

public class UserParse {
    private static final String USER_ID = "id";
    private static final String STUID = "stuid";
    private static final String NAME = "name";
    private static final String HEAD = "head";
    private static final String ONLINE = "online";
    private static final String SEX = "sex";
    private static final String BIRTHDAY = "birthday";
    private static final String COLLEGE = "college";
    private static final String MAJORCLASS = "majorclass";
    private static final String DEPARTMENT = "department";
    private static final String POSITION = "position";
    private static final String QQ = "qq";
    private static final String PHONE = "phone";
    private static final String EMAIL = "email";
    private static final String UTYPE = "utype";
    private static final String LOGIN_IP = "loginip";
    private static final String IFKEY = "ifkey";
    private static final String REGISTER_DAY = "registerdate";
    private static final String TOKEN = "token";
    private static final String XG_TOKEN = "xgtoken";
    private static final String DUTY = "duty";
    private static final String ULEVEL = "ulevel";
    private static final String SIGN_COUNT = "signcount";
    private static final String WX_ID = "wxid";
    private static final String PHOTO = "photo";
    private static final String POSITION_NAME = "positionName";

    public static User parseLogin(String user){
        User loginUser = null;
        try{
            JSONObject currentUser = new JSONObject(user);
            int id = currentUser.getInt(USER_ID);
            String stuid = currentUser.getString(STUID);
            String name = currentUser.getString(NAME);
            String head = Config.UPIMG(currentUser.getString(HEAD));
            String email = currentUser.getString(EMAIL);
            int online = currentUser.getInt(ONLINE);
            int sex = currentUser.getString(SEX).equals("null") ? 0 : currentUser.getInt(SEX);
            String birthday = currentUser.getString(BIRTHDAY);
            String college = currentUser.getString(COLLEGE);
            String majorclass = currentUser.getString(MAJORCLASS);
            String department = currentUser.getString(DEPARTMENT);
            int position = currentUser.getString(POSITION).equals("null") ? 0 : currentUser.getInt(POSITION);
            String qq = currentUser.getString(QQ);
            String phone = currentUser.getString(PHONE);
            int utype = currentUser.getInt(UTYPE);
            String loginip = currentUser.getString(LOGIN_IP);
            int ifkey = currentUser.getInt(IFKEY);
            String registerdat = currentUser.getString(REGISTER_DAY);
            String token = currentUser.getString(TOKEN);
            String xgtoken = currentUser.getString(XG_TOKEN);
            Duty duty = new Duty(currentUser.getString(DUTY));
            int ulevel = currentUser.getInt(ULEVEL);
            int signcount = currentUser.getInt(SIGN_COUNT);
            String wxid = currentUser.getString(WX_ID);
            String photo = currentUser.getString(PHOTO);
            String positionName = currentUser.getString(POSITION_NAME);
            loginUser = new User(id, stuid, name, head, email, online, sex, birthday, college, majorclass, department, position, qq, phone, utype, loginip, ifkey, registerdat, token, xgtoken, duty, ulevel, signcount, wxid, photo, positionName);
        }catch (JSONException e){
            e.printStackTrace();
        }
        return loginUser;
    }
}
