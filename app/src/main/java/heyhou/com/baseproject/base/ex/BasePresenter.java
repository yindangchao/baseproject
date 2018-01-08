package heyhou.com.baseproject.base.ex;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Proxy;
import java.lang.reflect.Type;

import heyhou.com.baseproject.utils.DebugTool;

/**
 * Created by Neil.Yang on 2017/2/23.
 *
 * BasePresenter
 *
 */

public class BasePresenter<T extends IBaseDataView>{

    protected final String TAG = getClass().getSimpleName();

    //presenter 对应的View的接口类的代理对象
    protected T mDataView;

    //与View进行通讯的接口对应的class
    private Class mViewClass;
    //真是的view对象
    private T mRealView;

    public BasePresenter() {

        Type viewType = getClass().getGenericSuperclass();
        Type realType = ((ParameterizedType)viewType).getActualTypeArguments()[0];
        mViewClass = (Class)realType;
        mDataView = new ViewProxy().getProxy();
    }

    /**
     * 绑定View到presenter上
     *
     * @view  view
     * **/
    public void attachView(T view) {
        DebugTool.warn(TAG, "attachView to presenter : " + view.getClass().getSimpleName());
        mRealView = view;
    }

    /**
     * 解绑定presenter 与 View
     *
     * @view  view
     * **/
    public void dettachView() {
        DebugTool.warn(TAG, "dettachView to presenter : " + mRealView.getClass().getSimpleName());
        mRealView = null;
    }

    protected boolean isViewDestroyed() {
        return mRealView == null;
    }


    /**
     *  此类为View的接口的动态代理,目的是为了在子类中直接调用view的方法，而不用担心view 为空的情况。
     *  原来的情况我们调用view的方法代码如下：
     *   if (mDataView != null) {
     *       mDataView.xxxxx();
     *   }
     *
     *   现在可以直接调用 如下：
     *   mDataView.xxxxx();
     *
     * */
    public class ViewProxy implements InvocationHandler {
        private T proxy;

        public ViewProxy() {
            proxy = (T) Proxy.newProxyInstance(this.getClass().getClassLoader(), new Class[]{mViewClass}, this) ;
        }
        public T getProxy() {
            return proxy;
        }
        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

            Object result = null;
            String methodName = method.getName();
            if (mRealView != null) {
                result = method.invoke(mRealView, args);
            } else {
                DebugTool.warn(TAG, "mRealView is null : "  + methodName);
            }
            return result;
        }
    }

}
