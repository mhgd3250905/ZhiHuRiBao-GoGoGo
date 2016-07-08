package skkk.gogogo.com.dakaizhihu.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.viewpagerindicator.TabPageIndicator;

import java.util.ArrayList;
import java.util.List;

import skkk.gogogo.com.dakaizhihu.R;
import skkk.gogogo.com.dakaizhihu.adapter.MyPagerAdapter;

/**
 * Created by admin on 2016/6/21.
 */
/*
* 
* 描    述：用于显示所有文章列表的fragment
* 作    者：ksheng
* 时    间：6/21
*/
public class HomeMainFragemnt extends Fragment {

    private View view;//加载之view

    ViewPager vpHome;
    TabPageIndicator tpiHome;


    private List<Fragment> fragmentList;
    private MyPagerAdapter adapter;
    private String[] TITLE={"今天","昨天","前天","大前天"};

    /*
    * @desc 创建之方法
    * @时间 2016/6/22 12:44
    */
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home_main, container, false);
        Log.d("TAG", "111-----------------------OnCteateView");
        initUI();
        return view;
    }
    @Override
    public void onStart() {
        super.onStart();

        Log.d("TAG", "111-----------------------OnStart");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d("TAG", "111-----------------------OnPause");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("TAG", "111-----------------------OnResume");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d("TAG", "111-----------------------OnStop");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("TAG", "111-----------------------OnDestory");
    }

    /*
    * @desc UI
    * @时间 2016/6/21 23:29
    */
    private void initUI() {

        vpHome= (ViewPager) view.findViewById(R.id.vp_home);
        tpiHome= (TabPageIndicator) view.findViewById(R.id.tpi_home);


        Log.d("TAG", "111-----------------------加载fragment");
        fragmentList=new ArrayList<Fragment>();
        Fragment homeFragemnt=new HomeFragemnt();
        Fragment homeFragemnt2=new HomeFragment_2();
        Fragment homeFragemnt3=new HomeFragment_3();
        Fragment homeFragemnt4=new HomeFragment_4();
        fragmentList.add(homeFragemnt);
        fragmentList.add(homeFragemnt2);
        fragmentList.add(homeFragemnt3);
        fragmentList.add(homeFragemnt4);

        adapter=new MyPagerAdapter(getActivity().getSupportFragmentManager(),fragmentList,TITLE);

        vpHome.setAdapter(adapter);

        //实例化TabPageIndicator然后设置ViewPager与之关联
        tpiHome.setViewPager(vpHome);


        //如果我们要对ViewPager设置监听，用indicator设置就行了
        tpiHome.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int arg0) {
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {

            }

            @Override
            public void onPageScrollStateChanged(int arg0) {

            }
        });

    }
}
