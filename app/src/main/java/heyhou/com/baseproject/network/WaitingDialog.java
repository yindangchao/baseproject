package heyhou.com.baseproject.network;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;

import heyhou.com.baseproject.R;


/**
 * Created by 1 on 2016/6/30.
 */
public class WaitingDialog extends AlertDialog {
    private Context context;
    private String content;

    protected WaitingDialog(Context context) {
        super(context);
        this.context = context;
    }

    public WaitingDialog(Context context, int theme, String content) {
        super(context);
        this.context = context;
    }

    public WaitingDialog(Context context, int theme) {
        super(context, theme);
        this.context = context;
    }

    protected WaitingDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_waiting, null);

        setContentView(view);
        setCancelable(false);
        setCanceledOnTouchOutside(false);
        ImageView imgProgress = (ImageView) view.findViewById(R.id.img_progress);
        Animation set = AnimationUtils.loadAnimation(context, R.anim.anim_center_rotate);
        set.setInterpolator(new LinearInterpolator());
        imgProgress.startAnimation(set);
        this.setCancelable(false);
        WindowManager.LayoutParams params = getWindow().getAttributes();
//        ((Activity) context).getWindow().setFlags(
//                WindowManager.LayoutParams.FLAG_BLUR_BEHIND,
//                WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
//        params.alpha = 0.5f;// 透明度
        getWindow().setAttributes(params);
    }
}
