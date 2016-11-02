package com.example.administrator.myapp;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.myapp.fragment.EditInterestFragment;
import com.example.administrator.myapp.fragment.EditNameFragment;
import com.example.administrator.myapp.fragment.EditPsdFragment;
import com.example.administrator.myapp.util.NetUtil;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class EditInfoActivity extends AppCompatActivity {

    @InjectView(R.id.imageView1)
    ImageView imageView1;
    @InjectView(R.id.textView1)
    TextView textView1;
    @InjectView(R.id.save)
    Button save;
    @InjectView(R.id.relativeLayout1)
    RelativeLayout relativeLayout1;
    @InjectView(R.id.fl_info)
    FrameLayout flInfo;
    String flag;

    EditNameFragment editNameFragment;
    EditPsdFragment editPsdFragment;
    EditInterestFragment editInterestFragment;
    FragmentManager fragmentManager=null;
    String phoneNum1=null;
    String name=null;
    String psd=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_info);
        SharedPreferences sharedPreferences = this.getSharedPreferences("SP",
                Activity.MODE_PRIVATE);
        phoneNum1 = sharedPreferences.getString("phone","");
        ButterKnife.inject(this);
        editNameFragment=new EditNameFragment();
        editPsdFragment=new EditPsdFragment();
        editInterestFragment=new EditInterestFragment();
        Intent intent = getIntent();
        flag = intent.getStringExtra("flag");
        //判断flag：修改姓名，密码，兴趣
        switch (flag) {
            case "name":
                 fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.fl_info, editNameFragment).commit();

                break;
            case "psd":
               fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.fl_info, editPsdFragment).commit();
                break;
            case "interest":
                fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.fl_info, editInterestFragment).commit();
                break;

        }
    }


    @OnClick(R.id.save)
    public void onClick() {
        //判断：修改是什么信息
        switch (flag){
            case "name":
                //获取EditNameFragment中输入 框的值
                EditText etName= (EditText) editNameFragment.getView().findViewById(R.id.et_edit_name);
               name=  etName.getText().toString();
                //传值给ModifyActivity; 关闭本身的activity
                Intent intent=new Intent();
                intent.putExtra("name",name);
                Log.i("mmmmmmmmmmmmmmm", "onClick: "+name);
                setResult(RESULT_OK,intent);
                getOrderData();
                finish();
                break;
            case "psd":
                //获取EditPsdFragment中输入 框的值
                EditText etPsd=(EditText)editPsdFragment.getView().findViewById(R.id.et_edit_psd);
               psd =etPsd.getText().toString();
                Intent intent2=new Intent();
                intent2.putExtra("psd",psd);
                setResult(RESULT_OK,intent2);
                getOrderData1();
                finish();
                break;

        }

    }
    public void  getOrderData1(){
        RequestParams requestParams=new RequestParams(NetUtil.url+"userinfoupdateservlet2");
        requestParams.addQueryStringParameter("phoneNum",phoneNum1);
        requestParams.addQueryStringParameter("userPsd",psd);
x.http().get(requestParams, new Callback.CommonCallback<String>() {
    @Override
    public void onSuccess(String result) {
        Toast.makeText(EditInfoActivity.this, "修改成功", Toast.LENGTH_LONG).show();

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

    public void  getOrderData(){
        RequestParams requestParams=new RequestParams(NetUtil.url+"userinfoupdateservlet");
        requestParams.addQueryStringParameter("phoneNum",phoneNum1);
        requestParams.addQueryStringParameter("userName",name);
        Log.i("+++++++++++++++", "getOrderData: "+name);
        x.http().get(requestParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Toast.makeText(EditInfoActivity.this, "修改成功", Toast.LENGTH_LONG).show();

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
}
