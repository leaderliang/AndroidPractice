package com.android.practice;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;

public class TestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        Bundle bundle = getIntent().getExtras();
        BitmapBinder bitmapBinder = (BitmapBinder) bundle.getBinder("bitmap");
        Bitmap bitmap = bitmapBinder.getBitmap();
        Log.i("TestActivity", "TestActivity bitmap大小" + bitmap.getByteCount() / 1024 + " kb");
        Log.i("TestActivity", "TestActivity bitmap大小 getAllocationByteCount" + bitmap.getAllocationByteCount() / 1024 + " kb");


    }

}