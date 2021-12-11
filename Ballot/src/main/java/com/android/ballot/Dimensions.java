package com.android.ballot;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Point;
import android.os.Build;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Display;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.view.Surface;
import android.view.ViewConfiguration;
import android.view.WindowManager;

import java.lang.reflect.Method;

/**
 * 尺寸转换工具类
 *
 * @author hubin
 * @date 16/6/23
 */
public class Dimensions {


    private static int getDpi(Activity context) {
        int dpi = 0;
        Display display = context.getWindowManager().getDefaultDisplay();
        DisplayMetrics dm = new DisplayMetrics();
        @SuppressWarnings("rawtypes")
        Class c;
        try {
            c = Class.forName("android.view.Display");
            @SuppressWarnings("unchecked")
            Method method = c.getMethod("getRealMetrics", DisplayMetrics.class);
            method.invoke(display, dm);
            dpi = dm.heightPixels;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dpi;
    }

    //获取是否存在NavigationBar
    public static boolean checkDeviceHasNavigationBar(Context context) {
        boolean hasNavigationBar = false;
        Resources rs = context.getResources();
        int id = rs.getIdentifier("config_showNavigationBar", "bool", "android");
        if (id > 0) {
            hasNavigationBar = rs.getBoolean(id);
        }
        try {
            Class systemPropertiesClass = Class.forName("android.os.SystemProperties");
            Method m = systemPropertiesClass.getMethod("get", String.class);
            String navBarOverride = (String) m.invoke(systemPropertiesClass, "qemu.hw.mainkeys");
            if ("1".equals(navBarOverride)) {
                hasNavigationBar = false;
            } else if ("0".equals(navBarOverride)) {
                hasNavigationBar = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return hasNavigationBar;

    }

    public static boolean isNavigationBarShow(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            Display display = activity.getWindowManager().getDefaultDisplay();
            Point size = new Point();
            Point realSize = new Point();
            display.getSize(size);
            display.getRealSize(realSize);
            return realSize.y != size.y;
        } else {
            boolean menu = ViewConfiguration.get(activity).hasPermanentMenuKey();
            boolean back = KeyCharacterMap.deviceHasKey(KeyEvent.KEYCODE_BACK);
            if (menu || back) {
                return false;
            } else {
                return true;
            }
        }
    }


//    /**
//     * 获取导航栏高度(如华为底部导航栏高度) 
//     * @param context
//     * @return
//     */
//    public static int getDaoHangHeight(Context context){
//        int rid=context.getResources().getIdentifier("config_showNavigationBar", "bool", "android"); 
//        if (rid!=0){
//            int resourceId=context.getResources().getIdentifier("navigation_bar_height", "dimen", "android"); 
//            return context.getResources().getDimensionPixelSize(resourceId); 
//        }
//        return 0; 
//    }

    /**
     * 将dp转换成px
     */
    public static int dip2px(float dipValue) {
        float scale = Resources.getSystem().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    public static int dip2px(Context context, float dipValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    /**
     * px转成为dp
     */
    public static int px2dip(float pxValue) {
        final float scale = Resources.getSystem().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * sp转成为px
     */
    public static int sp2px(float spValue) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
                spValue, Resources.getSystem().getDisplayMetrics());
    }

    /**
     * px转sp
     */
    public static float px2sp(float pxValue) {
        return (pxValue / Resources.getSystem().getDisplayMetrics().scaledDensity);
    }

    /**
     * 返回手机分辨率（宽*高）。注意：返回值手机屏幕是否旋转无关。
     *
     * @return null - 失败
     */
    public static String getResolution(Context context) {
        int[] info = getDispInfo(context);
        if (info[0] == -1 || info[1] == -1) return null;
        if (info[2] == Surface.ROTATION_0 || info[2] == Surface.ROTATION_180)
            return String.valueOf(info[0]) + "x" + info[1];
        else return String.valueOf(info[1]) + "x" + info[0];
    }

    /**
     * 返回手机当前屏幕宽带
     *
     * @return < 0 - 失败
     */
    public static int getScreenWidth(Context context) {
        return getDispInfo(context)[0];
    }

    /**
     * 返回手机当前屏幕高度
     *
     * @return < 0 - 失败
     */
    public static int getHeight(Context context) {
        return getDispInfo(context)[1];
    }

    /**
     * 获取屏幕的高度px
     * 貌似是不包括状态栏的高度
     * @param context 上下文
     * @return 屏幕高px
     */
    public static int getScreenHeight(Context context) {
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.heightPixels;
    }

    /**
     * 获得状态栏的高度
     *
     * @param context
     * @return
     */
    public static int getStatusHeight(Context context) {
        int statusHeight = -1;
        try {
            Class<?> clazz = Class.forName("com.android.internal.R$dimen");
            Object object = clazz.newInstance();
            int height = Integer.parseInt(clazz.getField("status_bar_height").get(object).toString());
            statusHeight = context.getResources().getDimensionPixelSize(height);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return statusHeight;
    }

    // 内部函数
    private static int[] getDispInfo(Context context) {
        if (context == null) return new int[]{-1, -1};
        Display dm = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        if (dm == null) return new int[]{-1, -1};
        if (Build.VERSION.SDK_INT < 14) {
            return new int[]{dm.getWidth(), dm.getHeight(), dm.getRotation()};
        } else {
            try {
                Point size = new Point();
                Method method = dm.getClass().getMethod("getRealSize", Point.class);
                method.invoke(dm, size);
                return new int[]{size.x, size.y, dm.getRotation()};
            } catch (Exception e) {
                return new int[]{-1, -1};
            }
        }
    }

    /**
     * 精确获取屏幕尺寸（例如：3.5、4.0、5.0寸屏幕）
     *
     * @param ctx
     * @return
     */
    public static double getScreenPhysicalSize(Context ctx) {
        DisplayMetrics dm = new DisplayMetrics();
        ((WindowManager) ctx.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getMetrics(dm);
        double diagonalPixels = Math.sqrt(Math.pow(dm.widthPixels, 2) + Math.pow(dm.heightPixels, 2));
        return diagonalPixels / (160 * dm.density);
    }

    /**
     * 判断是否是平板（官方用法）
     *
     * @param context
     * @return
     */
    public static boolean isTablet(Context context) {
        return (context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }

}
