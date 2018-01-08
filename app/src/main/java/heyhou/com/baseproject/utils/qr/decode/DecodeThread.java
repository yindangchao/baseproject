package heyhou.com.baseproject.utils.qr.decode;

import android.os.Handler;
import android.os.Looper;


import java.util.concurrent.CountDownLatch;

import heyhou.com.baseproject.utils.qr.OnHandle;

/**
 * 作者: 陈涛(1076559197@qq.com)
 * 
 * 时间: 2014年5月9日 下午12:24:34
 *
 * 版本: V_1.0.0
 *
 * 描述: 解码线程
 */
final class DecodeThread extends Thread {

	OnHandle activity;
	private Handler handler;
	private final CountDownLatch handlerInitLatch;

	DecodeThread(OnHandle activity) {
		this.activity = activity;
		handlerInitLatch = new CountDownLatch(1);
	}

	Handler getHandler() {
		try {
			handlerInitLatch.await();
		} catch (InterruptedException ie) {
			// continue?
		}
		return handler;
	}

	@Override
	public void run() {
		Looper.prepare();
		handler = new DecodeHandler(activity);
		handlerInitLatch.countDown();
		Looper.loop();
	}

}
