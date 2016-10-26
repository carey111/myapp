package com.example.administrator.myapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;


import com.example.administrator.myapp.pojo.Dongtai;
import com.example.administrator.myapp.util.HttpUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class PersonDongtaiActivity extends AppCompatActivity {

    private ListView lv_persondongtai;
    private BaseAdapter adapter;
    private List<Dongtai>newdongtailist =new ArrayList<Dongtai>();
    private Integer userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_dongtai);

       Intent intent= getIntent();
        userId= intent.getIntExtra("userId",-1);
        Log.i("activity_person_dongtai",userId+"===================================");
        initData();
    }
    public void initData(){
        lv_persondongtai = ((ListView)findViewById(R.id.lv_persondongtai));
         adapter = new BaseAdapter() {
            @Override
            public int getCount() {
                return newdongtailist.size();
            }
            @Override
            public Object getItem(int position) {
                return null;
            }
            @Override
            public long getItemId(int position) {
                return position;
            }
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {

                ViewHolder viewHolder = null;
                if (convertView == null) {
                    viewHolder = new ViewHolder();
                    convertView = View.inflate(PersonDongtaiActivity.this, R.layout.layout_persondongtaiitem, null);
                    viewHolder.iv_photo3 = ((ImageView) convertView.findViewById(R.id.iv_photo3));
                    viewHolder.tv_title2 = ((TextView) convertView.findViewById(R.id.tv_title2));
                    viewHolder.tv_name2 = ((TextView) convertView.findViewById(R.id.tv_name2));
                    viewHolder.tv_content2 = ((TextView) convertView.findViewById(R.id.tv_content2));

                    convertView.setTag(viewHolder);//缓存对象
                } else {
                    viewHolder = (ViewHolder) convertView.getTag();
                }

                Dongtai dongtai = newdongtailist.get(position);
                Log.i("activity_person_dongtai",dongtai.getTitle()+"????????????" );
                viewHolder.tv_title2.setText(dongtai.getTitle());
                viewHolder.tv_name2.setText(dongtai.getUser().getUserName());
                viewHolder.tv_content2.setText(dongtai.getContent());
                x.image().bind(viewHolder.iv_photo3, HttpUtils.url + dongtai.getUser().getPhotoImg());
                return convertView;
            }


        };
        lv_persondongtai.setAdapter(adapter);
        Log.i("YingyueActivity", "getDongtailist: ================");
        getDongtailistByuserId();
    }



    public void initEvent(){
    }
    private static class ViewHolder{
        TextView tv_title2;
        TextView tv_name2;
        ImageView iv_photo3;
        TextView tv_content2;
    }
    private void getDongtailistByuserId() {

        RequestParams params=new RequestParams(HttpUtils.url+"GetDongtaiByuserId");


            params.addQueryStringParameter("userId",userId+"");
            Log.i("YingyueActivity","传参成功");

        x.http().get(params, new Callback.CommonCallback<String>(){
            @Override
            public void onSuccess(String result) {
                Gson gson = new Gson();
                Type type = new TypeToken<List<Dongtai>>(){}.getType();
                List<Dongtai> dongtaiList= gson.fromJson(result,type);

                for (Dongtai dongtai : dongtaiList) {
                    System.out.println(dongtai.getContent()+"=="+dongtai.getUser().getUserName());
                }
                Log.i("YingyueActivity","获参成功");
                newdongtailist.addAll(dongtaiList);
                Log.i("YingyueActivity",newdongtailist+"===========================");
                adapter.notifyDataSetChanged();
                Log.i("YingyueActivity","更新成功");
            }
            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                System.out.println(ex+"");
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
