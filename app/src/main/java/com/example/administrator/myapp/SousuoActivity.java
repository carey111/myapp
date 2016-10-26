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

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class SousuoActivity extends AppCompatActivity {

    private ListView lv_sousuo;
    private BaseAdapter adapter;
    private List<Dongtai> newdongtailist = new ArrayList<Dongtai>();
    private String title;
    private String content;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sousuo);
        Intent intent= getIntent();
        title= intent.getStringExtra("title");
        content=intent.getStringExtra("content");
        initData();
    }

    public void initData() {
        lv_sousuo = ((ListView) findViewById(R.id.lv_sousuo));
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
                    convertView = View.inflate(SousuoActivity.this, R.layout.layout_yingyueitem, null);
                    viewHolder.iv_photo2 = ((ImageView) convertView.findViewById(R.id.iv_photo2));
                    viewHolder.tv_title1 = ((TextView) convertView.findViewById(R.id.tv_title1));
                    viewHolder.tv_name1 = ((TextView) convertView.findViewById(R.id.tv_name1));
                    viewHolder.tv_content1 = ((TextView) convertView.findViewById(R.id.tv_content1));

                    convertView.setTag(viewHolder);//缓存对象
                } else {
                    viewHolder = (ViewHolder) convertView.getTag();
                }
                Dongtai dongtai = newdongtailist.get(position);
                viewHolder.tv_title1.setText(dongtai.getTitle());
                viewHolder.tv_title1.setVisibility(View.GONE);
                viewHolder.tv_name1.setText(dongtai.getUser().getUserName());
                viewHolder.tv_content1.setText(dongtai.getContent());
                x.image().bind(viewHolder.iv_photo2, HttpUtils.url + dongtai.getUser().getPhotoImg());
                return convertView;
            }


        };
        lv_sousuo.setAdapter(adapter);
        Log.i("YingyueActivity", "getDongtailist: ================");
        getDongtailistBytitle();

    }

    private void getDongtailistBytitle() {
        RequestParams params=new RequestParams(HttpUtils.url+"GetDongtaiBytitle");
        try {
            params.addQueryStringParameter("content", URLEncoder.encode(content,"utf-8"));
            params.addQueryStringParameter("title",URLEncoder.encode(title,"utf-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Gson gson = new Gson();
                Type type = new TypeToken<List<Dongtai>>(){}.getType();
                List<Dongtai> dongtaiList= gson.fromJson(result,type);

                for (Dongtai dongtai : dongtaiList) {
                    System.out.println(dongtai.getContent()+"=="+dongtai.getUser().getUserName());
                }
                newdongtailist.addAll(dongtaiList);
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

    private static class ViewHolder{
        TextView tv_title1;
        TextView tv_name1;
        ImageView iv_photo2;
        TextView tv_content1;
    }
}