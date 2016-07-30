package skkk.gogogo.com.dakaizhihu.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;
import skkk.gogogo.com.dakaizhihu.R;

public class WebPageActivity extends AppCompatActivity {
    @ViewInject(R.id.tb_web_page)
    Toolbar tbWebPage;
    @ViewInject(R.id.wv_web_page)
    WebView wvWebPage;
    private String url;
    private String desc;
    private String imageUrl;
    private SharedPreferences mPref;
    private boolean isNight;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        beforeStart();
        initUI();
        initData();
    }

    /*
  * @desc 开始加载UI之前处理所有的动作
  * @时间 2016/7/24 11:32
  */
    private void beforeStart() {
        mPref = getSharedPreferences("config", MODE_PRIVATE);
        isNight = mPref.getBoolean("night", false);
        if (isNight) {
            //设置为夜间模式
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            //设置为非夜间模式
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
    }



    private void initUI() {
        setContentView(R.layout.activity_web_page);
        ViewUtils.inject(this);
        setSupportActionBar(tbWebPage);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        tbWebPage.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        WebSettings webSetting = wvWebPage.getSettings();//获取webview的设置
        setSettings(webSetting);

        webSetting.setBlockNetworkImage(true);


        //设置activity中大开的网页使用本webview打开
        wvWebPage.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
    }

    //设置websetting
    private void setSettings(WebSettings setting) {
        setting.setDefaultTextEncodingName("UTF-8");//设置webview的默认编码格式
        setting.setJavaScriptEnabled(true);
        setting.setBuiltInZoomControls(true);
        setting.setDisplayZoomControls(false);
        setting.setSupportZoom(true);
        setting.setDomStorageEnabled(true);
        setting.setDatabaseEnabled(true);
        // 全屏显示
        setting.setLoadWithOverviewMode(true);
        setting.setUseWideViewPort(true);
        setting.setCacheMode(WebSettings.LOAD_DEFAULT);
    }

    private void initData() {
        //获得从上一个Activity传来的intent对象
        Intent intent = getIntent();
        url = intent.getStringExtra("url");
        desc=intent.getStringExtra("desc");
        imageUrl=intent.getStringExtra("image");
        wvWebPage.loadUrl(url);
    }

    /*
    * @desc webview中点击back返回上一页
    * @时间 2016/7/21 22:27
    */
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && wvWebPage.canGoBack()) {
            wvWebPage.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
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
            oks.setTitle(desc);
            oks.setText(desc);
            oks.setUrl(url);

            if(!TextUtils.isEmpty(imageUrl)){
                oks.setImageUrl(imageUrl);
            }else{
                oks.setImageUrl(getResources().getString(R.string.image_default));
            }
            oks.show(WebPageActivity.this);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        wvWebPage.destroy();
    }
}
