package heyhou.com.baseproject.base;

import android.os.Environment;

import java.io.File;

import heyhou.com.baseproject.utils.AppUtil;

/**
 * @author ydc
 * @Description:设置Ip地址和端口号
 * @date 2012-12-4 下午2:42:57
 */
public class Constant {


    /**
     * 开发版本的配置
     * URL的类型， 1 ：表示开发环境
     * 2 ： 测试环境
     * 3 ： 线上环境
     **/
    public static final int URL_TYPE_DEV = 1;
    public static final int URL_TYPE_TEST = 2;
    public static final int URL_TYPE_ONLINE = 3;
    public static final int URL_TYPE_HUIDU = 4;

    /**
     * 音乐上传的地址， 分为开发，测试，和线上，
     */
    //音乐上传的开发环境地址
    public static final String MUSIC_UPLOAD_ADDRESS_DEV = "lmusic.heyhou.com";
    //音乐上传的测试环境地址
    public static final String MUSIC_UPLOAD_ADDRESS_TEST = "tmusic.heyhou.com";
    //音乐上传的线上环境地址
    public static final String MUSIC_UPLOAD_ADDRESS_ONLINE = "music.heyhou.com";

    /**
     * 接口的地址， 分为开发，测试，和线上，
     */
    //开发环境的地址
    public static final String BASE_SERVER_DEV = "http://192.168.1.130/";
    //测试环境的地址
    public static final String BASE_SERVER_TEST = "http://tapi.heyhou.com/";
    //线上环境的地址
    public static final String BASE_SERVER_ONLINE = "http://api2.heyhou.com/";

    //灰度环境的地址
    public static final String BASE_SERVER_HUIDU = "http://tt.heyhou.com/";

    /**
     * h5的地址， 分为开发，测试，和线上，
     */
    //h5的开发环境
    public static final String BASE_H5_DEV = "http://lm.heyhou.com/";
    //h5的测试环境
    public static final String BASE_H5_TEST = "http://tm.heyhou.com/";
    //h5的线上环境
    public static final String BASE_H5_ONLINE = "http://m.heyhou.com/";
    //灰度环境的地址
    public static final String BASE_H5_HUIDU = "http://m2.heyhou.com/";


    public static final boolean online = isOnline();

    public static final int NO_NET_CODE = -100;

    /**
     * 判断当前是否是线上环境
     */
    private static boolean isOnline() {
        int type = AppUtil.getUrlType();
        return (type == URL_TYPE_ONLINE);
    }

    /**
     * 获取音乐上传的地址
     */
    private static String getMusicUploadAddress() {
        int type = AppUtil.getUrlType();
        String address = MUSIC_UPLOAD_ADDRESS_ONLINE;
        switch (type) {
            case URL_TYPE_DEV:
                address = MUSIC_UPLOAD_ADDRESS_DEV;
                break;
            case URL_TYPE_TEST:
                address = MUSIC_UPLOAD_ADDRESS_TEST;
                break;
            case URL_TYPE_ONLINE:
                address = MUSIC_UPLOAD_ADDRESS_ONLINE;
                break;
            case URL_TYPE_HUIDU:
                address = MUSIC_UPLOAD_ADDRESS_ONLINE;
                break;
        }
        return address;
    }

    //红点或数字提示切换
    public static final boolean MESSAGE_IS_USE_ONLY_POINT = false;

    public static final String MUSIC_UPLOAD_ADDRESS = getMusicUploadAddress();

    public static final String COMMON_HTTP_HEAD_KEY = "X-Heyhou-Header";
    public static final String COMMON_HTTP_DEVICE_KEY = "X-Heyhou-DeviceId";
    public static final String COMMON_HTTP_CHANNEL_KEY = "X-Heyhou-ChannelId";
//    public static final String COMMON_HTTP_HEAD_VALUE = online ? "online" : "dev";


    public static final String BUGLY_ONLINE_APP_ID = "608300288b";
    public static final String BUGLY_TEST_APP_ID = "900037598";
    public static final String BUGLY_APP_ID = AppUtil.getUrlType() == URL_TYPE_ONLINE ? BUGLY_ONLINE_APP_ID : BUGLY_TEST_APP_ID;

