package cn.com.wangxuefeng.iyou;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;
import java.util.List;

class DynamicDataGet {
    static void getDynamicData(int page, final Context context, final DynamicGetCallbackListener callbackListener) {
        String url = Config.DYNAMIC_URL + "page=" + page;
        HttpUtil.sendHttpRequest(url, new HttpCallbackListener() {
            @Override
            public void onFinish(String response) {
                DynamicParse dynamicParse = new DynamicParse();
                List<Dynamic> dynamicList = dynamicParse.parse(response);
                callbackListener.handle(dynamicList);
            }
            @Override
            public void onError(Exception e) {
                Toast toast = Toast.makeText(context, R.string.get_intent_error, Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
            }
        });
    }
}
