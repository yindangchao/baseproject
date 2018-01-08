package heyhou.com.baseproject.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;
import android.net.Uri;
import android.os.Environment;
import android.widget.Toast;


import java.net.URI;
import java.net.URL;

import heyhou.com.baseproject.R;


/**
 * @ClassName: NetUtil
 * @Description:
 * @author: Liuqin
 * @date 2013-3-1 下午2:19:36
 */
public class NetUtil {
    private NetUtil() {
    }

    public static boolean isWifiOpen(Context context) {
        ConnectivityManager conManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifinfo = conManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (wifinfo != null) {
            State wifi = wifinfo.getState();
            if (wifi == State.CONNECTED || wifi == State.CONNECTING) {
                return true;
            }
        }
        return false;
    }

    public static boolean isNetworkAvailable(Context context) {
        if(context == null){
            return false;
        }
        ConnectivityManager connectivity = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo info = connectivity.getActiveNetworkInfo();
            if (info != null && info.isConnected())
            {
                // 当前网络是连接的
                if (info.getState() == NetworkInfo.State.CONNECTED)
                {
                    // 当前所连接的网络可用
                    return true;
                }
            }
        }
        return false;
    }


    public static boolean isNetworkAvailable(Context context, boolean isAllowConnecting) {
        // 获得网络系统连接服务
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        // 获得WIFI状态
        State state = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState();
        if (State.CONNECTED == state || (isAllowConnecting && State.CONNECTING == state)) {
            return true;
        }
        // 获取MOBILE状态
        state = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState();
        if (State.CONNECTED == state || (isAllowConnecting && State.CONNECTING == state)) {
            return true;
        }
        return false;
    }

    public static boolean isEnableDownload(Context context, boolean isToast) {
        boolean result = false;
        if (NetUtil.isWifiOpen(context)) {
            result = true;
        } else if (NetUtil.isNetworkAvailable(context, false)) {
            SharedPreferences preferences = context.getSharedPreferences("marketApp", Context.MODE_PRIVATE);
            result = preferences.getBoolean("wifi", false);
            if (!result && isToast) {
                Toast.makeText(context, R.string.down_error_disable_not_wifi, Toast.LENGTH_SHORT).show();
            }
        } else {
            if (isToast) {
                Toast.makeText(context, R.string.down_error_disconnect, Toast.LENGTH_SHORT).show();
            }
        }

        if (isSDCardUsable()) {
            result = false;
            if (isToast) {
                Toast.makeText(context, R.string.down_error_sd_not_mount, Toast.LENGTH_SHORT).show();
            }
        }

        return result;
    }

    public static boolean isSDCardUsable() {
        return Environment.MEDIA_MOUNTED.equalsIgnoreCase(Environment
                .getExternalStorageState());
    }

    public static URI getValidUri(String urlStr) {
        try {
            URL url = new URL(Uri.decode(urlStr));
            DebugTool.info("md", "decode url:" + url.toString());
            URI uri = new URI(url.getProtocol(), url.getUserInfo(), url.getHost(), url.getPort(), url.getPath(), url.getQuery(), url.getRef());
            return uri;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
}
