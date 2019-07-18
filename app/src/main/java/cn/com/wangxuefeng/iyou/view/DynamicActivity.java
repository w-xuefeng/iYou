package cn.com.wangxuefeng.iyou.view;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import cn.com.wangxuefeng.iyou.R;

public class DynamicActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dynamic);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 Snackbar.make(view, "(ง •_•)ง 正在拼命开发中...", Snackbar.LENGTH_LONG).setAction("Action", null).show();
            }
        });
    }
}