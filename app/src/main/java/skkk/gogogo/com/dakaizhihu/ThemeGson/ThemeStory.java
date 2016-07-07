package skkk.gogogo.com.dakaizhihu.ThemeGson;

import java.util.List;

/**
 * Created by admin on 2016/7/7.
 */
/*
* 
* 描    述：
* 作    者：ksheng
* 时    间：
*/
public class ThemeStory {
    public List<String> images;
    public int type;
    public int id;
    public String title;

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

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
