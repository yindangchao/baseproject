package heyhou.com.baseproject.adapter;

/**
 * Created by lky on 2016/10/14.
 */

public interface RecyclerMultiItemTypeSupport<T> {

    int getLayoutId(int itemType);

    int getItemViewType(int position, T t);

}
