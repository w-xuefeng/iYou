package cn.com.wangxuefeng.iyou.view;


import android.content.Intent;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import com.szysky.customize.siv.SImageView;

import cn.com.wangxuefeng.iyou.R;
import cn.com.wangxuefeng.iyou.bean.Media;

public class VideoPlayerActivity extends AppCompatActivity {
    Media playerMedia = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_player);
        fullscreen(true);
        Intent intent = getIntent();
        this.playerMedia = (Media) intent.getSerializableExtra("video");
        initPage(playerMedia);
    }

    private void initPage(Media mMedia){
        Toolbar toolbar = findViewById(R.id.vod_player_toolbar);
        String title = mMedia.getVideoTitle();
        toolbar.setTitle(title);
        TextView name = findViewById(R.id.vod_play_name);
        TextView timeLength = findViewById(R.id.vod_play_timelength);
        TextView viewCount = findViewById(R.id.vod_play_viewcount);
        TextView introduce = findViewById(R.id.vod_play_introduce);
        SImageView img = findViewById(R.id.vod_play_img);
        img.setImageUrls(mMedia.getCover());
        name.setText(title);
        timeLength.setText(mMedia.getTimeLength());
        viewCount.setText(mMedia.getViewCount());
        String introduction = mMedia.getIntroduction();
        if(introduction.equals("")){
            introduce.setText(R.string.vod_list_no_introduce);
        } else {
            introduce.setText("内容介绍：" + introduction);
        }
        TextView player = findViewById(R.id.vod_player);
        player.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppBarLayout appBarLayout = findViewById(R.id.vod_player_appbar);
                if(appBarLayout.getVisibility() == View.VISIBLE){
                    appBarLayout.setVisibility(View.INVISIBLE);
                }else{
                    appBarLayout.setVisibility(View.VISIBLE);
                }
            }
        });
    }


private void fullscreen(boolean enable) {
        if (enable) {
            WindowManager.LayoutParams lp = getWindow().getAttributes();
            lp.flags |= WindowManager.LayoutParams.FLAG_FULLSCREEN;
            getWindow().setAttributes(lp);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        } else {
            WindowManager.LayoutParams lp = getWindow().getAttributes();
            lp.flags &= (~WindowManager.LayoutParams.FLAG_FULLSCREEN);
            getWindow().setAttributes(lp);
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
    }
}
