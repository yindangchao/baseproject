package heyhou.com.baseproject.network;


import heyhou.com.baseproject.network.ex.HttpFactory;
import heyhou.com.baseproject.network.ex.IHttpInterface;
import heyhou.com.baseproject.network.ex.PostUI;
import heyhou.com.baseproject.network.ex.httpImpl.OKHttpImpl;

import java.util.Map;

/**
 * Created by zhy on 15/8/17.
 */
public class OkHttpManager {
    private static OkHttpManager mInstance;

    private static IHttpInterface mHttpInterface = HttpFactory.createHttpInterface();

    private OkHttpManager() {
    }

    public static OkHttpManager getInstance() {
        if (mInstance == null) {
            synchronized (OkHttpManager.class) {
                if (mInstance == null) {
                    mInstance = new OkHttpManager();
                }
            }
        }
        return mInstance;
    }

/*************************************************** 对外公布的方法  ****************************************************/

    /**
     *  与后台服务器进行通讯的GET 请求的接口，
     * @Param  url，   请求参数url
     * @param  params  请求参数
     * @Param  postUI  响应的回掉
     * **/
    public static <T> void doGet(String url, Map<String, Object> params, PostUI<T> postUI) {
        mHttpInterface.doGet(url, params, postUI);
    }

    /**
     *  与后台服务器进行通讯的POST 请求的接口，
     * @Param  url，   请求参数url
     * @param  params  请求参数
     * @Param  postUI  响应的回掉
     * **/
    public static <T> void doPost(String url, Map<String, Object> params, PostUI<T> postUI) {
        mHttpInterface.doPost(url, params, postUI);
    }

    /**
     *  与后台服务器进行通讯的POST 请求的接口，
     * @Param  url，   请求参数url
     * @param  params  请求参数
     * @Param  postUI  响应的回掉
     * **/
    public static <T> void doPost(String url, String params, PostUI<T> postUI) {
        mHttpInterface.doPost(url, params, postUI);
    }

}
