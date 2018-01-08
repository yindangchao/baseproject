package heyhou.com.baseproject.network;

import android.content.Context;


import org.apache.http.HttpStatus;

import heyhou.com.baseproject.R;
import heyhou.com.baseproject.utils.ToastTool;

public class ErrorCallBack {
    public static void processError(Context context, int statusCode, String responseString) {
        if (null != context) {
            switch (statusCode) {
                case HttpStatus.SC_REQUEST_TIMEOUT:
                    ToastTool.showShort(context, context.getString(R.string.error_call_back_no_network));
                break;
                case HttpStatus.SC_GATEWAY_TIMEOUT:
                    ToastTool.showShort(context, context.getString(R.string.error_call_back_no_network));
                    break;
                case HttpStatus.SC_INTERNAL_SERVER_ERROR:
                    ToastTool.showShort(context, context.getString(R.string.error_call_back_server_error));
                    break;
                case HttpStatus.SC_NOT_FOUND:
                    ToastTool.showShort(context, context.getString(R.string.error_call_back_no_param));
                    break;
                case HttpStatus.SC_BAD_REQUEST:
                    ToastTool.showShort(context, context.getString(R.string.error_call_back_param_error));
                    break;
                case HttpStatus.SC_FORBIDDEN:
                    ToastTool.showShort(context, context.getString(R.string.error_call_back_no_permission));
                    break;
                case 10000:  //保存的用户信息过期
                    ToastTool.showShort(context, context.getString(R.string.error_call_back_user_info_outtime));
                    break;
                case 10001:  //没有操作权限
                    ToastTool.showShort(context, context.getString(R.string.error_call_back_no_permission));
                    break;
                case 0:  //应用无联网权限
                    ToastTool.showShort(context, context.getString(R.string.error_call_back_no_network));
                    break;
                default:
                    ToastTool.showShort(context, context.getString(R.string.error_call_back_net_error));
                    break;
            }

        }
    }

    public static void processExc(int code, String msc, Context context) {
//        ToastTool.showShort(context, msc + ", 错误码:" + code);
//        ToastTool.showShort(context, msc);
    }

}
