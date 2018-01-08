package heyhou.com.baseproject.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by lky on 2016/10/13.
 */

public abstract class CommRecyclerViewAdapter<T> extends RecyclerView.Adapter<CommRecyclerViewHolder> {

    protected LayoutInflater mInflater;
    protected Context mContext;
    protected List<T> mDatas = null;
    protected final int mItemLayoutId;

    public CommRecyclerViewAdapter(Context context, List<T> data, int itemLayoutId) {
        mContext = context;
        mDatas = data;
        mItemLayoutId = itemLayoutId;
        mInflater = LayoutInflater.from(context);
    }


    @Override
    public CommRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CommRecyclerViewHolder viewHolder = CommRecyclerViewHolder.get(mContext, parent, mItemLayoutId);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(CommRecyclerViewHolder holder, int position) {
        try {
            convert(holder, mDatas.get(position));
        } catch (IndexOutOfBoundsException e) {
            convert(holder, null);
        }
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    public abstract void convert(CommRecyclerViewHolder holder, T t);

}
