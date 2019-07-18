package cn.com.wangxuefeng.iyou.util;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

import cn.com.wangxuefeng.iyou.R;

public class DynamicDataGet {
    public static void getDynamicData(int page, final Context context, final DynamicCallback callback) {
        OkGo.<String>get(Config.API(Config.DYNAMIC_URL)).params("page",page).tag(context).execute(new StringCallback() {
            @Override
            public void onSuccess(Response<String> response) {
                callback.handleDynamic((new DynamicParse()).parse(response.body()));
            }

            @Override
            public void onError(Response<String> response) {
                super.onError(response);
                Toast toast = Toast.makeText(context, R.string.get_intent_error, Toast.LENGTH_LONG);
                toast.setGravity(Gravity.BOTTOM, 0, 80);
                toast.show();
            }
        });
    }
}


