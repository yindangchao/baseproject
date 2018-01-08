package heyhou.com.baseproject.network.ex;

import android.content.Context;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import heyhou.com.baseproject.utils.AppUtil;
import heyhou.com.baseproject.utils.DebugTool;

/**
 * Created by Neil.Yang on 2017/11/7.
 */

public final class HttpUrlMap {

    private static final String TAG = "HttpUrlMap";

    private static final String API_FILE_NAME = "interface-file.json";

    private static HttpUrlMap sInstance;

    private Map<String, String> mUrlMap = null;

    private HttpUrlMap() {
        init();
    }
    public static final HttpUrlMap getInstance() {
        if (sInstance == null) {
            synchronized (HttpUrlMap.class) {
                if (sInstance == null) {
                    sInstance = new HttpUrlMap();
                }
            }
        }
        return sInstance;
    }

    private void init() {
        try {
            Context context = AppUtil.getApplicationContext();
            InputStream is = context.getAssets().open(API_FILE_NAME);
            StringBuilder sb = new StringBuilder();
            byte[] buffer = new byte[512];
            int readLen = is.read(buffer);
            while (readLen > 0) {
                sb.append(new String(buffer, 0, readLen));
                readLen = is.read(buffer);
            }
            is.close();

            TypeReference<Map<String, String>> type = new TypeReference<Map<String, String>>(){};
            mUrlMap = JSON.parseObject(sb.toString(), type);
            DebugTool.debug(TAG, "MAP SIZE : " + mUrlMap.size());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getUrl(String key) {
        if (mUrlMap != null) {
            return mUrlMap.get(key);
        }
        return null;
    }

}
