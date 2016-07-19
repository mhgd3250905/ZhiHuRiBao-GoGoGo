package skkk.gogogo.com.dakaizhihu.KanzhihuQuestionListGson;

/**
 * Created by admin on 2016/7/19.
 */
/*
* 
* 描    述：
* 作    者：ksheng
* 时    间：
*/
public class Answer {
    public String title;
    public String time;
    public String summary;
    public String questionid;
    public String answerid;
    public String authorname;
    public String anthorhash;
    public String avatar;
    public String vote;

    public String getAnswerid() {
        return answerid;
    }

    public void setAnswerid(String answerid) {
        this.answerid = answerid;
    }

    public String getAnthorhash() {
        return anthorhash;
    }

    public void setAnthorhash(String anthorhash) {
        this.anthorhash = anthorhash;
    }

    public String getAuthorname() {
        return authorname;
    }

    public void setAuthorname(String authorname) {
        this.authorname = authorname;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getQuestionid() {
        return questionid;
    }

    public void setQuestionid(String questionid) {
        this.questionid = questionid;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getVote() {
        return vote;
    }

    public void setVote(String vote) {
        this.vote = vote;
    }
}
