package heyhou.com.baseproject.databases;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import heyhou.com.baseproject.base.Constant;


/**
 * @author ydc
 * @ClassName: DBHelper.java
 * @Description: 数据库帮助类
 * @date 2012-12-12 下午12:48:48
 */
public class DBHelper extends OrmLiteSqliteOpenHelper {

    public static final String NAME = "social.db"; // DB name
    private Context context;

    Class[] mClasses = {};

    public DBHelper(Context context) {
        super(context, NAME, null, Constant.DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource) {
        /**
         *  创建所需要的表
         *
         * **/
        for (Class cls : mClasses) {
            try {
                TableUtils.createTable(connectionSource, cls);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource, int oldVersion, int newVersion) {

        DBMigration migration = new DBMigration(this);


//        if (oldVersion < 25) {
//            migration.dropTables(sqLiteDatabase, LocalVideoPlayInfo.class,
//                    DownloadVideoInfo.class, DownloadCacheVideoInfo.class);
//        }


        migration.migrateTables(sqLiteDatabase, mClasses);
    }


}
