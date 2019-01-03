package cn.com.wangxuefeng.iyou;

public interface HttpCallbackListener {
    void onFinish(String response);
    void onError(Exception e);
}
