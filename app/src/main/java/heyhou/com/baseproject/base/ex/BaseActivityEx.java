package heyhou.com.baseproject.base.ex;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import heyhou.com.baseproject.base.BaseActivity;


/**
 * Created by Neil.Yang on 2017/2/23.
 *
 * 此类为MVP 开发的基类， 所有的Activity 都必须继承这个类
 *
 *
 *
 */

public abstract class BaseActivityEx extends BaseActivity implements IBaseDataView{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void setContentView(int layoutResID) {
        View view = getLayoutInflater().inflate(layoutResID, null);
        ViewAnotationUtil.autoInjectAllField(this, view);
        setContentView(view);

        /**
         * 绑定presenter 中的View
         * */
        BasePresenter presenter = getPresenter();
        if (presenter != null) {
            presenter.attachView(this);
        }
    }

    @Override
    protected void onDestroy() {
        /**
         * 解绑定presenter 中的View
         * */
        BasePresenter presenter = getPresenter();
        if (presenter != null) {
            presenter.dettachView();
        }
        super.onDestroy();
    }

    @Override
    public Context getContext() {
        return this;
    }

    /**
     * 获取presenter。
     * presenter 在子类中创建， 并且只创建一次。在这个函数中返回。
     * */
    protected abstract BasePresenter getPresenter();
}
