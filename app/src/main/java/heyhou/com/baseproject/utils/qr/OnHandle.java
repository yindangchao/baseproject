package heyhou.com.baseproject.utils.qr;

import android.os.Handler;

/**
 * 创建:yb 2016/11/21.
 * 描述:
 */

public interface OnHandle {
    void handleDecode(String result);
    Handler getHandler();
    int getX();
    int getY();
    int getCropWidth();
    int getCropHeight();
    boolean isNeedCapture();
}
