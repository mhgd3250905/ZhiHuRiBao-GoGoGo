package skkk.gogogo.com.dakaizhihu.activity;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.lidroid.xutils.view.annotation.ViewInject;

import skkk.gogogo.com.dakaizhihu.R;
import skkk.gogogo.com.dakaizhihu.utils.MySQLiteHelper;

public class SettingActivity extends AppCompatActivity {
@ViewInject(R.id.tb_setting)
    Toolbar tbSetting;
    private MySQLiteHelper helper;
    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initUI();
        initDB();
    }

    private void initUI() {
        setContentView(R.layout.activity_main_activity_setting);
        setSupportActionBar(tbSetting);
    }

    private void initDB() {
        helper = new MySQLiteHelper(this, "News.db", null, 1);
    }

    public void clearSQL(View view){
        db=helper.getWritableDatabase();
        db.delete("News",null,null);
    }
}
