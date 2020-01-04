package com.android.practice;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Button mBtOpenOtherApp = findViewById(R.id.bt_open_app_activity);
        mBtOpenOtherApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setComponent(new ComponentName("com.android.permission",
                        "com.android.permission.MainActivity"));
//                intent.setAction("android.intent.test");
                startActivity(intent);
            }
        });
    }
}
