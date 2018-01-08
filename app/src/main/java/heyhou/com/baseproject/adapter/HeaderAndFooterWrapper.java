package heyhou.com.baseproject.adapter;

import android.support.v4.util.SparseArrayCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import heyhou.com.baseproject.utils.WrapperUtils;


/**
 * Created by lky on 2016/10/24.
 */

public abstract class HeaderAndFooterWrapper<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder>
{
    private static final int BASE_ITEM_TYPE_HEADER = 100000;
    private static final int BASE_ITEM_TYPE_FOOTER = 200000;

    private SparseArrayCompat<View> mHeaderViews = new SparseArrayCompat<>();
    private SparseArrayCompat<View> mFootViews = new SparseArrayCompat<>();

    private RecyclerView.Adapter mInnerAdapter;

    public HeaderAndFooterWrapper(RecyclerView.Adapter adapter)
    {
        mInnerAdapter = adapter;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        if (mHeaderViews.get(viewType) != null)
        {
            CommRecyclerViewHolder holder = CommRecyclerViewHolder.get(parent.getContext(), mHeaderViews.get(viewType));
            return holder;

        } else if (mFootViews.get(viewType) != null)
        {
            CommRecyclerViewHolder holder = CommRecyclerViewHolder.get(parent.getContext(), mFootViews.get(viewType));
            return holder;
        }
        return mInnerAdapter.onCreateViewHolder(parent, viewType);
    }

    @Override
    public int getItemViewType(int position)
    {
        if (isHeaderViewPos(position))
        {
            return mHeaderViews.keyAt(position);
        } else if (isFooterViewPos(position))
        {
            return mFootViews.keyAt(position - getHeadersCount() - getRealItemCount());
        }
        return mInnerAdapter.getItemViewType(position - getHeadersCount());
    }

    private int getRealItemCount()
    {
        return mInnerAdapter.getItemCount();
    }


    public abstract void convertHeader(RecyclerView.ViewHolder holder,int position);

    public abstract void convertFooter(RecyclerView.ViewHolder holder,int position);

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position)
    {
        if (isHeaderViewPos(position))
        {
            convertHeader(holder,position);
            return;
        }
        if (isFooterViewPos(position))
        {
            convertFooter(holder,position);
            return;
        }
        mInnerAdapter.onBindViewHolder(holder, position - getHeadersCount());
    }

    @Override
    public int getItemCount()
    {
        return getHeadersCount() + getFootersCount() + getRealItemCount();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView)
    {
        WrapperUtils.onAttachedToRecyclerView(mInnerAdapter, recyclerView, new WrapperUtils.SpanSizeCallback() {
            @Override
            public int getSpanSize(GridLayoutManager layoutManager, GridLayoutManager.SpanSizeLookup oldLookup, int position) {
                int viewType = getItemViewType(position);
                if (mHeaderViews.get(viewType) != null) {
                    return layoutManager.getSpanCount();
                } else if (mFootViews.get(viewType) != null) {
                    return layoutManager.getSpanCount();
                }
                if (oldLookup != null)
                    return oldLookup.getSpanSize(position);
                return 1;
            }
        });
    }

    @Override
    public void onViewAttachedToWindow(RecyclerView.ViewHolder holder)
    {
        mInnerAdapter.onViewAttachedToWindow(holder);
        int position = holder.getLayoutPosition();
        if (isHeaderViewPos(position) || isFooterViewPos(position))
        {
            WrapperUtils.setFullSpan(holder);
        }
    }

    private boolean isHeaderViewPos(int position)
    {
        return position < getHeadersCount();
    }

    private boolean isFooterViewPos(int position)
    {
        return position >= getHeadersCount() + getRealItemCount();
    }


    /**
     * （warning）不支持add同一个view多次
     * @param view
     */
    public void addHeaderView(View view)
    {

        if(mHeaderViews.indexOfValue(view) < 0){
            if(view.getParent() != null){
                ((ViewGroup)view.getParent()).removeView(view);
            }
            mHeaderViews.put(mHeaderViews.size() + BASE_ITEM_TYPE_HEADER, view);
        }
    }

    public void addHeaderViewToFirst(View view){
        if(mHeaderViews.indexOfValue(view) < 0){
            if(view.getParent() != null){
                ((ViewGroup)view.getParent()).removeView(view);
            }
            mHeaderViews.put(mHeaderViews.keyAt(0)-1, view);
        }
    }

    public void removeHeaderView(View view){
        if(mHeaderViews.size()>0){
            int position = mHeaderViews.indexOfValue(view);
            if(position >= 0){
                mHeaderViews.removeAt(position);
            }
        }
    }

    public void addFootView(View view)
    {
        mFootViews.put(mFootViews.size() + BASE_ITEM_TYPE_FOOTER, view);
    }

    public void removeFootView(View view){
        if(mFootViews.size()>0){
            int position = mFootViews.indexOfValue(view);
            if(position >= 0){
                mFootViews.removeAt(position);
            }
        }
    }

    public int getHeadersCount()
    {
        return mHeaderViews.size();
    }

    public int getFootersCount()
    {
        return mFootViews.size();
    }

    public RecyclerView.Adapter getWrapperAdapter(){
        return mInnerAdapter;
    }
}