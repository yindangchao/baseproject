package heyhou.com.baseproject.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import heyhou.com.baseproject.R;


/**
 * Created by 1 on 2016/6/1.
 */
public class CommonSureDialog {
    private static CommonSureDialog commonSureDialog;
    private AlertDialog dialog;

    private CommonSureDialog() {

    }

    public static CommonSureDialog getInstance() {
        if (commonSureDialog == null) {
            synchronized (CommonSureDialog.class) {
                if (commonSureDialog == null) {
                    commonSureDialog = new CommonSureDialog();
                }
            }
        }
        return commonSureDialog;
    }

    private void showDialog(Context context, String msg, final OnSureClickListener listener) {
        showDialog(context, "", msg, listener);
    }

    private void showDialog(Context context, String msg, final OnSureClickListener listener, final OnCancelClickListener cancelClickListener) {
        showDialog(context, "", msg, listener, cancelClickListener);
    }

    private void showDialog(Context context, String title, String msg, final OnSureClickListener listener) {
        showDialog(context, title, msg, listener, null);
    }


    private void showDialog(Context context, String title, String msg, final OnSureClickListener listener, final OnCancelClickListener cancelClickListener) {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
        dialog = new AlertDialog.Builder(context, R.style.dialog_bond).create();
        dialog.setCanceledOnTouchOutside(true);
        dialog.setCancelable(true);
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.dialog_common_sure, null);
        TextView textView = (TextView) view.findViewById(R.id.dialog_text);
        textView.setText(msg);
        TextView tvSure = (TextView) view.findViewById(R.id.dialog_sure);
        TextView tvtitle = (TextView) view.findViewById(R.id.dialog_title);
        TextView tvCancel = (TextView) view.findViewById(R.id.dialog_cancel);
        if (BasicTool.isEmpty(title)) {
            tvtitle.setVisibility(View.GONE);
        } else {
            tvtitle.setVisibility(View.VISIBLE);
            tvtitle.setText(title);
        }
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
//                dialog = null;
                if (cancelClickListener != null) {
                    cancelClickListener.onCancelClickListener();
                }
            }
        });
        tvSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                if (listener != null) {
                    listener.onSureClick();
                }
//                dialog = null;
            }
        });
        dialog.show();
        dialog.setContentView(view);
    }

    private void showDialog(Context context, String msg, final OnSureClickListener listener, final OnCancelClickListener cancelClickListener, boolean cancelable) {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
        dialog = new AlertDialog.Builder(context, R.style.dialog_bond).create();
        dialog.setCanceledOnTouchOutside(true);
        dialog.setCancelable(true);
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.dialog_common_sure, null);
        TextView textView = (TextView) view.findViewById(R.id.dialog_text);
        textView.setText(msg);
        TextView tvSure = (TextView) view.findViewById(R.id.dialog_sure);
        TextView tvCancel = (TextView) view.findViewById(R.id.dialog_cancel);

        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
//                dialog = null;
                if (cancelClickListener != null) {
                    cancelClickListener.onCancelClickListener();
                }
            }
        });
        tvSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onSureClick();
                dialog.dismiss();
//                dialog = null;
            }
        });
        dialog.setCancelable(cancelable);
        dialog.setCanceledOnTouchOutside(cancelable);
        dialog.show();
        dialog.setContentView(view);
    }

    public void showDialog(Context context, String msg, String cancelText, String sureText, final OnSureClickListener listener) {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
        dialog = new AlertDialog.Builder(context, R.style.dialog_bond).create();
        dialog.setCanceledOnTouchOutside(true);
        dialog.setCancelable(true);
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.dialog_common_sure, null);
        TextView textView = (TextView) view.findViewById(R.id.dialog_text);
        textView.setText(msg);
        TextView tvSure = (TextView) view.findViewById(R.id.dialog_sure);
        TextView tvCancel = (TextView) view.findViewById(R.id.dialog_cancel);
        if (!BasicTool.isEmpty(sureText)) {
            tvSure.setText(sureText);
        }
        if (!BasicTool.isEmpty(cancelText)) {
            tvCancel.setText(cancelText);
        }
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        tvSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onSureClick();
                dialog.dismiss();
            }
        });
        dialog.show();
        dialog.setContentView(view);
    }


    public void showDialog(Context context, String msg, String cancelText, String sureText, final OnSureClickListener listener, final OnCancelClickListener cancelClickListener) {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
        dialog = new AlertDialog.Builder(context, R.style.dialog_bond).create();
        dialog.setCanceledOnTouchOutside(true);
        dialog.setCancelable(true);
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.dialog_common_sure, null);
        TextView textView = (TextView) view.findViewById(R.id.dialog_text);
        textView.setText(msg);
        TextView tvSure = (TextView) view.findViewById(R.id.dialog_sure);
        TextView tvCancel = (TextView) view.findViewById(R.id.dialog_cancel);
        if (!BasicTool.isEmpty(sureText)) {
            tvSure.setText(sureText);
        }
        if (!BasicTool.isEmpty(cancelText)) {
            tvCancel.setText(cancelText);
        }
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                cancelClickListener.onCancelClickListener();
            }
        });
        tvSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                listener.onSureClick();
            }
        });
        dialog.show();
        dialog.setContentView(view);
    }

    public static void show(Context context, String title, String msg, final OnSureClickListener listener) {
        getInstance().showDialog(context, title, msg, listener);
    }

    public static void show(Context context, String msg, final OnSureClickListener listener) {
        getInstance().showDialog(context, msg, listener);
    }

    public static void show(Context context, String title, String msg, final OnSureClickListener listener, final OnCancelClickListener cancelClickListener) {
        getInstance().showDialog(context, title, msg, listener, cancelClickListener);
    }

    public static void show(Context context, String msg, final OnSureClickListener listener, final OnCancelClickListener cancelClickListener) {
        getInstance().showDialog(context, msg, listener, cancelClickListener);
    }

    public static void show(Context context, String msg, final OnSureClickListener listener, final OnCancelClickListener cancelClickListener, boolean cancelable) {
        getInstance().showDialog(context, msg, listener, cancelClickListener, cancelable);
    }


    public static void show(Context context, String msg, String cancelText, String sureText, final OnSureClickListener listener) {
        getInstance().showDialog(context, msg, cancelText, sureText, listener);
    }

    public static void show(Context context, String msg, String cancelText, String sureText, final OnSureClickListener listener, final OnCancelClickListener cancelClickListener) {
        getInstance().showDialog(context, msg, cancelText, sureText, listener, cancelClickListener);
    }

    public interface OnSureClickListener {
        void onSureClick();
    }

    public interface OnCancelClickListener {
        void onCancelClickListener();
    }
}
