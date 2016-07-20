package skkk.gogogo.com.dakaizhihu.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by admin on 2016/7/20.
 */
/*
* 
* 描    述：针对bitmap的工具类
* 作    者：ksheng
* 时    间：
*/
public class BitmapUtils {
    private Context context;

    public BitmapUtils(Context context) {
        this.context = context;
    }

    /**
     * 保存方法
     */
    public boolean saveBitmap(Bitmap bm,String pngName) {
        Log.d("TAG", "保存图片");
        File f = new File(context.getFilesDir(), pngName);
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
    public Bitmap getLoacalBitmap(String pngName) {
        try {
            File f = new File(context.getFilesDir(),pngName);
            FileInputStream fis = new FileInputStream(f);
            return BitmapFactory.decodeStream(fis);  ///把流转化为Bitmap图片
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
}
