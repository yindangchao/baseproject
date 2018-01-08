package heyhou.com.baseproject.utils;

import android.app.Activity;
import android.util.DisplayMetrics;

/**
 * Created by 1 on 2016/5/12.
 */
public class Screen {

    private int width;
    private int height;
    private Activity activity;

    public Screen(Activity context) {
        this.activity = context;
        DisplayMetrics metrics = new DisplayMetrics();
        context.getWindowManager().getDefaultDisplay().getMetrics(metrics);
        width = metrics.widthPixels;
        height = metrics.heightPixels;
    }

    public int getStatusBarHeight() {
        int result = 0;
        int resourceId = activity.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = activity.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    /**
     * @return 屏幕宽度 in pixel
     */
    public int getWidth() {
        return width;
    }

    /**
     * @return 屏幕高度 in pixel
     */
    public int getHeight() {
        return height;
    }

    @Override
    public String toString() {
        return "Screen{" +
                "width=" + width +
                ", height=" + height +
                '}';
    }
}