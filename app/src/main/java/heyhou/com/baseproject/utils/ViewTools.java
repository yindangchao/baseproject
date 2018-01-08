package heyhou.com.baseproject.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.graphics.Color;
import android.os.Build;
import android.os.CountDownTimer;
import android.text.InputType;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import java.lang.reflect.Method;

/**
 * Created by 1 on 2016/5/31.
 */
public class ViewTools {


    public static void setWindowStatusBarColor(Activity activity) {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                Window window = activity.getWindow();
                //透明状态栏
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(Color.BLACK);
                //底部导航栏
                //window.setNavigationBarColor(activity.getResources().getColor(colorResId));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 禁止Edittext弹出软件盘，光标依然正常显示。
     */
    public static void disableShowSoftInput(EditText editText) {
        if (android.os.Build.VERSION.SDK_INT <= 10) {
            editText.setInputType(InputType.TYPE_NULL);
        } else {
            Class<EditText> cls = EditText.class;
            Method method;
            try {
                method = cls.getMethod("setShowSoftInputOnFocus", boolean.class);
                method.setAccessible(true);
                method.invoke(editText, false);
            } catch (Exception e) {
                // TODO: handle exception
            }

            try {
                method = cls.getMethod("setSoftInputShownOnFocus", boolean.class);
                method.setAccessible(true);
                method.invoke(editText, false);
            } catch (Exception e) {
                // TODO: handle exception
            }
        }
    }


    private static class AutoCount extends CountDownTimer {
        private TextView tvTime;
        private AlertDialog hintDialog;
        private Activity activity;

        /**
         * @param millisInFuture    The number of millis in the future from the call
         *                          to {@link #start()} until the countdown is done and {@link #onFinish()}
         *                          is called.
         * @param countDownInterval The interval along the way to receive
         *                          {@link #onTick(long)} callbacks.
         */
        public AutoCount(Activity activity, AlertDialog dialog, TextView tvTime, long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
            this.tvTime = tvTime;
            this.activity = activity;
            this.hintDialog = dialog;
        }

        @Override
        public void onTick(long millisUntilFinished) {
            tvTime.setText("" + (millisUntilFinished / 1000 - 1));
        }

        @Override
        public void onFinish() {
            if (hintDialog.isShowing()) {
                hintDialog.dismiss();
                activity.finish();
            }
        }
    }

}
