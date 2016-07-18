package skkk.gogogo.com.dakaizhihu.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import skkk.gogogo.com.dakaizhihu.PirctureGson.PictureData;
import skkk.gogogo.com.dakaizhihu.R;
import skkk.gogogo.com.dakaizhihu.utils.MyStringRequest;
import skkk.gogogo.com.dakaizhihu.utils.URLStringUtils;

public class SplashActivity extends AppCompatActivity {
    @ViewInject(R.id.iv_splash)
    ImageView ivSplash;


    private SharedPreferences mPref;
    private RequestQueue queue;
    private StringRequest request;


    /*
    * @desc onCreate
    * @时间 2016/7/6 18:47
    */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fresco.initialize(this);
        mPref = getSharedPreferences("config", MODE_PRIVATE);
        initUI();
        initData();
    }

    /*
    * @desc 跳转到 home页面
    * @时间 22:07
    */
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            startActivity(new Intent(SplashActivity.this,HomeActivity.class));
            finish();
        }
    };

/*
* @desc activity关闭时关闭队列
* @时间 2016/7/6 22:07
*/
    @Override
    protected void onStop() {
        super.onStop();
    }

    /*
        * @desc 初始化UI
        * @时间 2016/7/6 18:47
        */
    private void initUI() {
        setContentView(R.layout.activity_splash);
        ViewUtils.inject(this);

    }


    /*
    * @desc 加载图片
    *       如果已经保存那么就加载图片
    *       否则就联网保存
    * @时间 2016/7/6 18:57
    */
    private void initData() {

        boolean splashImageSave = mPref.getBoolean("splash_image_save", false);

        if (splashImageSave) {
            Log.d("TAG", "----------------------图片来自本地");
            ivSplash.setImageBitmap(getLoacalBitmap());
            saveSplashPng();
            //延迟两秒发送消息跳转
            handler.sendEmptyMessageDelayed(0,2500);
        } else {
            Log.d("TAG", "----------------------开启网络获取图片");
            ivSplash.setImageResource(R.drawable.splash_default_image);
            saveSplashPng();

            handler.sendEmptyMessageDelayed(0,2500);
        }
    }

    public void saveSplashPng() {
        String url = URLStringUtils.getSPLASHIMAGEURL();
        queue = Volley.newRequestQueue(this);
        Log.d("TAG", "----------------------开启队列");
        request = new MyStringRequest(url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        Log.d("TAG", "----------------------获取到数据");
                        Gson gson = new Gson();
                        java.lang.reflect.Type type = new TypeToken<PictureData>() {
                        }.getType();
                        final PictureData pictureData = gson.fromJson(s, type);
                        if (!mPref.getString("splash_image_url","").equals(pictureData.getImg())){
                            ImageRequest imageRequest = new ImageRequest(
                                    pictureData.getImg(),
                                    new Response.Listener<Bitmap>() {
                                        @Override
                                        public void onResponse(Bitmap response) {
                                            if (saveBitmap(response)) {
                                                mPref.edit().putBoolean("splash_image_save", true).commit();
                                                mPref.edit().putString("splash_image_url",pictureData.getImg()).commit();
                                                Log.d("TAG", "----------------------图片保存成功");
                                            } else {
                                                Log.d("TAG", "----------------------图片保存失败");
                                            }
                                        }
                                    }, 0, 0, Bitmap.Config.RGB_565, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Log.d("TAG", "----------------------图片无法正确获取");
                                }
                            });
                            queue.add(imageRequest);
                        }else{
                            Log.d("TAG", "----------------------本地与网络一直");
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                SystemClock.sleep(1500);
                startActivity(new Intent(SplashActivity.this, HomeActivity.class));
            }
        });
        queue.add(request);
    }

    /**
     * 保存方法
     */
    public boolean saveBitmap(Bitmap bm) {
        Log.d("TAG", "保存图片");
        File f = new File(getFilesDir(), "splashImage.png");
        if (f.exists()) {
            f.delete();
        }
        try {
            FileOutputStream out = new FileOutputStream(f);
            bm.compress(Bitmap.CompressFormat.PNG, 90, out);
            out.flush();
            out.close();
            Log.d("TAG", "已经保存");
            return true;

        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 加载本地图片
     */
    public Bitmap getLoacalBitmap() {
        try {
            File f = new File(getFilesDir(), "splashImage.png");
            FileInputStream fis = new FileInputStream(f);
            return BitmapFactory.decodeStream(fis);  ///把流转化为Bitmap图片
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

}
