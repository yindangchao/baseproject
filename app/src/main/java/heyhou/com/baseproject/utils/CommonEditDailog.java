package heyhou.com.baseproject.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import heyhou.com.baseproject.R;


/**
 * Created by 1 on 2016/11/22.
 */
public class CommonEditDailog {
    private static AlertDialog dialog;
    private SureClickListener listener;
    public static final int TYPE_NUMBER = 1;

    public static void show(Context context, int type, final SureClickListener listener) {
        dialog = new AlertDialog.Builder(context, R.style.dialog_bond).create();
        dialog.setCanceledOnTouchOutside(true);
        dialog.setCancelable(true);
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.dialog_common_edit, null);
        TextView tvCancel = (TextView) view.findViewById(R.id.dialog_cancel);
        TextView tvSure = (TextView) view.findViewById(R.id.dialog_sure);
        final EditText etInput = (EditText) view.findViewById(R.id.et_input);
        if (type == TYPE_NUMBER) {
            etInput.setInputType(InputType.TYPE_CLASS_NUMBER);
        } else {
            etInput.setInputType(InputType.TYPE_CLASS_TEXT);
        }
        tvSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = etInput.getText().toString();
                if (content.equals("")) {
                    return;
                }
                listener.onSureClick(content);
                dialog.dismiss();
                dialog = null;
            }
        });
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                dialog = null;
            }
        });
        dialog.show();
        dialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        dialog.setContentView(view);
    }

    public interface SureClickListener {
        void onSureClick(String content);
    }
}
