package heyhou.com.baseproject.base.ex;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import heyhou.com.baseproject.base.BaseFragment;


/**
 * Created by Neil.Yang on 2017/2/23.
 *
 * 此类为MVP 开发的基类， 所有的Fragment 都必须继承这个类
 *
 */

public abstract class BaseFragmentEx extends BaseFragment implements IBaseDataView {

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ViewAnotationUtil.autoInjectAllField(this, view);
        /**
         * 绑定presenter 中的View
         * */
        BasePresenter presenter = getPresenter();
        if (presenter != null) {
            presenter.attachView(this);
        }
    }

    @Override
    public void onDestroyView() {
        /**
         * 解绑定presenter 中的View
         * */
        BasePresenter presenter = getPresenter();
        if (presenter != null) {
            presenter.dettachView();
        }
        super.onDestroyView();
    }

    /**
     * 获取presenter。
     * presenter 在子类中创建， 并且只创建一次。在这个函数中返回。
     * */
    protected abstract BasePresenter getPresenter();

}
