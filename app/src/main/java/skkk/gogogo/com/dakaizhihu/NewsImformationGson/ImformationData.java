package skkk.gogogo.com.dakaizhihu.NewsImformationGson;

/**
 * Created by admin on 2016/7/17.
 */
/*
* 
* 描    述：点赞信息类GSON
* 作    者：ksheng
* 时    间：
*/
public class ImformationData {

    public String long_comments;//长评论
    public String popularity;//点赞
    public String short_comments;//短评论
    public String comments;//评论总数

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getLong_comments() {
        return long_comments;
    }

    public void setLong_comments(String long_comments) {
        this.long_comments = long_comments;
    }

    public String getPopularity() {
        return popularity;
    }

    public void setPopularity(String popularity) {
        this.popularity = popularity;
    }

    public String getShort_comments() {
        return short_comments;
    }

    public void setShort_comments(String short_comments) {
        this.short_comments = short_comments;
    }
}
