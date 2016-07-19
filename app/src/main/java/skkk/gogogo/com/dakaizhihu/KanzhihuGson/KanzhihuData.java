package skkk.gogogo.com.dakaizhihu.KanzhihuGson;

import java.util.List;

/**
 * Created by admin on 2016/7/19.
 */
/*
* 
* 描    述：获取看知乎所有的data
* 作    者：ksheng
* 时    间：7/19
*/
public class KanzhihuData {
    public String error;
    public int count;
    public List<Kanzhihu> posts;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public List<Kanzhihu> getPosts() {
        return posts;
    }

    public void setPosts(List<Kanzhihu> posts) {
        this.posts = posts;
    }
}
