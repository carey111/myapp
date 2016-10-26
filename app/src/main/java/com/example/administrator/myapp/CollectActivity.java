package com.example.administrator.myapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.administrator.myapp.pojo.RentInfoBean;
import com.example.administrator.myapp.widget.TitleBar;
import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;

public class CollectActivity extends AppCompatActivity {
    private static final String TAG = "MyActivity";
    private ListView lv_rentinfo;
    final ArrayList<RentInfoBean.RentInfo> rentInfoList = new ArrayList<RentInfoBean.RentInfo>();
    private BaseAdapter adapter;
    private TitleBar tv_bar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collect);
        tv_bar = ((TitleBar) findViewById(R.id.tb_bar));
        tv_bar.setLeftLayoutClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        lv_rentinfo = ((ListView) findViewById(R.id.lv_rentinfo));
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
                Log.i(TAG, "加载listview item position:" + position);
                //打气筒
                View view = View.inflate(CollectActivity.this, R.layout.shoucangitemlayout, null);
                RentInfoBean.RentInfo rentInfo = rentInfoList.get(position);
                TextView tv_type = ((TextView) view.findViewById(R.id.tv_type));
                TextView tv_area = ((TextView) view.findViewById(R.id.tv_area));
                TextView tv_zujin = ((TextView) view.findViewById(R.id.tv_zujin));
                iv_photo = ((ImageView) view. findViewById(R.id.iv_photo));
                //Log.i(TAG, "getView:?????????????????????????? ");
                tv_type.setText(rentInfo.roomType);

                tv_area.setText(rentInfo.roomArea);
                tv_zujin.setText((rentInfo.roomRent).toString());
                x.image().bind(iv_photo ,"http://10.0.2.2:8080/"+rentInfo.photoImg);

                return view;
            }
        };
        lv_rentinfo.setAdapter(adapter);

//        //从服务器拿
        getRentInfo();
    }
    private void getRentInfo() {
        RequestParams params = new RequestParams("http://10.0.2.2:8080/myapp/getcollectsbypage");
        //System.out.println("==================?????????????");
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                System.out.println("=================="+result);
                Gson gson=new Gson();
                RentInfoBean bean= gson.fromJson(result, RentInfoBean.class);
                System.out.println("=================="+result);
                rentInfoList.addAll(bean.rentInfoList);
                System.out.println(rentInfoList);
                //通知listview更新界面
                adapter.notifyDataSetChanged();
            }
            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                System.out.println(ex.toString());
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

