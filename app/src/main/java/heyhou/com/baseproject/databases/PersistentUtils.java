package heyhou.com.baseproject.databases;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.stmt.DeleteBuilder;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import heyhou.com.baseproject.bean.AutoType;
import heyhou.com.baseproject.utils.AppUtil;


/**
 * @author ydc
 * @ClassName: PersistentUtils.java
 * @Description: 数据库操作工具
 * @date 2012-12-12 下午12:49:35
 */
public class PersistentUtils {

    private static DBHelper mDBHelper;

    /**
     * 增加新数据
     *
     * @param model
     */
    public static long addModel(BaseModel model) {
        RuntimeExceptionDao dao = getDBHelper().getRuntimeExceptionDao(model.getClass());
        Dao.CreateOrUpdateStatus status = dao.createOrUpdate(model);
        return status.getNumLinesChanged();
    }

    /**
     * 删除记录
     *
     * @param model
     */
    public static void delete(BaseModel model) {
        RuntimeExceptionDao dao = getDBHelper().getRuntimeExceptionDao(model.getClass());
        dao.delete(model);
    }

    /**
     * 更新数据
     *
     * @param model 数据模型必须是BaseModel的子类；
     */
    public static int update(BaseModel model) {
        RuntimeExceptionDao dao = getDBHelper().getRuntimeExceptionDao(model.getClass());
        return dao.update(model);
    }


    /**
     * @param @param model 设定文件
     * @param @param where 设定文件
     * @return void 返回类型
     * @throws
     * @Title: execSQL
     * @Description: TODO(执行删除数据)
     */
    public static void execDeleteData(Class clazz, String where) {
        RuntimeExceptionDao dao = getDBHelper().getRuntimeExceptionDao(clazz);
        DeleteBuilder deleteBuilder = dao.deleteBuilder();
        deleteBuilder.setWhere(deleteBuilder.where().raw(where));
        try {
            deleteBuilder.delete();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    /**
     * 查询符合条件的获取对象列表
     *
     * @param <T>
     * @param modelType 模型类的Class
     * @param conditon  SQL语句中where查询条件之后的条件
     * @return
     */
    public static <T extends AutoType> ArrayList<T> getModelList(
            Class<?> modelType, String conditon) {
        ArrayList<T> result = new ArrayList<T>();
        RuntimeExceptionDao dao = getDBHelper().getRuntimeExceptionDao(modelType);
        try {
            List data = dao.queryBuilder().where().raw(conditon).query();
            result.addAll(data);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static DBHelper getDBHelper() {
        if (mDBHelper == null) {
            synchronized (PersistentUtils.class) {
                if (mDBHelper == null) {
                    mDBHelper = new DBHelper(AppUtil.getApplicationContext());
                }
            }
        }
        return mDBHelper;
    }


}
