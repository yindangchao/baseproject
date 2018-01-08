package heyhou.com.baseproject.utils;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;

import heyhou.com.baseproject.base.BaseApplication;
import heyhou.com.baseproject.base.Constant;


/**
 * Created by lky on 2017/2/20.
 */

public class AppUtil {

    private static final String TAG = "AppUtil";

    public static Context getApplicationContext(){
        return BaseApplication.m_appContext;
    }


    /**
     * 开发版本的配置
     *  URL的类型， 1 ：表示开发环境
     *              2 ： 测试环境
     *              3 ： 线上环境
     * **/
    public static int getUrlType() {
        Context context = getApplicationContext();
        int type = 3; //默认给线上的地址
        try {
            ApplicationInfo appInfo = context.getPackageManager().getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
            type = appInfo.metaData.getInt("URL_TYPE");
        } catch (Exception e) {
            e.printStackTrace();
            type = Constant.URL_TYPE_ONLINE;
        }
        return type;
    }


    public static String getChannel() {
        Context context = getApplicationContext();
        String channel = "heyhou";
        try {
            ApplicationInfo appInfo = context.getPackageManager().getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
            channel = appInfo.metaData.getString("UMENG_CHANNEL");
            DebugTool.debug(TAG, "UMENG_CHANNEL=" + channel);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return channel;
    }


    public static String getString(int strRes){
        return getApplicationContext().getString(strRes);
    }

    public static int getColor(int colorRes){
        return getApplicationContext().getResources().getColor(colorRes);
    }
}
