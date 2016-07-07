package skkk.gogogo.com.dakaizhihu.ThemeGson;

import java.util.List;

/**
 * Created by admin on 2016/7/7.
 */
/*
* 
* 描    述：包含所有主题日报列表的类
* 作    者：ksheng
* 时    间：
*/
public class ThemeNewsListData {
    public List<ThemeStory> stories;
    public String description;
    public String background;
    public int color;
    public String name;
    public String image;
    public List<Editor> editors;
    public String image_source;

    public String getBackground() {
        return background;
    }

    public void setBackground(String background) {
        this.background = background;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Editor> getEditors() {
        return editors;
    }

    public void setEditors(List<Editor> editors) {
        this.editors = editors;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getImage_source() {
        return image_source;
    }

    public void setImage_source(String image_source) {
        this.image_source = image_source;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ThemeStory> getStories() {
        return stories;
    }

    public void setStories(List<ThemeStory> stories) {
        this.stories = stories;
    }
}
