package heyhou.com.baseproject.utils;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Rect;
import android.util.Log;
import android.view.ViewTreeObserver;

/**
 * 创建:yb 2016/12/26.
 * 描述:
 */

public class KeyBoardUtil {

    private static KeyBoardUtil instance;
    private KeyBoardUtil() {}

    public static KeyBoardUtil getInstance() {
        if (instance == null) {
            synchronized (KeyBoardUtil.class) {
                if (instance == null) {
                    instance = new KeyBoardUtil();
                }
            }
        }
        return instance;
    }

    public ViewTreeObserver.OnGlobalLayoutListener register(final Activity activity, final OnOperate onOperate) {
        return new ViewTreeObserver.OnGlobalLayoutListener() {

            //当键盘弹出隐藏的时候会 调用此方法。
            @Override
            public void onGlobalLayout() {
                Rect r = new Rect();
                //获取当前界面可视部分
                activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(r);
                //此处就是用来获取键盘的高度的， 在键盘没有弹出的时候 此高度为0 键盘弹出的时候为一个正数
                int heightDifference = ScreenUtil.getScreenHeight() - r.bottom;
                if (heightDifference > 0 && heightDifference > ScreenUtil.getScreenHeight() / 3) {
                    if (onOperate != null)
                        onOperate.onShow(heightDifference);
                } else {
                    if (onOperate != null)
                        onOperate.onHide(heightDifference);
                }
                Log.d("Keyboard Size", "Size: " + heightDifference);
            }

        };
    }

    public ViewTreeObserver.OnGlobalLayoutListener register(final Dialog dialog, final  OnOperate onOperate) {
        return new ViewTreeObserver.OnGlobalLayoutListener() {

            //当键盘弹出隐藏的时候会 调用此方法。
            @Override
            public void onGlobalLayout() {
                Rect r = new Rect();
                //获取当前界面可视部分
                dialog.getWindow().getDecorView().getWindowVisibleDisplayFrame(r);
                //此处就是用来获取键盘的高度的， 在键盘没有弹出的时候 此高度为0 键盘弹出的时候为一个正数
                int heightDifference = ScreenUtil.getScreenHeight() - r.bottom;
                if (heightDifference > 0 && heightDifference > ScreenUtil.getScreenHeight() / 3) {
                    if (onOperate != null)
                        onOperate.onShow(heightDifference);
                } else {
                    if (onOperate != null)
                        onOperate.onHide(heightDifference);
                }
                Log.d("Keyboard Size", "Size: " + heightDifference);
            }

        };
    }

    public static interface OnOperate {
        void onShow(int heightDiff);
        void onHide(int heightDiff);
    }
}
