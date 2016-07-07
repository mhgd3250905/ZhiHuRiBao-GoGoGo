package skkk.gogogo.com.dakaizhihu.activity;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import skkk.gogogo.com.dakaizhihu.NewsDetailsGson.NewDetailsData;
import skkk.gogogo.com.dakaizhihu.R;
import skkk.gogogo.com.dakaizhihu.utils.MySQLiteHelper;
import skkk.gogogo.com.dakaizhihu.utils.MyStringRequest;
import skkk.gogogo.com.dakaizhihu.utils.URLStringUtils;

public class NewsDetailActivity extends AppCompatActivity {
    @ViewInject(R.id.iv_news_title)
    SimpleDraweeView ivNewsTitle;
    SharedPreferences mPref;
    private int newsId;
    private NewDetailsData newsDetailsData;
    private WebView mWebView;
    private String newsHtmlContent;
    private String newsTitle;
    private Document doc_dis;
    private String titleImage;
    private String imageSource;
    private CollapsingToolbarLayout collapsingToolbar;
    private MySQLiteHelper dbHelper;
    private SQLiteDatabase db;
    private String url;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);
        initUI();
        initDB();
        if (checkStorage()){
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            mWebView.loadDataWithBaseURL("", newsHtmlContent, "text/html", "utf-8", "");
            try {
                ivNewsTitle.setImageURI(Uri.parse(titleImage));
            }catch (Exception e){
                ivNewsTitle.setVisibility(View.GONE);
            }
            Log.d("TAG","--------------------------"+newsTitle);
            Toast.makeText(NewsDetailActivity.this, "来自SQL", Toast.LENGTH_SHORT).show();
        }else {
            initData();
        }
    }



    /*
        * @desc 判断是否有缓存于数据库
        * @时间 2016/7/4 23:57
        */
    private boolean checkStorage() {
        mPref = getSharedPreferences("config", Context.MODE_PRIVATE);
        newsId = mPref.getInt("news_id", 0);

        db=dbHelper.getWritableDatabase();
        Cursor cursor = db.query("News",null,null,null,null,null,null);
        while (cursor.moveToNext()){
            if(String.valueOf(newsId).equals(cursor.getString(4))){
                Log.d("TAG","--------------------------"+cursor.getString(4));
                titleImage=cursor.getString(1);
                Log.d("TAG","--------------------------"+titleImage);
                imageSource=cursor.getString(2);
                Log.d("TAG","--------------------------"+imageSource);
                newsHtmlContent=cursor.getString(3);
                Log.d("TAG","--------------------------content");
                newsTitle=cursor.getString(5);

                if (cursor!=null){
                    cursor.close();
                }
                return true;
            }
        }
        if (cursor!=null){
            cursor.close();
        }
        return false;
    }

    private void initDB() {
        dbHelper = new MySQLiteHelper(this, "News.db", null, 1);
    }

    private void initUI() {
        ViewUtils.inject(this);

        //webview初始化设置
        mWebView = (WebView) findViewById(R.id.wv_news_details);
        WebSettings webSetting = mWebView.getSettings();
        webSetting.setDefaultTextEncodingName("UTF-8");
        webSetting.setJavaScriptEnabled(false);
        webSetting.setAllowFileAccess(true);
        webSetting.setBuiltInZoomControls(false);
        webSetting.setSupportZoom(false);


        //初始化toolbar
        toolbar = (Toolbar) findViewById(R.id.tb_news);
        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                onBackPressed();
//            }
//        });


        //使用CollapsingToolbarLayout后，title需要设置到CollapsingToolbarLayout上
        collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        //通过CollapsingToolbarLayout修改字体颜色
        collapsingToolbar.setExpandedTitleColor(Color.WHITE);//设置还没收缩时状态下字体颜色
        collapsingToolbar.setCollapsedTitleTextColor(Color.WHITE);//设置收缩后Toolbar上字体的颜色
        collapsingToolbar.setCollapsedTitleGravity(Gravity.CENTER);//设置收缩之后的标题位置
        collapsingToolbar.setCollapsedTitleTextColor(Color.WHITE);//设置收缩之后的字体颜色
        collapsingToolbar.setExpandedTitleGravity(Gravity.BOTTOM | Gravity.RIGHT);//设置未收缩时候的标题位置

        //设置FAB
//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("是否保存？", new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                            }
//                        }).show();
//            }
//        });


    }



    /*
    * @desc 获取数据
    * @时间 2016/7/2 22:54
    */
    private void initData() {
        //volley通过网络获取字符串信息
        url = URLStringUtils.getNEWSDETAILSURL(String.valueOf(newsId));
        RequestQueue queue = Volley.newRequestQueue(this);
        MyStringRequest request = new MyStringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                Gson gson = new Gson();
                java.lang.reflect.Type type = new TypeToken<NewDetailsData>() {
                }.getType();
                newsDetailsData = gson.fromJson(s, type);
                newsTitle = newsDetailsData.getTitle();
                titleImage = newsDetailsData.getImage();
                imageSource = newsDetailsData.getImage_source();

                doc_dis = null;
                doc_dis = Jsoup.parse(newsDetailsData.getBody());
                Elements ele_Img = doc_dis.getElementsByTag("img");
                if (ele_Img.size() != 0) {
                    for (Element e_Img : ele_Img) {
                        e_Img.attr("style", "width:100%");
                        if (e_Img.className().equals("avatar")) {
                            e_Img.attr("style", "width:8%");
                        }
                    }
                }

                newsHtmlContent = doc_dis.toString();
                ContentValues values = new ContentValues();

                //开始组装第一条数据
                values.put("image_uri", titleImage);
                values.put("image_source", imageSource);
                values.put("html_body", newsHtmlContent);
                values.put("news_id", newsId);
                values.put("title", newsTitle);
                db.insert("News", null, values);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        getSupportActionBar().setDisplayShowTitleEnabled(false);
                        mWebView.loadDataWithBaseURL("", newsHtmlContent, "text/html", "utf-8", "");
                        try {
                            ivNewsTitle.setImageURI(Uri.parse(titleImage));
                        }catch (Exception e){
                            ivNewsTitle.setVisibility(View.GONE);
                        }


                    }
                });
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        });
        queue.add(request);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(db!=null){
            db.close();
        }

    }
}
