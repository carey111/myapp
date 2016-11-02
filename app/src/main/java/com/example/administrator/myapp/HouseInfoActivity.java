package com.example.administrator.myapp;
import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.myapp.util.NetUtil;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;


public class HouseInfoActivity extends AppCompatActivity {

    @InjectView(R.id.ib_back_4)
    ImageButton ibBack4;
    @InjectView(R.id.tv_house_info_title)
    TextView tvHouseInfoTitle;
    @InjectView(R.id.ib_share_1)
    ImageButton ibShare1;
    @InjectView(R.id.iv_collect_1)
    ImageButton ivCollect1;
    @InjectView(R.id.rl_house_info_title)
    RelativeLayout rlHouseInfoTitle;
    @InjectView(R.id.tv_house_rent_2)
    TextView tvHouseRent2;
    @InjectView(R.id.tv_house_type_2)
    TextView tvHouseType2;
    @InjectView(R.id.tv_house_area_2)
    TextView tvHouseArea2;
    @InjectView(R.id.tv_house_no_2)
    TextView tvHouseNo2;
    @InjectView(R.id.tv_house_address_2)
    TextView tvHouseAddress2;
    @InjectView(R.id.tv_house_intro_2)
    TextView tvHouseIntro2;
    @InjectView(R.id.rl_house_info_s)
    RelativeLayout rlHouseInfoS;
    @InjectView(R.id.lv_evaluation_1)
    ListView lvEvaluation1;
    @InjectView(R.id.tv_house_no_3)
    TextView tvHouseNo3;
    @InjectView(R.id.et_house_no_2)
    EditText etHouseNo2;
    @InjectView(R.id.tv_rent_time_1)
    TextView tvRentTime1;
    @InjectView(R.id.sp_time)
    Spinner spTime;
    @InjectView(R.id.tv_house_rent_3)
    TextView tvHouseRent3;
    @InjectView(R.id.rl_house_rent_1)
    RelativeLayout rlHouseRent1;
    @InjectView(R.id.bt_call_landlord_1)
    Button btCallLandlord1;
    @InjectView(R.id.bt_pay_1)
    Button btPay1;
    @InjectView(R.id.rl_button_2)
    RelativeLayout rlButton2;
    @InjectView(R.id.rl_house_info)
    RelativeLayout rlHouseInfo;
    @InjectView(R.id.sv_house_info)
    ScrollView svHouseInfo;
    private boolean presscollect;
    String phoneNum1;
    String roomId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_house_info);
        SharedPreferences sharedPreferences = this.getSharedPreferences("SP",
                Activity.MODE_PRIVATE);
        phoneNum1 = sharedPreferences.getString("phone", "");
         roomId= getIntent().getStringExtra("roomId");
        System.out.println(roomId+"???????");
        iscollect();
        ButterKnife.inject(this);
    }


    @OnClick({R.id.ib_back_4, R.id.iv_collect_1})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ib_back_4:
                finish();
                break;
            case R.id.iv_collect_1:
                if (! presscollect) {
                    collect();
                    ivCollect1.setImageResource(R.drawable.yellow);
                    presscollect = true;
                } else {
                    cancelCollect();
                    ivCollect1.setImageResource(R.drawable.white);
                    presscollect = false;
                }
                break;
        }
    }
//    public void PictureRoll(){
//
//    }



    private void iscollect(){
        RequestParams requestParams=new RequestParams(NetUtil.url+"iscollectservlet");
        requestParams.addQueryStringParameter("roomId",roomId);
        requestParams.addQueryStringParameter("phoneNum",phoneNum1);
        x.http().get(requestParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                System.out.println(result+"++++++++++++++++");
       if(result.equals("true")){
        ivCollect1.setImageResource(R.drawable.yellow);
       }else{
        ivCollect1.setImageResource(R.drawable.white);
            }
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
    private void collect() {
        RequestParams requestParams=new RequestParams(NetUtil.url+"collectservlet");
        requestParams.addQueryStringParameter("roomId",roomId);
        requestParams.addQueryStringParameter("phoneNum",phoneNum1);
        x.http().get(requestParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Toast.makeText(HouseInfoActivity.this, "收藏成功", Toast.LENGTH_SHORT).show();
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
    private void cancelCollect() {
        RequestParams requestParams=new RequestParams(NetUtil.url+"uncollectservlet");
        requestParams.addQueryStringParameter("roomId",roomId);
        requestParams.addQueryStringParameter("phoneNum",phoneNum1);
   x.http().get(requestParams, new Callback.CommonCallback<String>() {
    @Override
    public void onSuccess(String result) {
        Toast.makeText(HouseInfoActivity.this, "取消收藏", Toast.LENGTH_SHORT).show();
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
