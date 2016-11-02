package com.example.administrator.myapp.fragment;


import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.administrator.myapp.HouseInfoActivity;
import com.example.administrator.myapp.R;
import com.example.administrator.myapp.ReleaseActivity;
import com.example.administrator.myapp.ToBeRentor;
import com.example.administrator.myapp.pojo.RentInfo;
import com.example.administrator.myapp.pojo.RentInfoBean;
import com.example.administrator.myapp.pojo.User;
import com.example.administrator.myapp.util.NetUtil;
import com.example.administrator.myapp.widget.TitleBar;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/9/28.
 */
public class Fragment_zufang extends Fragment {
    private static final String TAG = "MyActivity";
    final ArrayList<RentInfoBean.RentInfo> rentInfoList = new ArrayList<RentInfoBean.RentInfo>();
    @InjectView(R.id.b_house_release)
    Button bHouseRelease;
    @InjectView(R.id.rl_house_title)
    RelativeLayout rlHouseTitle;
    @InjectView(R.id.tv_area)
    TextView tvArea;
    @InjectView(R.id.tv_rent)
    TextView tvRent;
    @InjectView(R.id.tv_number)
    TextView tvNumber;
    @InjectView(R.id.bt_search)
    Button btSearch;
    @InjectView(R.id.lv_commendatory_house_list)
    ListView lvCommendatoryHouseList;
    @InjectView(R.id.sp_area)
    Spinner spArea;
    @InjectView(R.id.sp_rent)
    Spinner spRent;
    @InjectView(R.id.sp_number)
    Spinner spNumber;
    private BaseAdapter adapter;
    private ListView lv_rentinfo;
    User user = null;
    String phoneNum1 = null;
    private TitleBar tv_bar;
    //private Spinner sprent;
    private ListView lv_mianji;
    int position1 = 0;
    int position2 = 0;
    int position3 = 0;
    private Spinner spnum;
    private Spinner sprent;
    private Spinner sparea;
    private Button release;
    private ImageView picture;
    private TextView zujin;
    private TextView mianji;
    private TextView type;
    RentInfo rentInfo;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_fragment_zufang, null);
        spnum = ((Spinner) view.findViewById(R.id.sp_number));
        spnum.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                position3=position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        sparea = ((Spinner) view.findViewById(R.id.sp_area));
        sparea.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                position1=position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        sprent = ((Spinner) view.findViewById(R.id.sp_rent));
       sprent.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
           @Override
           public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
               position2=position;
           }

           @Override
           public void onNothingSelected(AdapterView<?> parent) {

           }
       });
        lv_rentinfo = ((ListView) view.findViewById(R.id.lv_commendatory_house_list));
        adapter = new BaseAdapter() {




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
                View view = View.inflate(getActivity(), R.layout.layout_room_item, null);
                RentInfoBean.RentInfo rentInfo = rentInfoList.get(position);
                type = ((TextView) view.findViewById(R.id.tv_zhentype));
                mianji = ((TextView) view.findViewById(R.id.tv_zhenmianji));
                zujin = ((TextView) view.findViewById(R.id.tv_zhenzujin));
                picture = ((ImageView) view.findViewById(R.id.iv_house_picture));
                release = ((Button) view.findViewById(R.id.b_house_release));
                type.setText(rentInfo.roomType);
                mianji.setText(rentInfo.roomArea);
                zujin.setText((rentInfo.roomRent).toString());
                x.image().bind(picture, NetUtil.url + rentInfo.photoImg);
                return view;
            }
        };
        lv_rentinfo.setAdapter(adapter);
        lv_rentinfo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), HouseInfoActivity.class);
               RentInfoBean.RentInfo info=  rentInfoList.get(position);
                int roomId=info.roomId;
                intent.putExtra("roomId",roomId+"");
                //Log.i(TAG, "onItemClick: "+rentInfo+"++++++++++++++++++++++");

                startActivity(intent);
            }
        });
        //从服务器拿
        getRentInfo();
        ButterKnife.inject(this, view);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("SP",
                Activity.MODE_PRIVATE);
        String phoneNum = sharedPreferences.getString("phoneNum", "");
        String userPsd = sharedPreferences.getString("userPsd", "");
        phoneNum1 = sharedPreferences.getString("phone", "");
        getOrderData();



    }

    private void getRentInfo() {
        rentInfoList.clear();
        RequestParams params = new RequestParams(NetUtil.url + "getallhouseservlet");
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Gson gson = new Gson();
                Type type = new TypeToken<List<RentInfoBean.RentInfo>>() {
                }.getType();
                List<RentInfoBean.RentInfo> rentInfoList1 = gson.fromJson(result, type);
                System.out.println("==================" + result);
                rentInfoList.addAll(rentInfoList1);
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

    //获取网络数据
    public void getOrderData() {
        RequestParams requestParams = new RequestParams(NetUtil.url + "userinfoqueryservlet");
        requestParams.addQueryStringParameter("phoneNum", phoneNum1);
        Log.i("ccc", "onSuccess: " + phoneNum1);
        x.http().get(requestParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Gson gson = new Gson();
                user = gson.fromJson(result, User.class);
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
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    @OnClick(R.id.b_house_release)
    public void onClick() {
        if (user.getLandlord().equals(true)) {
            Intent intent1 = new Intent(getActivity(), ReleaseActivity.class);
            startActivity(intent1);
        } else {
            Log.i(TAG, "onClick: " + user.getLandlord());
            Intent intent2 = new Intent(getActivity(), ToBeRentor.class);
            startActivity(intent2);
        }
    }
    public void getOrderData1(){
        RequestParams requestParams=new RequestParams(NetUtil.url+"searchhouseservlet");
        requestParams.addQueryStringParameter("position1",position1+"");
        requestParams.addQueryStringParameter("position2",position2+"");
        requestParams.addQueryStringParameter("position3",position3+"");
        x.http().get(requestParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                rentInfoList.clear();
                Gson gson=new Gson();
                Type type=new TypeToken<List<RentInfoBean.RentInfo>>(){}.getType();
                List<RentInfoBean.RentInfo> newList=gson.fromJson(result, type);
                rentInfoList.addAll(newList);
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
    @OnClick(R.id.bt_search)
    public void onClick1() {
        getOrderData1();
    }
}


