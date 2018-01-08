package heyhou.com.baseproject.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by ydc on 2017/7/19.
 */
public class MyFragmentPagerAdapter extends FragmentPagerAdapter {

    private List<Fragment> list;

    public MyFragmentPagerAdapter(FragmentManager fm, List<Fragment> list) {
        super(fm);
        this.list = list;
    }

    @Override
    public Fragment getItem(int arg0) {
        return list.get(arg0);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Fragment f = (Fragment) super.instantiateItem(container, position);
        return f;
    }

    @Override
    public int getItemPosition(Object object) {
        return PagerAdapter.POSITION_NONE;
    }

    @Override
    public int getCount() {
        return list.size();
    }

//    @Override
//    /**注意这里要重写 destroyItem 方法 重写后 每个 ViewPager就不会被回收掉,可以避免回收后再次返回时又要联网获取数据*/
//    public void destroyItem(ViewGroup container, int position, Object object) {
////		super.destroyItem(container, position, object);
//    }


}
