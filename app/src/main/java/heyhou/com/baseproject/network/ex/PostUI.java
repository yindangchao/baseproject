package heyhou.com.baseproject.network.ex;


import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import heyhou.com.baseproject.R;
import heyhou.com.baseproject.base.Constant;
import heyhou.com.baseproject.network.WaitingDialog;

/**
 * Created by admin on 2017/2/22.
 */

public abstract class PostUI<T> {

    //用户显示正在加载对话框的context
    private Context mContext;
    //正在加载的对话框
    private WaitingDialog mDialog;
    //正在加载的对话框显示的消息
    private String mMessage;

    //需要解析的参数的类型
    private Type mDataType;

    /**
     * 默认的构造函数
     */
    public PostUI() {
        Type type = getClass().getGenericSuperclass();
        mDataType = ((ParameterizedType) type).getActualTypeArguments()[0];
    }

    /**
     * 显示正在加载的对话框的构造函数
     *
     * @Param context
     * @Param msg 对话框上显示的消息
     */
    public PostUI(Context context, String msg) {
        mContext = context;
        mMessage = msg;
        Type type = getClass().getGenericSuperclass();
        mDataType = ((ParameterizedType) type).getActualTypeArguments()[0];
    }

    /**
     * 判断是否显示正在加载的对话框
     */
    public boolean isShowLoading() {
        return (mContext != null);
    }

    /**
     * 获取数据成功的回调
     */
    public abstract void onSucceed(HttpResponseData<T> data);

    /**
     * 获取数据失败的回调
     */
    public abstract void onFailed(int errorCode, String errorMsg);

    /***
     * 显示正在加载的Dialog
     */
    public void showLoading() {
        if (mContext == null) {
            return;
        }
        if (TextUtils.equals(mMessage, Constant.NOLOADING)) {
            return;
        }
        if (mContext instanceof Activity) {
            if (((Activity) mContext).isFinishing()) {
                return;
            }
            mDialog = new WaitingDialog(mContext, R.style.loadingDialogTheme);
            mDialog.show();
        }
    }

    /***
     * 隐藏正在加载的Dialog
     */
    public void hideLoading() {
        if (mDialog != null && mDialog.isShowing()) {
            mDialog.dismiss();
            mDialog = null;
        }
    }


    public Type getDataTypeClass() {
        return mDataType;
    }
}
