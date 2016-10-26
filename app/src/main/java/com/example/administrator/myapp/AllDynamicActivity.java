package com.example.administrator.myapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.administrator.myapp.pojo.AllDynamicBean;
import com.example.administrator.myapp.util.NetUtils;
import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

public class AllDynamicActivity extends AppCompatActivity {
    private TextView tv_userNicheng;
    private ListView lv_alldynamic;
    private BaseAdapter adapter;
    String userid;
    String userImg;
    private final List<AllDynamicBean.Dynamics> dynamicsList=new ArrayList<AllDynamicBean.Dynamics>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_dynamic);
        initData();
        lv_alldynamic = ((ListView) findViewById(R.id.lv_alldynamic));
        adapter=new BaseAdapter() {
            @Override
            public int getCount() {
                return dynamicsList.size();
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
                View view= View.inflate(AllDynamicActivity.this,R.layout.layout_alldynamic_item,null);
                ImageView iv_photoImg1=((ImageView) view.findViewById(R.id.iv_photoImg1));
                TextView tv_title1=((TextView) view.findViewById(R.id.tv_title1));
                TextView tv_content1=((TextView) view.findViewById(R.id.tv_content1));
                TextView tv_dianzancount1=((TextView) view.findViewById(R.id.tv_dianzancount1));
                ImageView iv_dynamicImg1=((ImageView) view.findViewById(R.id.iv_dynamicImg1));
                TextView tv_booleandemand=((TextView) view.findViewById(R.id.tv_booleandemand));
                AllDynamicBean.Dynamics dynamics=dynamicsList.get(position);
                tv_title1.setText(dynamics.dynamicTitle);
                tv_content1.setText(dynamics.dynamicContent);
                tv_dianzancount1.setText(dynamics.dynamicZan+"");
                if("true".equals(dynamics.hasDemand)) {
                    tv_booleandemand.setText("有");
                }else{
                    tv_booleandemand.setText("没有");
                }
                x.image().bind(iv_photoImg1, NetUtils.url+"myapp/"+userImg);
                x.image().bind(iv_dynamicImg1, NetUtils.url+"myapp/"+dynamics.dynamicImg);

                return view;

            }
        };
        //获得网络数据
        getAllDynamics();
        //向listview中添加item
        lv_alldynamic.setAdapter(adapter);
    }

    public void initData(){
        Intent intent3=getIntent();
        String username=intent3.getStringExtra("username");
        userid=intent3.getStringExtra("userid");
        userImg=intent3.getStringExtra("userimg");
        tv_userNicheng = ((TextView) findViewById(R.id.tv_userNicheng));
        tv_userNicheng.setText(username);

    }

    private void getAllDynamics(){
        RequestParams params=new RequestParams(NetUtils.url+"myapp/getalldynamicbypage");
        params.addQueryStringParameter("userId",userid);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Gson gson=new Gson();
                Log.i("result", "onSuccess: "+result);
                AllDynamicBean bean=gson.fromJson(result,AllDynamicBean.class);
                dynamicsList.addAll(bean.dynamicsList);
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
}
