package skkk.gogogo.com.dakaizhihu.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by admin on 2016/8/1.
 */
/*
* 
* 描    述：判断网络类型
* 作    者：ksheng
* 时    间：
*/
public class NetUtils {
    /**
     * make true current connect service is wifi
     * @param context
     * @return
     */
    public  boolean isWifi(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();
        if (activeNetInfo != null
                && activeNetInfo.getType() == ConnectivityManager.TYPE_WIFI) {
            return true;
        }
        return false;
    }
}
