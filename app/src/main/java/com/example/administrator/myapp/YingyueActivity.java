package com.example.administrator.myapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
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

public class YingyueActivity extends AppCompatActivity {
    private View view;
    private ListView lv_yingyue;
    private BaseAdapter adapter;
    private List<Dongtai>newdongtailist =new ArrayList<Dongtai>();
    private TextView tv_search;
    private ImageButton search_clear;
    private EditText et_query;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yingyue);
        initData();
        initEvent();
    }
    public void initView(){
    }
    public void initData(){
        lv_yingyue = ((ListView)findViewById(R.id.lv_yingyue));
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
                if(convertView==null) {
                    viewHolder = new ViewHolder();
                    convertView = View.inflate(YingyueActivity.this, R.layout.layout_yingyueitem, null);
                    viewHolder.iv_photo2= ((ImageView) convertView.findViewById(R.id.iv_photo2));
                    viewHolder.tv_title1 = ((TextView) convertView.findViewById(R.id.tv_title1));
                    viewHolder.tv_name1 = ((TextView) convertView.findViewById(R.id.tv_name1));
                    viewHolder.tv_content1= ((TextView)  convertView.findViewById(R.id.tv_content1));

                    convertView.setTag(viewHolder);//缓存对象
                }else{
                    viewHolder = (ViewHolder)convertView.getTag();
                }
                Dongtai dongtai= newdongtailist.get(position);
                viewHolder.tv_title1.setText(dongtai.getTitle());
                viewHolder.tv_title1.setVisibility(View.GONE);
                viewHolder.tv_name1.setText(dongtai.getUser().getUserName());
                viewHolder.tv_content1.setText(dongtai.getContent());
                x.image().bind(viewHolder.iv_photo2, HttpUtils.url+dongtai.getUser().getPhotoImg());
                return convertView;
            }


           };
        lv_yingyue.setAdapter(adapter);
        Log.i("YingyueActivity", "getDongtailist: ================");
        getDongtailistBytitle();


    }



    public void initEvent(){
        et_query = ((EditText) findViewById(R.id.query));
        tv_search = ((TextView) findViewById(R.id.tv_search));
        tv_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               String content= et_query.getText().toString();
                Intent intent = new Intent(YingyueActivity.this, SousuoActivity.class);
                intent.putExtra("title","音乐");
                intent.putExtra("content",content);
                startActivity(intent);

            }
        });
        search_clear = ((ImageButton) findViewById(R.id.search_clear));
        search_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et_query.getText().clear();
            }
        });

    }
    private static class ViewHolder{
        TextView tv_title1;
        TextView tv_name1;
        ImageView iv_photo2;
        TextView tv_content1;
    }
    private void getDongtailistBytitle() {

        RequestParams params=new RequestParams(HttpUtils.url+"GetDongtaiBytitle");

        try {
            params.addQueryStringParameter("title",URLEncoder.encode("音乐","utf-8"));
            Log.i("YingyueActivity","传参成功");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
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
