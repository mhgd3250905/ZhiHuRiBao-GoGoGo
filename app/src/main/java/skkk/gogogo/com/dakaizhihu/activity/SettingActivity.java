package skkk.gogogo.com.dakaizhihu.activity;

import android.app.ProgressDialog;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.format.Formatter;
import android.view.View;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.io.File;

import skkk.gogogo.com.dakaizhihu.R;
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
    private long l;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initUI();
        initDB();
        initData();
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


        tbSetting.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
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
