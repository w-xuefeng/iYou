package cn.com.wangxuefeng.iyou.util;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import cn.com.wangxuefeng.iyou.bean.Dynamic;

public class DynamicViewModel extends BaseObservable {
    private Dynamic dynamic;

    public DynamicViewModel(Dynamic dynamic) {
        this.dynamic = dynamic;
    }

    public void setDynamic(Dynamic dynamic) {
        this.dynamic = dynamic;
        notifyChange();
    }

    @Bindable
    public String getContent() {
        return dynamic.getTextContent();
    }

}