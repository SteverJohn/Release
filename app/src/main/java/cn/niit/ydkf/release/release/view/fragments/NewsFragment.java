package cn.niit.ydkf.release.release.view.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.alibaba.fastjson.JSON;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import cn.niit.ydkf.release.release.R;
import cn.niit.ydkf.release.release.adapter.ListItemDecoration;
import cn.niit.ydkf.release.release.adapter.NewsRecyclerViewAdapter;
import cn.niit.ydkf.release.release.model.entities.Contants;
import cn.niit.ydkf.release.release.model.entities.top_news.Data;
import cn.niit.ydkf.release.release.model.entities.top_news.NewsBean;


public class NewsFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener  {
    @Bind(R.id.news_recyclerView)
    public RecyclerView newsRecyclerView;
    @Bind(R.id.id_swipe_ly)
    public SwipeRefreshLayout swipeRefreshLayout;
    private NewsRecyclerViewAdapter newsRecyclerViewAdapter;
    private List<Data> newsBeanList;
    private NewsBean newsBean;
    private String mResponse;
    private String url;
    private Map<String, String> map;
    private Handler handler;

    @Override
    public int bindLayout() {
        return R.layout.frag_news;
    }

    @Override
    public void initView(View view) {
        //设置显示方式
        LinearLayoutManager llManager = new LinearLayoutManager(getActivity());
        llManager.setOrientation(LinearLayoutManager.VERTICAL);
        newsRecyclerView.setLayoutManager(llManager);
        //添加分割线
        newsRecyclerView.addItemDecoration(new ListItemDecoration(getActivity(),ListItemDecoration.VERTICAL_LIST));
        swipeRefreshLayout.setOnRefreshListener(this);

    }

    @Override
    public void initData(View view) {
        Bundle bundle=getArguments();
        String title= bundle.getString("title");
        int position=bundle.getInt("position");
        title = getType(title, position);
        url= Contants.NEWSURL+"type="+title;
        map=new HashMap<String,String>();
        map.put("key","c2a7af95bfe678624efbba6ca498f7fb");
        handler = new Handler();
        newsBeanList=new ArrayList<Data>();
        doPost(url,map);
    }

    private String getType(String title, int position) {
        switch (position){
            case Contants.RECOMMEND:
                title="top";
                break;
            case Contants.SOCIAL:
                title="shehui";
                break;
            case Contants.DOMESTIC:
                title="guonei";
                break;
            case Contants.INTERNATIONAL:
                title="guoji";
                break;
            case Contants.AMUSEMENT:
                title="yule";
                break;
            case Contants.PE:
                title="tiyu";
                break;
            case Contants.MILITARY:
                title="junshi";
                break;
            case Contants.SAT:
                title="keji";
                break;
            case Contants.FAE:
                title="caijing";
                break;
            case Contants.FASHION:
                title="shishang";
                break;
        }
        return title;
    }

    @Override
    public void onFailureData(String response) {
        toast(response.toString());
    }

    @Override
    public void onSuccessData(String response) {
        newsBean= JSON.parseObject(response, NewsBean.class);
        newsBeanList=newsBean.getResult().getData();
        //设置Adapter
        newsRecyclerViewAdapter = new NewsRecyclerViewAdapter(getActivity(),newsBeanList);
        newsRecyclerView.setAdapter(newsRecyclerViewAdapter);
        newsRecyclerViewAdapter.notifyDataSetChanged();
    }

    @Override
    public void onRefresh() {

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                /**
                 *要执行的操作
                 */
                newsBeanList.clear();
                newsRecyclerViewAdapter.notifyDataSetChanged();//必须调用此方法
                doPost(url,map);
                swipeRefreshLayout.setRefreshing(false);
            }
        },5000);//5秒后执行Runnable中的run方法
    }
}
