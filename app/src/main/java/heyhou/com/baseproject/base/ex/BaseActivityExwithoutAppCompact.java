package heyhou.com.baseproject.base.ex;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import heyhou.com.baseproject.R;


/**
 * Created by ydc on 2017/8/2.
 */
public abstract class BaseActivityExwithoutAppCompact extends Activity implements IBaseDataView {
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

    protected void setRightText(String text) {
        TextView tvRightText = (TextView) findViewById(R.id.title_right_text);
        tvRightText.setVisibility(View.VISIBLE);
        tvRightText.setText(text);
        tvRightText.setOnClickListener(listener);
    }
    protected void setRightText(int resId) {
        TextView tvRightText = (TextView) findViewById(R.id.title_right_text);
        tvRightText.setVisibility(View.VISIBLE);
        tvRightText.setText(resId);
        tvRightText.setOnClickListener(listener);
    }

    protected void onHeadRightClick() {

    }
    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.title_left_back:
                    onBackPressed();
                    break;
                case R.id.title_right_text:
                    onHeadRightClick();
                    break;
                case R.id.title_right_edit:
                    onHeadRightClick();
                    break;
            }
        }
    };
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
     */
    protected abstract BasePresenter getPresenter();
}
