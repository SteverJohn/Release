package cn.niit.ydkf.release.release.model.entities.choiceness;


import java.util.Date;

public class Newslist {
    private String title;
    private String picUrl;
    private String description;
    private Date ctime;
    private String url;
    public void setTitle(String title) {
        this.title = title;
    }
    public String getTitle() {
        return title;
    }

    public void setPicurl(String picUrl) {
        this.picUrl = picUrl;
    }
    public String getPicurl() {
        return picUrl;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    public String getDescription() {
        return description;
    }

    public void setCtime(Date ctime) {
        this.ctime = ctime;
    }
    public Date getCtime() {
        return ctime;
    }

    public void setUrl(String url) {
        this.url = url;
    }
    public String getUrl() {
        return url;
    }
}
