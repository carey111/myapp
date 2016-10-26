package com.example.administrator.myapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;


import com.example.administrator.myapp.pojo.RentInfo;

import com.example.administrator.myapp.widget.TitleBar;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class RentorActivity extends AppCompatActivity {

    private TitleBar tv_bar;
    private ListView lv_fangyuan;
    private BaseAdapter adapter;
  List<RentInfo> rentInfoList = new ArrayList<RentInfo>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rentor);
        tv_bar = ((TitleBar) findViewById(R.id.tb_bar));
        tv_bar.setLeftLayoutClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        lv_fangyuan = ((ListView) findViewById(R.id.lv_fangyuan));
        adapter=new BaseAdapter() {
            private ImageView iv_photo;
            @Override
            public int getCount() {
                return rentInfoList.size();
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
                View view =   View.inflate(RentorActivity.this,R.layout.fangyuan_item,null);
                RentInfo rentInfo = rentInfoList.get(position);
                TextView tv_type = ((TextView) view.findViewById(R.id.tv_type));
                TextView tv_area = ((TextView) view.findViewById(R.id.tv_area));
                TextView tv_zujin = ((TextView) view.findViewById(R.id.tv_zujin));
                iv_photo = ((ImageView) view. findViewById(R.id.iv_photo));
                tv_type.setText(rentInfo.getRoomType());
                tv_area.setText(rentInfo.getRoomArea()+"");
                tv_zujin.setText((rentInfo.getRoomRent()).toString());
                x.image().bind(iv_photo ,"http://10.0.2.2:8080/"+rentInfo.getPhotoImg());
                return view;
            }
        };
        lv_fangyuan.setAdapter(adapter);
        //从服务器拿
        getRentInfo();
    }
    private void getRentInfo() {

        RequestParams params = new RequestParams("http://10.0.2.2:8080/myapp/rentinfoqueryservlet");
           x.http().get(params, new Callback.CommonCallback<String>() {
    @Override
    public void onSuccess(String result) {
        Gson gson=new Gson();
        System.out.println("=================="+result);
        Type type=new TypeToken<List<RentInfo>>(){}.getType();

        rentInfoList= gson.fromJson(result, type);

//        System.out.println("=================="+bean);
//        rentInfoList.addAll(bean);
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
