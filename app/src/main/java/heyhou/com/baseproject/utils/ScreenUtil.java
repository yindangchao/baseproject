package heyhou.com.baseproject.utils;

import android.content.Context;
import android.graphics.Point;
import android.util.Log;
import android.view.WindowManager;

import heyhou.com.baseproject.base.BaseApplication;


/**
 * 创建:yb 2016/11/4.
 * 描述:
 */

public class ScreenUtil {
    private static final String TAG = "Screen";
    private int screenWidth;
    private int screenHeight;
    private static ScreenUtil instance;

    private ScreenUtil() {
        init();
    }

    public void init() {
        WindowManager windowManager = (WindowManager) BaseApplication.m_appContext
                .getSystemService(Context.WINDOW_SERVICE);
        Point point = new Point();
        windowManager.getDefaultDisplay().getSize(point);
        screenWidth = point.x;
        screenHeight = point.y;
        Log.i(TAG, "screenWidth:" + screenWidth + " ,screenHeight:" + screenHeight);
    }

    public static ScreenUtil getInstance() {
        if (instance == null) {
            synchronized (ScreenUtil.class) {
                if (instance == null) {
                    instance = new ScreenUtil();
                }
            }
        }
        return instance;
    }

    public static int getScreenWidth() {
        return getInstance().screenWidth;
    }

    public static int getScreenHeight() {
        return getInstance().screenHeight;
    }

    public static int[] getScreenSize() {
        return new int[]{getScreenWidth(),getScreenHeight()};
    }

    /**
     * 获取状态栏高度
     * @return
     */
    public static int getStatusHeight() {
        int statusBarHeight = -1;
        //获取status_bar_height资源的ID
        int resourceId = BaseApplication.m_appContext.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            //根据资源ID获取响应的尺寸值
            statusBarHeight = BaseApplication.m_appContext.getResources().getDimensionPixelSize(resourceId);
        }
        return statusBarHeight;
    }
}
