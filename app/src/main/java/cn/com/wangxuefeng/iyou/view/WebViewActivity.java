package cn.com.wangxuefeng.iyou.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import cn.com.wangxuefeng.iyou.R;

public class WebViewActivity extends AppCompatActivity {
    public static final String EXTRA_HTML = "EXTRA_HTML";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_web_view);
        String tableHtml = getIntent().getStringExtra(EXTRA_HTML);
        WebView webView = findViewById(R.id.web_view);
        if(tableHtml.substring(0,4).equals("http")){
            webView.setWebViewClient(new WebViewClient());
            webView.loadUrl(tableHtml);
        } else {
            webView.loadData(tableHtml, "text/html", "UTF-8");
        }
    }

}
