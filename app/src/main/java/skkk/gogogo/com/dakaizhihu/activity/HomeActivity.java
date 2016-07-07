package skkk.gogogo.com.dakaizhihu.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.viewpagerindicator.TabPageIndicator;

import java.util.ArrayList;
import java.util.List;

import skkk.gogogo.com.dakaizhihu.R;
import skkk.gogogo.com.dakaizhihu.adapter.MyPagerAdapter;
import skkk.gogogo.com.dakaizhihu.fragment.HomeFragemnt;
import skkk.gogogo.com.dakaizhihu.fragment.HomeFragment_2;
import skkk.gogogo.com.dakaizhihu.fragment.HomeFragment_3;
import skkk.gogogo.com.dakaizhihu.fragment.HomeFragment_4;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    @ViewInject(R.id.vp_home)
    ViewPager vpHome;
    @ViewInject(R.id.tpi_home)
    TabPageIndicator tpiHome;


    private List<Fragment> fragmentList;
    private MyPagerAdapter adapter;
    private String[] TITLE={"今天","昨天","前天","大前天"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initUI();
    }

    /*
    * @desc 填写初始fragment
    * @时间 2016/6/21 23:36
    */
    private void setDefaultFragment() {
        HomeFragemnt homeFragemnt = new HomeFragemnt();
        getSupportFragmentManager().beginTransaction().add(R.id.fl_home, homeFragemnt).commit();
    }

    private void initUI() {
        //设置layout
        setContentView(R.layout.activity_home);
        ViewUtils.inject(this);

        //设置ToolBar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //绘制
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        //设置导航页
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        fragmentList=new ArrayList<Fragment>();
        Fragment homeFragemnt=new HomeFragemnt();
        Fragment homeFragemnt2=new HomeFragment_2();
        Fragment homeFragemnt3=new HomeFragment_3();
        Fragment homeFragemnt4=new HomeFragment_4();
        fragmentList.add(homeFragemnt);
        fragmentList.add(homeFragemnt2);
        fragmentList.add(homeFragemnt3);
        fragmentList.add(homeFragemnt4);

        adapter=new MyPagerAdapter(getSupportFragmentManager(),fragmentList,TITLE);

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

    /*
    * @desc 点击back事件
    * @时间 2016/6/21 23:00
    */
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    /*
    * @desc 创建点击菜单
    * @时间 2016/6/21 23:06
    */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }


    /*
    * @desc toolbar菜单
    * @时间 2016/6/21 23:06
    */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /*
    * @desc 设置侧滑菜单点击事件
    * @时间 2016/6/21 23:07
    */

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.homeNews) {


        } else if (id == R.id.themeNews) {

            startActivity(new Intent(this,ThemeHomeActivity.class));

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
