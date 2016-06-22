package skkk.gogogo.com.dakaizhihu.fragment;

import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

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
    private WebView wvNewsDetails;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_news_details, container, false);
        mPref=getActivity().getSharedPreferences("config", Context.MODE_PRIVATE);
        url = mPref.getString("url_from_home", "http://www.baidu.com");
        wvNewsDetails = (WebView) view.findViewById(R.id.wv_news_details);
        initData();

        return view;
    }

    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            Gson gson = new Gson();
            java.lang.reflect.Type type = new TypeToken<NewDetailsData>() {
            }.getType();
            newsDetailsData = gson.fromJson(getData, type);
            wvNewsDetails.loadDataWithBaseURL(null, newsDetailsData.getBody(), "text/html", "UTF-8", null);;
        }
    };

    private void initData() {
        RequestQueue queue= Volley.newRequestQueue(getActivity());
        StringRequest request=new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                getData = s;
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
