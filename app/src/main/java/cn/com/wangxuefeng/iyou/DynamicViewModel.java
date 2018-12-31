package cn.com.wangxuefeng.iyou;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

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