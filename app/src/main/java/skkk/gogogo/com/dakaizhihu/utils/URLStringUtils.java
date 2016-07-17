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
    //主题日报列表
    private static String THEMENEWSLISTURL="http://news-at.zhihu.com/api/4/theme/";
    //获取文章其他信息 点赞评论。。。
    private static String NEWSIMFORMATIONS="http://news-at.zhihu.com/api/4/story-extra/";
    //获取专栏列表
    private static String IMFORMATIONLIST="http://news-at.zhihu.com/api/3/sections";
    //获取主题日报名称列表
    private static String GETHOWMANYTHEMES="http://news-at.zhihu.com/api/4/themes";
    //获取专栏具体信息条目
    private static String GETCOLUMNLIST="http://news-at.zhihu.com/api/3/section/";

    public static String getGETCOLUMNLIST(int id) {
        return GETCOLUMNLIST+id;
    }

    public static String getGETHOWMANYTHEMES() {
        return GETHOWMANYTHEMES;
    }

    public static String getIMFORMATIONLIST() {
        return IMFORMATIONLIST;
    }

    public static String getNEWSIMFORMATIONS(int id) {
        return NEWSIMFORMATIONS+id;
    }

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
