package skkk.gogogo.com.dakaizhihu.utils;

import android.util.Log;

/**
 * Created by admin on 2016/7/22.
 */
/*
*
* 描    述：log日志工具类
* 作    者：ksheng
* 时    间：
*/
public class LogUtils {
    public LogUtils() {
    }

    public static void MyLog(String name,String thing){
        Log.d("SKKK", "★★★★★★★★★★★★★★★★★★★★" + "\n"
                + name + "★★★★★★★★" + thing + "\n"
                +"                                       ");
    }
}
