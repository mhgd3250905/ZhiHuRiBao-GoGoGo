package skkk.gogogo.com.dakaizhihu.ColumnData;

import java.util.List;

/**
 * Created by admin on 2016/7/17.
 */
/*
* 
* 描    述：
* 作    者：ksheng
* 时    间：
*/
public class ColumnStory {
    public List<String> images;
    public String date;
    public String display_date;
    public int id;
    public String title;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDisplay_date() {
        return display_date;
    }

    public void setDisplay_date(String display_date) {
        this.display_date = display_date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
