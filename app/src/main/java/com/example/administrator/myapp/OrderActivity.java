package com.example.administrator.myapp;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.myapp.fragment.BaseFragment;
import com.example.administrator.myapp.fragment.OrderAllFragment;
import com.example.administrator.myapp.fragment.PayAllFragment;
import com.example.administrator.myapp.fragment.PayFinishFragment;
import com.example.administrator.myapp.fragment.prepaidFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class OrderActivity extends AppCompatActivity {

    @InjectView(R.id.all_order_goback)
    ImageView allOrderGoback;
    @InjectView(R.id.order_fragment_tab1)
    TextView orderFragmentTab1;
    @InjectView(R.id.order_fragment_tab2)
    TextView orderFragmentTab2;
    @InjectView(R.id.order_fragment_tab3)
    TextView orderFragmentTab3;
    @InjectView(R.id.order_fragment_tab4)
    TextView orderFragmentTab4;
    @InjectView(R.id.id_linearly)
    LinearLayout idLinearly;
    @InjectView(R.id.order_line_tab)
    ImageView orderLineTab;
    @InjectView(R.id.order_fragment_viewpager)
    ViewPager orderFragmentViewpager;
    List<BaseFragment> lists = new ArrayList<BaseFragment>();
    OrderAllFragment orderAllFragment;
    prepaidFragment prepaidFragment;
    PayAllFragment payAllFragment;
    PayFinishFragment payFinishFragment;
    private ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        ButterKnife.inject(this);
        prepaidFragment = new prepaidFragment();
        orderAllFragment = new OrderAllFragment();
        payAllFragment = new PayAllFragment();
        payFinishFragment = new PayFinishFragment();
        initData();
        //actionBar =getActionBar();
    }

    public void initData() {
        lists.add(orderAllFragment);
        lists.add(prepaidFragment);
        lists.add(payAllFragment);
        lists.add(payFinishFragment);
        orderFragmentViewpager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return lists.get(position);
            }

            @Override
            public int getCount() {
                return lists.size();
            }
        });
        orderFragmentViewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        orderAllFragment.getOrderData();
                        break;
                    case 1:
                        prepaidFragment.getOrderData();
                        break;
                    case 2:
                        payAllFragment.getOrderData();
                        break;
                    case 3:
                        payFinishFragment.getOrderData();
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @OnClick(R.id.all_order_goback)
    public void onClick() {
        //Intent intent=new Intent();
        finish();
    }

    @OnClick({R.id.order_fragment_tab1, R.id.order_fragment_tab2, R.id.order_fragment_tab3, R.id.order_fragment_tab4})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.order_fragment_tab1:
                orderFragmentViewpager.setCurrentItem(0);
                break;
            case R.id.order_fragment_tab2:
                orderFragmentViewpager.setCurrentItem(1);
                break;
            case R.id.order_fragment_tab3:
                orderFragmentViewpager.setCurrentItem(2);
                break;
            case R.id.order_fragment_tab4:
                orderFragmentViewpager.setCurrentItem(3);
                break;
        }
    }
}
