package heyhou.com.baseproject.databases;

import com.j256.ormlite.field.DatabaseField;

import java.io.Serializable;

/**
 * @author ydc
 * @ClassName: BaseModel.java
 * @Description: 数据库表的基础类型
 * @date 2012-12-12 下午12:47:24
 */
public class BaseModel implements Serializable{
    @DatabaseField(columnName = "modelId", generatedId = true)
    private Integer modelId;

    public String getModelId() {
        if (modelId == null) {
            return null;
        } else {
            return modelId.toString();
        }
    }

    public void setModelId(Integer modelId) {
        this.modelId = modelId;
    }
}
