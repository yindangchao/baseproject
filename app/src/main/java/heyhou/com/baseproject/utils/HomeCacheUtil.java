package heyhou.com.baseproject.utils;

import android.content.SharedPreferences;
import android.text.TextUtils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import heyhou.com.baseproject.base.BaseApplication;

/**
 * Created by lky on 2017/1/3.
 *
 * @see #saveObject(CacheType, Object)                          保存一个对象
 * @see #getObject(CacheType)                                   获取一个对象
 */

public class HomeCacheUtil {


    public enum CacheType {
        HOME_CACHE_DATA_FILE_NAME("HOME_CACHE_DATA_FILE_NAME"),


        HOME_SAVE_KEY_STREET_CATEGORY_LIST("HOME_SAVE_KEY_STREET_CATEGORY_LIST"),
        HOME_SAVE_KEY_STREET_DAILY_HOT_LIST("HOME_SAVE_KEY_STREET_DAILY_HOT_LIST"),
        HOME_SAVE_KEY_STREET_VIDEO_LIST("HOME_SAVE_KEY_STREET_VIDEO_LIST"),
        HOME_SAVE_KEY_STREET_TIDAL_PAT_LIST("HOME_SAVE_KEY_STREET_TIDAL_PAT_LIST"),

        DISCOVER_SAVE_KEY_HEAD_DATA("DISCOVER_SAVE_KEY_HEAD_DATA"),
        DISCOVER_SAVE_KEY_USER_LIST("DISCOVER_SAVE_KEY_USER_LIST"),
        DISCOVER_SAVE_KEY_MEDIA_LIST("DISCOVER_SAVE_KEY_MEDIA_LIST"),
        DISCOVER_SAVE_KEY_HAS_DATA("DISCOVER_SAVE_KEY_HAS_DATA"),


        HOME_SAVE_KEY_CONCERN_NEW("HOME_SAVE_KEY_CONCERN_NEW"),//关注
        HOME_SAVE_KEY_NEWS_BANNER("HOME_SAVE_KEY_NEWS_BANNER"),//资讯banner
        HOME_SAVE_KEY_NEWS_DATA("HOME_SAVE_KEY_NEWS_DATA"),//资讯数据
        HOME_SAVE_KEY_SELECTION_MODULE("HOME_SAVE_KEY_MODULE"),//首页模型
        HOME_SAVE_KEY_TIDAL("HOME_SAVE_KEY_TIDAL"),//首页潮拍
        HOME_SAVE_KEY_TIDAL_NEW("HOME_SAVE_KEY_TIDAL_NEW"),//首页新潮拍
        HOME_SAVE_MUSIC_CLASSIFY("HOME_SAVE_MUSIC_CLASSIFY"),//音乐分类
        HOME_SAVE_MUSIC_LIST("HOME_SAVE_MUSIC_LIST"),//音乐列表
        HOME_SAVE_MUSIC_COLLECT_LIST("HOME_SAVE_MUSIC_COLLECT_LIST"),//音乐列表
        HOME_SAVE_KEY_SELECTION("HOME_SAVE_KEY_SELECTION"),
        HOME_SAVE_KEY_SELECTION_NEW("HOME_SAVE_KEY_SELECTION_NEW"),
        HOME_SAVE_KEY_CLASSIFY("HOME_SAVE_KEY_CLASSIFY"),
        HOME_SAVE_KEY_PLAZA_DATA("HOME_SAVE_KEY_PLAZA_DATA"),
        HOME_SAVE_KEY_CONCERN_DATA("HOME_SAVE_KEY_CONCERN_DATA"),
        HOME_SAVE_KEY_SELECTION_BANNER("HOME_SAVE_KEY_SELECTION_BANNER"),
        HOME_SAVE_KEY_PLAZA_BANNER("HOME_SAVE_KEY_PLAZA_BANNER"),
        HOME_SAVE_KEY_POST_BAR("HOME_SAVE_KEY_POST_BAR"),


        BRAND_SAVE_KEY_BANNER("BRAND_SAVE_KEY_BANNER"),
        BRAND_SAVE_KEY_TAG("BRAND_SAVE_KEY_TAG"),
        BRAND_SAVE_KEY_DATA("BRAND_SAVE_KEY_DATA"),

        MUSIC_SAVE_KEY_BANNER("MUSIC_SAVE_KEY_BANNER"),

        //附近的人缓存
        NEARBY_FRINED_DATA("NEARBY_FRINED_DATA"),

        MUSIC_SAVE_KEY_DATA("MUSIC_SAVE_KEY_DATA"),

        TIDAL_PAT_RECORD_CACHE("TIDAL_PAT_RECORD_CACHE"),

        RECORDING_STUDIO_CACHE("RECORDING_STUDIO_CACHE"),

        TIDAL_PAT_CHALLENGE_BANNER("TIDAL_PAT_CHALLENGE_BANNER"),

        TIDAL_PAT_RECOMMEND_VIDEO("TIDAL_PAT_RECOMMEND_VIDEO"),

