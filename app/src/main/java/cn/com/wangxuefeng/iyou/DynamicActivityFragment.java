package cn.com.wangxuefeng.iyou;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.szysky.customize.siv.SImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class DynamicActivityFragment extends Fragment {

    private int mPage = 0;
    private List<Dynamic> mDynamicList = new ArrayList<>();

    public DynamicActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View mView =  inflater.inflate(R.layout.fragment_dynamic, container, false);
        initRecyclerView(mView);
        Log.i("name", "数据大小 = " + mDynamicList.size());
        return mView;
    }

    private void initRecyclerView (View mView) {
        final RecyclerView mRecyclerView = mView.findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(mView.getContext(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(layoutManager);
        DynamicDataGet.getDynamicData(this.mPage, mView.getContext(), new DynamicCallback() {
            @Override
            public void handleDynamic(List<Dynamic> e) {
                QuickAdapter mAdapter = new QuickAdapter<Dynamic>(e) {
                    @Override
                    public int getLayoutId(int viewType) {
                        return R.layout.list_item_dynamic;
                    }

                    @Override
                    public void convert(VH holder, Dynamic data, int position) {
                        SImageView head = holder.getView(R.id.publish_head);
                        TextView pub_name = holder.getView(R.id.publish_name);
                        TextView pub_time = holder.getView(R.id.publish_time);
                        TextView dynamicContent = holder.getView(R.id.dynamic_content);
                        TextView view_count = holder.getView(R.id.dynamic_view_count);
                        TextView liker = holder.getView(R.id.dynamic_liker);

                        head.setImageUrls(data.getAuthor().getHead());
                        pub_name.setText(data.getAuthor().getName());
                        pub_time.setText(data.getPublishTime());
                        dynamicContent.setText(Html.fromHtml(data.getTextContent()));
                        view_count.setText(getHotViewCountString(data.getViewCount()));
                        liker.setText(getLikerPeople(data.getLiker()));
                    }
                };
                mRecyclerView.setAdapter(mAdapter);
            }
        });
    }
    public String getHotViewCountString (long viewCount) {
        return viewCount < 100 ? ("浏览 " + viewCount + "次") : ("热度 " + viewCount + "℃");
    }

    public String getLikerPeople (List<User> liker) {
        StringBuilder a = new StringBuilder();
        String temp;
        for(int i = 0; i < liker.size(); i++){
            temp = liker.get(i).getName() + (i == liker.size() - 1 ? "" : ",");
            a.append(temp);
        }
        return a.toString();
    }

    public void getDynamic() {
        DynamicDataGet.getDynamicData(this.mPage, getActivity(), new DynamicCallback() {
            @Override
            public void handleDynamic(List<Dynamic> e) {
                mDynamicList.addAll(e);
            }
        });
    }
}
