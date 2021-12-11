package com.android.practice;

import android.content.ComponentName;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.drawable.DrawableCompat;


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


        //使用图片ic_launcher着色，得不到想要的效果。
        ImageView imageView = findViewById(R.id.image);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "i m invisible", Toast.LENGTH_SHORT).show();
            }
        });
        imageView.setColorFilter(Color.RED);
//        android:tint="@color/colorAccent"
        //设置透明度
        setBg(imageView);
        Drawable drawable = imageView.getDrawable();
        //方式一：启动就更改颜色，也可以通过事件
        //imageButton.setImageDrawable(tintDrawable(drawable, ColorStateList.valueOf(Color.RED)));

        //方式二：selector来改变颜色，注意btn_color.xml必须在color文件夹下
//        imageView.setImageDrawable(tintDrawable(drawable, getResources().getColorStateList(R.color.btn_color)));



        // double  16; long 19

        double s = 0.89232323434354545454545;
        String str = "0.8923232343435454545454589232323434354545454545923232343435454545454592323234343545454545459232323434354545454545";
        double d = Double.parseDouble(str);
        System.out.println(d);


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


    //设置透明度
    private void setBg(ImageView imageButton) {
        //0~255透明度值
//        imageButton.getBackground().setAlpha(100);
    }
    //着色
    public static Drawable tintDrawable(Drawable drawable, ColorStateList colors) {
        final Drawable wrappedDrawable = DrawableCompat.wrap(drawable);
        DrawableCompat.setTintList(wrappedDrawable, colors);
        return wrappedDrawable;
    }
}
