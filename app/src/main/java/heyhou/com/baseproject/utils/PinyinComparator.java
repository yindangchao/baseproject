package heyhou.com.baseproject.utils;


import java.util.Comparator;

import heyhou.com.baseproject.bean.SortModel;

public class PinyinComparator<T extends SortModel> implements Comparator<T> {

	public int compare(SortModel o1, SortModel o2) {
//		if (o1.getSortLetters().equals("@")
//				|| o2.getSortLetters().equals("#")) {
//			return -1;
//		} else if (o1.getSortLetters().equals("#")
//				|| o2.getSortLetters().equals("@")) {
//			return 1;
//		} else {
//			return o1.getSortLetters().compareTo(o2.getSortLetters());
//		}

		int result = o1.getType() - o2.getType();
		if (result > 0) {
			return -1;
		} else if (result < 0) {
			return 1;
		} else {
			if (o1.getSortLetters().equals("@")
					|| o2.getSortLetters().equals("#")) {
				return -1;
			} else if (o1.getSortLetters().equals("#")
					|| o2.getSortLetters().equals("@")) {
				return 1;
			} else {
				return o1.getSortLetters().compareTo(o2.getSortLetters());
			}
		}

	}

}
