package skkk.gogogo.com.dakaizhihu.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by admin on 2016/7/9.
 */
/*
* 
* 描    述：获取标准格式的时间的工具
* 作    者：ksheng
* 时    间：20160709
*/
public class TimeUtils {

    public static String getTime(long gap){
        //long now = android.os.SystemClock.uptimeMillis();
        long time=System.currentTimeMillis()-gap;
        SimpleDateFormat format=new SimpleDateFormat("yyyyMMdd");
        Date d1=new Date(time);
        return format.format(d1);
    }

    public static String getTimeTitle(long gap){
        //long now = android.os.SystemClock.uptimeMillis();
        long time=System.currentTimeMillis()-gap;
        SimpleDateFormat format=new SimpleDateFormat("yyyy年MM月dd日");
        Date d1=new Date(time);
        return format.format(d1);
    }

}
