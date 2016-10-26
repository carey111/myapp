package com.example.administrator.myapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class HaoyouActivity extends AppCompatActivity {


    private Button bt_send;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_haoyou);

        bt_send = ((Button) findViewById(R.id.bt_send));
        bt_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HaoyouActivity.this, SenddongtaiActivity.class);
                startActivity(intent);
            }
        });

    }


}
