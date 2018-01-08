package heyhou.com.baseproject.base.ex;

import android.support.v7.widget.RecyclerView;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Neil.Yang on 2017/3/28.
 *
 *  加载列表数据的Presenter
 *
 */

public abstract class BaseListPresenter<V extends IBaseListView, D> extends BasePresenter<V> {

    //下拉刷新数据的状态
    public static final int LOAD_STATUS_REFRESH = 1;
    //加载更多数据的状态
    public static final int LOAD_STATUS_MORE = 2;
    //默认每页加载数据的大小
    public static final int DEFAULT_LOAD_SIZE = 20;

    // 对应listview的数据集合
    private List<D> mData = new ArrayList<>();
    // 请求数据开始的位置
    private int mStart = 0;
    // 请求数据多少
    private int mLimit = DEFAULT_LOAD_SIZE;

    private Object[] mExtraParameter;

    protected void refreshData(List<D> newData, int status) {

        if (status == LOAD_STATUS_REFRESH) {
            mData.clear();
        }
        addData(newData, status);
        mDataView.onEmptyData(mData.isEmpty());
    }
    private void addData(List<D> newData, int status) {
        boolean hasMoreData = false;
        if (newData != null && !newData.isEmpty()) {
            hasMoreData = true;
            //如果获取的数据的数量小于请求的数据的数量，表示没有更多数据了。
            if (newData.isEmpty()) {
                hasMoreData = false;
            }
            mData.addAll(newData);
        }
        notifyDataSetChanges();
        //通知view 没有更多数据
        if (status == LOAD_STATUS_REFRESH) {
            mDataView.onRefreshComplete();
            if (!mData.isEmpty()) {
                mDataView.onLoadMoreComplete(hasMoreData);
            }
        } else if (status == LOAD_STATUS_MORE) {
            mDataView.onLoadMoreComplete(hasMoreData);
        }
    }

    public void setExtraParameter(Object...parameters) {
        mExtraParameter = parameters;
    }

    /**
     * 通知adapter 数据发生了变化
     *
     * */
    protected void notifyDataSetChanges() {
        RecyclerView.Adapter rvAdapter = mDataView.getRecycleViewAdapter();
        if (rvAdapter != null) {
            rvAdapter.notifyDataSetChanged();
        }

        BaseAdapter adapter = mDataView.getListViewAdapter();
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
    }

    /**
     *  获取开始的位置、
     */
    public int getStart() {
        return mStart;
    }
    /**
     *  设置开始的位置
     */
    public void setStart(int start) {
        mStart = start;
    }

    /**
     *  获取每页的数量
     */
    public int getLimit() {
        return mLimit;
    }
    /**
     *  设置每页的数量
     */
    public void setLimit(int limit) {
        mLimit = limit;
    }

    /**
     *  获取数据列表
     */
    public List<D> getData() {
        return mData;
    }

    /**
     *  设置数据列表
     */
    public void setData(List<D> d) {
        mData = d;
    }

    /**
     *  加载第一页的数据
     *
     * */
    public void loadFirstPageData() {
        mStart = 0;
        loadPageData(mStart, mLimit, LOAD_STATUS_REFRESH, mExtraParameter);
    }
    /**
     *  加载下一页的数据
     * */
    public void loadNextPageData() {
        mStart += mLimit;
        loadPageData(mStart, mLimit, LOAD_STATUS_MORE, mExtraParameter);
    }

    /**
     *  加载数据
     *
     * @Param start 开始
     * @Param limit 请求数据的多少
     * @Param  loadStatus 加载数据的状态，是下拉刷新更多还是上拉加载
     * */
    public abstract void loadPageData(int start, int limit, int loadStatus, Object...parameters);

    /**
     *  加载数据失败，同view 加载数据失败
     * */
    public void loadDataError(int errorCode, String errorMsg, int status) {
        if (status == LOAD_STATUS_REFRESH) {
            mDataView.onLoadDataFailed(errorCode, errorMsg);
            mDataView.onRefreshComplete();
            mDataView.onEmptyData(mData.isEmpty());
        } else if (status == LOAD_STATUS_MORE) {
            mDataView.onLoadDataFailed(errorCode, errorMsg);
            /**
             *  加载更多数据失败，表示还有更多的数据
             * */
            mDataView.onLoadMoreComplete(true);
        }

    }

}
