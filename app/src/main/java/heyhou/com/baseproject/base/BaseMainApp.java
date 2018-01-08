package heyhou.com.baseproject.base;

import android.app.Activity;

import java.util.List;


public class BaseMainApp {

    private static BaseMainApp instance;

    private List<Activity> activityList;
    /**
     * 是否登录的标志位
     */
    public boolean isLogin = false;
    /**
     * 用户MID
     */
    public String uid = "";
    public String token = "";
    public String mCity = "";
    public int ratio = 0;
    public int poundage = 0;
    public double lat = 0d;
    public double lon = 0d;
    public int friendRequestCount = 0;//是否给运动发送消息
    public int easeCount = 0;//是否给运动发送消息

    public static synchronized BaseMainApp getInstance() {
        if (null == instance) {
            instance = new BaseMainApp();
        }

        return instance;

    }


    /**
     * 清除用户登录信息
     */
    public void resetUserInfo() {
        isLogin = false;
        uid = "";
        // user = null;
    }


    /**
     * 遍历所有Activity并finish
     */
    public void exit() {
        // 释放资源(调用finish方法可以调用activity相关资源释放接口)
        if (activityList != null) {
            for (Activity activity : activityList) {
                activity.finish();
            }
        }
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(1);
    }

    /**
     * App退出时擦除对象
     */
    public void resetInstance() {
        if (null != instance) {
            instance = null;
        }
    }

}
