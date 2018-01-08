package heyhou.com.baseproject.utils;

import android.text.TextUtils;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import heyhou.com.baseproject.bean.SortModel;
import heyhou.com.baseproject.bean.Sortable;

/**
 * 创建:yb 2016/10/17.
 * 描述:字母排序
 */

public class SortDataHandler {
    private static SortDataHandler instance;

    /**
     * 根据拼音来排列ListView里面的数据类
     */
    private SortDataHandler() {
        characterParser = new CharacterParser();
    }

    private CharacterParser characterParser;

    public static SortDataHandler getInstance() {
        if (instance == null) {
            synchronized (SortDataHandler.class) {
                if (instance == null) {
                    instance = new SortDataHandler();
                }
            }
        }
        return instance;
    }

    public static <T> List<T> getSortedData(List<T> source, Comparator<T> comparator) {
        Collections.sort(source, comparator);
        return source;
    }

    public static <T extends Sortable> ArrayList<SortModel<T>> getDataList(List<T> data) {
        return getInstance()._getDataList(data);
    }

    public <T extends Sortable> ArrayList<SortModel<T>> _getDataList(List<T> data) {
        ArrayList<SortModel<T>> result = new ArrayList<>();
        for (T t : data) {
            SortModel<T> sortModel = SortModel.buildSortModel(t);
            String pinyin = characterParser.getSelling(t.getSortLetter());
            if (pinyin.indexOf("unknown") == 0) {
                pinyin = "#";
            }
            String sortString = "";
            if (!TextUtils.isEmpty(pinyin)) {
                sortString = pinyin.substring(0, 1).toUpperCase();
            }

            // 正则表达式，判断首字母是否是英文字母
            /**
             * if(sortString.matches("[A-Z]")){
             sortModel.setSortLetters(sortString.toUpperCase());
             }else{
             sortModel.setSortLetters("#");
             }
             */
            sortModel.setType(t.getType());
            if (sortString.matches("[A-Z]")) {
                sortModel.setSortLetters(sortString.toUpperCase());
            } else {
                sortModel.setSortLetters("#");
            }
            if (sortModel.getType() > 0) {
                sortModel.setSortLetters("*");
            }
            result.add(sortModel);
        }
        getSortedData(result, new PinyinComparator<SortModel<T>>());
        return result;
    }


}
