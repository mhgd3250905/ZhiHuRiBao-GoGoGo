package skkk.gogogo.com.dakaizhihu.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;


/*
* 
* 描    述：viewpager的
* 作    者：ksheng
* 时    间：
*/

public class MyPagerAdapter extends FragmentStatePagerAdapter {
    private List<Fragment> fragments;
    private ArrayList<String> TITLE;

    public MyPagerAdapter(FragmentManager fm,List<Fragment> fragments,ArrayList<String> TITLE) {
        super(fm);
        this.TITLE=TITLE;
        this.fragments=fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return TITLE.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return TITLE.get(position);
    }
}
