package heyhou.com.baseproject.utils;

import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;

/**
 * 创建:yb 2017/6/27.
 * 描述:heyhou跳转协议
 */

public class ProtocolHelper {
    private volatile static ProtocolHelper instance;
    private static final String HEY_HOU_SCHEME = "heyhou";
    private static final String HEY_HOU_QUERY_ID = "id";

    private ProtocolHelper() {
    }

    public static ProtocolHelper getInstance() {
        if (instance == null) {
            synchronized (ProtocolHelper.class) {
                if (instance == null)
                    instance = new ProtocolHelper();
            }
        }
        return instance;
    }

    public boolean isHeyHouScheme(String url) {
        return HEY_HOU_SCHEME.equals(Uri.parse(url).getScheme());
    }

    /**
     * heyhou协议
     * @return
     */
    public boolean isProtocol(Intent intent) {
        return intent.getAction() != null && intent.getAction().equals(Intent.ACTION_VIEW);
    }

    /**
     * 获取ID
     * @param intent
     * @return
     */
    public int obtainIdParam(Intent intent) {
        return getIntParam(intent,HEY_HOU_QUERY_ID);
    }

    public int getIntParam(Intent intent,String param) {
        int result = -1;
        String stringParam = getStringParam(intent,param);
        if (!TextUtils.isEmpty(stringParam)) {
            try {
                result = Integer.parseInt(stringParam);
            } catch (Exception e) {
                result = -1;
            }
        }
        return result;
    }

    public String getStringParam(Intent intent,String param) {
        Uri uri = intent.getData();
        String result = "";
        if (uri != null) {
            try {
                result = uri.getQueryParameter(param);
            } catch (NumberFormatException e) {
                result = "";
            }
        }
        return result;
    }



}
