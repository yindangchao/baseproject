package heyhou.com.baseproject.utils;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

/**
 * 创建:yb 2016/10/28.
 * 描述:
 */

public class LocalResourceHandler {
    private static SimpleQueryHandler queryHandler;
    private Context mContext;
    private ResourceType mType = ResourceType.VIDEO;
    private String[] projections;
    private QueryCallBack callBack;
    private String where;
    private String[] whereSelections;

    private LocalResourceHandler(Context context) {
        this.mContext = context;
    }

    public static LocalResourceHandler with(Context context) {
        LocalResourceHandler handler = new LocalResourceHandler(context);
        queryHandler = new SimpleQueryHandler(context.getContentResolver());
        return handler;
    }

    public enum ResourceType {
        VIDEO, AUDIO
    }

    public LocalResourceHandler type(ResourceType type) {
        mType = type;
        return this;
    }

    public LocalResourceHandler projection(String[] projections) {
        this.projections = projections;
        return this;
    }

    public LocalResourceHandler where(String where) {
        this.where = where;
        return this;
    }
    public LocalResourceHandler whereSelections(String[] whereSelections) {
        this.whereSelections = whereSelections;
        return this;
    }

    public LocalResourceHandler callback(QueryCallBack callBack) {
        this.callBack = callBack;
        return this;
    }

    public void execute() {
        if (mType == ResourceType.VIDEO) {
            loadViedeos();
        } else if (mType == ResourceType.AUDIO) {
            loadAudios();
        }
    }

    private void loadViedeos() {
        startQuery(MediaStore.Video.Media.EXTERNAL_CONTENT_URI);
    }

    private void loadAudios() {
        startQuery(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI);
    }

    private void startQuery(Uri uri) {
        queryHandler.startQuery(0, callBack, uri, projections, where, whereSelections, null);
    }

    public static interface QueryCallBack {
        void onSuccess(Cursor cursor);
    }

}
