package skkk.gogogo.com.dakaizhihu.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initUI();
        initData();
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
        webSetting.setDefaultTextEncodingName("UTF-8");//设置webview的默认编码格式
        webSetting.setJavaScriptEnabled(true);//使用网页中的一些JS交互


        //设置activity中大开的网页使用本webview打开
        wvWebPage.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
    }

    private void initData() {
        //获得从上一个Activity传来的intent对象
        Intent intent=getIntent();
        url = intent.getStringExtra("url");
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
            oks.setText("~~~~~~分享自大开知乎~~~~~"+"\n"+url);
            oks.show(WebPageActivity.this);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
