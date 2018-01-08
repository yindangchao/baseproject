package heyhou.com.baseproject.base;

import android.os.Environment;

/**
 * @author time：2012-8-6 下午6:02:16 description:
 */
public class Configuration {

    // 是否输出LogCat调试日志
    public static final boolean IS_DEBUG_ENABLE = true;

    // 是否使用调试服务器
    public static final boolean IS_HTTP_DEBUG_ENABLE = false;

    // LogCat的TAG标签
    public static final String DEBUG_TAG = "FunnyTicket";

    // 保存的本地文件的默认文件夹
    public static String FILE_CACHE_PATH = Environment
            .getExternalStorageDirectory().getPath() + "/eacanMobile/";


}
