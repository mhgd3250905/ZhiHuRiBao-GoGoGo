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

import skkk.gogogo.com.dakaizhihu.R;
import skkk.gogogo.com.dakaizhihu.ThemeGson.GetThemeData;
import skkk.gogogo.com.dakaizhihu.ThemeGson.Other;
import skkk.gogogo.com.dakaizhihu.adapter.MyPagerAdapter;
import skkk.gogogo.com.dakaizhihu.utils.LogUtils;
import skkk.gogogo.com.dakaizhihu.utils.MyStringRequest;
import skkk.gogogo.com.dakaizhihu.utils.URLStringUtils;

/**
 * Created by admin on 2016/7/8.
 */
/*
* 
* 描    述：
* 作    者：ksheng
* 时    间：
*/
public class ThemeMainFragment extends Fragment {

    ViewPager vpTheme;

    TabLayout tpiTheme;

    private List<Fragment> fragmentList;
    private MyPagerAdapter adapter;
    private String[] TITLE_0={"开始游戏","电影日报","设计日报","大公司日报",
            "财经日报","音乐日报","体育日报","动漫日报",
            "互联网安全","不许无聊","用户推荐日报","日常心理学"};
    private ArrayList<String> TITLE;
    private View view;
    private String urlList;
    private GetThemeData listData;
    private List<Other> datas;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_theme_main,container,false);

        LogUtils.MyLog("ThemeMainFragment", "onCreate");

        initUI();
        initData();

        return view;
    }

    private void initData() {

        LogUtils.MyLog("ThemeMainFragment", "获取数据");

        urlList = URLStringUtils.getGETHOWMANYTHEMES();
        RequestQueue queue= Volley.newRequestQueue(getContext());
        MyStringRequest request=new MyStringRequest(urlList, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                Gson gson = new Gson();
                java.lang.reflect.Type type = new TypeToken<GetThemeData>() {
                }.getType();
                listData = gson.fromJson(s, type);
                datas = listData.getOthers();



                fragmentList=new ArrayList<Fragment>();
                TITLE=new ArrayList<String>();
                for(int i=0;i<datas.size();i++){
                    Fragment themeFragment=new ThemeFragment(datas.get(i).getId());
                    fragmentList.add(themeFragment);
                    TITLE.add(datas.get(i).getName());
                }

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter = new MyPagerAdapter(getActivity().getSupportFragmentManager(), fragmentList, TITLE);

                        vpTheme.setAdapter(adapter);

                        //实例化TabPageIndicator然后设置ViewPager与之关联
                        tpiTheme.setupWithViewPager(vpTheme);


                        //如果我们要对ViewPager设置监听，用indicator设置就行了
                        tpiTheme.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {


                            @Override
                            public void onTabSelected(TabLayout.Tab tab) {
                                vpTheme.setCurrentItem(tab.getPosition());
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


    private void initUI() {
        vpTheme= (ViewPager) view.findViewById(R.id.vp_theme);
        tpiTheme= (TabLayout) view.findViewById(R.id.tpi_theme);

        LogUtils.MyLog("ThemeMainFragment", "初始化UI完毕");
    }

}
