package cn.niit.ydkf.release.release.model.entities.choiceness;

import java.util.List;

public class ShowapiResBody {
    private List<Newslist> newslist;
    private int code;
    private String msg;
    public void setNewslist(List<Newslist> newslist) {
        this.newslist = newslist;
    }
    public List<Newslist> getNewslist() {
        return newslist;
    }

    public void setCode(int code) {
        this.code = code;
    }
    public int getCode() {
        return code;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
    public String getMsg() {
        return msg;
    }

}
