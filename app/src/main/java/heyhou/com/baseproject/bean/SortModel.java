package heyhou.com.baseproject.bean;

public class SortModel<T> implements AutoType {

	private int type;
	private String name;
	private String sortLetters;
	private T target;

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public SortModel(T source) {
		this.target = source;
	}

	public T getTarget() {
		return target;
	}

	public void setTarget(T target) {
		this.target = target;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSortLetters() {
		return sortLetters;
	}
	public void setSortLetters(String sortLetters) {
		this.sortLetters = sortLetters;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		SortModel<?> sortModel = (SortModel<?>) o;

		return target.equals(sortModel.target);

	}

	@Override
	public int hashCode() {
		return target.hashCode();
	}

	public static <E> SortModel buildSortModel(E e) {
		SortModel<E> sortModel = new SortModel<>(e);
		return sortModel;
	}
}
