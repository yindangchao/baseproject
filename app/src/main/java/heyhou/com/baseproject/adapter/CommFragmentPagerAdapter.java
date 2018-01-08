package heyhou.com.baseproject.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import java.util.List;

public class CommFragmentPagerAdapter extends FragmentPagerAdapter {
	
	private List<Fragment> list;

	public CommFragmentPagerAdapter(FragmentManager fm, List<Fragment> list) {
		super(fm);
		this.list = list;
	}
	
	@Override
	public Fragment getItem(int arg0) {
		return list.get(arg0);
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	/**注意这里要重写 destroyItem 方法 重写后 每个 ViewPager就不会被回收掉,可以避免回收后再次返回时又要联网获取数据*/
	public void destroyItem(ViewGroup container, int position,Object object) {
//		super.destroyItem(container, position, object);
	}
	
	
}
