package heyhou.com.baseproject.network.ex;

import java.util.Map;

/**
 * Created by admin on 2017/2/22.
 */

public interface IHttpInterface {

    /**
     *  Http GET 方法的接口
     * @Param url  请求的url
     * @Param params 请求的参数
     * @param postUI 请求的回调
     *
     * */
    <T> void  doGet(String url, Map<String, Object> params, PostUI<T> postUI);


    /**
     *  Http POST 方法的接口
     * @Param url  请求的url
     * @Param params 请求的参数
     * @param postUI 请求的回调
     *
     * */
    <T> void doPost(String url, Map<String, Object> params, PostUI<T> postUI);

    /**
     *  Http POST 方法的接口
     * @Param url  请求的url
     * @Param params 请求的参数
     * @param postUI 请求的回调
     *
     * */
    <T> void doPost(String url, String params, PostUI<T> postUI);

    String buildUrl(String url, Map<String, Object> params);
}
