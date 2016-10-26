package com.example.administrator.myapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.administrator.myapp.pojo.NewestDynamicBean;
import com.example.administrator.myapp.util.NetUtils;
import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import io.rong.imkit.RongIM;

public class FriendinfoActivity extends AppCompatActivity implements View.OnClickListener{
    private TextView tv_friendName;
    private ImageView iv_haoyouImg;
    private TextView tv_nicheng;
    private TextView tv_sex;
    private TextView tv_birth;
    private TextView tv_hobby;
    private Button btn_moredongtai;
    String userId;
    String userName;
    String userImg;
    private ListView lv_newext_dongtai;
    private BaseAdapter adapter;
    final List<NewestDynamicBean.Dynamic> dynamicList=new ArrayList<NewestDynamicBean.Dynamic>();
    private Button btn_sendmsg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friendinfo);
        initData();
        btn_moredongtai = ((Button) findViewById(R.id.btn_moredongtai));
        btn_moredongtai.setOnClickListener(this);
        btn_sendmsg = ((Button) findViewById(R.id.btn_sendmsg));
        btn_sendmsg.setOnClickListener(this);
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
                View view= View.inflate(FriendinfoActivity.this,R.layout.activity_list_newest_dongtai_item,null);
                ImageView iv_photoImg=((ImageView) view.findViewById(R.id.iv_photoImg));
                TextView tv_title=((TextView) view.findViewById(R.id.tv_title));
                TextView tv_content=((TextView) view.findViewById(R.id.tv_content));
                TextView tv_dianzancount=((TextView) view.findViewById(R.id.tv_dianzancount));
                ImageView iv_dynamicImg=((ImageView) view.findViewById(R.id.iv_dynamicImg));
                NewestDynamicBean.Dynamic dynamic=dynamicList.get(position);
                tv_title.setText(dynamic.dynamicTitle);
                tv_content.setText(dynamic.dynamicContent);
                tv_dianzancount.setText(dynamic.dynamicZan+"");
                x.image().bind(iv_photoImg, NetUtils.url+"myapp/"+userImg);
                x.image().bind(iv_dynamicImg, NetUtils.url+"myapp/"+dynamic.dynamicImg);
                return view;
            }
        };
        //获取数据
        getNewestDynamic();
        //向listview中添加数据
        lv_newext_dongtai.setAdapter(adapter);
    }
    public void initData(){
        Intent intent1=getIntent();
        userId=intent1.getStringExtra("userId");
        userName=intent1.getStringExtra("userName");
        userImg=intent1.getStringExtra("userImg");
        String sex=intent1.getStringExtra("sex");
        String birthday=intent1.getStringExtra("birthday");
        String interestLabel=intent1.getStringExtra("interestLabel");

        iv_haoyouImg = ((ImageView) findViewById(R.id.iv_haoyouImg));
        x.image().bind(iv_haoyouImg,NetUtils.url+"myapp/"+userImg);
        tv_nicheng = ((TextView) findViewById(R.id.tv_nicheng2));
        tv_nicheng.setText(userName);
        tv_sex = ((TextView) findViewById(R.id.tv_sex));
        if(sex.equals("1")){
            tv_sex.setText("男");
        }else{
            tv_sex.setText("女");
        }
        tv_birth = ((TextView) findViewById(R.id.tv_birth));
        tv_birth.setText(birthday);
        tv_hobby = ((TextView) findViewById(R.id.tv_hobby));
        tv_hobby.setText(interestLabel);



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
                if (RongIM.getInstance()!=null){
                    RongIM.getInstance().startPrivateChat(this,"2","私人聊天");
                }
                break;
        }
    }
}
