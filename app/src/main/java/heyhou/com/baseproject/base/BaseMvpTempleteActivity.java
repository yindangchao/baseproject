package heyhou.com.baseproject.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import heyhou.com.baseproject.R;
import heyhou.com.baseproject.base.ex.BasePresenter;
import heyhou.com.baseproject.base.ex.IBaseDataView;
import heyhou.com.baseproject.base.ex.ViewAnotationUtil;

import java.io.Serializable;

/**
 * 创建:yb 2016/10/14.
 * 描述:
 */

public abstract class BaseMvpTempleteActivity extends BaseAppCompatActivity implements View.OnClickListener,IBaseDataView {

    private View title;
    private View contentView;
    protected Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(createContentView());
        this.mContext = this;
        init();
    }

    private View createContentView() {
        LinearLayout parent = new LinearLayout(this);
        parent.setOrientation(LinearLayout.VERTICAL);
        FrameLayout.LayoutParams contentParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        parent.setLayoutParams(contentParams);
        title = getCustomTitle() != null ? getCustomTitle() :
                LayoutInflater.from(this).inflate(R.layout.layout_common_title, parent, false);
        contentView = LayoutInflater.from(this).inflate(getLayoutId(), parent, false);
        ViewAnotationUtil.autoInjectAllField(this, contentView);//控件注入
        parent.addView(title);
        parent.addView(contentView);
        return parent;
    }

    protected void hideTitle() {
        title.setVisibility(View.GONE);
    }

    private final void init() {
        initPresenter();
        initViews();
        initEvents();
        initDatas();
    }

    private void initPresenter() {
        /**
         * 绑定presenter 中的View
         * */
        BasePresenter presenter = getPresenter();
        if (presenter != null) {
            presenter.attachView(this);
        }
    }

    protected View getCustomTitle() {
        return null;
    }

    protected View getContentView() {
        return contentView;
    }

    protected final <T> T getViewById(int viewId) {
        T view = (T) findViewById(viewId);
        return view;
    }

    protected void startActivity(Class<? extends Activity> target) {
        startActivity(target, null, null);
    }

    protected <T extends Serializable> void startActivity(Class<? extends Activity> target, String key, T data) {
        Intent intent = new Intent(this, target);
        if (data != null)
            intent.putExtra(key, data);
        startActivity(intent);
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

    public abstract int getLayoutId();

    public abstract void initViews();

    public abstract void initEvents();

    public abstract void initDatas();

    public abstract void processClick(View view);

    @Override
    public void onClick(View v) {
        processClick(v);
    }

}
