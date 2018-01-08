package heyhou.com.baseproject.utils;


import android.content.Context;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.widget.EditText;
import android.widget.TextView;

import heyhou.com.baseproject.R;


public class TimeCount extends CountDownTimer {

    private TextView view;
    private TextView loginView;
    private Context context;
    private EditText editText;

    /**
     * @param millisInFuture    总时长（毫秒数）
     * @param countDownInterval 计时的时间间隔（毫秒数）
     * @param view              需要显示文字的textview
     */
    public TimeCount(Context context, long millisInFuture, long countDownInterval, TextView view, EditText editText) {
        super(millisInFuture, countDownInterval);
        this.view = view;
        this.context = context;
        this.editText = editText;
    }

    public TimeCount(Context context, long millisInFuture, long countDownInterval, TextView view) {
        super(millisInFuture, countDownInterval);
        this.view = view;
        this.context = context;
    }

    public TimeCount(Context context, long millisInFuture, long countDownInterval, TextView view, TextView loginView) {
        super(millisInFuture, countDownInterval);
        this.view = view;
        this.loginView = loginView;
        this.context = context;
    }

    @Override
    public void onTick(long millisUntilFinished) {
        view.setBackgroundResource(R.drawable.bg_code_ticking);
        view.setTextColor(Color.parseColor("#FFFFFF"));
        view.setText( millisUntilFinished / 1000 + context.getString(R.string.reget_code_hint_behind));
    }

    @Override
    public void onFinish() {
        view.setBackgroundResource(R.drawable.bg_round_stoke_line_color);
        view.setTextColor(Color.parseColor("#99FFFFFF"));
        view.setText(R.string.reget_code_hint);
        view.setClickable(true);
        if (loginView != null) {
            loginView.setClickable(true);
        }
        if (editText != null) {
            editText.setEnabled(true);
        }
    }
}
