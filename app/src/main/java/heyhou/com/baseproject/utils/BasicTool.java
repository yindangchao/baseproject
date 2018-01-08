package heyhou.com.baseproject.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.text.Html;
import android.text.Spanned;
import android.text.format.Time;
import android.util.Base64;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import heyhou.com.baseproject.R;
import heyhou.com.baseproject.bean.AutoType;

public class BasicTool {

    //banner跳转协议取参数
    public static Map<String, String> protocolStrToMap(String str) {
        Map<String, String> map = new HashMap<>();
        if (!str.contains("?")) {
            return map;
        }
        String subStr = str.substring(str.indexOf("?") + 1);
        if (subStr == null || subStr.length() == 0) {
            return map;
        }
        String[] strings = subStr.split("&");
        if (strings == null || strings.length == 0) {
            return map;
        }
        for (String stru : strings) {
            map.put(stru.substring(0, stru.indexOf("=")), stru.substring(stru.indexOf("=") + 1));
        }
        return map;
    }

    public static Bitmap blurBitmap(Context context, Bitmap bitmap) {

        //Let's create an empty bitmap with the same size of the bitmap we want to blur
        Bitmap outBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);

        //Instantiate a new Renderscript
        RenderScript rs = RenderScript.create(context);

        //Create an Intrinsic Blur Script using the Renderscript
        ScriptIntrinsicBlur blurScript = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs));

        //Create the Allocations (in/out) with the Renderscript and the in/out bitmaps
        Allocation allIn = Allocation.createFromBitmap(rs, bitmap);
        Allocation allOut = Allocation.createFromBitmap(rs, outBitmap);

        //Set the radius of the blur
        blurScript.setRadius(25.f);

        //Perform the Renderscript
        blurScript.setInput(allIn);
        blurScript.forEach(allOut);

        //Copy the final bitmap created by the out Allocation to the outBitmap
        allOut.copyTo(outBitmap);

        //recycle the original bitmap
        bitmap.recycle();

        //After finishing everything, we destroy the Renderscript.
        rs.destroy();

        return outBitmap;


    }

    public static List<String> arrayToListString(JSONArray array) {
        if (array == null) {
            return null;
        }
        List<String> list = new ArrayList<>();
        if (array.length() == 0) {
            return list;
        }
        try {
            for (int i = 0; i < array.length(); i++) {
                list.add(array.getString(i));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }

    public static String getRandomString(int length) {
        String str = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; ++i) {
            int number = random.nextInt(62);//[0,62)
            sb.append(str.charAt(number));
        }
        return sb.toString();
    }

    public static Bitmap resizeImage(Bitmap bitmap, int w, int h) {
        Bitmap BitmapOrg = bitmap;
        int width = BitmapOrg.getWidth();
        int height = BitmapOrg.getHeight();
        int newWidth = w;
        int newHeight = h;

        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;

        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        // if you want to rotate the Bitmap
        // matrix.postRotate(45);
        Bitmap resizedBitmap = Bitmap.createBitmap(BitmapOrg, 0, 0, width, height, matrix, true);
        return resizedBitmap;
    }

    public static Bitmap getScaledBitmap(Activity activity, Bitmap bitmap, int roundPx) {
        int width = (int) (DetectTool.getResolutionX(activity) * 0.6);
        int height = DetectTool.getResolutionX(activity) / 3;
        Bitmap output = resizeImage(bitmap, width, height);
        return GetScaleRoundedCornerBitmap(activity, output, roundPx);
    }

    public static Bitmap GetScaleRoundedCornerBitmap(Activity activity,
                                                     Bitmap bitmap,
                                                     int roundPx) {
        try {
            Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
                    bitmap.getHeight(),
                    Bitmap.Config.ARGB_4444);

            Canvas canvas = new Canvas(output);
            final Paint paint = new Paint();
            final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
            final RectF rectF = new RectF(new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight()));
            paint.setAntiAlias(true);
            canvas.drawARGB(0, 0, 0, 0);
            paint.setColor(Color.BLACK);
            canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));

            final Rect src = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
            canvas.drawBitmap(bitmap, src, rect, paint);
            return output;
        } catch (Exception e) {
            return bitmap;
        }
    }

    //    public static boolean isName(String name) {
    //        Pattern pattern = Pattern
    //                .compile("[\\u4e00-\\u9fa5a-zA-Z]{6,20}");
    //        Matcher matcher = pattern.matcher(name);
    //        return matcher.matches();
    //    }

    /**
     * 检测字符串是否为空，
     *
     * @param str
     * @return 是空 返回 <code>false</code> 否则返回 <code>true</code>
     */
    public static boolean isEmpty(String str) {
        if (null == str) {
            return true;
        }

        if (str.equals("null")) {
            return true;
        }

        return "".equals(str.trim());
    }

    public static String getMd5(String string) {
        byte[] hash;
        try {
            hash = MessageDigest.getInstance("MD5")
                    .digest(string.getBytes("UTF-8"));
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Huh, MD5 should be supported?", e);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("Huh, UTF-8 should be supported?", e);
        }

        StringBuilder hex = new StringBuilder(hash.length * 2);
        for (byte b : hash) {
            if ((b & 0xFF) < 0x10) {
                hex.append("0");
            }
            hex.append(Integer.toHexString(b & 0xFF));
        }
        return hex.toString();
    }

    public static boolean isEmptyForCart(JSONObject keyObj)
            throws JSONException {
        return !(null == keyObj || "{}".equals(keyObj.toString()) || BasicTool.isEmpty(keyObj.getString(
                "value")));
    }

    public static String getShortCityName(String cityName) {
        String cityText = null;
        if (!cityName.endsWith("市")) {
            cityText = cityName;
        } else {
            cityText = cityName.substring(0, cityName.length() - 1);
        }
        return cityText;
    }

    public static Spanned formatHtml(Context context, String str1, String str2) {
        Spanned spanned = Html.fromHtml(String.format(context.getString(R.string.common_combine_text),
                new Object[]{str1,
                        str2}));
        return spanned;
    }


    /**
     * @function 判断只含有汉字，数字，字母，下划线，不能已下划线开头和结尾
     */
    public static boolean verifyNickName(String str) {
        Pattern pattern = Pattern.compile("^(?!_)(?!.*?_$)[a-zA-Z0-9_\u4e00-\u9fa5]+$");
        Matcher matcher = pattern.matcher(str);

        return matcher.matches();
    }

    /**
     * @param strEmail
     * @return
     * @function 邮箱验证, 验证通过则返回ture，否则为false
     */
    public static boolean isEmail(String strEmail) {
        String strPattern = "\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*";
        Pattern p = Pattern.compile(strPattern);
        Matcher m = p.matcher(strEmail);
        return m.matches();
    }

    public static boolean isName(String strEmail) {
        String strPattern = "[a-zA-Z\\u4e00-\\u9fa5]+";
        Pattern p = Pattern.compile(strPattern);
        Matcher m = p.matcher(strEmail);
        return m.matches();
    }

    public static boolean isPerformName(String strEmail) {
        //        String strPattern = "[a-zA-Z0-9\\u4e00-\\u9fa5]+";
        //        Pattern p = Pattern.compile(strPattern);
        //        Matcher m = p.matcher(strEmail);
        //        return m.matches();
        return true;
    }

    /**
     * @param str create at 2015-10-10 上午9:55:36
     * @function 验证字符串是否为小数，通过返回true，否则为false
     */
    public static boolean isDecimal(String str) {
        // String strPattern="([1-9]+[0-9]*|0)(\\.[\\d]+)?";
        String strPattern = "^[-+]?[0-9]+(\\.[0-9]+)?$";
        Pattern p = Pattern.compile(strPattern);
        Matcher matcher = p.matcher(str);
        return matcher.matches();
    }

    /**
     * @param str
     * @return 验证身份证
     */
    public static boolean isIdCard(String str) {
        Pattern pattern = Pattern.compile("^(\\d{15}$|^\\d{18}$|^\\d{17}(\\d|X|x))$");
        Matcher matcher = pattern.matcher(str);
        return matcher.matches();
    }

    /**
     * @param str
     * @return
     * @function 手机号的验证, 验证通过则返回ture，否则为false
     */
    public static boolean isCellphone(String str) {
        Pattern pattern = Pattern.compile("^1[3|4|5|6|7|8][0-9]\\d{8}$");
        Matcher matcher = pattern.matcher(str);
        return matcher.matches();
    }

    public static String getSafetyMobile(String cellPhone) {
        return cellPhone.substring(0, 3) + "****" + cellPhone.substring(cellPhone.length() - 4,
                cellPhone.length());
    }

    public static String getSafetyBankCode(String bankCode) {
        return bankCode.substring(0, 4) + "*********" + bankCode.substring(bankCode.length() - 4,
                bankCode.length());
    }

    public static boolean isMacaoCellphone(String str) {
        Pattern pattern = Pattern.compile(
                "^((((0?)|((00)?))(((\\s){0,2})|([-_－—\\s\\(]?)))|([+]?))(853)?([\\)]?)([-_－—\\s]?)(28[0-9]{2}|((6|8)[0-9]{3}))[-_－—\\s]?[0-9]{4}$");
        Matcher matcher = pattern.matcher(str);
        return matcher.matches();
    }

    /**
     * @param str
     * @return
     * @function 密码的验证, 验证通过则返回ture，否则为false
     */
    public static boolean isPassWord(String str) {
        Pattern pattern = Pattern.compile(
                "^(?![A-Z]+$)(?![a-z]+$)(?!\\d+$)(?![\\W_]+$)(?=\\S{6,32}$)\\S+$");
        Matcher matcher = pattern.matcher(str);

        return matcher.matches();
    }

    /**
     * 获取时间戳
     *
     * @return
     */
    public static String getCurString() {
        long msg = System.currentTimeMillis();

        return Long.toString(msg);

    }

    public static boolean isNetworkConnected(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context.getSystemService(
                    Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            if (mNetworkInfo != null) {
                return mNetworkInfo.isAvailable();
            }
        }
        return false;
    }

    public static <T extends AutoType> ArrayList<T> parseJsonList(JSONArray array, Type clazz)
            throws JSONException {
        ArrayList<T> list = new ArrayList<T>();
        if (array == null || array.length() < 1) {
            return list;
        }
        Gson gson = new Gson();
        for (int i = 0; i < array.length(); i++) {
            T obj = gson.fromJson(array.getJSONObject(i)
                    .toString(), clazz);
            list.add(obj);
        }
        return list;
    }

    /**
     * 将单个list转成json字符串
     *
     * @param list
     * @return
     * @throws Exception
     */
    public static String listToJsonString(List<Map<String, Object>> list)
            throws Exception {
        String jsonL = "";
        StringBuffer temp = new StringBuffer();
        temp.append("[");
        for (int i = 0; i < list.size(); i++) {
            Map<String, Object> m = list.get(i);
            if (i == list.size() - 1) {
                temp.append(mapToJsonObj(m));
            } else {
                temp.append(mapToJsonObj(m) + ",");
            }
        }
        if (temp.length() > 1) {
            temp = new StringBuffer(temp.substring(0, temp.length()));
        }
        temp.append("]");
        jsonL = temp.toString();
        return jsonL;
    }

    /**
     * 将map数据解析出来，并拼接成json字符串
     *
     * @param map
     * @return
     * @throws Exception
     */
    @SuppressWarnings({"rawtypes",
            "unchecked"})
    public static JSONObject mapToJsonObj(Map map)
            throws Exception {
        JSONObject json = null;
        StringBuffer temp = new StringBuffer();
        if (!map.isEmpty()) {
            temp.append("{");
            // 遍历map
            Set set = map.entrySet();
            Iterator i = set.iterator();
            while (i.hasNext()) {
                Map.Entry entry = (Map.Entry) i.next();
                String key = (String) entry.getKey();

                Object value = entry.getValue();

                temp.append("\"" + key + "\":");

                if (null == value || "".equals(value)) {
                    temp.append("\"\"" + ", ");
                } else if (value instanceof Map<?, ?>) {
                    temp.append(mapToJsonObj((Map<String, Object>) value) + ",");
                } else if (value instanceof List<?>) {
                    temp.append(listToJsonString((List<Map<String, Object>>) value) + ",");
                } else if (value instanceof String) {
                    temp.append("\"" + String.valueOf(value) + "\",");
                } else {
                    temp.append(String.valueOf(value) + ",");
                }

            }
            if (temp.length() > 1) {
                String mString = temp.toString();
                mString = mString.trim();

                temp = new StringBuffer(mString.substring(0, mString.length() - 1));
            }

            temp.append("}");

            json = new JSONObject(temp.toString());
        }
        return json;
    }

    /**
     * 将json 对象转换为Map 对象
     *
     * @param jsonString
     * @return
     */
    public static Map<String, Object> jsonToMap(String jsonString) {
        JSONObject jsonObject;
        try {
            jsonObject = new JSONObject(jsonString);
            @SuppressWarnings("unchecked")
            Iterator<String> keyIter = jsonObject.keys();
            String key;
            Object value;
            Map<String, Object> valueMap = new HashMap<String, Object>();
            while (keyIter.hasNext()) {
                key = keyIter.next();
                value = jsonObject.get(key);
                valueMap.put(key, value);
            }
            return valueMap;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 把json 转换为 ArrayList 形式
     *
     * @return
     */
    public static List<Map<String, Object>> jsonArrToList(String jsonString) {
        List<Map<String, Object>> list = null;

        try {
            JSONArray jsonArray = new JSONArray(jsonString);

            JSONObject jsonObject;

            list = new ArrayList<Map<String, Object>>();

            for (int i = 0; i < jsonArray.length(); i++) {
                jsonObject = jsonArray.getJSONObject(i);
                list.add(jsonToMap(jsonObject.toString()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * @param gap 获取时间的间隔，要获取之前的日期则为负，如获取前一周的时间，则为-7；反之当前日期之后的则为正
     * @return
     */
    @SuppressLint("SimpleDateFormat")
    public static String getDateBefore(int gap) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        Calendar now = Calendar.getInstance();

        now.setTime(new Date());
        now.set(Calendar.DATE, now.get(Calendar.DATE) + gap);

        return sdf.format(now.getTime());
    }


    //万以上转汉字
    public static String getShortNum(int money) {
        if (money > 10000) {
            return money / 10000 + "万";
        }
        return money + "";
    }


    /**
     * @return
     */
    @SuppressLint("SimpleDateFormat")
    public static String getCruDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        return sdf.format(new Date());
    }

    public static String getFormatDate(long time) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(time);
    }

    public static String getFormatDateWithoutHMSInSeconds(long time) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(time * 1000);
    }

    public static String getFormatDateInSeconds(long time) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(time * 1000);
    }

    public static String getFormatDateInSecondsShowInMusic(long time) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");
        return sdf.format(time * 1000);
    }

    public static String getFormatDateInSecondsShowInCircle(long time) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(time * 1000);
    }


    @SuppressLint("SimpleDateFormat")
    public static String getOrderTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");

        return sdf.format(new Date());
    }

    /**
     * 日期时间字符串转换为日期字符串
     */
    @SuppressLint("SimpleDateFormat")
    public static String dateTimeToDate(String datatime) {
        SimpleDateFormat dataTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date;
        try {
            date = dataTime.parse(datatime);
            SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
            return sdf2.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 日期时间字符串转换为日期字符串
     */
    @SuppressLint("SimpleDateFormat")
    public static String getActionTime(int datatime) {
        SimpleDateFormat dataTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date;

        date = new Date(datatime);
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy年MM月dd日");
        return "时间：" + sdf2.format(date);

    }

    /**
     * 日期时间字符串转换为日期字符串
     */
    @SuppressLint("SimpleDateFormat")
    public static String getActionTime(String datatime) {
        SimpleDateFormat dataTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date;

        try {
            date = dataTime.parse(datatime);
            SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy年MM月dd日");
            return "时间：" + sdf2.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;

    }


    private static final double EARTH_RADIUS = 6378137;

    private static double rad(double d) {
        return d * Math.PI / 180.0;
    }

    /**
     * 根据两点间经纬度坐标（double值），计算两点间距离，单位为米
     *
     * @param lng1
     * @param lat1
     * @param lng2
     * @param lat2
     * @return
     */
    public static double getDistance(double lng1, double lat1, double lng2, double lat2) {
        double radLat1 = rad(lat1);
        double radLat2 = rad(lat2);
        double a = radLat1 - radLat2;
        double b = rad(lng1) - rad(lng2);
        double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2),
                2) + Math.cos(radLat1) * Math.cos(radLat2) * Math.pow(
                Math.sin(b / 2),
                2)));
        s = s * EARTH_RADIUS;
        s = Math.round(s * 10000) / 10000;
        return s;
    }

    /**
     * 缩放Bitmap图片
     **/
    public static Bitmap zoomBitmap(Bitmap bitmap, int width, int height) {

        int w = bitmap.getWidth();

        int h = bitmap.getHeight();

        Matrix matrix = new Matrix();

        float scaleWidth = ((float) width / w);

        float scaleHeight = ((float) height / h);

        matrix.postScale(scaleWidth, scaleHeight);// 利用矩阵进行缩放不会造成内存溢出

        Bitmap newbmp = Bitmap.createBitmap(bitmap, 0, 0, w, h, matrix, true);

        return newbmp;

    }

    /**
     * 把Bitmap用Base64编码并返回生成的字符串
     */
    public static String bitmaptoString(Bitmap bitmap) {
        String string = null;

        ByteArrayOutputStream bStream = new ByteArrayOutputStream();

        bitmap.compress(CompressFormat.PNG, 100, bStream);

        byte[] bytes = bStream.toByteArray();

        string = Base64.encodeToString(bytes, Base64.DEFAULT);

        return string;
    }

