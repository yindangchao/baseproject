package heyhou.com.baseproject.network.ex;


import heyhou.com.baseproject.network.ex.httpImpl.OKHttpImpl;

/**
 * Created by admin on 2017/2/22.
 */

public class HttpFactory {

    /**
     *  获取一个可以进行http 通讯的事例
     *
     * **/
    public static IHttpInterface createHttpInterface() {
        return new OKHttpImpl();
    }
}
