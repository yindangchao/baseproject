package heyhou.com.baseproject.utils;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;

import java.io.File;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import heyhou.com.baseproject.base.BaseApplication;

/**
 * 创建:yb 2016/11/16.
 * 描述:利用Glide下载图片到本地相册
 */

public class GlideDownLoader {
    public static GlideDownLoader instance;
    public static ExecutorService mExecutor = null;
    private GlideDownLoader() {}

    public static GlideDownLoader build() {
        if (instance == null) {
            synchronized (GlideDownLoader.class) {
                if (instance == null) {
                    instance = new GlideDownLoader();
                }
            }
        }
        return instance;
    }

    /**
     * 执行单线程列队执行
     */
    public void runOnQueue(Runnable runnable) {
        if (mExecutor == null) {
            mExecutor = Executors.newSingleThreadExecutor();
        }
        mExecutor.submit(runnable);
    }

    /**
     * 启动下载
     * @param url
     * @param downCallBack
     */
    public static void start(String url,DownCallBack downCallBack) {
        build().runOnQueue(new DownLoadTask(url,downCallBack));
    }

    public static class DownLoadTask implements Runnable {

        private String url;
        private DownCallBack downCallBack;

        public DownLoadTask(String url, DownCallBack downCallBack) {
            this.url = url;
            this.downCallBack = downCallBack;
        }

        @Override
        public void run() {
            File file = null;
            try {
                file = Glide.with(BaseApplication.m_appContext)
                        .load(url)
                        .downloadOnly(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                        .get();
            } catch (Exception e) {
                DebugTool.error("down", e);
            } finally {
                if (downCallBack != null) {
                    if (file == null) {
                        downCallBack.onDownLoadFailed();
                    } else {
                        downCallBack.onDownLoadSuccess(file);
                    }
                }
            }
        }
    }

    public static interface DownCallBack {
        void onDownLoadSuccess(File file);
        void onDownLoadFailed();
    }

}
