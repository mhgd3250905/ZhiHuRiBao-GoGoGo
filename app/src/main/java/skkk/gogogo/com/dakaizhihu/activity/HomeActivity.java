package skkk.gogogo.com.dakaizhihu.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

import com.lidroid.xutils.ViewUtils;

import java.util.Timer;
import java.util.TimerTask;

import skkk.gogogo.com.dakaizhihu.R;
import skkk.gogogo.com.dakaizhihu.fragment.ColumnMainFragment;
import skkk.gogogo.com.dakaizhihu.fragment.HomeMainFragemnt;
import skkk.gogogo.com.dakaizhihu.fragment.KanzhihuFragment;
import skkk.gogogo.com.dakaizhihu.fragment.ThemeMainFragment;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initUI();
        setDefaultFragment();
    }

    /*
    * @desc 填写初始fragment
    * @时间 2016/6/21 23:36
    */
    private void setDefaultFragment() {
        HomeMainFragemnt homeMainFragemnt = new HomeMainFragemnt();
        getSupportFragmentManager().beginTransaction().replace(R.id.fl_home, homeMainFragemnt).commit();
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
            exitBy2Click();
        }
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
            //知乎日报
            HomeMainFragemnt homeMainFragemnt = new HomeMainFragemnt();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fl_home, homeMainFragemnt);
            transaction.commit();
        } else if (id == R.id.themeNews) {
            //主题日报
            ThemeMainFragment themeMainFragment=new ThemeMainFragment();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fl_home, themeMainFragment).commit();

        } else if (id == R.id.nav_kanzhihu) {
            //看知乎
            KanzhihuFragment kanzhihuFragment=new KanzhihuFragment();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fl_home, kanzhihuFragment).commit();
        }else if(id==R.id.nav_column){
            //知乎专栏
            ColumnMainFragment columnMainFragment=new ColumnMainFragment();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fl_home, columnMainFragment).commit();
        }else if (id == R.id.nav_setting) {
            startActivity(new Intent(HomeActivity.this,SettingActivity.class));
        }else if (id == R.id.nav_send) {
            startActivity(new Intent());
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }



    /**
     * 双击退出函数
     */
    private static Boolean isExit = false;

    private void exitBy2Click() {
        Timer tExit = null;
        if (isExit == false) {
            isExit = true; // 准备退出
            Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
            tExit = new Timer();
            tExit.schedule(new TimerTask() {
                @Override
                public void run() {
                    isExit = false; // 取消退出
                }
            }, 2000); // 如果2秒钟内没有按下返回键，则启动定时器取消掉刚才执行的任务

        } else {
            finish();
            System.exit(0);
        }
    }

}
