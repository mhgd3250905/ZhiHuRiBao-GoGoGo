package skkk.gogogo.com.dakaizhihu.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.List;

import skkk.gogogo.com.dakaizhihu.KanzhihuQuestionListGson.Answer;
import skkk.gogogo.com.dakaizhihu.KanzhihuQuestionListGson.QuestionListData;
import skkk.gogogo.com.dakaizhihu.R;
import skkk.gogogo.com.dakaizhihu.adapter.KanzhihuQuestionListAdapter;
import skkk.gogogo.com.dakaizhihu.utils.MyStringRequest;
import skkk.gogogo.com.dakaizhihu.utils.URLStringUtils;

public class KanzhihuQuestionListActivity extends AppCompatActivity {
    @ViewInject(R.id.tb_kanzhihu)
    Toolbar tbKanzhihu;
    @ViewInject(R.id.rv_kanzhihu)
    RecyclerView rvKanzhihu;
    @ViewInject(R.id.srl_kanzhihu_question_list)
    SwipeRefreshLayout srlKanzhihuQuestionList;
    private LinearLayoutManager mLayoutManager;
    private QuestionListData questionListData;
    private List<Answer> mData;
    private SharedPreferences mPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPref=getSharedPreferences("config",MODE_PRIVATE);
        initUI();
        initData();
    }

    private void initUI() {
        setContentView(R.layout.activity_kanzhihu_question_list);
        ViewUtils.inject(this);
        setSupportActionBar(tbKanzhihu);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        tbKanzhihu.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        /*设置RecyclerView*/
        /*创建默认的线性LayoutManager*/
        mLayoutManager = new LinearLayoutManager(KanzhihuQuestionListActivity.this);
        rvKanzhihu.setLayoutManager(mLayoutManager);
        /*如果可以确定每个item的高度是固定的设置这个选项可以提高性能*/
        rvKanzhihu.setHasFixedSize(true);

    }

    private void initData() {
        RequestQueue queue= Volley.newRequestQueue(this);
        String url= URLStringUtils.getKANZHIHUQUESTIONLIST("20160719","archive");
        MyStringRequest request=new MyStringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                Gson gson = new Gson();
                java.lang.reflect.Type type = new TypeToken<QuestionListData>() {
                }.getType();
                questionListData = gson.fromJson(s, type);
                mData = questionListData.getAnswers();

                //发送消息
                mHandler.sendEmptyMessage(0);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        });
        queue.add(request);
    }


    private Handler mHandler=new Handler() {
        @Override
        public void handleMessage(Message msg) {
            /*创建并设置Adapter*/
            KanzhihuQuestionListAdapter adapter =
                    new KanzhihuQuestionListAdapter(KanzhihuQuestionListActivity.this, mData);
            adapter.setOnItemClickLitener(new KanzhihuQuestionListAdapter.OnItemClickLitener() {
                @Override
                public void onItemClick(View view, int position) {
                    final String questionId = mData.get(position).getQuestionid();
                    final String answerId = mData.get(position).getAnswerid();

                    Log.d("TAG","questionId--------------------------------"+questionId);
                    Log.d("TAG","answerId--------------------------------"+answerId);
                    Intent intent = new Intent();
                    intent.putExtra("url",URLStringUtils.getKANZHIHUQUESTIONANDANSWER(questionId,answerId));
                    intent.setClass(KanzhihuQuestionListActivity.this, WebPageActivity.class);
                    startActivity(intent);
                }

                @Override
                public void onItemLongClick(View view, int position) {

                }
            });
                    //recyclerView添加数据适配器
            rvKanzhihu.setAdapter(adapter);
        }
    };

}
