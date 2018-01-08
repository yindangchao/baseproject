package heyhou.com.baseproject.network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import heyhou.com.baseproject.utils.AppUtil;


/**
 * Created by Neil.Yang on 2017/3/16.
 */

public class NetworkUtils {

    private static boolean mNetworkAvailable = false;
    public static boolean isNetworkAvailable() {
        if (!mNetworkAvailable) {
            checkNetworkAvailable();
        }
        return mNetworkAvailable;
    }

    public static void checkNetworkAvailable() {
        Context context = AppUtil.getApplicationContext();
        ConnectivityManager connManager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connManager != null) {
            NetworkInfo networkInfo = connManager.getActiveNetworkInfo();
            if(networkInfo!= null) {

                mNetworkAvailable = networkInfo.isConnected();
                return;
            }
        }
        mNetworkAvailable = false;
    }

}
