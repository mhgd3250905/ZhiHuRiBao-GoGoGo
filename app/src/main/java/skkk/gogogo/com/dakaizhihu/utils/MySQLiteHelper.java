package skkk.gogogo.com.dakaizhihu.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by admin on 2016/7/4.
 */
/*
* 
* 描    述：数据库帮助类
* 作    者：ksheng
* 时    间：
*/
public class MySQLiteHelper extends SQLiteOpenHelper {
    /**
     * 定义一个数据库 * SQL * 创建id为主键包含作者、价格、页码、书名的table
     */
    public static final String CREATE_NEWS = "create table News("
            + "id integer primary key autoincrement,"
            + "image_uri text,"
            + "image_source text,"
            + "html_body text,"
            + "news_id text)";
    private Context mContext;

    public MySQLiteHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        mContext = context;
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_NEWS);
        Log.d("TAG","创建成功AAAAAAAAAAAAAAAAAAAAA");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
