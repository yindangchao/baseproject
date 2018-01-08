package heyhou.com.baseproject.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import heyhou.com.baseproject.R;


/**
 * Created by 1 on 2017/3/8.
 */
public class CommonDialog extends AlertDialog {
    private Context context;
    private View view;
    private TextView textView;
    private TextView tvSure;
    private TextView tvCancel;
    private TextView tvTitle;
    private OnSureClickListener onSureClickListener;
    private OnCancelClickListener onCancelClickListener;

    public CommonDialog(Context context) {
        super(context, R.style.dialog_bond);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setCanceledOnTouchOutside(true);
        setCancelable(true);
        LayoutInflater inflater = LayoutInflater.from(context);
        view = inflater.inflate(R.layout.dialog_common_sure, null);
        textView = (TextView) view.findViewById(R.id.dialog_text);
        tvSure = (TextView) view.findViewById(R.id.dialog_sure);
        tvCancel = (TextView) view.findViewById(R.id.dialog_cancel);
        tvTitle = (TextView) view.findViewById(R.id.dialog_title);
        tvSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onSureClickListener != null) {
                    onSureClickListener.onSureClick();
                }
            }
        });
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onCancelClickListener != null) {
                    onCancelClickListener.onCancelClick();
                }
            }
        });
    }

    public CommonDialog build() {
        show();
        setContentView(view);
        return this;
    }


    public CommonDialog setMsg(String msg) {
        textView.setText(msg);
        return this;
    }

    public CommonDialog setSureText(String text) {
        tvSure.setText(text);
        return this;
    }

    public CommonDialog setCancelText(String text) {
        tvCancel.setText(text);
        return this;
    }

    public CommonDialog setTitle(String title) {
        if (BasicTool.isEmpty(title)) {
            tvTitle.setVisibility(View.GONE);
        } else {
            tvTitle.setVisibility(View.VISIBLE);
            tvTitle.setText(title);
        }
        return this;
    }

    public CommonDialog setCancelListener(OnCancelClickListener onCancelClickListener) {
        this.onCancelClickListener = onCancelClickListener;
        return this;
    }

    public CommonDialog setOnSureListener(OnSureClickListener onSureListener) {
        this.onSureClickListener = onSureListener;
        return this;
    }

    public interface OnSureClickListener {
        void onSureClick();
    }

    public interface OnCancelClickListener {
        void onCancelClick();
    }


}
