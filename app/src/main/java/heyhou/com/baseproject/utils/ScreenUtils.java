package heyhou.com.baseproject.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;


import java.lang.reflect.Method;

import heyhou.com.baseproject.base.BaseApplication;

/**
 * Created by 李凯源 on 2016/4/1.
 * 手机屏幕参数工具类
 */
public final class ScreenUtils
{
	/**
	 * 获取手机屏幕的宽度
	 * @return
	 */
	public static int getScreenWidth()
	{
		return BaseApplication.m_appContext.getResources().getDisplayMetrics().widthPixels;
	}

	/**
	 * 获得手机屏幕的高度
	 * @return
	 */
	public static int getScreenHeight()
	{
		return BaseApplication.m_appContext.getResources().getDisplayMetrics().heightPixels;
	}

	/**
	 * 获得某个View的高度
	 * @param view
	 * @return
	 */
	public static int getViewHeight(View view)
	{
		int w = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
		int h = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
		view.measure(w, h);
		return view.getMeasuredHeight();
	}

	/**
	 * 获得某个View的宽度
	 * @param view
	 * @return
	 */
	public static int getViewWidth(View view)
	{
		int w = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
		int h = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
		view.measure(w, h);
		return view.getMeasuredWidth();
	}

	public static Point getViewPoint(View view){
		int w = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
		int h = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
		view.measure(w, h);
		Point point = new Point(view.getMeasuredWidth(),view.getMeasuredHeight());
		return point;
	}

	/**
	 * 获得状态栏的高度
	 * @param context
	 * @return
	 */
	public static int getScreenStateTabHeight(Activity context)
	{
		Rect frame = new Rect();
		context.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
		int statusBarHeight = frame.top;
		return statusBarHeight;
	}

	/**
	 * 获取 虚拟按键的高度
	 * @param context
	 * @return
	 */
	public static  int getBottomStatusHeight(Context context){
		int totalHeight = getDpi(context);

		int contentHeight = getScreenHeight();

//		DebugTool.warn("getBottomStatusHeight:" + totalHeight + "," + contentHeight);
		return totalHeight  - contentHeight;
	}

	//获取屏幕原始尺寸高度，包括虚拟功能键高度
	public static int getDpi(Context context){
		int dpi = 0;
		WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		Display display = windowManager.getDefaultDisplay();
		DisplayMetrics displayMetrics = new DisplayMetrics();
		@SuppressWarnings("rawtypes")
		Class c;
		try {
			c = Class.forName("android.view.Display");
			@SuppressWarnings("unchecked")
			Method method = c.getMethod("getRealMetrics",DisplayMetrics.class);
			method.invoke(display, displayMetrics);
			dpi=displayMetrics.heightPixels;
		}catch(Exception e){
			e.printStackTrace();
		}
		return dpi;
	}


	/**
	 * 监听键盘状态
	 * @param activity
	 * @param parentLayout 界面的根布局（一定是根布局）
	 * @param keyboardChangeListener
     */
	public static void checkKeyboardHeight(@NonNull final Activity activity,@NonNull final View parentLayout,@NonNull final KeyboardChangeListener keyboardChangeListener) {
		parentLayout.getViewTreeObserver().addOnGlobalLayoutListener(
				new ViewTreeObserver.OnGlobalLayoutListener() {

					@Override
					public void onGlobalLayout() {

						Rect r = new Rect();
						parentLayout.getWindowVisibleDisplayFrame(r);

						int screenHeight = parentLayout.getRootView()
								.getHeight();
						int heightDifference = screenHeight - (r.bottom);


						if (heightDifference > DensityUtils.dp2px(AppUtil.getApplicationContext(), 80)) {
							int height = heightDifference - ScreenUtils.getBottomStatusHeight(BaseApplication.m_appContext);
							if (parentLayout.getHeight() + ScreenUtils.getScreenStateTabHeight(activity) >= ScreenUtils.getDpi(AppUtil.getApplicationContext())) {
								height = heightDifference;
							}
							keyboardChangeListener.show(height);
						} else {
							keyboardChangeListener.hide();
						}

					}
				});
	}

	/**
	 * 键盘状态转变回调接口
	 */
	public interface KeyboardChangeListener{
		/**
		 * 显示
		 * @param height 键盘高度
         */
		void show(int height);

		/**
		 * 键盘隐藏
		 */
		void hide();
	}

}
