package cn.com.wangxuefeng.iyou.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import cn.com.wangxuefeng.iyou.R;

public class WebViewActivity extends AppCompatActivity {
    public static final String EXTRA_HTML = "EXTRA_HTML";

    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        String tableHtml = getIntent().getStringExtra(EXTRA_HTML);
        webView = findViewById(R.id.web_view);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setAllowFileAccess(true);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webSettings.setLoadsImagesAutomatically(true);
        webSettings.setDefaultTextEncodingName("utf-8");
        if(tableHtml.substring(0,4).equals("http")){
            webView.setWebViewClient(new WebViewClient());
            webView.loadUrl(tableHtml);
        } else {
            webView.loadData(tableHtml, "text/html", "UTF-8");
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
    }

    @Override
    protected void onStop() {
        super.onStop();
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(false);
    }
}
