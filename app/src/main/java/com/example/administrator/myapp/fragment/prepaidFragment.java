package com.example.administrator.myapp.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.administrator.myapp.R;
import com.example.administrator.myapp.pojo.Order;
import com.example.administrator.myapp.pojo.OrderDetail;
import com.example.administrator.myapp.util.CommonAdapter;
import com.example.administrator.myapp.util.NetUtil;
import com.example.administrator.myapp.util.ViewHolder;
import com.example.administrator.myapp.widget.NoScrollListview;
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

/**
 * Created by Administrator on 2016/10/17.
 */
public class prepaidFragment extends BaseFragment {
    @InjectView(R.id.frag_allorders_listview)
    ListView fragAllordersListview;
    List<Order> orders;
    CommonAdapter<Order> orderAdapter;
    public static final int PAYPART=1;
    public static final int PAYALL=2;
    public static final int UNREMARK=3;
    public static final int FINISHED=4;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.paid_pay, null);

        ButterKnife.inject(this, v);
        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        orders=new ArrayList<>();
        orderAdapter=new CommonAdapter<Order>(getActivity(),orders,R.layout.frag_allorders_item) {
            @Override
            public void convert(ViewHolder viewHolder, Order order, int position) {
                //shezhi item空间里面的取值
                initItemView(viewHolder,order);


            }
        };
        fragAllordersListview.setAdapter(orderAdapter);
        getOrderData();
    }

    @Override
    public void initView() {

    }

    @Override
    public void initEvent() {

    }
    @Override
    public void onResume() {
        super.onResume();
        Log.e("posiotion", "onSuccess:4 "+"");
    }
    @Override
    public void initData() {

        Log.e("posiotion", "onSuccess:1 "+"prepaid");

    }
    //获取网络数据
    public void  getOrderData() {
        orders.clear();
        RequestParams requestParams = new RequestParams(NetUtil.url + "orderqueryservlet");
        requestParams.addQueryStringParameter("userId", 1 + "");
        requestParams.addQueryStringParameter("orderStatusId", 1 + "");
        x.http().get(requestParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Gson gson=new Gson();
                Type type=new TypeToken<List<Order>>(){}.getType();
                List <Order> newOrders= gson.fromJson(result,type);

                orders.addAll(newOrders);

                    orderAdapter.notifyDataSetChanged();


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

    public void initItemView(ViewHolder viewHolder,Order order){
        TextView tvTime=viewHolder.getViewById(R.id.frag_allorders_item_time);
        TextView tvOrderState=viewHolder.getViewById(R.id.frag_allorders_item_trade);
        TextView tvOrderPrice=viewHolder.getViewById(R.id.frag_allorders_item_money);
        Button btnLeft=viewHolder.getViewById(R.id.btn_item_left);
        Button btnRight=viewHolder.getViewById(R.id.btn_item_right);
        tvTime.setText("订单时间："+order.getGoodsCreateTime());
        tvOrderState.setText(order.getGoodsOrderState().getOrderStates());
        tvOrderPrice.setText("实付￥"+order.getOrderPrice());

        btnShow(order.getGoodsOrderState().getOrderStateId(),btnLeft,btnRight);
        btnOnClick(order.getGoodsOrderState().getOrderStateId(),btnLeft,btnRight);
        NoScrollListview noScrollListview=viewHolder.getViewById(R.id.frag_allorders_item_listView);
        CommonAdapter<OrderDetail>orderDetailAdapter=null;
        if(orderDetailAdapter==null){
            orderDetailAdapter=new CommonAdapter<OrderDetail>(getActivity(),order.getOrderDetails(),R.layout.layout_ordertetailed) {
                @Override
                public void convert(ViewHolder viewHolder, OrderDetail orderDetail, int position) {
                    TextView tvType=viewHolder.getViewById(R.id.tv_type);
                    tvType.setText(orderDetail.getRentInfo().roomType);
                    TextView tvArea=viewHolder.getViewById(R.id.tv_area);
                    tvArea.setText(orderDetail.getRentInfo().roomArea);
                    TextView tvZujin=viewHolder.getViewById(R.id.tv_zujin);
                    tvZujin.setText(orderDetail.getRentInfo().roomRent.toString());
                    ImageView ivPhoto=viewHolder.getViewById(R.id.iv_photo);
                    x.image().bind(ivPhoto ,"http://10.0.2.2:8080/"+orderDetail.getRentInfo().photoImg);

                }
            };
            noScrollListview.setAdapter(orderDetailAdapter);
        }else{
            orderDetailAdapter.notifyDataSetChanged();
        }
    }
    public void btnShow(int orderStateId,Button btnLeft,Button btnRight){
        switch (orderStateId){
            case PAYPART:
                btnLeft.setVisibility(View.VISIBLE);
                btnRight.setVisibility(View.VISIBLE);
                btnLeft.setText("申请退款");
                btnRight.setText("补齐余额");
                break;
            case PAYALL:
                btnLeft.setVisibility(View.GONE);
                btnRight.setVisibility(View.VISIBLE);
                btnRight.setText("评价");
                break;
            case FINISHED:
                btnLeft.setVisibility(View.GONE);
                btnRight.setVisibility(View.GONE);
               // btnRight.setText("交易完成");
                break;
            default:
                btnLeft.setVisibility(View.VISIBLE);
                btnRight.setVisibility(View.VISIBLE);
                btnLeft.setText("申请退款");
                btnRight.setText("补齐余额");
                break;
        }


    }
    public void btnOnClick( final int orderStateId,Button btnLeft,Button btnRight){

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }
}
