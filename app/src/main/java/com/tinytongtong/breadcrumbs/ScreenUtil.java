package com.tinytongtong.breadcrumbs;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

/**
 * @Description: 屏幕util，获取屏幕宽高等
 *
 * @Author wangjianzhou
 * @Date 2020/5/26 3:05 PM
 * @Version
 */
public class ScreenUtil {

    /**
     * 取得屏幕分辨率
     * @param context
     * @return
     */
    public static Point getScreenResolution(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        return new Point(display.getWidth(), display.getHeight());
    }

    /**
     * 获取屏幕宽度
     * @param context
     * @return
     */
    public static int getScreenWidth(Context context) {
        return getScreenResolution(context).x;
    }

    /**
     * 获取屏幕高度
     * @param context
     * @return
     */
    public static int getScreenHeight(Context context) {
        return getScreenResolution(context).y;
    }


    /**
     * dp转px
     * @param context
     * @param dipValue
     * @return
     */
    public static int dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    /**
     * px转dp
     * @param context
     * @param pxValue
     * @return
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale - 0.5f);
    }

    /**
     * 获取应用区的高度
     * @param context
     * @return
     */
    public static int getAppScreenHeight(Activity context){
        Rect outRect = new Rect();
        context.getWindow().getDecorView().getWindowVisibleDisplayFrame(outRect);
        return outRect.height();
    }

    /**
     * 获取屏幕像素
     */
    public static float getDensity(Context context) {
        WindowManager mWindowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        mWindowManager.getDefaultDisplay().getMetrics(dm);
        return dm.density;
    }
}
