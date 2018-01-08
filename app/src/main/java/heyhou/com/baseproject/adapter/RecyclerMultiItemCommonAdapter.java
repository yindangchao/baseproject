package heyhou.com.baseproject.adapter;

import android.content.Context;
import android.view.ViewGroup;

import java.util.ArrayList;

import heyhou.com.baseproject.bean.AutoType;


/**
 * Created by lky on 2016/10/14.
 */

public abstract class RecyclerMultiItemCommonAdapter<T extends AutoType> extends CommRecyclerViewAdapter<T>
{
    protected RecyclerMultiItemTypeSupport<T> mMultiItemTypeSupport;

    public RecyclerMultiItemCommonAdapter(Context context, ArrayList<T> datas,
                                          RecyclerMultiItemTypeSupport<T> multiItemTypeSupport)
    {
        super(context, datas, -1);
        mMultiItemTypeSupport = multiItemTypeSupport;
    }

    @Override
    public int getItemViewType(int position)
    {
        return mMultiItemTypeSupport.getItemViewType(position, mDatas.get(position));
    }

    @Override
    public CommRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        int layoutId = mMultiItemTypeSupport.getLayoutId(viewType);
        CommRecyclerViewHolder holder = CommRecyclerViewHolder.get(mContext, parent, layoutId);
        return holder;
    }

}