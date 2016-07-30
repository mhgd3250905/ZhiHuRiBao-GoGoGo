package skkk.gogogo.com.dakaizhihu.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.Toolbar;
import android.text.format.Formatter;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.io.File;

import skkk.gogogo.com.dakaizhihu.R;
import skkk.gogogo.com.dakaizhihu.View.HorizortalProgressbarWithProgress;
import skkk.gogogo.com.dakaizhihu.View.SettingItemView;
import skkk.gogogo.com.dakaizhihu.utils.GetFileSize;
import skkk.gogogo.com.dakaizhihu.utils.MySQLiteHelper;

public class SettingActivity extends AppCompatActivity {
    @ViewInject(R.id.tv_setting_size)
    TextView tvSettingSize;

    private MySQLiteHelper helper;
    private SQLiteDatabase db;
    private Toolbar tbSetting;
    private GetFileSize getFileSize;
    private SharedPreferences mPref;
    private long l;
    private SettingItemView sivImageMode;
    private AlertDialog clearDailog;
    private HorizortalProgressbarWithProgress progress;
    private SettingItemView sivNightMode;
    private boolean isNight;
    private boolean dataStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //开始加载UI之前处理所有的动作
        beforeStart();
        initUI();
        initDB();
        initData();
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
        setContentView(R.layout.activity_main_activity_setting);
        ViewUtils.inject(this);
        tbSetting = (Toolbar) findViewById(R.id.tb_setting);
        setSupportActionBar(tbSetting);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        tbSetting.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        /*
        * @desc 设置无图模式
        * @时间 2016/7/24 12:57
        */
        sivImageMode = (SettingItemView) findViewById(R.id.siv_setting_image_mode);
        boolean autoUpdate = mPref.getBoolean("image_mode", false);

        if (autoUpdate) {
            sivImageMode.setChecked(true);
        } else {
            sivImageMode.setChecked(false);
        }

        sivImageMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //判断当前勾选状态
                if (sivImageMode.isChecked()) {
                    //设置不勾选
                    sivImageMode.setChecked(false);
                    //更新SP
                    mPref.edit().putBoolean("image_mode", false).commit();
                    Toast.makeText(SettingActivity.this, "已关闭无图模式", Toast.LENGTH_SHORT).show();
                } else {
                    sivImageMode.setChecked(true);
                    //更新SP
                    mPref.edit().putBoolean("image_mode", true).commit();
                    Toast.makeText(SettingActivity.this, "已开启无图模式", Toast.LENGTH_SHORT).show();
                }
            }
        });



        /*
        * @desc 设置夜间模式模式
        * @时间 2016/7/24 12:57
        */
        sivNightMode = (SettingItemView) findViewById(R.id.siv_setting_night_mode);
        isNight = mPref.getBoolean("night", false);

        if (isNight) {
            sivNightMode.setChecked(true);
        } else {
            sivNightMode.setChecked(false);
        }

        sivNightMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //判断当前勾选状态
                if (sivNightMode.isChecked()) {
                    //设置不勾选
                    sivNightMode.setChecked(false);
                    //更新SP
                    //设置为非夜间模式
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    mPref.edit().putBoolean("night", false).commit();
                    Toast.makeText(SettingActivity.this, "已关闭夜间模式模式", Toast.LENGTH_SHORT).show();
                    //重启homeActivity
                    Intent intent=new Intent();
                    intent.setClass(SettingActivity.this, HomeActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    sivNightMode.setChecked(true);
                    //更新SP
                    //设置为夜间模式
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    mPref.edit().putBoolean("night", true).commit();
                    Toast.makeText(SettingActivity.this, "已开启夜间模式", Toast.LENGTH_SHORT).show();
                    //重启homeActivity
                    Intent intent=new Intent();
                    intent.setClass(SettingActivity.this,HomeActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });

    }

    private void initDB() {
        helper = new MySQLiteHelper(this, "News.db", null, 1);
    }


    public void clearSQL(View view) {
        dataStore = mPref.getBoolean("data_store",true);
        if (dataStore){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("提示");
            builder.setMessage("正在清理缓存...");
            View view_2 = View.inflate(this, R.layout.view_progress, null);
            progress = (HorizortalProgressbarWithProgress) view_2.findViewById(R.id.my_progress);

            //删除数据库数据
            db = helper.getWritableDatabase();
            db.delete("News", null, null);
            builder.setView(view_2);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    db.delete("News", null, null);
                    for (int i = 1; i <= 100; i++) {
                        try {
                            Thread.sleep(50);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        progress.setProgress(i);//设置进度
                    }
                    clearDailog.dismiss();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            tvSettingSize.setText("0K");
                            mPref.edit().putBoolean("data_store",false).commit();
                        }
                    });
                }
            }).start();
            clearDailog = builder.show();
        }else{
            Toast.makeText(SettingActivity.this, "暂无缓存", Toast.LENGTH_SHORT).show();
        }


    }

    private void initData() {

        try {
            File file = new File("/data/data/"
                    + getPackageName() + "/databases");
            l = getFileSize.getFolderSize(file);

        } catch (Exception e) {
            e.printStackTrace();
        }
        if (l < 50 * 1024) {
            tvSettingSize.setText("0K");
        } else {
            String fileSize = Formatter.formatFileSize(this, l);
            tvSettingSize.setText(fileSize);
        }

    }
}