        TIDAL_PAT_LATEST_VIDEO("TIDAL_PAT_LATEST_VIDEO"),

        RECORDING_STUDIO_GUIDE("RECORDING_STUDIO_GUIDE"),//进录音棚时候的提示
        RECORDING_STUDIO_GUIDE_TRACK("RECORDING_STUDIO_GUIDE_TRACK"),//录音过一次的提示
        RECORDING_STUDIO_GUIDE_M("RECORDING_STUDIO_GUIDE_M"),//点击M或S的提示
        RECORDING_STUDIO_GUIDE_S("RECORDING_STUDIO_GUIDE_S");//点击M或S的提示

        String value;

        CacheType(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }


    /**
     * 保存一个对象到本地
     *
     * @param cacheType
     * @param object
     */
    public static synchronized void saveObject(CacheType cacheType, Object object) {
        saveItem(cacheType.getValue(), object);
    }

    /**
     * 获取一个对象
     *
     * @param cacheType
     * @return
     */
    public static Object getObject(CacheType cacheType) {
        return getItem(cacheType.getValue());
    }

    public static void saveObjectEx(CacheType cacheType, Object obj) {
        saveItem(cacheType.getValue(), obj);
    }


//    public static void saveHomeClassify(List<VideoCate> videoCates) {
//        saveItem(CacheType.HOME_SAVE_KEY_CLASSIFY.getValue(), videoCates);
//    }
//
//    public static List<VideoCate> getHomeClassify() {
//        try {
//            List<VideoCate> videoCates = (List<VideoCate>) getItem(CacheType.HOME_SAVE_KEY_CLASSIFY.getValue());
//            return videoCates;
//        } catch (Exception e) {
//            return null;
//        }
//    }


    /**
     * 保存一个对象
     *
     * @param key 保存的键
     * @param obj 需要保存的对象
     * @return 是否保存成功
     */
    private static boolean saveItem(String key, Object obj) {
        try {
            saveObject(key, serialize(obj));
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 获取一个保存到了本地的对象
     *
     * @param key 保存的键
     * @return 返回一个从本地取出来的对象
     */
    private static Object getItem(String key) {
        try {
            return deSerialization(getObject(key));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        //出现异常返回null
        return null;
    }

    /**
     * 序列化对象
     *
     * @param obj 需要序列化的对象
     * @return 序列化过的对象，变成String
     * @throws IOException IO操作异常
     */
    public static String serialize(Object obj) throws IOException {
//        long startTime = System.currentTimeMillis();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(
                byteArrayOutputStream);
        objectOutputStream.writeObject(obj);
        String serStr = byteArrayOutputStream.toString("ISO-8859-1");
        serStr = java.net.URLEncoder.encode(serStr, "UTF-8");
        objectOutputStream.close();
        byteArrayOutputStream.close();
//        Log.d("serial", "serialize str =" + serStr);
//        long endTime = System.currentTimeMillis();
//        Log.d("serial", "序列化耗时为:" + (endTime - startTime));
        return serStr;
    }

    /**
     * 反序列化对象
     *
     * @param str 需要反序列化的对象的String
     * @return 返回一个反序列化过的对象
     * @throws IOException            IO操作的异常
     * @throws ClassNotFoundException 异常
     */
    public static Object deSerialization(String str) throws IOException,
            ClassNotFoundException {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
//        long startTime = System.currentTimeMillis();
        String redStr = java.net.URLDecoder.decode(str, "UTF-8");
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(
                redStr.getBytes("ISO-8859-1"));
        ObjectInputStream objectInputStream = new ObjectInputStream(
                byteArrayInputStream);
        Object obj = objectInputStream.readObject();
        objectInputStream.close();
        byteArrayInputStream.close();
//        long endTime = System.currentTimeMillis();
//        Log.d("serial", "反序列化耗时为:" + (endTime - startTime));
        return obj;
    }

    /**
     * 保存一个对象到SharedPreferences
     *
     * @param key       键
     * @param strObject 需要保存的序列化过的对象
     */
    private static void saveObject(String key, String strObject) {
        SharedPreferences sp = BaseApplication.m_appContext.getSharedPreferences(CacheType.HOME_CACHE_DATA_FILE_NAME.getValue(), 0);
        SharedPreferences.Editor edit = sp.edit();
        edit.putString(key, strObject);
        edit.commit();
    }

    /**
     * 获取到一个对象
     *
     * @param key 键
     * @return 序列化过的对象，取出后需要反序列化
     */
    private static String getObject(String key) {
        SharedPreferences sp = BaseApplication.m_appContext.getSharedPreferences(CacheType.HOME_CACHE_DATA_FILE_NAME.getValue(), 0);
        return sp.getString(key, null);
    }

    /**
     * 清除SharedPreferences里所有的信息
     */
    private static void clearAllSharedPreferencesData() {
        SharedPreferences sp = BaseApplication.m_appContext.getSharedPreferences(CacheType.HOME_CACHE_DATA_FILE_NAME.getValue(), 0);
        SharedPreferences.Editor edit = sp.edit();
        edit.clear();
    }

}
