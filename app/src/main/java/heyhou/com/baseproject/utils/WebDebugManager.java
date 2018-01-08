package heyhou.com.baseproject.utils;

import android.os.Build;
import android.webkit.WebView;

/**
 * Created by 1 on 2017/3/31.
 */
public class WebDebugManager {

    public static void setWebsiteDebugable(WebView webView) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {

            WebView.setWebContentsDebuggingEnabled(true);

        }
    }
}
