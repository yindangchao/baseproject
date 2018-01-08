package heyhou.com.baseproject.utils;

import android.content.AsyncQueryHandler;
import android.content.ContentResolver;
import android.database.Cursor;
import android.widget.CursorAdapter;

/**
 * 创建:yb 2016/10/22.
 * 描述:
 */

public class SimpleQueryHandler extends AsyncQueryHandler {
    public SimpleQueryHandler(ContentResolver cr) {
        super(cr);
    }

    @Override
    protected void onQueryComplete(int token, Object cookie, Cursor cursor) {
        if(cookie!=null && cookie instanceof CursorAdapter){
            CursorAdapter adapter = (CursorAdapter) cookie;
            adapter.changeCursor(cursor);//  相当于notifyDatesetChange
        } else if (cookie != null && cookie instanceof LocalResourceHandler.QueryCallBack) {
            LocalResourceHandler.QueryCallBack queryCallBack = (LocalResourceHandler.QueryCallBack) cookie;
            queryCallBack.onSuccess(cursor);
        }
    }
}
