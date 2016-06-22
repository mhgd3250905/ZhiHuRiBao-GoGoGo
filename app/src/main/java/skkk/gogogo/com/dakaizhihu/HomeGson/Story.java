package skkk.gogogo.com.dakaizhihu.HomeGson;

import java.util.List;

/**
 * Created by admin on 2016/6/22.
 */
/*
* 
* 描    述：今日推送故事类
* 作    者：ksheng
* 时    间：6-22
*/
public class Story {
    public List<String> images;
    public int type;
    public int id;
    public String ga_prefix;
    public String title;

    public String getGa_prefix() {
        return ga_prefix;
    }

    public void setGa_prefix(String ga_prefix) {
        this.ga_prefix = ga_prefix;
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

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
