package skkk.gogogo.com.dakaizhihu.activity;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

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

import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;
import skkk.gogogo.com.dakaizhihu.NewsDetailsGson.NewDetailsData;
import skkk.gogogo.com.dakaizhihu.R;
import skkk.gogogo.com.dakaizhihu.utils.LogUtils;
import skkk.gogogo.com.dakaizhihu.utils.MySQLiteHelper;
import skkk.gogogo.com.dakaizhihu.utils.MyStringRequest;
import skkk.gogogo.com.dakaizhihu.utils.URLStringUtils;

public class NewsDetailActivity extends AppCompatActivity {
    @ViewInject(R.id.iv_news_title)
    SimpleDraweeView ivNewsTitle;
    @ViewInject(R.id.NSV_details)
    NestedScrollView nsvDetails;
    SharedPreferences mPref;

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
    private String shareURL;
    private int newsId;
    private Boolean imageMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //处理加载UI之前需要的各种动作
        beforeStart();
        initUI();
        initDB();

        if (checkStorage()){
            //getSupportActionBar().setDisplayShowTitleEnabled(false);
            mWebView.loadDataWithBaseURL("", newsHtmlContent, "text/html", "utf-8", "");
            try {
                ivNewsTitle.setImageURI(Uri.parse(titleImage));
            }catch (Exception e){
                ivNewsTitle.setVisibility(View.GONE);
            }

            LogUtils.MyLog("文章详情", "判断data来自DB");

        }else {

            LogUtils.MyLog("文章详情","尝试从网络获取");
            initData();
        }
    }

    private void beforeStart() {
        //获取穿过来的的news_id信息
        Intent intent=getIntent();
        newsId = intent.getIntExtra("news_id", 0);
        //获取是否为无图模式
        mPref=getSharedPreferences("config", MODE_PRIVATE);

    }


    /*
        * @desc 判断是否有缓存于数据库
        * @时间 2016/7/4 23:57
        * 如果有缓存那么就返回true 否则返回false
        */
    private boolean checkStorage() {


        //获取一个可以写的数据库db
        db=dbHelper.getWritableDatabase();
        //获取一个游标并且对db中所有的条目进行检索
        Cursor cursor = db.query("News",null,null,null,null,null,null);
        while (cursor.moveToNext()){
            //如果db中找到对应的newsId，则获取出相应的信息
            if(String.valueOf(newsId).equals(cursor.getString(4))){
                Log.d("TAG","--------------------------"+cursor.getString(4));
                titleImage=cursor.getString(1);
                Log.d("TAG","--------------------------"+titleImage);
                imageSource=cursor.getString(2);
                Log.d("TAG","--------------------------"+imageSource);
                newsHtmlContent=cursor.getString(3);
                Log.d("TAG","--------------------------content");
                shareURL=cursor.getString(5);
                newsTitle=cursor.getString(6);

                //使用完毕之后关闭游标
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

        setContentView(R.layout.activity_news_detail);
        ViewUtils.inject(this);

        //webview初始化设置



        mWebView = (WebView) findViewById(R.id.wv_news_details);
        WebSettings webSetting = mWebView.getSettings();//获取webview的设置
        webSetting.setDefaultTextEncodingName("UTF-8");//设置webview的默认编码格式
        webSetting.setJavaScriptEnabled(true);//使用网页中的一些JS交互
        //无图模式
        //imageMode是true则证明为希望是无图模式
        //false则证明为非无图模式
        imageMode = mPref.getBoolean("image_mode",false);
        if (imageMode){
            webSetting.setBlockNetworkImage(true);
        }else {
            webSetting.setBlockNetworkImage(false);
        }



        // WebViewClient用来处理WebView各种通知、请求事件等,重写里面的方法即可
        mWebView.setWebViewClient(new WebViewClient() {
            // 点击页面中的链接会调用这个方法
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // TODO Auto-generated method stub
                // 跳转到另外的activity
                Intent intent = new Intent();
                intent.putExtra("url", url);
                intent.setClass(NewsDetailActivity.this, WebPageActivity.class);
                startActivity(intent);
                Log.i("qing", "shouldOverrideUrlLoading..." + url);
                return super.shouldOverrideUrlLoading(view, url);
            }
        });


        //初始化toolbar
        toolbar = (Toolbar) findViewById(R.id.tb_news);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        //使用CollapsingToolbarLayout后，title需要设置到CollapsingToolbarLayout上
        collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        //通过CollapsingToolbarLayout修改字体颜色
        collapsingToolbar.setExpandedTitleColor(Color.WHITE);//设置还没收缩时状态下字体颜色
        collapsingToolbar.setCollapsedTitleTextColor(Color.WHITE);//设置收缩后Toolbar上字体的颜色
        collapsingToolbar.setCollapsedTitleGravity(Gravity.CENTER);//设置收缩之后的标题位置
        collapsingToolbar.setCollapsedTitleTextColor(Color.WHITE);//设置收缩之后的字体颜色
        collapsingToolbar.setExpandedTitleGravity(Gravity.BOTTOM | Gravity.RIGHT);//设置未收缩时候的标题位置

        LogUtils.MyLog("文章详情", "UI初始化完毕");
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
        int id = item.getItemId();
        if (id == R.id.action_share) {
            ShareSDK.initSDK(this);
            OnekeyShare oks = new OnekeyShare();
            oks.setText(newsTitle+"\n"+"~~~~~~分享自大开知乎~~~~~"+"\n"+shareURL);
            oks.show(NewsDetailActivity.this);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



    /*
    * @desc 获取数据
    * @时间 2016/7/2 22:54
    */
    private void initData() {

        //volley通过网络获取字符串信息

        //获取url
        url = URLStringUtils.getNEWSDETAILSURL(String.valueOf(newsId));
        //新建一个volley队列用来清幽网络数据库
        RequestQueue queue = Volley.newRequestQueue(this);
        //新建一个String请求request
        MyStringRequest request = new MyStringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {

                LogUtils.MyLog("文章详情","连接网络并获取到数据");

                //获取到数据之后通过Gson来将json转化为可用的javaBean
                Gson gson = new Gson();
                java.lang.reflect.Type type = new TypeToken<NewDetailsData>() {
                }.getType();
                newsDetailsData = gson.fromJson(s, type);
                //获取新闻标题
                newsTitle = newsDetailsData.getTitle();
                //获取新闻标题图片
                titleImage = newsDetailsData.getImage();
                //获取新闻图片来源作者
                imageSource = newsDetailsData.getImage_source();
                //获取分享url
                shareURL = newsDetailsData.getShare_url();


                LogUtils.MyLog("文章详情","开始解析html文本");

                doc_dis = null;
                //这里通过Jsoup来获取html文本文件中的dom然后进行对应的修改
                doc_dis = Jsoup.parse(newsDetailsData.getBody());

                //把html中所有的图片都大小设置为适应100%，遇到作者头像图片设置为适应8%
                Elements ele_Img = doc_dis.getElementsByTag("img");
                if (ele_Img.size() != 0) {
                    for (Element e_Img : ele_Img) {
                        e_Img.attr("style", "width:100%");
                        if (e_Img.className().equals("avatar")) {
                            e_Img.attr("style", "width:8%");
                        }
                    }
                }

                //html中所有文字设置为下面的属性编剧为7dp
                Elements ele_Div=doc_dis.getElementsByTag("div");
                if (ele_Div.size() != 0) {
                    for (Element e_div : ele_Div) {
                        e_div.attr("style", "line-height:155%;font-family:微软雅黑 SC;color:#141414;font-size:17px");
                        if (e_div.className().equals("main-wrap content-wrap")) {
                            e_div.attr("style", "margin:7");
                        }else if(e_div.className().equals("view-more")){
                            e_div.attr("style", "text-align:center");
                        }

                    }
                }

                //设置html文本中的超链接文字style
                Elements ele_herf=doc_dis.getElementsByTag("a");
                if (ele_herf.size() != 0) {
                    for (Element e_herf : ele_herf) {
                            e_herf.attr("style","font-family:宋体;color:#607d8b");
                    }
                }
                //获得webView加载需要的html文本
                newsHtmlContent = doc_dis.toString();

                //这里开始将我们前面从网络获取的data装入数据库
                ContentValues values = new ContentValues();

                LogUtils.MyLog("文章详情","将网络获取之data保存至DB");

                //开始组装第一条数据
                values.put("image_uri", titleImage);
                values.put("image_source", imageSource);
                values.put("html_body", newsHtmlContent);
                values.put("news_id", newsId);
                values.put("share_url", shareURL);
                values.put("title", newsTitle);
                //写入数据库
                db.insert("News", null, values);

                //我们在UI线程中进行UI更新的操作
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //使用WebView的 loadDataWithBaseURL（）方法
                        //如果有图片就加载否则设置图片visible为GONE
                        mWebView.loadDataWithBaseURL("", newsHtmlContent, "text/html", "utf-8", "");
                        try {
                            ivNewsTitle.setImageURI(Uri.parse(titleImage));
                            collapsingToolbar.setTitle(newsTitle);
                        }catch (Exception e){
                            ivNewsTitle.setVisibility(View.GONE);
                            collapsingToolbar.setTitle(newsTitle);
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


    /*
    * @desc 选择在pause中关闭db防止内存泄漏
    * @时间 2016/7/17 22:46
    */
    @Override
    protected void onPause() {
        super.onPause();
        //为了避免内存泄漏 我们关闭db
        if(db!=null){
            db.close();
        }
    }


}
