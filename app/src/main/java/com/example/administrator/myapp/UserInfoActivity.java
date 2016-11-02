package com.example.administrator.myapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.administrator.myapp.pojo.NewestDynamicBean;
import com.example.administrator.myapp.pojo.User;
import com.example.administrator.myapp.util.DateUtil;
import com.example.administrator.myapp.util.NetUtil;
import com.example.administrator.myapp.util.NetUtils;
import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import io.rong.imkit.RongIM;

public class UserInfoActivity extends AppCompatActivity implements View.OnClickListener{
    private ImageView iv_haoyouImg;
    private TextView tv_nicheng;
    private TextView tv_sex;
    private TextView tv_birth;
    private TextView tv_hobby;


    private ListView lv_newext_dongtai;
    private BaseAdapter adapter;
    final List<NewestDynamicBean.Dynamic> dynamicList=new ArrayList<NewestDynamicBean.Dynamic>();
    private Button btn_sendmsg;
    private Button btn_moredongtai;
    String userId;
    String userName;
    String userImg;
    String interestLabel;
    String birthday;
    Boolean sex;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
        initData();

        getUserInfoByUserId();
        ((ImageView) findViewById(R.id.img_back)).setOnClickListener(this);
        btn_moredongtai = ((Button) findViewById(R.id.btn_moredongtai));
        btn_moredongtai.setOnClickListener(this);
        btn_sendmsg = ((Button) findViewById(R.id.btn_sendmsg));
        btn_sendmsg.setOnClickListener(this);
        iv_haoyouImg = ((ImageView) findViewById(R.id.iv_haoyouImg));
        tv_nicheng = ((TextView) findViewById(R.id.tv_nicheng2));
        tv_sex = ((TextView) findViewById(R.id.tv_sex));
        tv_birth = ((TextView) findViewById(R.id.tv_birth));
        tv_hobby = ((TextView) findViewById(R.id.tv_hobby));
        lv_newext_dongtai = ((ListView) findViewById(R.id.lv_newext_dongtai));
        adapter=new BaseAdapter() {
            @Override
            public int getCount() {
                return dynamicList.size();
            }

            @Override
            public Object getItem(int position) {
                return null;
            }

            @Override
            public long getItemId(int position) {
                return 0;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view= View.inflate(UserInfoActivity.this,R.layout.activity_list_newest_dongtai_item,null);
                ImageView iv_photoImg=((ImageView) view.findViewById(R.id.iv_photoImg));
                TextView tv_title=((TextView) view.findViewById(R.id.tv_title));
                TextView tv_content=((TextView) view.findViewById(R.id.tv_content));
//                TextView tv_dianzancount=((TextView) view.findViewById(R.id.tv_dianzancount));
                ImageView iv_dynamicImg=((ImageView) view.findViewById(R.id.iv_dynamicImg));
                NewestDynamicBean.Dynamic dynamic=dynamicList.get(position);
                tv_title.setText(dynamic.dynamicTitle);
                tv_content.setText(dynamic.dynamicContent);
//                tv_dianzancount.setText(dynamic.dynamicZan+"");

                x.image().bind(iv_photoImg, NetUtils.url+"myapp/"+userImg);

                x.image().bind(iv_dynamicImg, NetUtils.url+"myapp/"+dynamic.dynamicImg);
                return view;
            }
        };
        //获取数据
        getNewestDynamic();
        //向listview中添加数据
        lv_newext_dongtai.setAdapter(adapter);

        //向好友信息中添加数据



    }

    public void initData(){
        Intent intent=getIntent();
        userId=intent.getStringExtra("userId");
    }


    private void getUserInfoByUserId(){
        RequestParams params=new RequestParams(NetUtil.url+"getuserinfobyuserid");
        params.addQueryStringParameter("userId",userId);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Gson gson=new Gson();
                User user=gson.fromJson(result,User.class);
//                Log.i("UserInfoActivity", "onSuccess: "+user);
                userImg=user.getPhotoImg();
                userName=user.getUserName();
                interestLabel= user.getInterestLabel();
                birthday= DateUtil.dateToString(user.getBirthday());
//                System.out.println(birthday);
                x.image().bind(iv_haoyouImg,NetUtils.url+"myapp/"+userImg);
                tv_birth.setText(birthday);
                tv_hobby.setText(interestLabel);
                tv_nicheng.setText(userName);
                if(user.getSex()== true){
                    tv_sex.setText("男");
                }else {
                    tv_sex.setText("女");
                }
            }
            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.i("FriendinfoActivity", "onError: "+ex);
            }
            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

    private void getNewestDynamic(){
        RequestParams params=new RequestParams(NetUtils.url+"myapp/getnewestdynamicbypage");
        params.addQueryStringParameter("userId",userId);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Gson gson = new Gson();
                NewestDynamicBean bean=gson.fromJson(result, NewestDynamicBean.class);
                dynamicList.addAll(bean.dynamicList);
                //通知listview更新界面
                adapter.notifyDataSetChanged();
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
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_moredongtai:
                Intent intent2=new Intent(this,AllDynamicActivity.class);
                intent2.putExtra("username",userName);
                intent2.putExtra("userid",userId);
                intent2.putExtra("userimg",userImg);
                startActivity(intent2);
                break;
            case R.id.btn_sendmsg:
                finish();
                break;
            case R.id.img_back:
                finish();
                break;
        }
    }
}
