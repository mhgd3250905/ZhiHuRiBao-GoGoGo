package skkk.gogogo.com.dakaizhihu.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;

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

public class NewsDetailActivity extends AppCompatActivity {
    @ViewInject(R.id.iv_news_title)
    SimpleDraweeView ivNewsTitle;
    SharedPreferences mPref;
    private int newsId;
    private NewDetailsData newsDetailsData;
    private WebView mWebView;
    private String newHtmlContent;
    private Document doc_dis;
    private String titleImage;
    private String imageSource;
    private CollapsingToolbarLayout collapsingToolbar;
    private String title;
    private MySQLiteHelper dbHelper;
    private SQLiteDatabase db;
    private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);
        initUI();
        initDB();
        checkStorage();

    }

    /*
    * @desc 判断是否有缓存于数据库
    * @时间 2016/7/4 23:57
    */
    private void checkStorage() {
        mPref = getSharedPreferences("config", Context.MODE_PRIVATE);
        newsId = mPref.getInt("news_id", 0);

        db=dbHelper.getReadableDatabase();
        Cursor cursor = db.query("News",null,null,null,null,null,null);
        while (cursor.moveToNext()){
            if(String.valueOf(newsId).equals(cursor.getString(4))){
                titleImage=cursor.getString(1);
                imageSource=cursor.getString(2);
                newHtmlContent=cursor.getString(3);
                getSupportActionBar().setDisplayShowTitleEnabled(false);
                mWebView.loadDataWithBaseURL("", newHtmlContent, "text/html", "utf-8", "");
                ivNewsTitle.setImageURI(Uri.parse(titleImage));
            }else{
                initData();
            }
        }
        if (cursor!=null){
            cursor.close();
        }
        if(db!=null){
            db.close();
        }
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
        Toolbar toolbar = (Toolbar) findViewById(R.id.tb_news);
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
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("是否保存？", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                            }
                        }).show();
            }
        });


    }


    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {

        }
    };

    /*
    * @desc 获取数据
    * @时间 2016/7/2 22:54
    */
    private void initData() {
        //volley通过网络获取字符串信息
        url = "http://news-at.zhihu.com/api/4/news/" + newsId;
        RequestQueue queue = Volley.newRequestQueue(this);
        MyStringRequest request = new MyStringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                Gson gson = new Gson();
                java.lang.reflect.Type type = new TypeToken<NewDetailsData>() {
                }.getType();
                newsDetailsData = gson.fromJson(s, type);
                title = newsDetailsData.getTitle();
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
                newHtmlContent = doc_dis.toString();


                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        getSupportActionBar().setDisplayShowTitleEnabled(false);
                        mWebView.loadDataWithBaseURL("", newHtmlContent, "text/html", "utf-8", "");
                        ivNewsTitle.setImageURI(Uri.parse(titleImage));

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
}
