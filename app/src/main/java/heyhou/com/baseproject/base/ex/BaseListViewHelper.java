package heyhou.com.baseproject.base.ex;

import android.view.View;

import heyhou.com.baseproject.pull.PtrClassicFrameLayout;
import heyhou.com.baseproject.pull.PtrDefaultHandler;
import heyhou.com.baseproject.pull.PtrFrameLayout;
import heyhou.com.baseproject.pull.loadmore.OnLoadMoreListener;


/**
 * Created by Neil.Yang on 2017/3/30.
 */

public final class BaseListViewHelper {

    /**
     * 初始化 上拉，下拉刷新的页面
     * */
    public static void initRefreshableView(final IBaseListView baseListView, final BasePresenter presenter, boolean autoRefresh) {

        PtrClassicFrameLayout refreshView = baseListView.getRefreshableView();
        if (refreshView == null) {
            return;
        }
        refreshView.setLoadMoreEnable(true);
        refreshView.setPtrHandler(new PtrDefaultHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                if (presenter != null && presenter instanceof BaseListPresenter) {
                    BaseListPresenter listPresenter = (BaseListPresenter)presenter;
                    listPresenter.loadFirstPageData();
                }
            }
        });

        refreshView.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void loadMore() {
                if (presenter != null && presenter instanceof BaseListPresenter) {
                    BaseListPresenter listPresenter = (BaseListPresenter)presenter;
                    listPresenter.loadNextPageData();
                }
            }
        });

        if (autoRefresh) {
            refreshView.autoRefresh();
        }
    }

    public static void onLoadMoreComplete(boolean hasMore, final IBaseListView baseListView) {
        PtrClassicFrameLayout refreshView = baseListView.getRefreshableView();
        if (refreshView != null) {
            refreshView.loadMoreComplete(hasMore);
        }
    }

    public static void onRefreshComplete(IBaseListView baseListView) {
        PtrClassicFrameLayout refreshView = baseListView.getRefreshableView();
        if (refreshView != null) {
            refreshView.refreshComplete();
        }
    }

    public  static void onEmptyData(boolean empty, IBaseListView baseListView) {
        View v = baseListView.getEmptyDataView();
        if (v != null) {
            int visible = empty ? View.VISIBLE : View.GONE;
            v.setVisibility(visible);
        }

        if (empty) {
            PtrClassicFrameLayout refreshView = baseListView.getRefreshableView();
            if (refreshView != null) {
                refreshView.setLoadMoreEnable(false);
            }
        }
    }

}
