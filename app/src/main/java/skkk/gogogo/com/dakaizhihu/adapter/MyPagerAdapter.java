package skkk.gogogo.com.dakaizhihu.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by admin on 2016/7/6.
 */
/*
* 
* 描    述：viewpager的
* 作    者：ksheng
* 时    间：
*/

public class MyPagerAdapter extends FragmentPagerAdapter{
    private List<Fragment> fragments;
    private String[] TITLE={"今天","昨天","前天","大前天"};

    public MyPagerAdapter(FragmentManager fm,List<Fragment> fragments) {
        super(fm);
        this.fragments=fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return TITLE.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return TITLE[position];
    }
}
