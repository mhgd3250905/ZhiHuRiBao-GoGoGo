package skkk.gogogo.com.dakaizhihu.HomeGson;

import java.util.List;

/**
 * Created by admin on 2016/6/22.
 */
/*
* 
* 描    述：获取到的home的信息类
* 作    者：ksheng
* 时    间：6-22
*/
public class HomeData {
    //获取时间
    public String date;
    //获取今日推送故事
    public List<Story> stories;
    //获取今日推送头条故事
    public List<Story> top_stories;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<Story> getStories() {
        return stories;
    }

    public void setStories(List<Story> stories) {
        this.stories = stories;
    }

    public List<Story> getTop_stories() {
        return top_stories;
    }

    public void setTop_stories(List<Story> top_stories) {
        this.top_stories = top_stories;
    }
}
