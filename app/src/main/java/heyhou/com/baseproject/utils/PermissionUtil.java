package heyhou.com.baseproject.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

/**
 * 创建:yb 2016/11/26.
 * 描述:
 */

public class PermissionUtil {
    public void permission(Activity activity, int code) {
        //检查权限
        if (ContextCompat.checkSelfPermission(activity, Manifest.permission.CALL_PHONE)
                != PackageManager.PERMISSION_GRANTED) {
            //进入到这里代表没有权限.
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.CALL_PHONE}, code);
        } else {

        }
    }

    public boolean hasPermission(Context context,String permission) {
        return ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED;
    }
}
