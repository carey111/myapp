package com.example.administrator.myapp;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.myapp.pojo.User;
import com.example.administrator.myapp.util.NetUtil;
import com.example.administrator.myapp.widget.TitleBar;
import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class ToBeRentor extends AppCompatActivity {
    @InjectView(R.id.tb_bar)
    TitleBar tbBar;
    @InjectView(R.id.tv_title)
    TextView tvTitle;
    @InjectView(R.id.tv_shiming)
    TextView tvShiming;
    @InjectView(R.id.et_shiming)
    EditText etShiming;
    @InjectView(R.id.tv_zhengjianxinxi)
    TextView tvZhengjianxinxi;
    @InjectView(R.id.rl_shimingkuang)
    RelativeLayout rlShimingkuang;
    @InjectView(R.id.tv_shenfen)
    TextView tvShenfen;
    @InjectView(R.id.view1)
    View view1;
    @InjectView(R.id.tv_zhengfan)
    TextView tvZhengfan;
    @InjectView(R.id.iv_jiahao1)
    ImageView ivJiahao1;
    @InjectView(R.id.iv_jiahao2)
    ImageView ivJiahao2;
    @InjectView(R.id.bt_tijiao)
    Button btTijiao;
    @InjectView(R.id.rl_shenfenkuang)
    RelativeLayout rlShenfenkuang;
    private TitleBar tv_bar;
    String phoneNum1=null;
    private EditText et_shiming;
    private EditText et_shenfen;
    private Button bt_tijiao;
    String realName;
    String cardNum;
    private ImageView cardZheng;
    private ImageView cardFan;
    User user=null;
    String imgUrl1=null;
    String imgUrl2=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_be_rentor);
        ButterKnife.inject(this);

        SharedPreferences sharedPreferences =this.getSharedPreferences("SP",
                Activity.MODE_PRIVATE);
        phoneNum1 = sharedPreferences.getString("phone","");
        tv_bar = ((TitleBar) findViewById(R.id.tb_bar));
        cardZheng = ((ImageView) findViewById(R.id.iv_jiahao1));
        cardFan = ((ImageView) findViewById(R.id.iv_jiahao2));
        tv_bar.setLeftLayoutClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }

    private void initView() {
        et_shiming = ((EditText) findViewById(R.id.et_shiming));
        et_shenfen = ((EditText) findViewById(R.id.et_shenfen));
        bt_tijiao = ((Button) findViewById(R.id.bt_tijiao));

        realName=et_shiming.getText()+"";
        cardNum=et_shenfen.getText()+"";

    }
    private void initData(){
        RequestParams requestParams = new RequestParams(NetUtil.url + "toberentorservlet");
        requestParams.addQueryStringParameter("phoneNum",phoneNum1);
        requestParams.addQueryStringParameter("realName",realName);
        requestParams.addQueryStringParameter("cardNum",cardNum);
        x.http().get(requestParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                if (result.equals("true")) {
                    Toast.makeText(ToBeRentor.this, "等待审核", Toast.LENGTH_LONG).show();

                }else{

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
    private void initData1(){
        RequestParams requestParams=new RequestParams(NetUtil.url+"userinfoqueryservlet");
        requestParams.addQueryStringParameter("phoneNum",phoneNum1);
    x.http().get(requestParams, new Callback.CommonCallback<String>() {
    @Override
    public void onSuccess(String result) {
        Gson gson=new Gson();
        user=gson.fromJson(result,User.class);
        imgUrl2=user.getCardZheng();
        imgUrl1=user.getCardFan();
        Log.i( "onSuccess: ", "onSuccess: "+imgUrl1);
        Log.i( "onSuccess: ", "onSuccess: "+imgUrl2);
        x.image().bind(cardZheng,NetUtil.url+imgUrl1);
        x.image().bind(cardFan,NetUtil.url+imgUrl2);

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
    @Override
    public void onStart() {
        super.onStart();
        initData1();
    }
    @OnClick(R.id.bt_tijiao)
    public void onClick() {
        initView();
        initData();

    }
}
