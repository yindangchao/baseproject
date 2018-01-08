package heyhou.com.baseproject.network.ex;

import java.lang.reflect.Type;

/**
 * Created by Neil.Yang on 2017/3/29.
 */

public class PostUIDecorator<T> extends PostUI<T> {

    PostUI mPostUI;

    public PostUIDecorator(PostUI postUI) {
        mPostUI = postUI;
    }

    @Override
    public void showLoading() {
        if (mPostUI != null) {
            mPostUI.showLoading();
        }
    }

    @Override
    public void hideLoading() {
        if (mPostUI != null) {
            mPostUI.hideLoading();
        }
    }

    @Override
    public boolean isShowLoading() {
        return mPostUI.isShowLoading();
    }

    @Override
    public Type getDataTypeClass() {
        if (mPostUI != null) {
            return mPostUI.getDataTypeClass();
        }
        return null;
    }

    @Override
    public void onSucceed(HttpResponseData data) {
        if (mPostUI != null) {
            mPostUI.onSucceed(data);
        }
    }

    @Override
    public void onFailed(int errorCode, String errorMsg) {
        if (mPostUI != null) {

            mPostUI.onFailed(errorCode, errorMsg);
        }
    }
}
