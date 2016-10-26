package com.example.administrator.myapp.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.administrator.myapp.R;
import com.example.administrator.myapp.application.MyApplication;

/**
 * Created by Administrator on 2016/10/15.
 */
public class EditNameFragment extends BaseFragment{
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v=LayoutInflater.from(getActivity()).inflate(R.layout.fragment_edit_name, null);

        EditText etName= (EditText) v.findViewById(R.id.et_edit_name);


        //初始化姓名
       // etName.setText(((MyApplication)getActivity().getApplication()).getUser().getUserName());


        return v;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initEvent() {

    }

    @Override
    public void initData() {

    }
}
