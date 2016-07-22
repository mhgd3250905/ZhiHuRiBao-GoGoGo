package skkk.gogogo.com.dakaizhihu.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import skkk.gogogo.com.dakaizhihu.ImFormationListGson.ImformationListData;
import skkk.gogogo.com.dakaizhihu.ImFormationListGson.SingleData;
import skkk.gogogo.com.dakaizhihu.R;
import skkk.gogogo.com.dakaizhihu.adapter.MyPagerAdapter;
import skkk.gogogo.com.dakaizhihu.utils.LogUtils;
import skkk.gogogo.com.dakaizhihu.utils.MyStringRequest;
import skkk.gogogo.com.dakaizhihu.utils.TimeUtils;
import skkk.gogogo.com.dakaizhihu.utils.URLStringUtils;

/**
 * Created by admin on 2016/6/21.
 */
/*
* 
* 描    述：用于显示所有文章列表的fragment
* 作    者：ksheng
* 时    间：6/21
*/
public class ColumnMainFragment extends Fragment {

    private View view;//加载之view

    ViewPager vpHome;
    TabLayout tpiHome;


    private List<Fragment> fragmentList;
    private MyPagerAdapter adapter;
    private ArrayList<String> TITLE;
    private String url;
    private String urlList;
    private ImformationListData listData;
    private List<SingleData> datas;


    /*
    * @desc 创建之方法
    * @时间 2016/6/22 12:44
    */
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home_main, container, false);

        LogUtils.MyLog("ThemeMainFragment", "OnCreate");

        initUI();
        initData();
        return view;
    }

    private void initData() {
        urlList = URLStringUtils.getIMFORMATIONLIST();
        RequestQueue queue= Volley.newRequestQueue(getContext());
        MyStringRequest request=new MyStringRequest(urlList, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                Gson gson = new Gson();
                java.lang.reflect.Type type = new TypeToken<ImformationListData>() {
                }.getType();
                listData = gson.fromJson(s, type);
                datas = listData.getData();

                LogUtils.MyLog("ThemeMainFragment", TimeUtils.getTimeTitle(0));

                LogUtils.MyLog("ThemeMainFragment", "加载数据");

                fragmentList=new ArrayList<Fragment>();
                TITLE=new ArrayList<String>();
                for (int i=0;i<datas.size();i++){

                    url = URLStringUtils.getGETCOLUMNLIST(datas.get(i).getId());
                    TITLE.add(datas.get(i).getName());

                    ColumnFragment columnFragment=new ColumnFragment(url);
                    fragmentList.add(columnFragment);
                }


                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter = new MyPagerAdapter(getActivity().getSupportFragmentManager(), fragmentList, TITLE);

                        vpHome.setAdapter(adapter);

                        //实例化TabPageIndicator然后设置ViewPager与之关联
                        tpiHome.setupWithViewPager(vpHome);


                        //如果我们要对ViewPager设置监听，用indicator设置就行了
                        tpiHome.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                            @Override
                            public void onTabSelected(TabLayout.Tab tab) {
                                vpHome.setCurrentItem(tab.getPosition());
                            }

                            @Override
                            public void onTabUnselected(TabLayout.Tab tab) {

                            }

                            @Override
                            public void onTabReselected(TabLayout.Tab tab) {

                            }
                        });
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
    * @desc UI
    * @时间 2016/6/21 23:29
    */
    private void initUI() {

        vpHome= (ViewPager) view.findViewById(R.id.vp_home);
        tpiHome= (TabLayout) view.findViewById(R.id.tpi_home);

        LogUtils.MyLog("ThemeMainFragment", "UI加载完毕");
    }

}
