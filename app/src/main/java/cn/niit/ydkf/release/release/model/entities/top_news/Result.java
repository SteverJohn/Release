package cn.niit.ydkf.release.release.model.entities.top_news;


import java.util.List;

public class Result {
    private String stat;

    private List<Data> data ;

    public void setStat(String stat){
        this.stat = stat;
    }
    public String getStat(){
        return this.stat;
    }
    public void setData(List<Data> data){
        this.data = data;
    }
    public List<Data> getData(){
        return this.data;
    }

}
