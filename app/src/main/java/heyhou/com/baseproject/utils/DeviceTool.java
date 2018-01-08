package heyhou.com.baseproject.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.TextUtils;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

import heyhou.com.baseproject.base.BaseApplication;

/**
 * Created by 1 on 2016/7/2.
 */
public class DeviceTool {
    private static final String SP_GETED_INFO = "SP_GETED_INFO";
    private static final String SP_IS_GETED = "SP_IS_GETED";
    private static final String SP_GETED_STR = "SP_GETED_STR";

    private static final String SP_DEVICE_ID = "SP_DEVICE_ID";
    private static final String DEFAULT_DEVICE_INFO = "sys:android|HUAWEI|6.0,app:v2.0";

    private static final String TAG = "DeviceTool";

    private static String CHANNEL = null;

    public static void clearSp() {
        SharedPreferences sp = BaseApplication.m_appContext.getSharedPreferences(SP_GETED_INFO, Context.MODE_PRIVATE);
        sp.edit().clear().commit();
    }

    public static String getHttpHeadValue(Context context) {
        SharedPreferences sp = context.getSharedPreferences(SP_GETED_INFO, Context.MODE_PRIVATE);
        if (sp.getBoolean(SP_IS_GETED, false)) {
            return sp.getString(SP_GETED_STR, DEFAULT_DEVICE_INFO);
        }
        String str = "sys:android|";
        String deviceType = getDevideTypeInfo();
        String systemVersion = getSysVersion();
        String appVersion = getAppVersion(context);
        String getedDeviceInfo = str + deviceType + "|" + systemVersion + ",app:" + appVersion;
        DebugTool.error("deviceType", deviceType);
        sp.edit().putBoolean(SP_IS_GETED, true).putString(SP_GETED_STR, getedDeviceInfo).commit();
        return getedDeviceInfo;

    }

    public static boolean isContainChinese(String str) {

        Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
        Matcher m = p.matcher(str);
        if (m.find()) {
            return true;
        }
        return false;
    }

    public static String getChannel() {
        if (TextUtils.isEmpty(CHANNEL)) {
            CHANNEL = AppUtil.getChannel();
            DebugTool.debug(TAG, "CHANNEL=" + CHANNEL);
        }
        return CHANNEL;
    }


    private static String DEVICE_ID = null;

    public static synchronized String getImei(Context context) {

        if (!TextUtils.isEmpty(DEVICE_ID)) {
            return DEVICE_ID;
        }

        SharedPreferences sp = context.getSharedPreferences(SP_GETED_INFO, Context.MODE_PRIVATE);
        //从sharedpreference 中读取deviceid
        if (TextUtils.isEmpty(DEVICE_ID)) {
            DEVICE_ID = sp.getString(SP_DEVICE_ID, null);
            DebugTool.debug(TAG, "read device id from SharedPreference : " + DEVICE_ID);
            if (!TextUtils.isEmpty(DEVICE_ID)) {
                return DEVICE_ID;
            }
        }

        //获取device id，
        try {
            TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            if (tm != null) {
                DEVICE_ID = tm.getDeviceId();
                DebugTool.debug(TAG, "use IMEI as device id : " + DEVICE_ID);
            }

        } catch (Exception e) {
            e.printStackTrace();
            DEVICE_ID = null;
        }

        //尝试获取mac地址
        if (TextUtils.isEmpty(DEVICE_ID)) {
            try {
                WifiManager wifi = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
                if (wifi != null) {
                    WifiInfo info = wifi.getConnectionInfo();
                    if (info != null) {
                        DEVICE_ID = info.getMacAddress();
                        DebugTool.debug(TAG, "use mac address as device id : " + DEVICE_ID);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                DEVICE_ID = null;
            }
        }

        //使用android id 作为设备的标识符
        if (TextUtils.isEmpty(DEVICE_ID)) {
            try {
                DEVICE_ID = android.provider.Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
                DebugTool.debug(TAG, "use ANDROID ID as device id : " + DEVICE_ID);
            } catch (Exception e) {
                e.printStackTrace();
                DEVICE_ID = null;
            }
        }

        //自己生成deviceid
        if (TextUtils.isEmpty(DEVICE_ID)) {
            String version = Build.VERSION.RELEASE;
            String manufacture = Build.BRAND;
            DebugTool.debug(TAG, "version: " + version + ", manufacture=" + manufacture);
            String random = String.valueOf((int) (Math.random() * 100));
            String src = version + manufacture + System.currentTimeMillis() + random;
            DebugTool.debug(TAG, "src: " + src);
            DEVICE_ID = BasicTool.getMd5(src);
            DebugTool.debug(TAG, "Auto Generate Device Id: " + DEVICE_ID);
        }

        SharedPreferences.Editor editor = sp.edit();
        editor.putString(SP_DEVICE_ID, DEVICE_ID);
        editor.commit();

        return DEVICE_ID;
    }

    //    public static String getMac() {
//        return NetworkInterface.
//    }
    private static String getDevideTypeInfo() {
        String deviceTypeInfo = getDeviceBrand() + "-" + getDeviceType();
        if (isContainChinese(deviceTypeInfo)) {
            return "other";
        }
        return deviceTypeInfo;
    }

    public static String getDeviceBrand() {
        String brand = android.os.Build.BRAND;
        return brand;
    }

    public static String getDeviceType() {
        return android.os.Build.MODEL;
    }

    public static String getSysVersion() {
        return android.os.Build.VERSION.RELEASE;
    }

    public static String getAppVersion(Context context) {
        try {
            PackageManager manager = context.getPackageManager();
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
            String version = info.versionName;
            return version;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }


}
