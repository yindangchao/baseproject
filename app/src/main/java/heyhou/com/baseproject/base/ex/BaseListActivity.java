package heyhou.com.baseproject.base.ex;

/**
 * Created by Neil.Yang on 2017/3/29.
 */

public abstract class BaseListActivity extends BaseActivityEx implements IBaseListView {

    /**
     * 初始化 上拉，下拉刷新的页面
     * */
    protected void initRefreshableView() {
        BaseListViewHelper.initRefreshableView(this, getPresenter(), true);
    }

    /**
     * 初始化 上拉，下拉刷新的页面
     * */
    protected void initRefreshableView(boolean autoRefersh) {
        BaseListViewHelper.initRefreshableView(this, getPresenter(), autoRefersh);
    }

    @Override
    public void onLoadMoreComplete(boolean hasMore) {
        BaseListViewHelper.onLoadMoreComplete(hasMore, this);
    }

    @Override
    public void onRefreshComplete() {
        BaseListViewHelper.onRefreshComplete(this);
    }

    @Override
    public void onEmptyData(boolean empty) {
        BaseListViewHelper.onEmptyData(empty, this);
    }
}
