package skkk.gogogo.com.dakaizhihu.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.io.UnsupportedEncodingException;

import skkk.gogogo.com.dakaizhihu.PirctureGson.PictureData;
import skkk.gogogo.com.dakaizhihu.R;

public class SplashActivity extends AppCompatActivity {
    @ViewInject(R.id.iv_splash)
    SimpleDraweeView ivSplash;
    @ViewInject(R.id.tv_splash_author)
    TextView tvSplashAuthor;

    private RequestQueue queue;
    private StringRequest request;
    private String getData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fresco.initialize(this);
        initUI();
        initData();
    }

    private void initUI() {
        setContentView(R.layout.activity_splash);
        ViewUtils.inject(this);
    }

    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            Gson gson = new Gson();
            java.lang.reflect.Type type = new TypeToken<PictureData>() {
            }.getType();
            PictureData pictureData = gson.fromJson(getData, type);

//            ImageLoader loader=new ImageLoader(queue,new BitmapCache());
//            ImageLoader.ImageListener listener=ImageLoader.getImageListener(ivSplash,
//                    R.drawable.ic_launcher,R.drawable.fall);
//            loader.get(pictureData.getImg(),listener);

            ivSplash.setImageURI(Uri.parse(pictureData.getImg()));

            tvSplashAuthor.setText("BY  " + pictureData.getText());

            SystemClock.sleep(1500);
            startActivity(new Intent(SplashActivity.this, HomeActivity.class));
            finish();
        }
    };

    private void initData() {
        String url="http://news-at.zhihu.com/api/4/start-image/1080*1776";
        queue = Volley.newRequestQueue(this);
        request = new StringRequest(url,
                new Response.Listener<String>() {
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
                        handler.sendEmptyMessage(0);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                SystemClock.sleep(1500);
                startActivity(new Intent(SplashActivity.this,HomeActivity.class));
            }
        });
        queue.add(request);


    }
}
