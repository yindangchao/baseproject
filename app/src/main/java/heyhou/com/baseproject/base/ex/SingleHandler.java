package heyhou.com.baseproject.base.ex;


import android.os.Handler;

/**
 * Created by admin on 2017/2/22.
 */

public class SingleHandler extends Handler {

    private static SingleHandler sInstance;

    private SingleHandler() {
    }

    public static SingleHandler getsInstance() {
        if (sInstance == null) {
            synchronized (SingleHandler.class) {
                if (sInstance == null) {
                    sInstance = new SingleHandler();
                }
            }
        }

        return sInstance;
    }

}
