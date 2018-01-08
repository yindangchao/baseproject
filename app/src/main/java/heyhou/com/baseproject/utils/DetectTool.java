package heyhou.com.baseproject.utils;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.view.inputmethod.InputMethodManager;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;


/**
 * @author LYM
 * @function 检测工具类
 */
public class DetectTool {
    static TelephonyManager tm;

    /**
     * 检测当前网络状态
     *
     * @param act 上下文对象
     * @return
     */
    public static boolean getNetState(Activity act) {
        ConnectivityManager manage = (ConnectivityManager) act.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);

        if (null == manage) {
            return false;
        }

        NetworkInfo networkinfo = manage.getActiveNetworkInfo();

        return !(null == networkinfo || !networkinfo.isAvailable());

    }

    public static int getVersionSdk() {
        return android.os.Build.VERSION.SDK_INT;
    }

    /**
     * 获取版本名称(非版本号versoinCode)
     *
     * @param act 上下文对象
     * @return
     */
    public static String getVersionCode(Activity act) {

        PackageManager manager = act.getPackageManager();

        PackageInfo packageInfo = null;

        try {
            packageInfo = manager.getPackageInfo(act.getPackageName(), 0);

        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }

        if (null != packageInfo) {
            return packageInfo.versionName;
        }

        return null;

    }

    /**
     * 获取时间戳
     *
     * @return
     */
    public static String getTS() {
        return System.currentTimeMillis() + "";

    }

    /**
     * 获取手机唯一串号IMEI
     *
     * @param context
     * @return imei
     */
    public static String getIMEI(Context context) {
        if (null == tm) {
            tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        }

        return tm.getDeviceId();

    }


    public static String getSign(HashMap<String, String> params) {

        // 先将参数以其参数名的字典序升序进行排序
        Map<String, String> sortedParams = new TreeMap<String, String>(params);
        Set<Entry<String, String>> entrys = sortedParams.entrySet();


        // 遍历排序后的字典，将所有参数按"key=value"格式拼接在一起
        StringBuilder basestring = new StringBuilder();
        for (Entry<String, String> param : entrys) {
            basestring.append(param.getKey()).append("=").append(param.getValue());
        }
        /*****************对排序后的参数进行MD5散列函数运算***********************/
        byte[] hash;

        try {
            hash = MessageDigest.getInstance("MD5").digest(basestring.toString().getBytes("UTF-8"));
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Huh, MD5 should be supported?", e);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("Huh, UTF-8 should be supported?", e);
        }

        StringBuilder hex = new StringBuilder(hash.length * 2);
        for (byte b : hash) {
            if ((b & 0xFF) < 0x10) hex.append("0");
            hex.append(Integer.toHexString(b & 0xFF));
        }
        /*****************对排序后的参数进行MD5散列函数运算***********************/
        //返回md5加密后的字符串注意统一转化为大写
        return hex.toString().toUpperCase();

    }

    /**
     * 获取屏幕横向(宽度)分辨率
     *
     * @param context
     * @return
     */
    public static int getResolutionX(Context context) {
        DisplayMetrics mDisplayMetrics = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(mDisplayMetrics);

        int width = mDisplayMetrics.widthPixels;


        return width;
    }

    /**
     * 根据屏幕宽度获得订单列表item对应的的图片数量
     */
    public static int getImageNum(Context context) {
        int width = getResolutionX(context);
        int itemWidth = DensityUtils.dp2px(context, 60);
        int itemWidth1 = DensityUtils.dp2px(context, 30);
        int num = (width - (itemWidth + itemWidth1)) / itemWidth;
        return num;
    }

    /**
     * 获取屏幕纵向(高度)分辨率
     *
     * @param context
     * @return
     */
    public static int getResolutionY(Context context) {
        DisplayMetrics mDisplayMetrics = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(mDisplayMetrics);

        int height = mDisplayMetrics.heightPixels;


        return height;
    }

    /**
     * 如果软键盘打开状态，隐藏软键盘。
     *
     * @param activity 上下文对象
     */
    public static void hideSoftInput(Activity activity) {
        InputMethodManager mm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);

        if (mm.isActive()) {
            mm.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    /**
     * 获取凭证(Token)
     *
     * @return
     */
    public static String getToken() {
        return "100";
    }

    /**
     * 写死的版本号，对应versionName
     *
     * @return
     */
    public static String getVersionName() {
        return "1.0.0";
    }


    /**
     * 获取设备类型，1-Android，2-IOS
     *
     * @return
     */
    public static String getType() {
        return "1";
    }


}
