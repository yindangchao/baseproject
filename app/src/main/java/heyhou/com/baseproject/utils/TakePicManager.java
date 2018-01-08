package heyhou.com.baseproject.utils;

/**
 * 创建:yb 2016/10/14.
 * 描述:
 */

public class TakePicManager {
    private static TakePicManager instance;
    private TakePicManager() {
    }

    public static TakePicManager getInstance() {
        if (instance == null) {
            synchronized (TakePicManager.class) {
                if (instance == null) {
                    instance = new TakePicManager();
                }
            }
        }
        return instance;
    }
}
