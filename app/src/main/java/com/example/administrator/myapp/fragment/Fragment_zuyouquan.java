package com.example.administrator.myapp.fragment;




import android.app.FragmentTransaction;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.administrator.myapp.R;
import com.example.administrator.myapp.pojo.Friend;
import com.example.administrator.myapp.util.NetUtils;

import java.util.ArrayList;
import java.util.List;

import io.rong.imkit.RongIM;
import io.rong.imkit.fragment.ConversationListFragment;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.UserInfo;

/**
 * Created by Administrator on 2016/9/28.
 */
public class Fragment_zuyouquan extends Fragment {
    private static final String token1 = "yVOHN+v9p4pN6VSxxBqSy+Ti1mHV1bzsBudRVcrx0Llb0nHAujsAFCyRPOfT2PLZep1h9Svlnij4kXgHRGUiXQ==";
    Fragment_zuyouquan_haoyou fragment_zuyouquan_haoyou;
    //      Fragment_zuyouquan_xiaoxi fragment_zuyouquan_xiaoxi;
    Fragment[] fragments;
    private static final String TAG = "Fragment_zuyouquan";
    //按钮的数组，一开始第一个按钮被选中
    Button[] tabs;
    int oldIndex;//用户看到的item
    int newIndex;//用户即将看到的item
    private Button btn_xiaoxi;
    private Button btn_haoyou;
    private Fragment mConversationList;
    private Fragment mConversationFragment = null;

    private List<Friend> userIdList;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.layout_fragment_zuyoquan,null);

        //初始化fragment
        Fragment_zuyouquan_haoyou fragment_zuyouquan_haoyou=new Fragment_zuyouquan_haoyou();
//        Fragment_zuyouquan_xiaoxi fragment_zuyouquan_xiaoxi=new Fragment_zuyouquan_xiaoxi();
        mConversationList=initConversationList();//获取融云会话列表的对象
        //所有fragment的数组
        fragments=new Fragment[]{mConversationList,fragment_zuyouquan_haoyou};
        //设置按钮的数组
        tabs=new Button[2];
        btn_xiaoxi = ((Button) view.findViewById(R.id.btn_xiaoxi));
        btn_haoyou = ((Button) view.findViewById(R.id.btn_haoyou));
        tabs[0]=btn_xiaoxi;
        tabs[1]=btn_haoyou;
        btn_xiaoxi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newIndex=0;//选中第1项
                switchFragment();
                btn_haoyou.setBackgroundResource(R.drawable.buttonstyle);
                btn_xiaoxi.setBackgroundColor(Color.WHITE);
            }
        });

        btn_haoyou.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newIndex=1;//选中第2项
                switchFragment();
                btn_xiaoxi.setBackgroundResource(R.drawable.buttonstyle);
                btn_haoyou.setBackgroundColor(Color.WHITE);
            }
        });

        //界面初始显示第一个fragment;添加第一个fragment
        getActivity().getSupportFragmentManager().beginTransaction().add(R.id.fl_xiaoxi_haoyou, fragments[0]).commit();
        //初始时，按钮1选中
        tabs[0].setSelected(true);

        connectRongServer(token1);
        initUserInfo();
        return view;
    }

    public void switchFragment() {
        android.support.v4.app.FragmentTransaction transaction;
        //如果选择的项不是当前选中项，则替换；否则，不做操作
        if(newIndex!=oldIndex){
            transaction=getActivity().getSupportFragmentManager().beginTransaction();
            transaction.hide(fragments[oldIndex]);//隐藏当前显示项

            //如果选中项没有加过，则添加
            if(!fragments[newIndex].isAdded()){
                //添加fragment
                transaction.add(R.id.fl_xiaoxi_haoyou,fragments[newIndex]);
            }
            //显示当前选择项
            transaction.show(fragments[newIndex]).commit();
        }
        //之前选中的项，取消选中
        tabs[oldIndex].setSelected(false);
        //当前选择项，按钮被选中
        tabs[newIndex].setSelected(true);
        //当前选择项变为选中项
        oldIndex=newIndex;
    }
    private Fragment initConversationList() {

        /**
         * appendQueryParameter对具体的会话列表做展示
         */
        if (mConversationFragment == null) {
            ConversationListFragment listFragment = ConversationListFragment.getInstance();
            Uri uri = Uri.parse("rong://" + getActivity().getApplicationInfo().packageName).buildUpon()
                    .appendPath("conversationList")
                    .appendQueryParameter(Conversation.ConversationType.PRIVATE.getName(), "false")//设置私聊会话是否聚合显示
                    .appendQueryParameter(Conversation.ConversationType.GROUP.getName(), "true")
                    .appendQueryParameter(Conversation.ConversationType.DISCUSSION.getName(), "false")//设置私聊会话是否聚合显示
                    .appendQueryParameter(Conversation.ConversationType.SYSTEM.getName(), "false")//设置私聊会是否聚合显示
                    .build();
            listFragment.setUri(uri);
            return listFragment;
        } else {
            return mConversationFragment;
        }
    }
    private void connectRongServer(String token) {

        RongIM.connect(token, new RongIMClient.ConnectCallback() {
            @Override
            public void onSuccess(String userId) {
                Log.d(TAG, "onSuccess: "+userId);

            }
            @Override
            public void onError(RongIMClient.ErrorCode errorCode) {
                // Log.e("onError", "onError userid:" + errorCode.getValue());//获取错误的错误码
                Log.e(TAG, "connect failure errorCode is : " + errorCode.getValue());
            }
            @Override
            public void onTokenIncorrect() {
                Log.e(TAG, "token is error ,please check token and appkey");
            }
        });

    }
    private void initUserInfo() {
        userIdList = new ArrayList<Friend>();
        userIdList.add(new Friend("1", "liu", NetUtils.url+"myapp/imgs/bb.png"));
        userIdList.add(new Friend("2","li", NetUtils.url+"myapp/imgs/aa.png"));
        RongIM.setUserInfoProvider(new RongIM.UserInfoProvider() {
            @Override
            public UserInfo getUserInfo(String s) {
                for (Friend i : userIdList) {
                    if ((i.getUserId()+"").equals(s)) {
                        Log.e(TAG, i.getPortraitUri());
                        return new UserInfo(i.getUserId()+"", i.getName(), Uri.parse(i.getPortraitUri()));
                    }
                }
                return null;
            }
        }, true);
    }

}
