package skkk.gogogo.com.dakaizhihu.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import skkk.gogogo.com.dakaizhihu.NewsDetailsGson.NewDetailsData;
import skkk.gogogo.com.dakaizhihu.R;
import skkk.gogogo.com.dakaizhihu.utils.MyStringRequest;

public class NewsDetailActivity extends AppCompatActivity {
    SharedPreferences mPref;
    private String url;
    private String getData;
    private NewDetailsData newsDetailsData;
    private WebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);
        initUI();
    }

    private void initUI() {
        mPref = getSharedPreferences("config", Context.MODE_PRIVATE);
        url = mPref.getString("url_from_home", "http://www.baidu.com");
        Log.d("TAG", url);


        mWebView = (WebView)findViewById(R.id.wv_news_details);

        WebSettings webSetting = mWebView.getSettings();
        webSetting.setDefaultTextEncodingName("UTF-8");
        webSetting.setJavaScriptEnabled(false);
        webSetting.setAllowFileAccess(true);
        webSetting.setBuiltInZoomControls(false);
        webSetting.setSupportZoom(false);
        initData();

    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            Gson gson = new Gson();
            java.lang.reflect.Type type = new TypeToken<NewDetailsData>() {
            }.getType();


            newsDetailsData = gson.fromJson(getData, type);

            Document doc_Dis = null;

                doc_Dis = Jsoup.parse(newsDetailsData.getBody());
                Elements ele_Img = doc_Dis.getElementsByTag("img");
                if (ele_Img.size() != 0){
                    for (Element e_Img : ele_Img) {
                        e_Img.attr("style", "width:100%");
                    }
                }

            String newHtmlContent= null;

            newHtmlContent = doc_Dis.toString();

            mWebView.loadDataWithBaseURL("", newHtmlContent, "text/html", "utf-8", "");

        }
    };



    private void initData() {
        RequestQueue queue = Volley.newRequestQueue(this);

        MyStringRequest request = new MyStringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                getData=s;
                handler.sendEmptyMessage(0);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        });
        queue.add(request);
    }
}
