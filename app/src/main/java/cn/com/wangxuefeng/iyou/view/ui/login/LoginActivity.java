package cn.com.wangxuefeng.iyou.view.ui.login;

import android.app.Activity;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import cn.com.wangxuefeng.iyou.R;
import cn.com.wangxuefeng.iyou.bean.User;
import cn.com.wangxuefeng.iyou.view.MainActivity;
import cn.com.wangxuefeng.iyou.view.data.LoginDataSource;

public class LoginActivity extends AppCompatActivity {

    public static LoginActivity instance;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        instance = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final EditText stuidEditText = findViewById(R.id.stuid);
        final EditText passwordEditText = findViewById(R.id.password);
        final Button loginButton = findViewById(R.id.login);
        final ProgressBar loadingProgressBar = findViewById(R.id.loading);

        TextWatcher afterTextChangedListener = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // ignore
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // ignore
            }

            @Override
            public void afterTextChanged(Editable s) {
                String stuid = stuidEditText.getText().toString();
                String password =  passwordEditText.getText().toString();
                boolean notEmpty = !stuid.equals("") && !password.equals("");
                loginButton.setEnabled(notEmpty);
            }
        };
        stuidEditText.addTextChangedListener(afterTextChangedListener);
        passwordEditText.addTextChangedListener(afterTextChangedListener);
        passwordEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    LoginDataSource.login(stuidEditText.getText().toString(), passwordEditText.getText().toString(), LoginActivity.this, loadingProgressBar);
                }
                return false;
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingProgressBar.setVisibility(View.VISIBLE);
                LoginDataSource.login(stuidEditText.getText().toString(), passwordEditText.getText().toString(),LoginActivity.this, loadingProgressBar);
            }
        });
    }
}
