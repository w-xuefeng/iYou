package cn.com.wangxuefeng.iyou.view;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.method.LinkMovementMethod;
import android.text.method.ScrollingMovementMethod;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.scwang.smartrefresh.header.DeliveryHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.footer.BallPulseFooter;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.szysky.customize.siv.SImageView;

import org.sufficientlysecure.htmltextview.ClickableTableSpan;
import org.sufficientlysecure.htmltextview.DrawTableLinkSpan;
import org.sufficientlysecure.htmltextview.HtmlHttpImageGetter;
import org.sufficientlysecure.htmltextview.HtmlTextView;

import java.util.ArrayList;
import java.util.List;

import cn.com.wangxuefeng.iyou.R;
import cn.com.wangxuefeng.iyou.adapter.QuickAdapter;
import cn.com.wangxuefeng.iyou.bean.Dynamic;
import cn.com.wangxuefeng.iyou.bean.User;
import cn.com.wangxuefeng.iyou.util.DynamicCallback;
import cn.com.wangxuefeng.iyou.util.DynamicDataGet;
import cn.com.wangxuefeng.iyou.util.GetDataCallback;

import static cn.com.wangxuefeng.iyou.view.WebViewActivity.EXTRA_HTML;

/**
 * A placeholder fragment containing a simple view.
 */
public class DynamicActivityFragment extends Fragment {
    private List<Dynamic> mDynamicList = new ArrayList<>();
    private QuickAdapter mAdapter;

    public DynamicActivityFragment() {

    }
    class ClickableTableSpanImpl extends ClickableTableSpan {
        @Override
        public ClickableTableSpan newInstance() {
            return new ClickableTableSpanImpl();
        }

        @Override
        public void onClick(View widget) {
            Intent intent = new Intent(getActivity(), WebViewActivity.class);
            intent.putExtra(EXTRA_HTML, getTableHtml());
            startActivity(intent);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View mView =  inflater.inflate(R.layout.fragment_dynamic, container, false);
        initRecyclerView(mView, 0);
        RefreshLayout refreshLayout = mView.findViewById(R.id.refreshLayout);
        refreshLayout.setRefreshHeader(new DeliveryHeader(mView.getContext()));
        refreshLayout.setRefreshFooter(new BallPulseFooter(mView.getContext()).setSpinnerStyle(SpinnerStyle.Scale));
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(final RefreshLayout refreshlayout) {
                mDynamicList.clear();
                getDynamicData(0, mView, new GetDataCallback() {
                    @Override
                    public void dataFinished(List data) {
                        Toast.makeText(mView.getContext(), R.string.refresh_finish, Toast.LENGTH_SHORT).show();
                        refreshlayout.finishRefresh(2000/*,false*/);//传入false表示刷新失败
                    }
                });
            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(final RefreshLayout refreshlayout) {
                getDynamicData(mDynamicList.size(), mView, new GetDataCallback() {
                    @Override
                    public void dataFinished(List data) {
                        if(data.size() == 0){
                            Toast.makeText(mView.getContext(), R.string.not_any_more, Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(mView.getContext(), R.string.load_finished, Toast.LENGTH_SHORT).show();
                        }
                        refreshlayout.finishLoadMore(2000/*,false*/);//传入false表示加载失败
                    }
                });
            }
        });
        return mView;
    }

    private void initRecyclerView (View mView, int page) {
        final RecyclerView mRecyclerView = mView.findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(mView.getContext(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(layoutManager);
        mAdapter = new QuickAdapter<Dynamic>(mDynamicList) {
            @Override
            public int getLayoutId(int viewType) {
                return R.layout.list_item_dynamic;
            }

            @Override
            public void convert(VH holder, Dynamic data, int position) {
                SImageView head = holder.getView(R.id.publish_head);
                TextView pub_name = holder.getView(R.id.publish_name);
                TextView pub_time = holder.getView(R.id.publish_time);
                HtmlTextView dynamicContent = holder.getView(R.id.dynamic_content);
                TextView view_count = holder.getView(R.id.dynamic_view_count);
                TextView liker = holder.getView(R.id.dynamic_liker);

                head.setErrPicResID(R.drawable.default_head).setLoadingResID(R.drawable.default_head).setImageUrls(data.getAuthor().getHead());
                pub_name.setText(data.getAuthor().getName());
                pub_time.setText(data.getPublishTime());
                view_count.setText(getHotViewCountString(data.getViewCount()));
                liker.setText(getLikerPeople(data.getLiker()));

                dynamicContent.setClickableTableSpan(new ClickableTableSpanImpl());
                DrawTableLinkSpan drawTableLinkSpan = new DrawTableLinkSpan();
                drawTableLinkSpan.setTableLinkText("[tap for table]");
                dynamicContent.setDrawTableLinkSpan(drawTableLinkSpan);

                // Best to use indentation that matches screen density.
                DisplayMetrics metrics = new DisplayMetrics();
                getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);
                dynamicContent.setListIndentPx(metrics.density * 10);
                dynamicContent.setMovementMethod(ScrollingMovementMethod.getInstance()); // 设置可滚动
                dynamicContent.setMovementMethod(LinkMovementMethod.getInstance()); //设置超链接可以打开网页
                dynamicContent.setHtml(data.getTextContent(), new HtmlHttpImageGetter(dynamicContent, null, true));
            }
        };
        getDynamicData(page, mView, null);
        mRecyclerView.setAdapter(mAdapter);
    }

    public String getHotViewCountString (long viewCount) {
        return viewCount < 100 ? ("浏览 " + viewCount + "次") : ("热度 " + viewCount + "℃");
    }

    public String getLikerPeople (List<User> liker) {
        StringBuilder a = new StringBuilder();
        String temp;
        for(int i = 0; i < liker.size(); i++){
            temp = liker.get(i).getName() + (i == liker.size() - 1 ? "觉得很赞" : "，");
            a.append(temp);
        }
        return a.toString();
    }

    public void getDynamicData(int page, View mView, final GetDataCallback callback) {
        DynamicDataGet.getDynamicData(page, mView.getContext(), new DynamicCallback() {
            @Override
            public void handleDynamic(List<Dynamic> e) {
                mDynamicList.addAll(e);
                mAdapter.notifyDataSetChanged();
                if(callback != null){
                    callback.dataFinished(e);
                }
            }
        });
    }
}
