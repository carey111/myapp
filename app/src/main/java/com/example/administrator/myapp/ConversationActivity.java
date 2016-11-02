package com.example.administrator.myapp;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import io.rong.imkit.RongIM;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.Message;
import io.rong.imlib.model.UserInfo;

public class ConversationActivity extends AppCompatActivity {
    /**
     * 目标 Id
     */
    private String mTargetId;
    private Button btn_single;
    private TextView tv_nicheng;
    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation);
        findViewById(R.id.img_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        userId=getIntent().getData().getQueryParameter("targetId");//获取id
        String title=getIntent().getData().getQueryParameter("title");//获取消息title
        tv_nicheng = ((TextView)findViewById(R.id.tv_nicheng));
        tv_nicheng.setText(title);

        RongIM.setConversationBehaviorListener(new RongIM.ConversationBehaviorListener() {
            @Override
            public boolean onUserPortraitClick(Context context, Conversation.ConversationType conversationType, UserInfo userInfo) {
                Intent intent=new Intent(ConversationActivity.this,UserInfoActivity.class);
                intent.putExtra("userId",userId);
                startActivity(intent);

                return true;

            }

            @Override
            public boolean onUserPortraitLongClick(Context context, Conversation.ConversationType conversationType, UserInfo userInfo) {
                Toast.makeText(ConversationActivity.this, "长按", Toast.LENGTH_SHORT).show();
                return false;
            }

            @Override
            public boolean onMessageClick(Context context, View view, Message message) {
                return false;
            }

            @Override
            public boolean onMessageLinkClick(Context context, String s) {
                return false;
            }

            @Override
            public boolean onMessageLongClick(Context context, View view, Message message) {
                return false;
            }
        });

    }
}


