package heyhou.com.baseproject.base;

import android.os.Bundle;

import heyhou.com.baseproject.R;
import heyhou.com.baseproject.base.ex.BaseActivityEx;
import heyhou.com.baseproject.utils.StatusBarCompat;

import me.imid.swipebacklayout.lib.SwipeBackLayout;
import me.imid.swipebacklayout.lib.Utils;
import me.imid.swipebacklayout.lib.app.SwipeBackActivityBase;
import me.imid.swipebacklayout.lib.app.SwipeBackActivityHelper;

/**
 * 创建:yb 2017/7/10.
 * 描述:滑动返回
 */

public abstract class BaseSwipeBackActivity extends BaseActivityEx implements SwipeBackActivityBase {
    private SwipeBackActivityHelper mHelper;

    protected enum SwipeDirection {
        LEFT,RIGHT,BOTTOM,ALL
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarCompat.translucentStatus(this);
        overridePendingTransition(R.anim.slide_in_from_right, R.anim.tidal_search_no_anim);
        mHelper = new SwipeBackActivityHelper(this);
        mHelper.onActivityCreate();
        getSwipeBackLayout().setEdgeTrackingEnabled(getEdge());
//        setTheme(R.style.Swipe_back);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mHelper.onPostCreate();
    }

    private int getEdge() {
        SwipeDirection direction = getSwipeDirection();
        int edage = SwipeBackLayout.EDGE_LEFT;
        if (direction == SwipeDirection.LEFT) {
        } else if (direction == SwipeDirection.RIGHT) {
            edage = SwipeBackLayout.EDGE_RIGHT;
        } else if (direction == SwipeDirection.BOTTOM) {
            edage = SwipeBackLayout.EDGE_BOTTOM;
        } else if (direction == SwipeDirection.ALL) {
            edage = SwipeBackLayout.EDGE_ALL;
        }
        return edage;
    }

    @Override
    public final SwipeBackLayout getSwipeBackLayout() {
        return mHelper.getSwipeBackLayout();
    }

    @Override
    public final void setSwipeBackEnable(boolean enable) {
        getSwipeBackLayout().setEnableGesture(enable);
    }

    @Override
    public final void scrollToFinishActivity() {
        Utils.convertActivityToTranslucent(this);
        getSwipeBackLayout().scrollToFinishActivity();
    }

    protected SwipeDirection getSwipeDirection() {
        return SwipeDirection.LEFT;
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.tidal_search_no_anim, R.anim.slide_out_to_right);
    }
}
