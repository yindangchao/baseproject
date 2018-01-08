package heyhou.com.baseproject.databases;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.table.TableUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Neil.Yang on 2017/3/2.
 * <p/>
 * 数据库中数据的迁移
 */

public class DBMigration {

    private static final String TAG = "DBMigration";
    private OrmLiteSqliteOpenHelper mDBHelper;

    public DBMigration(OrmLiteSqliteOpenHelper dbHelper) {
        mDBHelper = dbHelper;
    }

    /**
     * 根据所给的类迁移原有数据库中的数据
     */
    public void migrateTables(SQLiteDatabase db, Class... classes) {
        if (classes != null && classes.length > 0) {
            for (Class cls : classes) {
                migrate(cls, db);
            }
        }
    }

    /**
     * 根据所给的类删除对应的表
     */
    public void dropTables(SQLiteDatabase db, Class... classes) {
        if (classes != null && classes.length > 0) {
            for (Class cls : classes) {
                RuntimeExceptionDao dao = mDBHelper.getRuntimeExceptionDao(cls);
                dropTable(dao.getTableName(), db);
            }
        }
    }


    /**
     * 迁移一个表的数据的步骤。
     * 1. 判断之前的表是否存在，如果存在就备份原有的表，并且创建新表。
     * 2. 获取新表的全部字段和老表的全部字段，从中筛选中公共的字段。
     * 3. 把这些公共的字段对应的数据从旧表导入新表，
     * 4. 删除旧表的备份
     */
    private void migrate(Class cls, SQLiteDatabase db) {
        try {

            RuntimeExceptionDao dao = mDBHelper.getRuntimeExceptionDao(cls);
            boolean exist = dao.isTableExists();
            String tableName = dao.getTableName();
            Log.d(TAG, tableName + "  exist value: " + exist);
            String oldTableName = tableName + "_temp";
            if (exist) {
                if (checkTableExist(oldTableName, db)) {
                    dropTable(oldTableName, db);
                }
                renameTable(tableName, oldTableName, db);
            }
            dao = mDBHelper.getRuntimeExceptionDao(cls);
            //创建新表
            TableUtils.createTable(dao);
            if (!exist) {
                return;
            }

            String newTable = dao.getTableName();
            //统计新表中有的旧表的字段
            List<String> colums = new ArrayList<String>();
            String[] oldColumnNames = getColumnNames(oldTableName, db);
            String[] newColumnNames = getColumnNames(newTable, db);
            if (oldColumnNames != null && newColumnNames != null) {
                for (String oldName : oldColumnNames) {
                    for (String newName : newColumnNames) {
                        if (TextUtils.equals(oldName, newName)) {
                            colums.add(oldName);
                            break;
                        }
                    }
                }
            }

            //凭借所有的列的名字
            String dataColums = "";
            for (int i = 0; i < colums.size(); i++) {
                dataColums += colums.get(i);
                if (i < colums.size() - 1) {
                    dataColums += ",";
                }
            }
            //导入旧表的数据到新的表
            String sql = String.format("insert into %s(%s) select %s from %s", newTable, dataColums, dataColums, oldTableName);
            executeSQL(sql, db);
            //删除旧的表
            dropTable(oldTableName, db);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void renameTable(String oldName, String newName, SQLiteDatabase db) {
        String sql = String.format("ALTER TABLE %s RENAME TO %s", oldName, newName);
        executeSQL(sql, db);
    }

    private void dropTable(String tableName, SQLiteDatabase db) {
        String sql = String.format("drop table %s", tableName);
        if (checkTableExist(tableName, db)) {
            executeSQL(sql, db);
        }
    }

    private boolean checkTableExist(String tableName, SQLiteDatabase db) {
        boolean result = false;
        String sql = String.format("SELECT count(*) as number  FROM sqlite_master where type='table' and name='%s'", tableName);
        Cursor cursor = db.rawQuery(sql, null);
        try {
            if (cursor != null && cursor.moveToFirst()) {
                Long count = cursor.getLong(cursor.getColumnIndex("number"));
                result = (count > 0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }
        Log.d(TAG, "checkTableExist-->" + tableName + " : " + result);
        return result;
    }

    private String[] getColumnNames(String tableName, SQLiteDatabase db) {
        Cursor cursor = null;
        String[] columnNames = null;
        try {
            cursor = db.rawQuery("select * from " + tableName, null);
            columnNames = cursor.getColumnNames();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return columnNames;
    }


    private void executeSQL(String sql, SQLiteDatabase db) {
        Log.d(TAG, "execute sql :" + sql);
        try {
            db.beginTransaction();
            db.execSQL(sql);
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
    }

}
