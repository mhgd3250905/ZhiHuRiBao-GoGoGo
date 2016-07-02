package skkk.gogogo.com.dakaizhihu.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import skkk.gogogo.com.dakaizhihu.Cache.BitmapCache;
import skkk.gogogo.com.dakaizhihu.NewsDetailsGson.NewDetailsData;
import skkk.gogogo.com.dakaizhihu.R;
import skkk.gogogo.com.dakaizhihu.utils.MyStringRequest;

public class NewsDetailActivity extends AppCompatActivity {
    @ViewInject(R.id.tv_news_title)
    TextView tvNewsTitle;
    @ViewInject(R.id.iv_news_title)
    NetworkImageView ivNewsTitle;
    SharedPreferences mPref;
    private String url;
    private NewDetailsData newsDetailsData;
    private WebView mWebView;
    private String newHtmlContent;
    private Document doc_dis;
    private String titleImage;
    private String imageSource;
    private CollapsingToolbarLayout collapsingToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);
        initUI();
        initData();
    }

    private void initUI() {
        ViewUtils.inject(this);
        mPref = getSharedPreferences("config", Context.MODE_PRIVATE);
        url = mPref.getString("url_from_home", "http://www.baidu.com");

        //webview初始化设置
        mWebView = (WebView) findViewById(R.id.wv_news_details);
        WebSettings webSetting = mWebView.getSettings();
        webSetting.setDefaultTextEncodingName("UTF-8");
        webSetting.setJavaScriptEnabled(false);
        webSetting.setAllowFileAccess(true);
        webSetting.setBuiltInZoomControls(false);
        webSetting.setSupportZoom(false);


        //初始化toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //使用CollapsingToolbarLayout后，title需要设置到CollapsingToolbarLayout上
        collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        //通过CollapsingToolbarLayout修改字体颜色
        collapsingToolbar.setExpandedTitleColor(Color.WHITE);//设置还没收缩时状态下字体颜色
        collapsingToolbar.setCollapsedTitleTextColor(Color.WHITE);//设置收缩后Toolbar上字体的颜色
        collapsingToolbar.setCollapsedTitleGravity(Gravity.CENTER);//设置收缩之后的标题位置
        collapsingToolbar.setCollapsedTitleTextColor(Color.WHITE);//设置收缩之后的字体颜色
        collapsingToolbar.setExpandedTitleGravity(Gravity.BOTTOM | Gravity.RIGHT);//设置未收缩时候的标题位置

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
        RequestQueue queue = Volley.newRequestQueue(this);
        MyStringRequest request = new MyStringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                Gson gson = new Gson();
                java.lang.reflect.Type type = new TypeToken<NewDetailsData>() {
                }.getType();
                newsDetailsData = gson.fromJson(s, type);
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
                        tvNewsTitle.setText(imageSource);
                        mWebView.loadDataWithBaseURL("", newHtmlContent, "text/html", "utf-8", "");
                        RequestQueue queue2 = Volley.newRequestQueue(NewsDetailActivity.this);
                        ImageLoader imageLoader = new ImageLoader(queue2, new BitmapCache());
                        ivNewsTitle.setDefaultImageResId(R.drawable.ic_launcher);
                        ivNewsTitle.setErrorImageResId(R.drawable.ic_launcher);
                        ivNewsTitle.setImageUrl(titleImage,
                                imageLoader);

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
