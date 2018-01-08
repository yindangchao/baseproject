package heyhou.com.baseproject.network.ex.httpImpl;

import android.content.Intent;
import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import heyhou.com.baseproject.base.Constant;
import heyhou.com.baseproject.base.ex.SingleHandler;
import heyhou.com.baseproject.network.ex.HttpResponseData;
import heyhou.com.baseproject.network.ex.HttpUrlMap;
import heyhou.com.baseproject.network.ex.IHttpInterface;
import heyhou.com.baseproject.network.ex.PostUI;
import heyhou.com.baseproject.utils.AppUtil;
import heyhou.com.baseproject.utils.DebugTool;
import heyhou.com.baseproject.utils.DeviceTool;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by admin on 2017/2/22.
 */

public class OKHttpImpl implements IHttpInterface {

    private static final String ERROR_MSG_NETWORK = "网络错误~";
    private static final String ERROR_MSG_DATA_ERROR = "网络错误~";

    private static final String TAG = "OKHttpImpl";

    private static final int TIME_OUT = 25;
    private OkHttpClient mOkHttpClient;

    public OKHttpImpl() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
                .writeTimeout(TIME_OUT, TimeUnit.SECONDS)
                .readTimeout(TIME_OUT, TimeUnit.SECONDS)
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request request = chain.request()
                                .newBuilder()
                                .addHeader(Constant.COMMON_HTTP_HEAD_KEY, DeviceTool.getHttpHeadValue(AppUtil.getApplicationContext()))
                                .addHeader(Constant.COMMON_HTTP_DEVICE_KEY, DeviceTool.getImei(AppUtil.getApplicationContext()))
                                .addHeader(Constant.COMMON_HTTP_CHANNEL_KEY, DeviceTool.getChannel())
                                .build();
                        return chain.proceed(request);
                    }
                });

        mOkHttpClient = builder.build();
    }

    private static String urlConvert(String url) {
        String targetUrl = HttpUrlMap.getInstance().getUrl(url);
        if (!TextUtils.isEmpty(targetUrl)) {
            return targetUrl;
        }

        if (url.startsWith("/app") || url.startsWith("app")) {
            int pos = -1;
            if (url.startsWith("/app")) {
                pos = url.indexOf("/", 1);
            } else if (url.startsWith("app")) {
                pos = url.indexOf("/");
            }

            if (pos != -1) {
                targetUrl = url.substring(pos + 1);
            } else {
                targetUrl = url;
            }
        }
        targetUrl = targetUrl.replace("/", "_");
        targetUrl = HttpUrlMap.getInstance().getUrl(targetUrl);
        if (TextUtils.isEmpty(targetUrl)) {
            targetUrl = url;
        }
        return targetUrl;
    }

    private static String getAbsoluteUrl(String url) {
        String relativeUrl = urlConvert(url);
        if (!relativeUrl.endsWith("?"))
            relativeUrl += "?";
        return Constant.BASE_ONLINE_SERVER + relativeUrl;
    }

    /**
     * 把Map 格式的请求参数 转为 GET 格式。
     */
    private Param[] map2Params(Map<String, Object> params) {
        if (params == null) return new Param[0];
        int size = params.size();
        Param[] res = new Param[size];
        Set<Map.Entry<String, Object>> entries = params.entrySet();
        int i = 0;
        for (Map.Entry<String, Object> entry : entries) {
            res[i++] = new Param(entry.getKey(), entry.getValue() == null ? "" : String.valueOf(entry.getValue()));
        }
        return res;
    }

    /**
     * 构造 Http 的POST请求
     */
    private Request buildPostRequest(String url, Param[] params) {
        if (params == null) {
            params = new Param[0];
        }
        FormBody.Builder builder = new FormBody.Builder();
        for (Param param : params) {
            builder.add(param.key, param.value == null ? "" : param.value);
        }

        RequestBody requestBody = builder.build();
        return new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();
    }

    /**
     * 构造 Http 的POST请求
     */
    private Request buildPostRequest(String url, String params) {
        FormBody.Builder builder = new FormBody.Builder();
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), params);
        return new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();
    }

    @Override
    public String buildUrl(String url, Map<String, Object> params) {
        url = getAbsoluteUrl(url);
        Param[] paramsArr = map2Params(params);
        DebugTool.error(TAG, url + arraytoString(paramsArr));
        return buildGetReqeust(url, paramsArr).url().toString();
    }

    /**
     * 构造 Http 的GET请求
     */
    private Request buildGetReqeust(String url, Param[] paramsArr) {
        if (paramsArr != null) {
            for (int i = 0; i < paramsArr.length; i++) {
                Param param = paramsArr[i];
                url += (param.key + "=" + param.value);
                if (i != paramsArr.length - 1) {
                    url += "&";
                }
            }
        }
        Request request = new Request.Builder()
                .url(url)
                .build();


        return request;
    }

    /**
     * 执行 Http 请求
     */
    private <T> void execute(Request request, final PostUI<T> postUI) {
        if (postUI != null && postUI.isShowLoading()) {
            postUI.showLoading();
        }
        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                if (postUI == null) {
                    return;
                }
                SingleHandler.getsInstance().post(new Runnable() {
                    @Override
                    public void run() {
                        if (postUI.isShowLoading()) {
                            postUI.hideLoading();
                        }
                        postUI.onFailed(Constant.CONNECTION_FAIL_CODE, ERROR_MSG_NETWORK);
                    }
                });
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                try {
                    String strResponse = response.body().string();
                    DebugTool.error(TAG, strResponse);
                    TypeReference<HttpResponseData<T>> typeReference = new TypeReference<HttpResponseData<T>>() {
                    };
                    Type t = postUI.getDataTypeClass();

                    final HttpResponseData<T> resObj = JSON.parseObject(strResponse, typeReference);
                    T data = resObj.getData();
                    if (data != null) {
                        if (data instanceof JSONObject) {
                            JSONObject jObject = (JSONObject) data;
                            T obj = JSON.parseObject(jObject.toString(), t);
                            resObj.setData(obj);
                        } else if (data instanceof JSONArray) {
                            JSONArray jObjectArray = (JSONArray) data;
                            T obj = JSON.parseObject(jObjectArray.toString(), t);
                            resObj.setData(obj);
                        } else {
                            T jObject = (T) data;
                            resObj.setData(jObject);
                        }
                    }

                    if (postUI == null) {
                        return;
                    }
                    SingleHandler.getsInstance().post(new Runnable() {
                        @Override
                        public void run() {
                            if (postUI.isShowLoading()) {
                                postUI.hideLoading();
                            }

                            if (resObj.getRet() == 1000) {
                                /**
                                 * 登陆信息过期
                                 * */
                                AppUtil.getApplicationContext().sendBroadcast(new Intent(Constant.ACTION_LOGIN_INFO_OUT_OF_DATE));
                            } else if (resObj.getRet() == 4000) {
                                /**
                                 *  服务器返回的信息不对，统一提示网络错误
                                 * */
                                SingleHandler.getsInstance().post(new Runnable() {
                                    @Override
                                    public void run() {
                                        if (postUI.isShowLoading()) {
                                            postUI.hideLoading();
                                        }
                                        postUI.onFailed(4000, ERROR_MSG_NETWORK);
                                    }
                                });
                            } else if (resObj.getRet() == 0) {
                                /**
                                 * 成功
                                 * */
                                postUI.onSucceed(resObj);
                            } else {
                                /**
                                 * 失败
                                 * */
                                postUI.onFailed(resObj.getRet(), resObj.getMsg());
                            }
                        }
                    });
                } catch (final Exception e) {
                    if (postUI != null) {
                        SingleHandler.getsInstance().post(new Runnable() {
                            @Override
                            public void run() {
                                if (postUI.isShowLoading()) {
                                    postUI.hideLoading();
                                }
                                postUI.onFailed(response.code(), ERROR_MSG_DATA_ERROR);
                            }
                        });
                    }
                }
            }
        });
    }

    @Override
    public <T> void doGet(String url, Map<String, Object> params, PostUI<T> postUI) {
        url = getAbsoluteUrl(url);
        Param[] paramsArr = map2Params(params);
        DebugTool.error(TAG, url + arraytoString(paramsArr));
        Request request = buildGetReqeust(url, paramsArr);
        execute(request, postUI);
    }

    //新版本post
    @Override
    public <T> void doPost(String url, Map<String, Object> params, PostUI<T> postUI) {
        url = getAbsoluteUrl(url);
        Param[] paramArray = map2Params(params);
        DebugTool.error(TAG, url + arraytoString(paramArray));
        Request request = buildPostRequest(url, paramArray);
        execute(request, postUI);
    }

    public static class Param {
        public Param(String key, String value) {
            this.key = key;
            this.value = value;
        }

        String key;
        String value;

        @Override
        public String toString() {
            return key + "=" + value;
        }
    }

    @Override
    public <T> void doPost(String url, String params, PostUI<T> postUI) {
        url = getAbsoluteUrl(url);
        DebugTool.error(TAG, url + ", params=" + params);
        Request request = buildPostRequest(url, params);
        execute(request, postUI);
    }


    private String arraytoString(Param[] params) {
        String str = "";
        for (int i = 0; i < params.length; i++) {
            Param param = params[i];
            str += param.key + "=" + param.value + "&";
        }
        return str;
    }
}
