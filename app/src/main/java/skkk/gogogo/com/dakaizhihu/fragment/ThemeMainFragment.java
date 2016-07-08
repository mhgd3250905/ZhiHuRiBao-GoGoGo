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
 * Created by admin on 2016/7/8.
 */
/*
* 
* 描    述：
* 作    者：ksheng
* 时    间：
*/
public class ThemeMainFragment extends Fragment {

    ViewPager vpTheme;

    TabPageIndicator tpiTheme;

    private List<Fragment> fragmentList;
    private MyPagerAdapter adapter;
    private String[] TITLE={"开始游戏","电影日报","设计日报","大公司日报",
            "财经日报","音乐日报","体育日报","动漫日报",
            "互联网安全","不许无聊","用户推荐日报","日常心理学"};
    private View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_theme_main,container,false);
        Log.d("TAG", "222-----------------------OnCreate");
        initUI();

        return view;
    }




    private void initUI() {
        vpTheme= (ViewPager) view.findViewById(R.id.vp_theme);
        tpiTheme= (TabPageIndicator) view.findViewById(R.id.tpi_theme);

        Log.d("TAG", "111-----------------------加载fragment");
        fragmentList=new ArrayList<Fragment>();

        for(int i=2;i<14;i++){
            Fragment themeFragment=new ThemeFragment(i);
            fragmentList.add(themeFragment);
        }
        adapter=new MyPagerAdapter(getActivity().getSupportFragmentManager(),fragmentList,TITLE);

        vpTheme.setAdapter(adapter);

        //实例化TabPageIndicator然后设置ViewPager与之关联
        tpiTheme.setViewPager(vpTheme);


        //如果我们要对ViewPager设置监听，用indicator设置就行了
        tpiTheme.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

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

    @Override
    public void onStart() {
        super.onStart();
        Log.d("TAG", "222-----------------------OnStart");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d("TAG", "222-----------------------OnPause");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("TAG", "222-----------------------OnResume");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d("TAG", "222-----------------------OnStop");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("TAG", "222-----------------------OnDestory");
    }
}
