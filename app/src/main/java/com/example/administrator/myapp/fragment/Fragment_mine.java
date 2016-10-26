package com.example.administrator.myapp.fragment;


import android.app.Activity;

import android.content.Context;
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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.example.administrator.myapp.LoginActivity;
import com.example.administrator.myapp.ModifyInfoActivity;
import com.example.administrator.myapp.OrderActivity;
import com.example.administrator.myapp.R;
import com.example.administrator.myapp.RegisterActivity;
import com.example.administrator.myapp.RentorActivity;
import com.example.administrator.myapp.ToBeRentor;
import com.example.administrator.myapp.pojo.Order;
import com.example.administrator.myapp.pojo.User;
import com.example.administrator.myapp.util.NetUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/9/28.
 */
public class Fragment_mine extends Fragment {
    @InjectView(R.id.tv_login)
    TextView tvLogin;
    @InjectView(R.id.iv_touxiang)
    ImageView ivTouxiang;
    @InjectView(R.id.bt_zhuxiao)
    Button btZhuxiao;
    @InjectView(R.id.lv_mine)
    ListView lvMine;
    private ListView lv_mine;
    private ICallBack iCallBack;
    private TextView tv_login;
   // List<User> userList;
    private ImageView touxiang;
    String imgUrl=null;
    String phoneNum1=null;
User user=null;
    String userId=null;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_fragment_mine, null);
        lv_mine = ((ListView) view.findViewById(R.id.lv_mine));
        tv_login = ((TextView) view.findViewById(R.id.tv_login));
        touxiang = ((ImageView) view.findViewById(R.id.iv_touxiang));
        Log.i("Fragment_mine", "onCreateView: "+imgUrl);
        tv_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
            }
        });
        iCallBack = (ICallBack) getActivity();
        int[] iv_photoImgs = new int[]{R.drawable.a, R.drawable.b, R.drawable.c, R.drawable.d, R.drawable.e, R.drawable.f, R.drawable.h};
        String[] titiles = new String[]{"用户信息", "我的订单", "我是房东", "我的收藏", "浏览记录", "关于我们", "我的动态"};
        List<HashMap<String, Object>> data = new ArrayList<HashMap<String, Object>>();
        HashMap<String, Object> map = null;
        for (int i = 0; i < iv_photoImgs.length; i++) {
            map = new HashMap<String, Object>();
            map.put("iv_photoImg", iv_photoImgs[i]);
            map.put("titiles", titiles[i]);
            data.add(map);
        }
        String[] from = {"titiles", "iv_photoImg"};
        int[] to = {R.id.tv_titles, R.id.iv_photoImg};
        lv_mine.setAdapter(
                new SimpleAdapter(
                        getActivity(),   //上下文
                        data,               //map结构的数据
                        R.layout.wodelayout,// listview item的布局文件
                        from,           // map 的key
                        to              // 布局文件中控件的id
                ));
        lv_mine.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                       // iCallBack.onclick0(view);
                        Intent intent=new Intent(getActivity(), ModifyInfoActivity.class);
                        intent.putExtra("User",user);
                        getActivity().startActivity(intent);
                        break;
                    case 1:
                        iCallBack.onclick1(view);
                        break;
                    case 2:
                        if(user.getLandlord().equals(true)){
                            Intent intent1=new Intent(getActivity(), RentorActivity.class);
                            startActivity(intent1);
                        }else{
                            Intent intent2=new Intent(getActivity(), ToBeRentor.class);
                            startActivity(intent2);

                        }
                        break;
                    case 3:
                       iCallBack.onclick3(view);
                        break;
                    case 4:
                        iCallBack.onclick4(view);
                        break;
                    case 5:
                        iCallBack.onclick5(view);
                        break;
                    case 6:
                        iCallBack.onclick6(view);
                        break;
                }
            }
        });
        ButterKnife.inject(getActivity(), view);
        return view;
    }


    @Override
    public void onStart() {
        super.onStart();

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("SP",
                Activity.MODE_PRIVATE);
        String phoneNum = sharedPreferences.getString("phoneNum", "");
        String userPsd = sharedPreferences.getString("userPsd", "");
        phoneNum1 = sharedPreferences.getString("phone","");
        getOrderData();
        if (" ".equals(phoneNum)) {
            tv_login.setVisibility(View.VISIBLE);
        } else {
            tv_login.setVisibility(View.INVISIBLE);
        }
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    public interface ICallBack {
        void onclick0(View view);

        void onclick1(View view);

        void onclick2(View view);

        void onclick3(View view);

        void onclick4(View view);

        void onclick5(View view);

        void onclick6(View view);
    }
    //获取网络数据
    public void  getOrderData(){
        RequestParams requestParams=new RequestParams(NetUtil.url+"userinfoqueryservlet");
        requestParams.addQueryStringParameter("phoneNum",phoneNum1);
        Log.i("ccc", "onSuccess: "+phoneNum1);
        x.http().get(requestParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Gson gson=new Gson();
                user=gson.fromJson(result,User.class);
                 imgUrl=user.getPhotoImg();
                x.image().bind(touxiang,"http://10.0.2.2:8080/myapp/"+imgUrl);
                //Log.e("ccc", "http://10.0.2.2:8080/myapp/"+imgUrl);
            }
            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.e("ccc", "http://10.0.2.2:8080/myapp/1111");
            }
            @Override
            public void onCancelled(CancelledException cex) {
                Log.e("ccc", "http://10.0.2.2:8080/myapp/1122");
            }
            @Override
            public void onFinished() {
                Log.e("ccc", "http://10.0.2.2:8080/myapp/11233");
            }
        });
    }
}
