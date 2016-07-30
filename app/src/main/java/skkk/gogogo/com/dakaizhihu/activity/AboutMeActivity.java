package skkk.gogogo.com.dakaizhihu.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.Toolbar;
import android.view.View;

import skkk.gogogo.com.dakaizhihu.R;

public class AboutMeActivity extends AppCompatActivity {

    private Toolbar tbSetting;
    private SharedPreferences mPref;
    private boolean isNight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        beforeStart();
        initUI();

    }

    /*
   * @desc 开始加载UI之前处理所有的动作
   * @时间 2016/7/24 11:32
   */
    private void beforeStart() {
        mPref = getSharedPreferences("config", MODE_PRIVATE);
        isNight = mPref.getBoolean("night", false);
        if (isNight) {
            //设置为夜间模式
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            //设置为非夜间模式
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
    }


    private void initUI() {
        setContentView(R.layout.activity_about_me);

        tbSetting = (Toolbar) findViewById(R.id.tb_about_me);
        setSupportActionBar(tbSetting);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        tbSetting.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

}
