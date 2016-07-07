package skkk.gogogo.com.dakaizhihu.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.viewpagerindicator.TabPageIndicator;

import java.util.ArrayList;
import java.util.List;

import skkk.gogogo.com.dakaizhihu.R;
import skkk.gogogo.com.dakaizhihu.adapter.MyPagerAdapter;
import skkk.gogogo.com.dakaizhihu.fragment.ThemeFragment;

public class ThemeHomeActivity extends AppCompatActivity {
    @ViewInject(R.id.vp_theme)
    ViewPager vpTheme;
    @ViewInject(R.id.tpi_theme)
    TabPageIndicator tpiTheme;

    private List<Fragment> fragmentList;
    private MyPagerAdapter adapter;
    private String[] TITLE={"开始游戏","电影日报","设计日报","大公司日报",
            "财经日报","音乐日报","体育日报","动漫日报",
            "互联网安全","不许无聊","用户推荐日报","日常心理学"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initUI();



    }

    private void initUI() {
        setContentView(R.layout.activity_theme_home);
        ViewUtils.inject(this);


        fragmentList=new ArrayList<Fragment>();

        for(int i=2;i<14;i++){
            Fragment themeFragment=new ThemeFragment(i);
            fragmentList.add(themeFragment);
        }
        adapter=new MyPagerAdapter(getSupportFragmentManager(),fragmentList,TITLE);

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

}
