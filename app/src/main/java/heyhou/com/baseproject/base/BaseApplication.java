package heyhou.com.baseproject.base;

import android.app.Application;
import android.content.Context;

import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.load.engine.cache.LruResourceCache;

/**
 * Created by lky on 2017/2/20.
 */

public class BaseApplication extends Application {

    /**
     * ApplicationContext实例
     **/
    public static Context m_appContext = null;


    private final String APP_CONTEXT_TAG = "appContext";

    @Override
    public void onCreate() {
        super.onCreate();
        m_appContext = getApplicationContext();
        /********** 异步下载图片缓存类 初始化 */
        int maxMemory = (int) Runtime.getRuntime().maxMemory();//获取系统分配给应用的总内存大小
        int memoryCacheSize = maxMemory / 8;//设置图片内存缓存占用八分之一
        //设置内存缓存大小
        new GlideBuilder(m_appContext).setMemoryCache(new LruResourceCache(memoryCacheSize));

    }





}
