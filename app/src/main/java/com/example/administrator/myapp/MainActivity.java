package com.example.administrator.myapp;

import android.content.Intent;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.administrator.myapp.fragment.Fragment_home;
import com.example.administrator.myapp.fragment.Fragment_mine;
import com.example.administrator.myapp.fragment.Fragment_zufang;
import com.example.administrator.myapp.fragment.Fragment_zuyouquan;


public class MainActivity extends AppCompatActivity implements  View.OnClickListener,Fragment_mine.ICallBack {
    private RadioButton rb_zuyouquan;
    private RadioButton rb_home;
    private RadioButton rb_house;
    private RadioButton rb_mine;
    private Fragment fragment_zuyouquan;
    private Fragment fragment_home;
    private Fragment fragment_zufang;
    private Fragment fragment_mine;
    private Fragment oldFragment;
    private Fragment newFragment;
    private RadioGroup radioGroup;
    Fragment[] fragments;
    Fragment_home FragmentHome;//主页
    Fragment_zuyouquan FragmentZuYouQuan;//
    Fragment_mine FragmentMine ;//个人中心
    Fragment_zufang fragmentZuFang;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        switchFragment(new Fragment_home());
        rb_zuyouquan = ((RadioButton) findViewById(R.id.rb_zuyouquan));
        rb_home = ((RadioButton) findViewById(R.id.rb_home));
        rb_house = ((RadioButton) findViewById(R.id.rb_house));
        rb_mine = ((RadioButton) findViewById(R.id.rb_mine));
        rb_zuyouquan.setOnClickListener(this);
        rb_home.setOnClickListener(this);
        rb_house.setOnClickListener(this);
        rb_mine.setOnClickListener(this);
        FragmentHome=new Fragment_home();
        FragmentZuYouQuan=new Fragment_zuyouquan();
        FragmentMine=new Fragment_mine();
        fragmentZuFang=new Fragment_zufang();
      fragments=new Fragment[]{FragmentHome,FragmentZuYouQuan,FragmentMine,fragmentZuFang};
        radioGroup= (RadioGroup) findViewById(R.id.rg_tab);
        if(fragment_home==null) fragment_home = new Fragment_home();
        newFragment = fragment_home;
        rb_home.setChecked(true);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){

                    case R.id.rb_zuyouquan:
                        if(fragment_zuyouquan==null)
                            fragment_zuyouquan = new Fragment_zuyouquan();
                        newFragment = fragment_zuyouquan;
                        break;
                    case R.id.rb_home:
                        if(fragment_home==null)
                            fragment_home = new Fragment_home();
                        newFragment = fragment_home;
                        break;
                    case R.id.rb_house:
                        if(fragment_zufang==null)
                            fragment_zufang = new Fragment_zufang();
                        newFragment = fragment_zufang;
                        break;
                    case R.id.rb_mine:
                        if(fragment_mine==null)
                            fragment_mine = new Fragment_mine();
                        newFragment = fragment_mine;
                        break;
                }

            }

        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
        }
        switchFragment(newFragment);
    }
    public void switchFragment(Fragment fragment){
        this.getSupportFragmentManager().beginTransaction().replace(R.id.fl_content,fragment).commit();
    }
    @Override
    public void onclick0(View view) {
        Intent intent=new Intent(this, ModifyInfoActivity.class);
        this.startActivity(intent);
    }

    @Override
    public void onclick1(View view) {
        Intent intent=new Intent(this, OrderActivity.class);
        this.startActivity(intent);
    }

    @Override
    public void onclick2(View view) {
        Intent intent=new Intent(this, RentorActivity.class);
        this.startActivity(intent);
    }

    @Override
    public void onclick3(View view) {
        Intent intent=new Intent(this, CollectActivity.class);
        this.startActivity(intent);
    }

    @Override
    public void onclick4(View view) {
        Intent intent=new Intent(this, LookRecordActivity.class);
        this.startActivity(intent);
    }

    @Override
    public void onclick5(View view) {
        Intent intent=new Intent(this, AboutUsActivity.class);
        this.startActivity(intent);
    }

    @Override
    public void onclick6(View view) {
        Intent intent=new Intent(this, DynamicActivity.class);
        this.startActivity(intent);
    }


}

