package heyhou.com.baseproject.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;


import java.util.ArrayList;

import heyhou.com.baseproject.bean.AutoType;

/**
 * 创建:yb 2017/2/4.
 * 描述:ListView多布局
 */

public abstract class MultiItemListAdapter<T extends AutoType> extends CommAdapter<T> {

    protected MultiItemListTypeSupport<T> typeSupport;

    public MultiItemListAdapter(Context mContext,ArrayList<T> mData,MultiItemListTypeSupport<T> support) {
        super(mContext,mData,-1);
        this.typeSupport = support;
    }

    /**
     * @param mContext     上下文对象
     * @param mData        数据源
     * @param itemLayoutId listview对应的Item
     */
    public MultiItemListAdapter(Context mContext, ArrayList<T> mData, int itemLayoutId) {
        super(mContext, mData, itemLayoutId);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        int layoutId = typeSupport.getLayoutId(getItemViewType(position));
        CommViewHolder viewHolder = CommViewHolder.get(mContext, convertView, parent, layoutId, position);
        convert(viewHolder, getItem(position));
        return viewHolder.getConvertView();
    }

    @Override
    public int getViewTypeCount() {
        return typeSupport.obtainViewTypeCount();
    }

    @Override
    public int getItemViewType(int position) {
        return typeSupport.obtainItemViewType(position,mData.get(position));
    }

    public interface MultiItemListTypeSupport<T> {
        int getLayoutId(int viewType);
        int obtainViewTypeCount();
        int obtainItemViewType(int position, T item);
    }

}
