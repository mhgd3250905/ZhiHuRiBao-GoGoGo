package skkk.gogogo.com.dakaizhihu.fragment;

import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.UnsupportedEncodingException;

import skkk.gogogo.com.dakaizhihu.NewsDetailsGson.NewDetailsData;
import skkk.gogogo.com.dakaizhihu.R;

/**
 * Created by admin on 2016/6/22.
 */
/*
* 
* 描    述：展示相信的新闻内容
* 作    者：ksheng
* 时    间：
*/
public class NewsDetailsFragment extends Fragment{
    SharedPreferences mPref;
    private View view;
    private String url;
    private String getData;
    private NewDetailsData newsDetailsData;
    private Document document;
    private String title;
    private String html;
    private WebView wvNewsDetails;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_news_details, container, false);
        mPref=getActivity().getSharedPreferences("config", Context.MODE_PRIVATE);
        url = mPref.getString("url_from_home", "http://www.baidu.com");
        wvNewsDetails= (WebView) view.findViewById(R.id.wv_news_details);
        wvNewsDetails.getSettings().setJavaScriptEnabled(false);
        wvNewsDetails.getSettings().setSupportZoom(false);
        wvNewsDetails.getSettings().setBuiltInZoomControls(false);
        wvNewsDetails.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        wvNewsDetails.getSettings().setDefaultFontSize(18);

        initData();

        return view;
    }

    private String author;
    private String content;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            Gson gson = new Gson();
            java.lang.reflect.Type type = new TypeToken<NewDetailsData>() {
            }.getType();

            newsDetailsData = gson.fromJson(getData, type);

            try {
                html = new String(newsDetailsData.getBody().getBytes(), "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }


            document = Jsoup.parse(html);
            //获取标题
            Elements elementsByClass = document.getElementsByClass("question-title");

                title = elementsByClass.text();

            Log.d("TAG","title   "+title);
            //获取作者
            Elements getAuthor = document.getElementsByClass("author");

                 author= getAuthor.text();

            Log.d("TAG", "author   "+author);

            Elements getContent = document.getElementsByClass("content");

                content=getContent.text();
            Log.d("TAG", "content   "+content);

            Elements allElements = document.getAllElements();

            StringBuilder sb=new StringBuilder();
            for(Element element:allElements){
                sb.append(element.text()+"\r\n");
            }

            wvNewsDetails.loadDataWithBaseURL(null, html, "text/html", "utf-8", null);
        }
    };

    private void initData() {
        RequestQueue queue= Volley.newRequestQueue(getActivity());
        StringRequest request=new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {

                //由于Volley是用的ISO-8859-1编码，这里需要把字符串转换为utf-8编码。
                //不然会出现乱码。这个乱码跟AndroidStudio中的乱码还不一样。

                try {
                    getData = new String (s.getBytes("ISO-8859-1"),"utf-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
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
