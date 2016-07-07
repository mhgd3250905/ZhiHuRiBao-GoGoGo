package skkk.gogogo.com.dakaizhihu.utils;

/**
 * Created by admin on 2016/7/6.
 */
/*
* 
* 描    述：包含所有的API路径
* 作    者：ksheng
* 时    间：
*/
public class URLStringUtils {
    //闪屏页面图片资源来源
    private static String SPLASHIMAGEURL="http://news-at.zhihu.com/api/4/start-image/1080*1776";
    //主页面最近新闻列表资源来源
    private static String HOMENEWSLISTURL="http://news-at.zhihu.com/api/4/news/latest";
    //内容获取
    private static String NEWSDETAILSURL="http://news-at.zhihu.com/api/4/news/";
    //过往消息列表
    private static String PASTNEWSLISTURL="http://news.at.zhihu.com/api/4/news/before/";

    private static String THEMENEWSLISTURL="http://news-at.zhihu.com/api/4/theme/";

    public static String getTHEMENEWSLISTURL(int id) {
        return THEMENEWSLISTURL+id;
    }

    public static String getHOMENEWSLISTURL() {
        return HOMENEWSLISTURL;
    }

    public static String getNEWSDETAILSURL(String id) {
        return NEWSDETAILSURL+id;
    }

    public static String getPASTNEWSLISTURL(String date) {
        return PASTNEWSLISTURL+date;
    }


    public static String getSPLASHIMAGEURL() {
        return SPLASHIMAGEURL;
    }

}
