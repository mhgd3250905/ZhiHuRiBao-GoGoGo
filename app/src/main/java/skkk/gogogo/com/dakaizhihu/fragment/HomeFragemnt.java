package skkk.gogogo.com.dakaizhihu.fragment;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.UnsupportedEncodingException;
import java.util.List;

import skkk.gogogo.com.dakaizhihu.Cache.BitmapCache;
import skkk.gogogo.com.dakaizhihu.HomeGson.HomeData;
import skkk.gogogo.com.dakaizhihu.HomeGson.Story;
import skkk.gogogo.com.dakaizhihu.R;
import skkk.gogogo.com.dakaizhihu.activity.NewsDetailActivity;
import skkk.gogogo.com.dakaizhihu.adapter.HomeAdapter;

/**
 * Created by admin on 2016/6/21.
 */
/*
* 
* 描    述：用于显示所有文章列表的fragment
* 作    者：ksheng
* 时    间：6/21
*/
public class HomeFragemnt extends Fragment{
    private RecyclerView mRecyclerView;//recyclerView
    private LinearLayoutManager mLayoutManager;//线性布局管理器
    private View view;//加载之view
    private String getData;//c从网络端获取到的数据
    private HomeData homeData;//获取到的home数据类
    private List<Story> mData;
    private SwipeRefreshLayout mSwipeRefreshWidget;
    private ImageLoader loader;
    private SharedPreferences mPref;

    /*
    * @desc 创建之方法
    * @时间 2016/6/22 12:44
    */
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home,container,false);
        mPref=getActivity().getSharedPreferences("config", Context.MODE_PRIVATE);
        initUI();
        initData();
        return view;
    }

/*
* @desc 获取网络数据
* @时间 2016/6/22 11:50
*/
    private void initData() {
        //创建队列
        RequestQueue queue= Volley.newRequestQueue(getActivity());

        loader = new ImageLoader(queue, new BitmapCache());

        //URL
        String url="http://news-at.zhihu.com/api/4/news/latest";

        StringRequest request=new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {

                //由于Volley是用的ISO-8859-1编码，这里需要把字符串转换为utf-8编码。
                //不然会出现乱码。这个乱码跟AndroidStudio中的乱码还不一样。

                try {
                    //获取标准制式的数据
                    getData = new String (s.getBytes("ISO-8859-1"),"utf-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                if (mSwipeRefreshWidget.isRefreshing()){
                    mSwipeRefreshWidget.setRefreshing(false);
                }
                //发送消息
                mHandler.sendEmptyMessage(0);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                if (mSwipeRefreshWidget.isRefreshing()){
                    mSwipeRefreshWidget.setRefreshing(false);
                    Toast.makeText(getActivity(), "无法获取网络", Toast.LENGTH_SHORT).show();
                }
            }
        });
        queue.add(request);
    }
    /*
    * @desc Handler 接收消息做出反应
    * @时间 2016/6/22 11:54
    */
    private Handler mHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            Log.d("TAG",getData);
            Gson gson = new Gson();
            java.lang.reflect.Type type = new TypeToken<HomeData>() {
            }.getType();
            homeData = gson.fromJson(getData, type);
            mData = homeData.getStories();

            /*创建并设置Adapter*/

            HomeAdapter homeAdapter=new HomeAdapter(getActivity(),mData,loader);
            homeAdapter.setOnItemClickLitener(new HomeAdapter.OnItemClickLitener() {
                @Override
                public void onItemClick(View view, int position) {
                    int id = mData.get(position).getId();
                    String details="http://news-at.zhihu.com/api/4/news/"+id;
                    mPref.edit().putString("url_from_home",details).commit();
                    startActivity(new Intent(getActivity(), NewsDetailActivity.class));
                }

                @Override
                public void onItemLongClick(View view, int position) {

                }
            });
            mRecyclerView.setAdapter(homeAdapter);

        }
    };

    /*
    * @desc UI
    * @时间 2016/6/21 23:29
    */
    private void initUI() {
        /*设置RecyclerView*/
        mRecyclerView = (RecyclerView) view.findViewById(R.id.rv_home);
        /*创建默认的线性LayoutManager*/
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        /*如果可以确定每个item的高度是固定的设置这个选项可以提高性能*/
        mRecyclerView.setHasFixedSize(true);
        /*设置SwipeRefreshLayout*/
        mSwipeRefreshWidget = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_widget);
        mSwipeRefreshWidget.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initData();
            }
        });


    }
}
