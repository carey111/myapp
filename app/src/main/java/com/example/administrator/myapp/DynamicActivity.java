package com.example.administrator.myapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.myapp.pojo.DynamicInfoBean;
import com.example.administrator.myapp.widget.TitleBar;
import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;

public class DynamicActivity extends AppCompatActivity implements View.OnClickListener{
    private static final String TAG = "MyActivity";
    private ListView lv_dynamicinfo;
    private ImageView iv_shuaxin;
    final ArrayList<DynamicInfoBean.DynamicInfo> dynamicInfoList = new ArrayList<DynamicInfoBean.DynamicInfo>();
    private BaseAdapter adapter;
    float mPosX= 0;
    float mPosY = 0;
    float mCurPosX = 0;
    float mCurPosY = 0;
    private TitleBar tv_bar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dynamic);
        tv_bar = ((TitleBar) findViewById(R.id.tb_bar));
        tv_bar.setLeftLayoutClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        lv_dynamicinfo = ((ListView) findViewById(R.id.lv_dynamicinfo));
        ImageView view=new ImageView(DynamicActivity.this);
        //view.setImageResource(R.drawable.adv2);
        iv_shuaxin = ((ImageView)findViewById(R.id.iv_shuaxin));
        iv_shuaxin.setOnClickListener(this);
        iv_shuaxin.setVisibility(View.GONE);
        view.setBackgroundResource(R.drawable.adv2);
        lv_dynamicinfo.addHeaderView(view);
          setGestureListener();
        adapter=new BaseAdapter() {
            private TextView tv_title;
            private ImageView iv_photo;
            @Override
            public int getCount() {
                return dynamicInfoList.size();
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
                View view = View.inflate(DynamicActivity.this, R.layout.dongtaiitemlayout, null);
                DynamicInfoBean.DynamicInfo dynamicInfo = dynamicInfoList.get(position);
                TextView  tv_title = ((TextView) view.findViewById(R.id.tv_title));
                TextView tv_content = ((TextView) view.findViewById(R.id.tv_content));
                TextView tv_time = ((TextView) view.findViewById(R.id.tv_time));
                TextView tv_dianzan = ((TextView) view.findViewById(R.id.tv_dianzan));
                iv_photo = ((ImageView) view. findViewById(R.id.iv_photo));
                try {
                    tv_title.setText (URLDecoder.decode(dynamicInfo.dynamicTitle,"utf-8"));
                    tv_content.setText(URLDecoder.decode(dynamicInfo.dynamicContent,"utf-8"));
                    tv_time.setText((dynamicInfo.dynamicTime).toString());
                    tv_dianzan.setText(URLDecoder.decode(dynamicInfo.dynamicZan,"utf-8"));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                x.image().bind(iv_photo ,"http://10.0.2.2:8080/myapp/"+dynamicInfo.dynamicImg);

                return view;
            }
        };
        lv_dynamicinfo.setAdapter(adapter);
//        //从服务器拿
        getDynamicInfo();
       }
    private void getDynamicInfo() {
        dynamicInfoList.clear();
        System.out.println("==================?????????????");
        RequestParams params = new RequestParams("http://10.0.2.2:8080/myapp/getdynamicbypage");
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Gson gson=new Gson();
                DynamicInfoBean bean= gson.fromJson(result, DynamicInfoBean.class);
                System.out.println("=================="+result);
                dynamicInfoList.addAll(bean.dynamicInfoList);

                System.out.println(dynamicInfoList);
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
    private void setGestureListener(){
        lv_dynamicinfo.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Log.i(TAG, "onTouch: "+"???????????????????");
                switch (event.getAction()) {

                    case MotionEvent.ACTION_DOWN:
                        mPosX = event.getX();
                        mPosY  = event.getY();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        mCurPosX = event.getX();
                        mCurPosY = event.getY();
                        break;
                    case MotionEvent.ACTION_UP:
                        if (mCurPosY - mPosY > 0
                                && (Math.abs(mCurPosY - mPosY) > 15)) {
                            //向下滑動
                            iv_shuaxin.setVisibility(View.GONE);
                        } else if (mCurPosY - mPosY < 0
                                && (Math.abs(mCurPosY - mPosY) > 15)) {
                            //向上滑动
                            iv_shuaxin.setVisibility(View.VISIBLE);
                        }
                        break;
                }
                return false;
            }
        });
    }
    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.iv_shuaxin:
                getDynamicInfo();
                lv_dynamicinfo.setAdapter(adapter);
                break;
        }
    }
}


