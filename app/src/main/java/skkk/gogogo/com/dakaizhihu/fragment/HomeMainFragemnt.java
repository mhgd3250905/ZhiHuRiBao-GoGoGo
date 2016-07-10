package skkk.gogogo.com.dakaizhihu.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import skkk.gogogo.com.dakaizhihu.R;
import skkk.gogogo.com.dakaizhihu.adapter.MyPagerAdapter;
import skkk.gogogo.com.dakaizhihu.utils.TimeUtils;
import skkk.gogogo.com.dakaizhihu.utils.URLStringUtils;

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
    TabLayout tpiHome;


    private List<Fragment> fragmentList;
    private MyPagerAdapter adapter;
    private String[] TITLE={"","","","","","",""};
    private String url;


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

    /*
    * @desc UI
    * @时间 2016/6/21 23:29
    */
    private void initUI() {

        vpHome= (ViewPager) view.findViewById(R.id.vp_home);
        tpiHome= (TabLayout) view.findViewById(R.id.tpi_home);


        Log.d("TAG","--------------------"+TimeUtils.getTimeTitle(0));



        Log.d("TAG", "111-----------------------加载fragment");
        fragmentList=new ArrayList<Fragment>();
        for (int i=0;i<7;i++){
            if(i==0){
                url = URLStringUtils.getHOMENEWSLISTURL();
                TITLE[i]="今天";
            }else{
                url=URLStringUtils.getPASTNEWSLISTURL(String.valueOf(TimeUtils.getTime(24 * 60 * 60 * 1000 * i)));
                TITLE[i]=TimeUtils.getTimeTitle(24 * 60 * 60 * 1000 * i);
            }
            Fragment homeFragemnt=new HomeFragemnt(url);
            fragmentList.add(homeFragemnt);
        }

        adapter=new MyPagerAdapter(getActivity().getSupportFragmentManager(),fragmentList,TITLE);

        vpHome.setAdapter(adapter);

        //实例化TabPageIndicator然后设置ViewPager与之关联
        tpiHome.setupWithViewPager(vpHome);


        //如果我们要对ViewPager设置监听，用indicator设置就行了
        tpiHome.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                vpHome.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    /*
    * @desc 生命周期
    * @时间 2016/7/9 13:04
    */
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
}
