package cn.com.wangxuefeng.iyou.view.data;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

import org.json.JSONException;
import org.json.JSONObject;

import cn.com.wangxuefeng.iyou.R;
import cn.com.wangxuefeng.iyou.bean.User;
import cn.com.wangxuefeng.iyou.util.Config;
import cn.com.wangxuefeng.iyou.util.YGUtils;
import cn.com.wangxuefeng.iyou.view.MainActivity;
import cn.com.wangxuefeng.iyou.view.ui.login.LoginActivity;

import java.util.HashMap;
import java.util.Map;

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
public class LoginDataSource {

    public static void login(String stuid, String password, final Context context, final ProgressBar loadingProgressBar) {
        Map<String, String> map = new HashMap<>();
        map.put("stuid", stuid);
        map.put("password", password);
        JSONObject loginInfor = new JSONObject(map);
        OkGo.<String>post(Config.API(Config.LOGIN_URL)).tag(context).upJson(loginInfor).execute(new StringCallback() {
            @Override
            public void onSuccess(Response<String> response) {
                String body = response.body();
                try {
                    JSONObject res = new JSONObject(body);
                    if (res.getBoolean("status")) {
                        String rawUserInfor = res.getString("infor");
                        User model = UserParse.parseLogin(rawUserInfor);
                        Map<String, String> user = new HashMap<>();
                        user.put("id", String.valueOf(model.getId()));
                        user.put("stuid", String.valueOf(model.getStuid()));
                        user.put("name", String.valueOf(model.getName()));
                        user.put("head", String.valueOf(model.getHead()));
                        user.put("email", String.valueOf(model.getEmail()));
                        user.put("online", String.valueOf(model.getOnline()));
                        user.put("sex", String.valueOf(model.getSex()));
                        user.put("birthday", String.valueOf(model.getBirthday()));
                        user.put("college", String.valueOf(model.getCollege()));
                        user.put("majorclass", String.valueOf(model.getMajorclass()));
                        user.put("department", String.valueOf(model.getDepartment()));
                        user.put("position", String.valueOf(model.getPosition()));
                        user.put("qq", String.valueOf(model.getQq()));
                        user.put("phone", String.valueOf(model.getPhone()));
                        user.put("utype", String.valueOf(model.getUtype()));
                        user.put("loginip", String.valueOf(model.getLoginip()));
                        user.put("ifkey", String.valueOf(model.getIfkey()));
                        user.put("registerdat", String.valueOf(model.getRegisterdat()));
                        user.put("token", String.valueOf(model.getToken()));
                        user.put("xgtoken", String.valueOf(model.getXgtoken()));
                        user.put("duty", String.valueOf(model.getDuty().toString()));
                        user.put("ulevel", String.valueOf(model.getUlevel()));
                        user.put("signcount", String.valueOf(model.getSigncount()));
                        user.put("wxid", String.valueOf(model.getWxid()));
                        user.put("photo", String.valueOf(model.getPhoto()));
                        user.put("positionName", String.valueOf(model.getPositionName()));
                        user.put("rawUserData", String.valueOf(rawUserInfor));
                        YGUtils.saveData(context, Config.LoginDataSP, user);
                        Toast.makeText(context, R.string.login_success, Toast.LENGTH_LONG).show();
                        loadingProgressBar.setVisibility(View.GONE);
                        Intent intent = new Intent(context, MainActivity.class);
                        context.startActivity(intent);
                        LoginActivity.instance.finish();
                    } else {
                        loadingProgressBar.setVisibility(View.GONE);
                        Toast.makeText(context, res.getString("title"), Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    loadingProgressBar.setVisibility(View.GONE);
                    Toast.makeText(context, R.string.login_error, Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onError(Response<String> response) {
                super.onError(response);
                Toast.makeText(context, R.string.login_error, Toast.LENGTH_LONG).show();
            }
        });
    }

    public void logout(Context context) {
        YGUtils.clear(context, Config.LoginDataSP);
    }
}
