package com.android.practice;

import android.content.ComponentName;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.drawable.DrawableCompat;

import com.google.android.material.bottomsheet.BottomSheetDialog;


/**
 * @author devliang
 */
public class MainActivity extends AppCompatActivity {

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Looper.loop();

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

        intentFileWithBinder();


        // double  16; long 19
        double s = 0.89232323434354545454545;
        String str = "0.8923232343435454545454589232323434354545454545923232343435454545454592323234343545454545459232323434354545454545";
        double d = Double.parseDouble(str);
        Long l = Long.parseLong("1111111111111111111");
        System.out.println(l);


    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    private void intentFileWithBinder() {
        Intent intent = new Intent(this, TestActivity.class);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.test);
        Bundle bundle = new Bundle();
        Log.i("MainActivity", "bitmap大小: " + bitmap.getByteCount() / 1024 + " kb");
        bundle.putBinder("bitmap", new BitmapBinder(bitmap));
        intent.putExtras(bundle);
        startActivity(intent);
    }

    private void changedDrawableTint() {
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
    }


    /**
     * 设置透明度
     *
     * @param imageButton
     */
    private void setBg(ImageView imageButton) {
        //0~255透明度值
        imageButton.getBackground().setAlpha(100);
    }

    //着色
    public static Drawable tintDrawable(Drawable drawable, ColorStateList colors) {
        final Drawable wrappedDrawable = DrawableCompat.wrap(drawable);
        DrawableCompat.setTintList(wrappedDrawable, colors);
        return wrappedDrawable;
    }


}
