package heyhou.com.baseproject.base.ex;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.BaseAdapter;

import heyhou.com.baseproject.pull.PtrClassicFrameLayout;


/**
 * Created by Neil.Yang on 2017/3/28.
 */

public interface IBaseListView extends IBaseDataView {

    /**
     *  获取可刷新的view
     * */
    PtrClassicFrameLayout getRefreshableView();

    /**
     * 获取list的adapter
     * */
    BaseAdapter getListViewAdapter();

    /**
     * 获取RecycleView的adapter
     * */
    RecyclerView.Adapter getRecycleViewAdapter();

    /**
     * 获取列表数据为空的时候显示的view
     * */
    View getEmptyDataView();

    /**
     *  列表没有数据的回调，
     *  @Param empty  true: 没有数据； false：有数据
     *
     * */
    void onEmptyData(boolean empty);

    /**
     * 加载数据数据失败
     * */
    void onLoadDataFailed(int errorStatus, String errorMsg);

    /**
     *  刷新数据完成
     * */
    void onRefreshComplete();

    /**
     *  加载更多数据完成
     * */
    void onLoadMoreComplete(boolean hasMore);
}
