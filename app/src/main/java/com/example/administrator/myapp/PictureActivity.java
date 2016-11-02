package com.example.administrator.myapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;


import org.xutils.x;

public class PictureActivity extends AppCompatActivity {

    private ImageView iv_picture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture);
        Intent intent=getIntent();
        String str= intent.getStringExtra("picture");
        iv_picture = ((ImageView) findViewById(R.id.iv_picture));
        x.image().bind(iv_picture,str);
        iv_picture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
