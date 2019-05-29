package cn.com.wangxuefeng.iyou.view;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
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

    private String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
    private AlertDialog dialog;

    private List<Media> mVodMedia = new ArrayList<>();
    private QuickAdapter mAdapter;
    private long lastVideoPid = 0;
    private int pageCount = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vod);
        applyPower();
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


    public void getVodData(Long []pid, Map<String, String> header) {
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



    private void applyPower(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // 检查该权限是否已经获取
            int i = ContextCompat.checkSelfPermission(this, permissions[0]);
            // 权限是否已经 授权 GRANTED---授权  DINIED---拒绝
            if (i != PackageManager.PERMISSION_GRANTED) {
                // 如果没有授予该权限，就去提示用户请求
                showDialogTipUserRequestPermission();
            }
        }
    }

    private void showDialogTipUserRequestPermission() {

        new AlertDialog.Builder(this)
                .setTitle("存储权限不可用")
                .setMessage("由于iYou需要获取存储空间，为你存储个人信息；\n否则，您将无法正常使用iYou")
                .setPositiveButton("立即开启", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startRequestPermission();
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                }).setCancelable(false).show();
    }

    // 开始提交请求权限
    private void startRequestPermission() {
        ActivityCompat.requestPermissions(this, permissions, 321);
    }

    // 用户权限 申请 的回调方法
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 321) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    // 判断用户是否 点击了不再提醒。(检测该权限是否还可以申请)
                    boolean b = shouldShowRequestPermissionRationale(permissions[0]);
                    if (!b) {
                        // 用户还是想用我的 APP 的
                        // 提示用户去应用设置界面手动开启权限
                        showDialogTipUserGoToAppSettting();
                    } else
                        finish();
                } else {
                    Toast.makeText(this, "权限获取成功", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    // 提示用户去应用设置界面手动开启权限

    private void showDialogTipUserGoToAppSettting() {

        dialog = new AlertDialog.Builder(this)
                .setTitle("存储权限不可用")
                .setMessage("请在-应用设置-权限-中，允许iYou使用存储权限来保存用户数据")
                .setPositiveButton("立即开启", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // 跳转到应用设置界面
                        goToAppSetting();
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                }).setCancelable(false).show();
    }

    // 跳转到当前应用的设置界面
    private void goToAppSetting() {
        Intent intent = new Intent();

        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", getPackageName(), null);
        intent.setData(uri);

        startActivityForResult(intent, 123);
    }

    //
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 123) {

            if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                // 检查该权限是否已经获取
                int i = ContextCompat.checkSelfPermission(this, permissions[0]);
                // 权限是否已经 授权 GRANTED---授权  DINIED---拒绝
                if (i != PackageManager.PERMISSION_GRANTED) {
                    // 提示用户应该去应用设置界面手动开启权限
                    showDialogTipUserGoToAppSettting();
                } else {
                    if (dialog != null && dialog.isShowing()) {
                        dialog.dismiss();
                    }
                    Toast.makeText(this, "权限获取成功", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}