    //   新的App ID 和APP key 微信的
    //    AppID: wx270e3984d3114e01
    //    AppSecret 7400ccb464246963afb629ea92f83108

//    public static final String WECHAT_APP_ID = "wx270e3984d3114e01";
//    public static final String WECHAT_APP_KEY = "7400ccb464246963afb629ea92f83108";

    //统一使用旧的key
    public static final String WECHAT_APP_ID = "wx7ac3f169c7d39e56";
    public static final String WECHAT_APP_KEY = "4007cad9d97926db586c77c52c0c335d";

    //  旧的key
    public static final String OLD_WECHAT_APP_ID = "wx7ac3f169c7d39e56";
    public static final String OLD_WECHAT_APP_KEY = "4007cad9d97926db586c77c52c0c335d";


    public static final String QQ_LOGIN_ID = "1105538460";
    public static final String QQ_LOGIN_KEY = "wJCq7i1q3oHG3uHF";

    public static final String SINA_LOGIN_ID = "c148ec67f6cd87d21d6f92c65b08dafb";
    public static final String SINA_LOGIN_KEY = "2721666250";

    //    heyhou_
    public static final String XG_PUSH_UID_FRONT = "heyhou_";

    public static final String PRIVATE_KEY = "133e6826b8b5bbdb4c3a9f396f65b1e4";

    public static final String PKG_NAME = "com.heyhou.social";

    public static final String TEST_IMG_DNS = "http://ofal8iyjp.bkt.clouddn.com";
    public static final String TEST_IMG = TEST_IMG_DNS + "/04.png";
    public static final String TEST_HEAD_IMG = TEST_IMG_DNS + "/head_demo.png";
    public static final String[] TEST_IMG_LIST = {TEST_IMG_DNS + "/01.png", TEST_IMG_DNS + "/02.png"
            , TEST_IMG_DNS + "/03.png", TEST_IMG_DNS + "/04.png"};

    // 腾讯云地址
//    public static final String BASE_TEST_ONLINE_SERVER = "http://ttjh.f3322.net:82/";
//    public static final String BASE_TEST_ONLINE_SERVER = "http://192.168.1.130/";
//    public static final String BASE_TEST_ONLINE_SERVER = "http://tapi.heyhou.com/";
//    public static final String BASE_TEST_ONLINE_SERVER = "https://sapi.heyhou.com/";
    //     线上地址
    //public static final String BASE_LINE_ONLINE_SERVER = "http://api2.heyhou.com/";

    /**
     *  各个环境的url
     * */

    /**
     * 获取接口的地址
     */
    private static String getInterfaceAddress() {
        int type = AppUtil.getUrlType();
        String address = BASE_SERVER_ONLINE;
        switch (type) {
            case URL_TYPE_DEV:
                address = BASE_SERVER_DEV;
                break;
            case URL_TYPE_TEST:
                address = BASE_SERVER_TEST;
                break;
            case URL_TYPE_ONLINE:
                address = BASE_SERVER_ONLINE;
                break;
            case URL_TYPE_HUIDU:
                address = BASE_SERVER_HUIDU;
                break;
        }
        return address;
    }

    //本地测试地址
    public static final String BASE_ONLINE_SERVER = getInterfaceAddress();

    //h5地址
    //public static final String BASE_TEST_ONLINE_H5_URL = "http://lm.heyhou.com/";
    //public static final String BASE_ONLINE_H5_URL = "http://m.heyhou.com/";


    /**
     * 获取H5页面的地址
     */
    private static String getH5Address() {
        int type = AppUtil.getUrlType();
        String address = BASE_H5_ONLINE;
        switch (type) {
            case URL_TYPE_DEV:
                address = BASE_H5_DEV;
                break;
            case URL_TYPE_TEST:
                address = BASE_H5_TEST;
                break;
            case URL_TYPE_ONLINE:
                address = BASE_H5_ONLINE;
                break;
            case URL_TYPE_HUIDU:
                address = BASE_H5_HUIDU;
                break;
        }
        return address;
    }

    public static final String BASE_H5_HOST = getH5Address();


    //数据库版本
    public static final int DATABASE_VERSION = 56;

