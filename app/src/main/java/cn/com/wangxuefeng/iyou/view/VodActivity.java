package cn.com.wangxuefeng.iyou.view;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.scwang.smartrefresh.header.DeliveryHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.footer.BallPulseFooter;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.szysky.customize.siv.ImageLoader;
import com.szysky.customize.siv.SImageView;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.com.wangxuefeng.iyou.R;
import cn.com.wangxuefeng.iyou.adapter.QuickAdapter;
import cn.com.wangxuefeng.iyou.bean.Media;
import cn.com.wangxuefeng.iyou.util.GetLastVideoPid;
import cn.com.wangxuefeng.iyou.util.GetMediaInfor;
import cn.com.wangxuefeng.iyou.util.IEventHandler;
import cn.com.wangxuefeng.iyou.util.VODConfig;

public class VodActivity extends AppCompatActivity {

    private List<Media> mVodMedia = new ArrayList<>();
    private QuickAdapter mAdapter;
    private long lastVideoPid = 0;
    private int pageCount = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vod);
        isWifi();
        GetLastVideoPid getPid = new GetLastVideoPid(this);
        RefreshLayout refreshLayout = VodActivity.this.findViewById(R.id.refreshLayout_vod);
        refreshLayout.setRefreshHeader(new DeliveryHeader(VodActivity.this));
        refreshLayout.setRefreshFooter(new BallPulseFooter(VodActivity.this).setSpinnerStyle(SpinnerStyle.Scale));
        refreshLayout.setEnableLoadMoreWhenContentNotFull(false);
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(final RefreshLayout refreshlayout) {
                mVodMedia.clear();
                getData(lastVideoPid, pageCount);
                refreshlayout.finishRefresh(0/*,false*/);
            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(final RefreshLayout refreshlayout) {
                getData(lastVideoPid - mVodMedia.size(), pageCount);
                refreshlayout.finishLoadMore(0/*,false*/);
            }
        });
        getPid.on("error", new IEventHandler() {
            @Override
            public void handleEvent(List<Object> params) {
                onError(params);
            }
        }).on("success", new IEventHandler() {
            @Override
            public void handleEvent(List<Object> params) {
                lastVideoPid = (long) params.get(1);
                initRecyclerView();
            }
        }).execute();
    }

    private void initRecyclerView () {
        final RecyclerView mRecyclerView = findViewById(R.id.recycler_view_vod);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(layoutManager);
        mAdapter = new QuickAdapter<Media>(mVodMedia) {
            @Override
            public int getLayoutId(int viewType) {
                return R.layout.list_item_vod;
            }

            @Override
            public void convert(VH holder, final Media data, int position) {
                TextView name = holder.getView(R.id.vod_name);
                TextView timeLength = holder.getView(R.id.vod_timelength);
                TextView viewCount = holder.getView(R.id.vod_viewcount);
                TextView introduce = holder.getView(R.id.vod_introduce);
                SImageView vodImg = holder.getView(R.id.vod_img);
                CardView cardView = holder.getView(R.id.vod_card);
                name.setText(data.getVideoTitle());
                timeLength.setText("时长：" + data.getTimeLength());
                viewCount.setText("播放量：" + data.getViewCount());
                ImageLoader.getInstance(getApplicationContext()).setPicUrlRegex("https?://.*?");
                vodImg
//                        .setErrPicResID(R.drawable.error_vod)
//                        .setLoadingResID(R.drawable.default_vod)
                        .setImageUrls(data.getCover());
                String introduction = data.getIntroduction();
                if(introduction.equals("")){
                    introduce.setText(R.string.vod_list_no_introduce);
                } else if (introduction.length() > 30){
                    String introductionSub = introduction.substring(0, 30) + getString(R.string.more);
                    introduce.setText("内容介绍：" + introductionSub);
                } else {
                    introduce.setText("内容介绍：" + introduction);
                }
                DisplayMetrics metrics = new DisplayMetrics();
                VodActivity.this.getWindowManager().getDefaultDisplay().getMetrics(metrics);
                cardView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent();
                        intent.setClass(VodActivity.this, VideoPlayerActivity.class);
                        intent.putExtra("video", data);
                        startActivity(intent);
                    }
                });
            }
        };
        getData(lastVideoPid, pageCount);
        mRecyclerView.setAdapter(mAdapter);
    }


    public void onSuccess(List<Object> params) {
        for(int i = 0; i < params.size(); i++){
            mVodMedia.add((Media) params.get(i));
        }
        mAdapter.notifyDataSetChanged();
    }

    public void onError(List<Object> params) {
        Log.e("Error", params.get(1).toString());
        String data = (String) params.get(1);
//        Toast.makeText(VodActivity.this, data, Toast.LENGTH_SHORT).show();
    }

  public void getData(long startPid, int pagecount) {
        if(startPid <= 0) startPid = 1;
        if(startPid > lastVideoPid) startPid = lastVideoPid;
        Long []pid = new Long[pagecount];
        for (int i = 0; i < pagecount; i++){
          pid[i] = startPid - i;
        }
        Map<String, String> header = new HashMap<>();
        header.put("Host", VODConfig.IPAddress);
        getVodData(pid, header);
  }


    public void getVodData(Object []pid, Map<String, String> header) {
        GetMediaInfor get = new GetMediaInfor(VodActivity.this);
        get.on("success", new IEventHandler() {
            @Override
            public void handleEvent(List<Object> params) {
                onSuccess(params);
            }
        }).on("error", new IEventHandler() {
            @Override
            public void handleEvent(List<Object> params) {
                onError(params);
            }
        }).header(header).execute(pid);
    }

    private void isWifi(){
        Context myContext = this;
        WifiManager wifiMgr = (WifiManager) myContext.getSystemService(Context.WIFI_SERVICE);
        if (isWifiEnabled()) {
            int wifiState = wifiMgr.getWifiState();
            WifiInfo info = wifiMgr.getConnectionInfo();
            String wifiId = info != null ? info.getSSID() : null;
            Log.i("WIFI", wifiId);
            if(!VODConfig.isWifi(wifiId)){
                Toast.makeText(VodActivity.this, R.string.err_vod_wifi, Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(VodActivity.this, R.string.err_vod_wifi, Toast.LENGTH_LONG).show();
        }
    }

    private boolean isWifiEnabled() {
        Context myContext = this;
        WifiManager wifiMgr = (WifiManager) myContext.getSystemService(Context.WIFI_SERVICE);
        if (wifiMgr.getWifiState() == WifiManager.WIFI_STATE_ENABLED) {
            ConnectivityManager connManager = (ConnectivityManager) myContext
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo wifiInfo = connManager
                    .getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            return wifiInfo.isConnected();
        } else {
            return false;
        }
    }
}
