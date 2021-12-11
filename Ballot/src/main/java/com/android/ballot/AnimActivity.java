package com.android.ballot;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

public class AnimActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anim);
    }


    public void showAnim(View view) {
        CourseCardDialog dialog = new CourseCardDialog(this);
        dialog.setClickDialogLister(new CourseCardDialog.ClickDialogLister() {
            @Override
            public void dialogClick() {

            }
        });
        dialog.showDialog();
    }

}