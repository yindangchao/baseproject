package heyhou.com.baseproject.base.ex;

import android.content.Context;

/**
 * Created by Neil.Yang on 2017/2/23.
 *
 * 此类为Presenter和View 通信的接口类的父接口，
 * 所有与View 通信的接口都必须继承这个
 *
 */

public interface IBaseDataView {

    /**
     *
     * 获取Activity 或者Fragment的Context
     *
     * */
    Context getContext();
}
