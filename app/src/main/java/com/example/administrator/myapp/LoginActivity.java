package com.example.administrator.myapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.myapp.pojo.User;
import com.example.administrator.myapp.util.NetUtil;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity {
    @InjectView(R.id.img_yonghuming)
    ImageView imgyonghuming;
    @InjectView(R.id.img_mima)
    ImageView imgmima;

//    @InjectView(R.id.tv_zhanghao)
//    TextView tvZhanghao;
    @InjectView(R.id.et_zhanghao)
    EditText etZhanghao;
    @InjectView(R.id.ll_aa)
    LinearLayout llAa;
//    @InjectView(R.id.tv_mima)
//    TextView tvMima;
    @InjectView(R.id.et_mima)
    EditText etMima;
    @InjectView(R.id.ll_bb)
    LinearLayout llBb;
    @InjectView(R.id.bt_zhuce)
    Button btZhuce;
    @InjectView(R.id.bt_login)
    Button btLogin;
    private EditText et_zhanghao;
    private EditText et_mima;
    String phoneNum = null;
    String userPsd = null;
    SharedPreferences sp;
    User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.inject(this);
         sp = getSharedPreferences("SP", MODE_PRIVATE);
    }
    public void initView() {
        et_zhanghao = ((EditText) findViewById(R.id.et_zhanghao));
        et_mima = ((EditText) findViewById(R.id.et_mima));
        phoneNum = et_zhanghao.getText() + "";
        userPsd = et_mima.getText() + "";
    }
    public void initData() {
        RequestParams requestParams = new RequestParams(NetUtil.url + "logininfoservlet");
        requestParams.addQueryStringParameter("phoneNum", phoneNum);
        requestParams.addQueryStringParameter("userPsd", userPsd);
        x.http().get(requestParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                if (result.equals("true")) {
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putString("phoneNum","phoneNum");
                    editor.putString("userPsd","userPsd");
                    editor.commit();
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(LoginActivity.this, "用户名或者密码错误", Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
            }
            @Override
            public void onCancelled(CancelledException cex) {
            }
            @Override
            public void onFinished() {
            }
        });
    }
    @OnClick({R.id.et_zhanghao, R.id.et_mima, R.id.bt_login})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.et_zhanghao:
                break;
            case R.id.et_mima:
                break;
            case R.id.bt_login:
                initView();
                initData();
                SharedPreferences sp = this.getSharedPreferences("SP", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sp.edit();
                editor.putString("phone",phoneNum);


                editor.commit();
                break;
        }
    }

    @OnClick(R.id.bt_zhuce)
    public void onClick() {
       Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(intent);
    }
}
