package heyhou.com.baseproject.base.ex;

/**
 * Created by Neil.Yang on 2017/3/28.
 */

public abstract class BaseListFragment extends BaseFragmentEx implements IBaseListView {

    /**
     * 初始化 上拉，下拉刷新的页面
     * */
    protected void initRefreshableView() {
        BaseListViewHelper.initRefreshableView(this, getPresenter(), true);
    }

    /**
     * 初始化 上拉，下拉刷新的页面
     * */
    protected void initRefreshableView(boolean autoRefresh) {
        BaseListViewHelper.initRefreshableView(this, getPresenter(), autoRefresh);
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
        BaseListViewHelper.onEmptyData(empty,this);
    }
}