    public static final int RETURN_TYPE_LIST = 1;
    public static final int RETURN_TYPE_OBJECT = 2;
    public static final int RETURN_TYPE_JSON = 3;
    public static final int RETURN_TYPE_JSONARRAY = 4;

    public static final String SP_PHONE_NUMBER = "phone_number";
    public static final String SP_USER = "userSp";
    public static final String SP_USER_NAME = "userName";
    public static final String SP_USER_PWD = "userPwd";
    public static final String SP_USER_IS_LOGIN = "userIsLogin";

    public static final String SP_SETTING_NAME = "settingSp";
    public static final String SP_SETTING_HAS_OPEN = "hasOpen";
    public static final String SP_SETTING_CACHE_LOCATION = "cacheLocation";

    public static final String SP_LBS_SETTING_ADDRESS_LIMIT = "lbs_setting_address_limit";
    public static final String SP_LBS_GPS = "lbs_gps";
    public static final String SP_LBS_GPS_LON = "lbs_gps_lon";
    public static final String SP_LBS_GPS_LAT = "lbs_gps_lat";
    public static final String SP_LBS_GPS_CITY = "lbs_gps_city";
    public static final String SP_LBS_GPS_PROVINCE = "lbs_gps_province";
    public static final String SP_GUIDE = "spGuide";
    public static final String SP_IS_FIRST_IN = "isFirstIn";
    public static final String SP_GUIDE_TYPE = "guide_type";

    public static final long COUNT_TIME = 60 * 1000;
    public static final long COUNT_UNIT = 1000;

    public static final String SELECT_TIME_END = "2036-1-1 00:00";

    public static final String UP_DEVICE_SP = "upDeviceSp";
    public static final String HAS_UP_DEVICE = "hasUpDevice";

    public static final int CONNECTION_FAIL_CODE = -1008;

    public static final String NOLOADING = "no_loading";

    public static final int CHECK_VERSION_AUTO = 0;
    public static final int CHECK_VERSION_BY_USER = 1;

    public static final int TYPE_GIFT = 200;
    public static final int TYPE_LIVE_TIME_OUT = 202;
    public static final int TYPE_VIP_IN = 300;

    public static final int IMG_TYPE_HEAD = 1;
    public static final int IMG_TYPE_LANDSCAPE = 2;
    public static final int IMG_TYPE_PORTRAIT = 3;
    public static final int IMG_TYPE_CIRCLE = 4;
    public static final int IMG_TYPE_LANDSCAPE_ROUND = 5;
    public static final int IMG_TYPE_PORTRAIT_ROUND = 6;
    public static final int IMG_TYPE_HEAD_ROUND = 7;

    public static final String APP_LOCAL_DIR = "/heyhou";
    public static final String PIC_FILE = Environment.getExternalStorageDirectory().getAbsolutePath() + APP_LOCAL_DIR;
    public static final String DIC_FILE = BaseApplication.m_appContext.getCacheDir().getAbsolutePath() + APP_LOCAL_DIR;
    public static final String VIDEO_FILE = "record_video";
    public static final String DOWNLOAD_CACHE = "download";
    public static final String MIXTURE = PIC_FILE + File.separator + "mixture";
    public static final String MIXTURE_PATH = MIXTURE + File.separator + "resource";
    public static final String MIXTURE_ZIP_PATH = MIXTURE + File.separator + "zip";
    public static final String MIXTURE_RECORD_PATH = MIXTURE + File.separator + "record";
    public static final String MIXTURE_VOICE_PATH = MIXTURE + File.separator + "mix_voice";
    public static final String LOCAL_VOICE_PATH = DIC_FILE + File.separator + "voice";
    public static final String RECORD_VIDEO_PATH_TEMP = DIC_FILE + File.separator + "record_video";
    public static final String RECORD_VIDEO_PATH = PIC_FILE + File.separator + "record_video";
    public static final String RECORD_VIDEO_TEMP_PATH = PIC_FILE + File.separator + "record_video_temp";
    public static final String RECORD_AUDIO_PATH_TEMP = PIC_FILE + File.separator + "record_audio";
    public static final String RECORD_AUDIO_CACHE_PATH_TEMP = DIC_FILE + File.separator + "record_audio_cache";
    public static final String INDEX_CACHE_PATH = PIC_FILE + File.separator + "index_res";
    public static final String SPEED_AUDIO_CACHE_PATH = DIC_FILE + File.separator + "speed_audio";
    public static final String CUT_AUDIO_CACHE_PATH = PIC_FILE + File.separator + "cut";
    public static final String CROP_PHOTO_CACHE_PATH = PIC_FILE + File.separator + "crop";
    public static final String RECORD_CROP_PHOTO_CACHE_PATH = DIC_FILE + File.separator + "record_crop";
    public static final String MUSIC_DOWNLOAD_PATH = PIC_FILE + File.separator + "music" + File.separator + "download";
    public static final String VIDEO_PATH = PIC_FILE + File.separator + VIDEO_FILE;
    public static final String DOWNLOAD_PATH = PIC_FILE + File.separator + DOWNLOAD_CACHE;
    public static final String IMAGE_SAVE_PATH = PIC_FILE + File.separator + "image";
    public static final String MUSIC_CLOUD_DOWNLOAD_PATH = PIC_FILE + File.separator + DOWNLOAD_CACHE + File.separator + "music_cloud";
    public static final String VIDEO_CACHE_PATH = BaseApplication.m_appContext.getFilesDir().getAbsolutePath() + File.separator + "video_cache";
    public static final String PIC_UPLOAD_LOG_PATH = BaseApplication.m_appContext.getFilesDir().getAbsolutePath() + File.separator + "log";

