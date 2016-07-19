package skkk.gogogo.com.dakaizhihu.KanzhihuGson;

/**
 * Created by admin on 2016/7/19.
 */
/*
* 
* 描    述：看知乎的每隔条目类
* 作    者：ksheng
* 时    间：
*/
public class Kanzhihu {
    public String id;
    public String date;
    public String name;
    public String pic;
    public String publishtime;
    public String count;
    public String excerpt;

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getExcerpt() {
        return excerpt;
    }

    public void setExcerpt(String excerpt) {
        this.excerpt = excerpt;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getPublishtime() {
        return publishtime;
    }

    public void setPublishtime(String publishtime) {
        this.publishtime = publishtime;
    }
}
