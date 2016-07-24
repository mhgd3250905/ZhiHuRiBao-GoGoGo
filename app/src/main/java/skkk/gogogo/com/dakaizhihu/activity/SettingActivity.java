package skkk.gogogo.com.dakaizhihu.activity;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.format.Formatter;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.io.File;

import skkk.gogogo.com.dakaizhihu.R;
import skkk.gogogo.com.dakaizhihu.View.SettingItemView;
import skkk.gogogo.com.dakaizhihu.utils.GetFileSize;
import skkk.gogogo.com.dakaizhihu.utils.MySQLiteHelper;

public class SettingActivity extends AppCompatActivity {
    @ViewInject(R.id.tv_setting_size)
    TextView tvSettingSize;

    private MySQLiteHelper helper;
    private SQLiteDatabase db;
    private Toolbar tbSetting;
    private ProgressDialog pd;
    private GetFileSize getFileSize;
    private SharedPreferences mPref;
    private long l;
    private SettingItemView sivImageMode;

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
        mPref=getSharedPreferences("config",MODE_PRIVATE);
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
        sivImageMode= (SettingItemView) findViewById(R.id.siv_setting_image_mode);
        boolean autoUpdate=mPref.getBoolean("image_mode",false);

        if(autoUpdate){
            sivImageMode.setChecked(true);
        }else{
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
    }

    private void initDB() {
        helper = new MySQLiteHelper(this, "News.db", null, 1);
    }

    public void clearSQL(View view){
        pd = new ProgressDialog(SettingActivity.this);
        pd.setTitle("提示");
        pd.setMessage("正在清理...");
        pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        pd.setProgressNumberFormat("");
        pd.show();
        pd.setMax(100);//设置最大值
        db=helper.getWritableDatabase();
         new Thread(new Runnable() {
             @Override
             public void run() {
                 db.delete("News", null, null);
                 for (int i=1;i<101;i++){
                     try {
                         Thread.sleep(30);
                     } catch (InterruptedException e) {
                         e.printStackTrace();
                     }
                     pd.setProgress(i);//设置进度
                 }
                 pd.dismiss();//关闭进度条
                 runOnUiThread(new Runnable() {
                     @Override
                     public void run() {
                         tvSettingSize.setText("0K");
                     }
                 });
             }
         }).start();
    }

    private void initData() {

        try {
            File file=new File("/data/data/"
                    + getPackageName() + "/databases");
            l = getFileSize.getFolderSize(file);

        } catch (Exception e) {
            e.printStackTrace();
        }
        if(l<50*1024){
            tvSettingSize.setText("0K");
        }else{
            String fileSize= Formatter.formatFileSize(this, l);
            tvSettingSize.setText(fileSize);
        }

    }
}
