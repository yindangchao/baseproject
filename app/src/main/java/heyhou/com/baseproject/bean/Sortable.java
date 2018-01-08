package heyhou.com.baseproject.bean;

/**
 * 创建:yb 2016/10/17.
 * 描述:首字符排序
 */

public interface Sortable {
    /**
     * 根据返回字符串的首字母继续排序
     * @return
     */
    String getSortLetter();

//    /**
//     * 是否是特殊对象
//     * @return
//     */
//    boolean isSpecial();

    /**
     * 类型
     * @return
     */
    int getType();
}
