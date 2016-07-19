package skkk.gogogo.com.dakaizhihu.KanzhihuQuestionListGson;

import java.util.List;

/**
 * Created by admin on 2016/7/19.
 */
/*
* 
* 描    述：看知乎所有问题列表数据
* 作    者：ksheng
* 时    间：
*/
public class QuestionListData {
    public String error;
    public int count;
    public List<Answer> answers;

    public List<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(List<Answer> answers) {
        this.answers = answers;
    }

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
}
