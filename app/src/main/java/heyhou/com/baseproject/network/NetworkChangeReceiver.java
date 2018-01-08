package heyhou.com.baseproject.network;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import heyhou.com.baseproject.utils.DebugTool;


/**
 * Created by Neil.Yang on 2017/3/16.
 */

public class NetworkChangeReceiver extends BroadcastReceiver{
    public static final String TAG = "NetworkChangeReceiver";
    @Override
    public void onReceive(Context context, Intent intent) {
        DebugTool.warn(TAG, intent.getAction() + ", hashCode : " + hashCode());
        new Thread(new Runnable() {
            @Override
            public void run() {
                NetworkUtils.checkNetworkAvailable();
            }
        }).start();
    }
}
