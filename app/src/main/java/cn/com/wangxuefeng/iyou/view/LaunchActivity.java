package cn.com.wangxuefeng.iyou.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import java.util.Timer;
import java.util.TimerTask;

import cn.com.wangxuefeng.iyou.R;
import cn.com.wangxuefeng.iyou.util.Config;
import cn.com.wangxuefeng.iyou.util.YGUtils;
import cn.com.wangxuefeng.iyou.view.ui.login.LoginActivity;

public class LaunchActivity extends AppCompatActivity {

    ImageView launchImg;
    final int launchImgKeepTime = 3000;
    final int adImgKeepTime = 5000;
    boolean isLogin = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isLogin = isLogin();
        setContentView(R.layout.activity_launch);
        launchImg = findViewById(R.id.LaunchImg);
        Boolean isNotDefaultLaunchImg = false;
        String newLaunchImg = "";
        final Boolean isNotDefaultAdImg = false;
        final String newAdImg = "";
        if (isNotDefaultLaunchImg) {
            Glide.with(this).load(newLaunchImg).into(launchImg);
        }
        final Intent intentMain = new Intent(this, MainActivity.class);
        final Intent intentLogin = new Intent(this, LoginActivity.class);

        final Timer timer = new Timer();
        TimerTask taskToAd = new TimerTask() {
            @Override
            public void run(){
                if(isNotDefaultAdImg){
                    Glide.with(LaunchActivity.this).load(newAdImg).into(launchImg);
                } else {
                    launchImg.setImageResource(R.drawable.ad_img_default);
                }
            }
        };
        TimerTask taskToMain = new TimerTask() {
            @Override
            public void run(){
                startActivity(isLogin ? intentMain : intentLogin);
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                timer.cancel();
                finish();
            }
        };
        timer.schedule(taskToAd, launchImgKeepTime);
        timer.schedule(taskToMain, launchImgKeepTime + adImgKeepTime);
        launchImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(isLogin ? intentMain : intentLogin);
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                timer.cancel();
                finish();
            }
        });
    }

    public boolean isLogin() {
        String stuid = YGUtils.getData(this, Config.LoginDataSP, "stuid");
        Log.i("STUID", stuid);
        return !stuid.equals("");
    }
}
