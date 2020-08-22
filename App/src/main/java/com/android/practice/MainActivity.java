package com.android.practice;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


/**
 * @author devliang
 */
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
                        "com.android.permission.SecondActivity"));
//                intent.setAction("android.intent.test");
                startActivity(intent);
            }
        });


//        showDeleteDialog();
    }


   /* public void showDeleteDialog() {
        final BottomSheetDialog dialog = new BottomSheetDialog(this);
        View view = LayoutInflater.from(this).inflate(R.layout.view_works_show_delete_video_dialog, null);
        Button btDelete = view.findViewById(R.id.bt_delete);
        Button btCancel = view.findViewById(R.id.bt_cancel);

        dialog.setContentView(view);
        try {
            // hack bg of the BottomSheetDialog
            ViewGroup parent = (ViewGroup) view.getParent();
            parent.setBackgroundResource(R.drawable.shape_bottom_dialog_bg);
        } catch (Exception e) {
            e.printStackTrace();
        }
        dialog.show();
    }*/

}
