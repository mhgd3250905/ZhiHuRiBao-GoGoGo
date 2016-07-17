package skkk.gogogo.com.dakaizhihu.ColumnData;

import java.util.List;

/**
 * Created by admin on 2016/7/17.
 */
/*
* 
* 描    述：获取每个知乎专栏的推送列表的Gson
* 作    者：ksheng
* 时    间：
*/
public class ColumnListData {
    public int timestamp;
    public List<ColumnStory> stories;

    public List<ColumnStory> getStories() {
        return stories;
    }

    public void setStories(List<ColumnStory> stories) {
        this.stories = stories;
    }

    public int getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(int timestamp) {
        this.timestamp = timestamp;
    }
}
