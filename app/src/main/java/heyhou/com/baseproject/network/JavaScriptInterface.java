package heyhou.com.baseproject.network;


import android.app.Activity;
import android.view.WindowManager.LayoutParams;
import android.webkit.JavascriptInterface;

import heyhou.com.baseproject.R;
import heyhou.com.baseproject.utils.DetectTool;


public class JavaScriptInterface {
    private Activity context;

    private String idStr = "";

    public JavaScriptInterface(Activity context) {
        this.context = context;
    }

    /**
     * @param context 上下文对象
     * @param idStr   商品ID
     */

    public JavaScriptInterface(Activity context, String idStr) {
        this.context = context;
        this.idStr = idStr;
    }

    /**
     * 获取请求的路径
     */
    @JavascriptInterface
    public String getBaseUrl() {
//        return BaseMainApp.getInstance().getMainHostUrl(context);
        return null;
    }

    /**
     * 获取图片路径的前缀
     */
    @JavascriptInterface
    public String getImageUrlPrefix() {
//        return BaseData.PHOTO_URL;
        return null;
    }

    /**
     * 获取UID
     */
    @JavascriptInterface
    public String getUid() {
//        return BaseData.UID;
        return null;
    }

    /**
     * 获取版本号
     */
    @JavascriptInterface
    public String getVersion() {
        return DetectTool.getVersionName();
    }

    /**
     * 获取Token
     */
    @JavascriptInterface
    public String getToken() {
        return DetectTool.getToken();
    }

    /**
     * 获取设备类型，即mn
     */
    @JavascriptInterface
    public String getMn() {
        return DetectTool.getType();
    }

    /**
     * 显示加载对话框，子类如果不需要显示，则请覆盖此方法。
     */
    Loading loading = null;

    @JavascriptInterface
    public void showMask(boolean flag) {
        if (!flag) {
            return;
        }

        context.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                loading = new Loading(context, R.style.loadingDialogTheme, "正在查询，请稍后...");
                LayoutParams params = loading.getWindow().getAttributes();
                context.getWindow().setFlags(LayoutParams.FLAG_BLUR_BEHIND, LayoutParams.FLAG_BLUR_BEHIND);
                params.alpha = 1.0f;// 透明度
                loading.getWindow().setAttributes(params);
                loading.show();
            }
        });

    }

    @JavascriptInterface
    public void dismissmask() {
        context.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (loading != null && loading.isShowing()) {
                    loading.dismiss();
                }
            }
        });

    }
}
