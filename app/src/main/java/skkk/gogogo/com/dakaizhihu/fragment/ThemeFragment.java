package skkk.gogogo.com.dakaizhihu.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

import skkk.gogogo.com.dakaizhihu.R;
import skkk.gogogo.com.dakaizhihu.ThemeGson.ThemeNewsListData;
import skkk.gogogo.com.dakaizhihu.ThemeGson.ThemeStory;
import skkk.gogogo.com.dakaizhihu.activity.NewsDetailActivity;
import skkk.gogogo.com.dakaizhihu.adapter.ThemeAdapter;
import skkk.gogogo.com.dakaizhihu.utils.LogUtils;
import skkk.gogogo.com.dakaizhihu.utils.MySQLiteHelper;
import skkk.gogogo.com.dakaizhihu.utils.MyStringRequest;
import skkk.gogogo.com.dakaizhihu.utils.URLStringUtils;

/**
 * Created by admin on 2016/7/5.
 */
/*
* 
* 描    述：
* 作    者：ksheng
* 时    间：
*/
public class ThemeFragment extends Fragment {

    private RecyclerView mRecyclerView;//recyclerView
    private LinearLayoutManager mLayoutManager;//线性布局管理器
    private View view;//加载之view
    private ThemeNewsListData themeData;//获取到的home数据类
    private List<ThemeStory> mData;
    private SwipeRefreshLayout mSwipeRefreshWidget;
    private ImageLoader loader;
    private MySQLiteHelper dbHelper;
    private SQLiteDatabase db;
    private ThemeAdapter themeAdapter;
    private RequestQueue queue;
    private int THEMEID;

    @SuppressLint("ValidFragment")
    public ThemeFragment(int THEMEID) {
        this.THEMEID = THEMEID;
    }

    public ThemeFragment() {
    }

    /*
            * @desc 创建之方法
            * @时间 2016/6/22 12:44
            */
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        LogUtils.MyLog("ThemeFragment", "onCreate");

        view = inflater.inflate(R.layout.fragment_home, container, false);

        initUI();
        initData();
        initDB();
        return view;
    }

    @Override
    public void onStop() {

        LogUtils.MyLog("ThemeFragment", "onStop");

        super.onStop();
    }

    private void initDB() {
        dbHelper = new MySQLiteHelper(getActivity(), "News.db", null, 1);
    }

    /*
    * @desc 获取网络数据
    * @时间 2016/6/22 11:50
    */
    private void initData() {

        LogUtils.MyLog("ThemeFragment", "获取数据");

        //创建队列
        queue = Volley.newRequestQueue(getActivity());

        //URL
        String url = URLStringUtils.getTHEMENEWSLISTURL(THEMEID);

        MyStringRequest request = new MyStringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {

                Gson gson = new Gson();
                java.lang.reflect.Type type = new TypeToken<ThemeNewsListData>() {
                }.getType();
                themeData = gson.fromJson(s, type);
                mData = themeData.getStories();

                if (mSwipeRefreshWidget.isRefreshing()) {
                    mSwipeRefreshWidget.setRefreshing(false);
                }


                //发送消息
                mHandler.sendEmptyMessage(0);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                if (mSwipeRefreshWidget.isRefreshing()) {
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
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            /*创建并设置Adapter*/
            themeAdapter = new ThemeAdapter(getActivity(), mData);
            themeAdapter.setOnItemClickLitener(new ThemeAdapter.OnItemClickLitener() {
                @Override
                public void onItemClick(View view, int position) {
                    final int id = mData.get(position).getId();

                    Intent intent = new Intent();
                    intent.putExtra("news_id", id);
                    try {
                        intent.putExtra("image", mData.get(position).getImages().get(0));
                    } catch (Exception e) {
                        intent.putExtra("image", "");
                    }
                    intent.setClass(getContext(), NewsDetailActivity.class);
                    startActivity(intent);
                }

                @Override
                public void onItemLongClick(View view, int position) {

                }
            });
            //recyclerView添加数据适配器
            mRecyclerView.setAdapter(themeAdapter);
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

        LogUtils.MyLog("ThemeFragment", "UI加载完毕");

    }
}
