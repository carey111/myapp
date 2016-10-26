package com.example.administrator.myapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.myapp.util.NetUtil;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

import static com.mob.tools.utils.R.getStringRes;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener{

    @InjectView(R.id.tv_zhanghao)
    TextView tvZhanghao;
    @InjectView(R.id.et_zhanghao)
    EditText etZhanghao;
    @InjectView(R.id.ll_aa)
    LinearLayout llAa;
    @InjectView(R.id.tv_mima)
    TextView tvMima;
    @InjectView(R.id.et_mima)
    EditText etMima;
    @InjectView(R.id.ll_bb)
    LinearLayout llBb;
    LinearLayout llCc;
    @InjectView(R.id.tv_nicheng)
    TextView tvNicheng;
    @InjectView(R.id.et_nicheng)
    EditText etNicheng;
    @InjectView(R.id.ll_dd)
    LinearLayout llDd;
    @InjectView(R.id.tv_yanzhengma)
    Button tvYanzhengma;
    @InjectView(R.id.et_yanzhengma)
    EditText etYanzhengma;
    @InjectView(R.id.ll_ee)
    LinearLayout llEe;
    @InjectView(R.id.bt_zhuce)
    Button btZhuce;
    @InjectView(R.id.ll_ff)
    LinearLayout llFf;
   // @InjectView(R.id.now)
    private TextView now;
    private EditText et_zhanghao;
    private EditText et_mima;
    private EditText et_nicheng;
    String phoneNum;
    String userPsd;
    String userName;
    private Button getCord;
    private Button saveCord;
    private String iPhone;
    private String iCord;
    private int time = 60;
    private boolean flag = true;
    private EditText yanzhengma;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_register);
        ButterKnife.inject(this);
        init();
        SMSSDK.initSDK(this, "1813abf6ec3b4", "b64897518e2cc94ec5ca5fd27bf07705");
        EventHandler eh =new EventHandler(){
            @Override
            public void afterEvent(int event, int result, Object data) {
                //super.afterEvent(i, i1, o);
                Message msg = new Message();
                msg.arg1 = event;
                msg.arg2 = result;
                msg.obj = data;
                // Handler handler=null;
                handler.sendMessage(msg);
            }
        };
        SMSSDK.registerEventHandler(eh);
    }

    private void init() {
        et_zhanghao= ((EditText) findViewById(R.id.et_zhanghao));
        et_mima=((EditText) findViewById(R.id.et_mima));
        yanzhengma = ((EditText) findViewById(R.id.et_yanzhengma));
        now=((TextView) findViewById(R.id.now));
        getCord = ((Button) findViewById(R.id.tv_yanzhengma));
        saveCord = ((Button) findViewById(R.id.bt_zhuce));
        getCord.setOnClickListener(this);
        saveCord.setOnClickListener(this);
    }

    public void initView() {
        et_zhanghao = ((EditText) findViewById(R.id.et_zhanghao));
        et_mima = ((EditText) findViewById(R.id.et_mima));
        et_nicheng = ((EditText) findViewById(R.id.et_nicheng));
        phoneNum = et_zhanghao.getText() + "";
        userPsd = et_mima.getText() + "";
        System.out.println(phoneNum);
        userName = et_nicheng.getText() + "";
    }

    public void initData() {
        RequestParams requestParams = new RequestParams(NetUtil.url + "registerinfoservlet");
        Log.i("RegisterActivity", "initData: " + "???????????????????/");
        requestParams.addQueryStringParameter("phoneNum", phoneNum);
        requestParams.addQueryStringParameter("userPsd", userPsd);
        requestParams.addQueryStringParameter("userName", userName);

        x.http().get(requestParams, new Callback.CommonCallback<String>() {

            @Override
            public void onSuccess(String result) {
                // Log.i("RegisterActivity", "initData: "+"???????????????????/");

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

    @OnClick(R.id.bt_zhuce)
    public void onClick() {

//        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
//        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_yanzhengma:
                if(!TextUtils.isEmpty(et_zhanghao.getText().toString().trim())){
                    if(et_zhanghao.getText().toString().trim().length()==11){
                        iPhone = et_zhanghao.getText().toString().trim();
                        SMSSDK.getVerificationCode("86",iPhone);
                        et_mima.requestFocus();
                        getCord.setVisibility(View.GONE);
                    }else{
                        Toast.makeText(RegisterActivity.this, "请输入完整电话号码", Toast.LENGTH_LONG).show();
                        et_zhanghao.requestFocus();
                    }
                }else{
                    Toast.makeText(RegisterActivity.this, "请输入您的电话号码", Toast.LENGTH_LONG).show();
                    et_zhanghao.requestFocus();
                }
                break;
            case R.id.bt_zhuce:
                initView();
                initData();
                Toast.makeText(getApplicationContext(),"等待审核",Toast.LENGTH_LONG).show();
                if(!TextUtils.isEmpty(yanzhengma.getText().toString().trim())){
                    if(yanzhengma.getText().toString().trim().length()==4){
                        iCord = yanzhengma.getText().toString().trim();
                        SMSSDK.submitVerificationCode("86", iPhone, iCord);
                        flag = false;
                    }else{
                        Toast.makeText(RegisterActivity.this, "请输入完整验证码", Toast.LENGTH_LONG).show();
                        yanzhengma.requestFocus();
                    }
                }else{
                    Toast.makeText(RegisterActivity.this, "请输入验证码", Toast.LENGTH_LONG).show();
                    yanzhengma.requestFocus();
                }
                break;
            default:
                break;
        }
    }
    //验证码送成功后提示文字
    private void reminderText() {
        now.setVisibility(View.VISIBLE);
        handlerText.sendEmptyMessageDelayed(1, 1000);
    }
    Handler handlerText=new Handler(){
        public  void habdleMessage(Message msg){


            if(msg.what==1){
                if(time>0){
                    now.setText("验证码已经发送"+time+"秒");
                    time--;
                    handlerText.sendEmptyMessageDelayed(1,1000);
                }else{
                    now.setText("提示信息");
                    time=60;
                    now.setVisibility(View.GONE);
                    getCord.setVisibility(View.VISIBLE);
                }

            }else{
                yanzhengma.setText("");
                now.setText("提示信息");
                time=60;
                now.setVisibility(View.GONE);
                getCord.setVisibility(View.VISIBLE);

            }


        }


    };
    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            int event = msg.arg1;
            int result = msg.arg2;
            Object data = msg.obj;
            Log.e("event", "event="+event);
            if (result == SMSSDK.RESULT_COMPLETE) {
//短信注册成功后，返回MainActivity,然后提示新好友
                if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {//提交验证码成功,验证通过
                    Toast.makeText(getApplicationContext(), "注册成功", Toast.LENGTH_SHORT).show();
                    handlerText.sendEmptyMessage(2);
                } else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE){//服务器验证码发送成功
                    reminderText();
                    Toast.makeText(getApplicationContext(), "验证码已经发送", Toast.LENGTH_SHORT).show();
                }else if (event ==SMSSDK.EVENT_GET_SUPPORTED_COUNTRIES){//返回支持发送验证码的国家列表
                    Toast.makeText(getApplicationContext(), "获取国家列表成功", Toast.LENGTH_SHORT).show();
                }
            } else {
                if(flag){
                    getCord.setVisibility(View.VISIBLE);
                    Toast.makeText(RegisterActivity.this, "验证码获取失败，请重新获取", Toast.LENGTH_SHORT).show();
                    et_zhanghao.requestFocus();
                }else{
                    ((Throwable) data).printStackTrace();
                    int resId = getStringRes(RegisterActivity.this, "smssdk_network_error");
                    Toast.makeText(RegisterActivity.this, "验证码错误", Toast.LENGTH_SHORT).show();
                    yanzhengma.selectAll();
                    if (resId > 0) {
                        Toast.makeText(RegisterActivity.this, resId, Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }
    };
    @Override
    protected void onDestroy() {
        super.onDestroy();
        SMSSDK.unregisterAllEventHandler();
    }
}
