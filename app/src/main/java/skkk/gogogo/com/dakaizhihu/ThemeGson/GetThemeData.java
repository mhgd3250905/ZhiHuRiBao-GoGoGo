package skkk.gogogo.com.dakaizhihu.ThemeGson;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2016/7/17.
 */
/*
* 
* 描    述：获取主题日报列表的Gson
* 作    者：ksheng
* 时    间：
*/
public class GetThemeData {
    public String limit;
    public ArrayList<String> subscribed;
    public List<Other> others;

    public String getLimit() {
        return limit;
    }

    public void setLimit(String limit) {
        this.limit = limit;
    }

    public List<Other> getOthers() {
        return others;
    }

    public void setOthers(List<Other> others) {
        this.others = others;
    }

    public ArrayList<String> getSubscribed() {
        return subscribed;
    }

    public void setSubscribed(ArrayList<String> subscribed) {
        this.subscribed = subscribed;
    }
}