//    /**
//     * 把JSONObject转换为RequestParams，即key1=a&key2=b&key3=c形式
//     *
//     * @param obj  待转化的JSONObject对象
//     * @param flag 标示位，第一次验证的时候祛除signMsg、referDataType以及referData字段,true:祛除，false
//     *             ：不祛除
//     * @return 转化后的RequestParams对象
//     */
//    @SuppressWarnings("unchecked")
//    public static RequestParams jsonObjToParams(JSONObject obj, boolean flag) {
//        RequestParams params = new RequestParams();
//
//        Iterator<String> it = obj.keys();
//
//        while (it.hasNext()) {
//            String key = it.next();
//
//            if (flag) {
//                if (key.equals("signMsg") || key.equals("referDataType") || key.equals("referData")) {
//                    continue;
//                }
//            }
//
//            try {
//                if (flag) {
//                    if (BasicTool.isEmpty(obj.getString(key))) {
//                        continue;
//                    }
//                }
//
//                params.put(key, obj.getString(key));
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//        }
//
//        return params;
//    }

    /**
     * 根据Object obj以及key获取对应的value值
     *
     * @param obj 目标对象
     * @param key 待取值的key
     * @return
     */
    public static String getValue(JSONObject obj, String key) {
        String value = "";

        if (obj.has(key)) {
            try {
                value = obj.getString(key);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return value;
    }


    /**
     * 日期时间比较
     *
     * @param DATE 输入的日期、时间
     * @return
     */
    @SuppressLint("SimpleDateFormat")
    public static boolean compare_date(String DATE, boolean hasHour) {
        Time time = new Time();
        time.setToNow();
        String DATE2;
        SimpleDateFormat df;
        if (hasHour) {
            DATE2 = time.year + "-" + (time.month + 1) + "-" + time.monthDay + " " + time.hour + ":" + time.minute;
            df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        } else {
            DATE2 = time.year + "-" + (time.month + 1) + "-" + time.monthDay;
            df = new SimpleDateFormat("yyyy-MM-dd");
        }

        try {
            Date dt1 = df.parse(DATE);
            Date dt2 = df.parse(DATE2);

            if (hasHour) {
                if (dt1.getTime() >= dt2.getTime()) {
                    return true;
                } else if (dt1.getTime() < dt2.getTime()) {
                    return false;
                }

            } else {
                if (dt1.getTime() > dt2.getTime()) {
                    return true;
                } else if (dt1.getTime() <= dt2.getTime()) {
                    return false;
                }
            }

        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return true;
    }


    /**
     * @param bitmap     原图
     * @param edgeLength 希望得到的正方形部分的边长
     * @return 缩放截取正中部分后的位图。
     */
    public static Bitmap centerSquareScaleBitmap(Bitmap bitmap, int edgeLength) {
        if (null == bitmap || edgeLength <= 0) {
            return null;
        }

        Bitmap result = bitmap;
        int widthOrg = bitmap.getWidth();
        int heightOrg = bitmap.getHeight();

        if (widthOrg > edgeLength && heightOrg > edgeLength) {
            //压缩到一个最小长度是edgeLength的bitmap
            int longerEdge = (int) (edgeLength * Math.max(widthOrg,
                    heightOrg) / Math.min(widthOrg,
                    heightOrg));
            int scaledWidth = widthOrg > heightOrg
                    ? longerEdge
                    : edgeLength;
            int scaledHeight = widthOrg > heightOrg
                    ? edgeLength
                    : longerEdge;
            Bitmap scaledBitmap;

            try {
                scaledBitmap = Bitmap.createScaledBitmap(bitmap, scaledWidth, scaledHeight, true);
            } catch (Exception e) {
                return null;
            }

            //从图中截取正中间的正方形部分。
            int xTopLeft = (scaledWidth - edgeLength) / 2;
            int yTopLeft = (scaledHeight - edgeLength) / 2;

            try {
                result = Bitmap.createBitmap(scaledBitmap,
                        xTopLeft,
                        yTopLeft,
                        edgeLength,
                        edgeLength);
                scaledBitmap.recycle();
            } catch (Exception e) {
                return null;
            }
        }

        return result;
    }

}
