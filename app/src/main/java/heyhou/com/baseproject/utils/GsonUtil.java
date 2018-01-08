package heyhou.com.baseproject.utils;

import com.google.gson.Gson;

/**
 * 创建:yb 2016/11/11.
 * 描述:
 */

public class GsonUtil {
    public static GsonUtil instance;
    private Gson gson;
    private GsonUtil() {
        gson = new Gson();
    }

    public static GsonUtil getInstance() {
        if (instance == null) {
            synchronized (GsonUtil.class) {
                if (instance == null) {
                    instance = new GsonUtil();
                }
            }
        }
        return instance;
    }

    public static Gson get() {
        return getInstance().gson;
    }
}