    //任务中心组成md5Sum的key
    public static final String TASK_CENTER_KEY = "KEY";

    //battl超级用户
    public static final String BATTLE_SUPER_USER = "1000016";


    public static final String HIPHOP_MALL_H5 = "http://lm.heyhou.com/hiphop-mall.html";
    public static final String HEYHOU_TICKET_H5 = "http://lm.heyhou.com/ticket-list.html";

    public static final String HEYHOU_GOOD_DETAIL_H5 = "http://lm.heyhou.com/hiphop-mall-detail.html?id=";
    public static final String HEYHOU_TICKET_DETAIL_H5 = "http://lm.heyhou.com/ticket-detail.html?id=";
    public static final String HEYHOU_TICKET_BUY_H5 = "http://lm.heyhou.com/ticket-buy.html?id=";
    public static final String HEYHOU_ACTION_DETAIL_H5 = "http://lm.heyhou.com/activity-detail.html?id=";
    //众筹  http://lm.heyhou.com/crowdfunding-detail.html?id={id}
    public static final String HEYHOU_FUNDING_H5 = "http://lm.heyhou.com/crowdfunding-list.html";
    public static final String HEYHOU_FUNDING_DETAIL_H5 = "http://lm.heyhou.com/crowdfunding-detail.html?id=";

    //fAQ
    public static final String HEYHOU_FAQ_H5 = "http://lm.heyhou.com/faq.html";


    //七牛缩略图策略
    public static final String BITMAP_THUMBNAIL_STR_DEFAULT = "?imageMogr2";
    public static final String BITMAP_THUMBNAIL_STR = BITMAP_THUMBNAIL_STR_DEFAULT + "/auto-orient/thumbnail/100x/format/jpg";
    public static final String BITMAP_THUMBNAIL_STR_300X = BITMAP_THUMBNAIL_STR_DEFAULT + "/auto-orient/thumbnail/300x/format/jpg";
    public static final String BITMAP_THUMBNAIL_STR_660X = BITMAP_THUMBNAIL_STR_DEFAULT + "/auto-orient/thumbnail/660x/format/jpg";
    public static final String BITMAP_THUMBNATL_STR_ORIGINAL = BITMAP_THUMBNAIL_STR_DEFAULT + "/auto-orient/format/jpg";

    public static final String BREAK_POINT_PATH = DIC_FILE + File.separator + "qiniu" + File.separator + "break_point";

    //点赞抽奖活动
    public static final String HEYHOU_ACTION_H5 = "http://lm.heyhou.com/faq.html";

    //用户登录信息过期的广播
    public static final String ACTION_LOGIN_INFO_OUT_OF_DATE = "com.heyhou.social.ACTION_LOGIN_INFO_OUT_OF_DATE";

}
