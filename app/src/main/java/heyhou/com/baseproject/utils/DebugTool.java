package heyhou.com.baseproject.utils;
import android.text.TextUtils;


import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.IOException;

import de.mindpipe.android.logging.log4j.LogConfigurator;
import heyhou.com.baseproject.base.Constant;

/**
 * @author wsd
 * @Description:调试工具类
 * @date 2012-12-3 下午1:52:27
 */
public class DebugTool {

    //默认的日志大小为30M
    private static final int LOG_FILE_SIZE = 30 * 1024 * 1024;

    private static Logger mLogger;

    private static Level mLevel = Constant.online ?  Level.OFF  : Level.ALL;

    /**
     * 打印调试信息.
     *
     * @param msg
     */
    @Deprecated
    public static void debug(String msg) {
        debug(null, msg);
    }

    /**
     * 打印调试信息.
     *
     * @param msg
     */
    public static void debug(String tag, String msg) {
        Logger log = getLogger(tag);
        log.debug(msg);
    }

    /**
     * 打印警告信息.
     *
     * @param msg
     */
    @Deprecated
    public static void warn(String msg) {
        warn(null, msg);
    }

    /**
     * 打印警告信息.
     *
     * @param msg
     */
    public static void warn(String tag, String msg) {
        Logger log = getLogger(tag);
        log.warn(msg);
    }

    /**
     * 打印提示信息.
     *
     * @param msg
     */
    @Deprecated
    public static void info(String msg) {
        info(null, msg);
    }

    /**
     * 打印提示信息.
     *
     * @param msg
     */
    public static void info(String tag, String msg) {
        Logger log = getLogger(tag);
        log.info(msg);
    }


    /**
     * 打印错误信息.
     *
     * @param msg
     */
    public static void error(String tag, String msg) {
        error(tag, msg, null);
    }

    /**
     * 打印错误信息.
     *
     * @param msg
     */
    @Deprecated
    public static void error(String msg, Exception e) {
        Logger log = getLogger(null);
        log.error(msg, e);
    }

    /**
     * 打印错误信息.
     *
     * @param msg
     */
    public static void error(String tag, String msg, Exception e) {
        Logger log = getLogger(tag);
        log.error(msg, e);
    }

    public static Logger getLogger(String tag) {
        if (mLogger == null) {
            initLogConfig();
        }
        if (TextUtils.isEmpty(tag)) {
            return mLogger;
        }
        return Logger.getLogger(tag);
    }


    private static void initLogConfig(){
        final LogConfigurator logConfigurator = new LogConfigurator();
        logConfigurator.setMaxFileSize(LOG_FILE_SIZE);
        String logFilePath = StringUtil.getLogFilePath();
        File file = new File(logFilePath);
        if (!file.exists()) {
            File parentFile = file.getParentFile();
            if (!parentFile.exists()) {
                parentFile.mkdirs();
            }
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        logConfigurator.setFileName(logFilePath);
        logConfigurator.setRootLevel(mLevel);
        logConfigurator.setLevel("org.apache", mLevel);
        /**
         *  设置日志的输出格式
         *  [%d][%p-%c] - %m%n
         *  例如： [2017-04-11 15:44:39,125][ERROR-OKHttpImpl] - http://aaa
         *
         * */
        logConfigurator.setFilePattern("[%d][%p-%c (%t)] - %m%n");
        logConfigurator.configure();

        //gLogger = Logger.getLogger(this.getClass());
        mLogger = Logger.getLogger("HeyHou");

        mLogger.trace("\n手机型号：" + android.os.Build.MODEL
                + "\n手机品牌：" + android.os.Build.BRAND
                + "\n系统版本号：" + android.os.Build.VERSION.RELEASE);
    }

    /**
     * 添加log信息到本地log文件
     * @param infoStr
     */
    public static void pushLog(String infoStr){
        if (mLogger == null) {
            initLogConfig();
        }
        mLogger.trace(infoStr);
    }
}
