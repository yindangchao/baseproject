package heyhou.com.baseproject.utils;

import android.support.v4.widget.NestedScrollView;

/**
 * 创建:yb 2017/6/5.
 * 描述:
 */

public class NestedScrollChangeListener implements NestedScrollView.OnScrollChangeListener {

    @Override
    public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
        if (scrollY > oldScrollY) {
            // 向上滑动
        }
        if (scrollY < oldScrollY) {
            // 向下滑动
        }
        if (scrollY == 0) {
            // 顶部
        }
        if (scrollY == (v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight())) {
            // 底部
            onScrollBottom();
        }
        update(v,scrollX,scrollY,oldScrollX,oldScrollY);
    }

    protected void onScrollBottom() {
    }

    protected void update(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
    }
}
