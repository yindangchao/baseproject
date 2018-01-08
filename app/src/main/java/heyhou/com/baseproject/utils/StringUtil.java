package heyhou.com.baseproject.utils;

import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.text.TextUtils;


import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import heyhou.com.baseproject.R;
import heyhou.com.baseproject.base.BaseApplication;
import heyhou.com.baseproject.base.Constant;

/**
 * Created by lky on 2016/11/24.
 */

public class StringUtil {

    public static String getAbsolutePathFromUri(final Context context, final Uri uri) {
        if (null == uri) return null;
        final String scheme = uri.getScheme();
        String data = null;
        if (scheme == null)
            data = uri.getPath();
        else if (ContentResolver.SCHEME_FILE.equals(scheme)) {
            data = uri.getPath();
        } else if (ContentResolver.SCHEME_CONTENT.equals(scheme)) {
            Cursor cursor = context.getContentResolver().query(uri,
                    new String[]{MediaStore.Images.ImageColumns.DATA}, null, null, null);
            if (null != cursor) {
                if (cursor.moveToFirst()) {
                    int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                    if (index > -1) {
                        data = cursor.getString(index);
                    }
                }
                cursor.close();
            }
        }
        return data;
    }

    public static String generateTime(int position) {

        int seconds = position % 60;
        int minutes = (position / 60) % 60;
        int hours = position / 3600;
        if (hours > 0) {
            return String.format(Locale.US,
                    "%02d" + BaseApplication.m_appContext.getString(R.string.home_play_time_hour)
                            + "%02d" + BaseApplication.m_appContext.getString(R.string.home_play_time_minute)
                            + "%02d" + BaseApplication.m_appContext.getString(R.string.home_play_time_sec), hours, minutes,
                    seconds).toString();
        } else if (minutes > 0) {
            return String.format(Locale.US,
                    "%02d" + BaseApplication.m_appContext.getString(R.string.home_play_time_minute)
                            + "%02d" + BaseApplication.m_appContext.getString(R.string.home_play_time_sec), minutes, seconds)
                    .toString();
        } else {
            return String.format(Locale.US, "%d" + BaseApplication.m_appContext.getString(R.string.home_play_time_sec), seconds)
                    .toString();
        }
    }

    public static String getMusicTime(String formatDuration) {
        double duration = 0;
        if (!TextUtils.isEmpty(formatDuration)) {
            try {
                duration = Double.parseDouble(formatDuration);
            } catch (NumberFormatException e) {
                duration = 0;
            }
        }
        long videoDuration = (long) duration;
        long hour = videoDuration / 3600;
        long minites = (videoDuration % 3600) / 60;
        long seconds = videoDuration % 60;
        String str = String.format("%02d'%02d''", minites, seconds);
        if (hour > 0) {
            str = String.format("%02d'%s", hour);
        }
        return str;
    }

    public static String generateTimeFromSymbol(long position) {
        int totalSeconds = (int) (position / 1000);

        int seconds = totalSeconds % 60;
        int minutes = (totalSeconds / 60) % 60;
        int hours = totalSeconds / 3600;

        if (hours > 0) {
            return String.format(Locale.US, "%02d:%02d:%02d", hours, minutes,
                    seconds).toString();
        } else {
            return String.format(Locale.US, "%02d:%02d", minutes, seconds)
                    .toString();
        }
    }

    public static String generateTimeFromSymbolMeter(int position) {
//        int totalSeconds = (int) (position / 1000);

        int seconds = position % 60;
        int minutes = (position / 60) % 60;
        int hours = position / 3600;

        if (hours > 0) {
            return String.format(Locale.US, "%02d'%02d'%02d''", hours, minutes,
                    seconds).toString();
        } else {
            return String.format(Locale.US, "%02d'%02d''", minutes, seconds)
                    .toString();
        }
    }

    public static String numberChangeToW(int count) {
        if (count < 10000) {
            return count + "";
        }
        float value = count / 10000f;
        String result = String.format("%.1f", value);
        return BaseApplication.m_appContext.getString(R.string.home_number_from_w, result);
    }

    public static String numberChangeToW(int count, int limit) {
        if (count < limit) {
            return count + "";
        }
        float value = count / 10000f;
        String result = String.format("%.1f", value);
        return BaseApplication.m_appContext.getString(R.string.home_number_from_w, result);
    }

    public static String getResString(int res) {
        return AppUtil.getApplicationContext().getString(res);
    }

    public static String getLogFilePath() {
        return Constant.PIC_UPLOAD_LOG_PATH + File.separator + getLogFileName();
    }


    public static String getLogFileName() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        String dateStr = formatter.format(curDate);
        String version = "";
        try {
            PackageManager manager = AppUtil.getApplicationContext().getPackageManager();
            PackageInfo info = manager.getPackageInfo(AppUtil.getApplicationContext().getPackageName(), 0);
            version = info.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return dateStr + "_Android_" + version + "-log.txt";
    }
}
