package cn.niit.ydkf.release.release.view.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.fastjson.JSON;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.niit.ydkf.release.release.App;
import cn.niit.ydkf.release.release.R;
import cn.niit.ydkf.release.release.adapter.ChoicenessRecyclerViewAdapter;
import cn.niit.ydkf.release.release.adapter.ListItemDecoration;
import cn.niit.ydkf.release.release.adapter.NewsRecyclerViewAdapter;
import cn.niit.ydkf.release.release.model.entities.Contants;
import cn.niit.ydkf.release.release.model.entities.choiceness.JsonsRootBean;
import cn.niit.ydkf.release.release.model.entities.choiceness.Newslist;
import cn.niit.ydkf.release.release.model.entities.top_news.Data;
import cn.niit.ydkf.release.release.model.entities.top_news.NewsBean;

public class ChoicenessFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener{
    private static volatile ChoicenessFragment choicenessFragment;
    @Bind(R.id.choiceness_recyclerView)
    RecyclerView choiceness_recyclerView;
    @Bind(R.id.sp_choiceness)
    SwipeRefreshLayout sp_choiceness;
    private String url;
    private HashMap<String, String> map;
    private Handler handler;
    private List<Newslist> choicenessBeanList;
    private String mResponse;
    private JsonsRootBean jsonsRootBean;
    private ChoicenessRecyclerViewAdapter choicenessRecyclerViewAdapter;

    public static ChoicenessFragment getInstance() {
        if (choicenessFragment == null) {
            synchronized (HomeFragment.class) {
                choicenessFragment = new ChoicenessFragment();
            }
        }
        return choicenessFragment;
    }

    @Override
    public int bindLayout() {
        return R.layout.frag_choiceness;
    }

    @Override
    public void initView(View view) {
        //设置显示方式
        LinearLayoutManager llManager = new LinearLayoutManager(getActivity());
        llManager.setOrientation(LinearLayoutManager.VERTICAL);
        choiceness_recyclerView.setLayoutManager(llManager);
        //添加分割线
        choiceness_recyclerView.addItemDecoration(new ListItemDecoration(getActivity(),ListItemDecoration.VERTICAL_LIST));
        sp_choiceness.setOnRefreshListener(this);
    }

    @Override
    public void initData(View view) {
        url= Contants.CHOICENESSURL+"showapi_appid=36010&num=10&rand=1";
        map=new HashMap<String,String>();
        map.put("showapi_sign","28ae277f02634590a2e7340ef2a1ce76");
        handler = new Handler();
        choicenessBeanList=new ArrayList<Newslist>();
        doPost(url,map);
    }


    @Override
    public void onFailureData(String response) {

    }

    @Override
    public void onSuccessData(String response) {
        jsonsRootBean= JSON.parseObject(response, JsonsRootBean.class);
        choicenessBeanList = jsonsRootBean.getShowapi_res_body().getNewslist();
        //设置Adapter
        choicenessRecyclerViewAdapter = new ChoicenessRecyclerViewAdapter(getActivity(),choicenessBeanList);
        choiceness_recyclerView.setAdapter(choicenessRecyclerViewAdapter);
        choicenessRecyclerViewAdapter.notifyDataSetChanged();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onRefresh() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                /**
                 *要执行的操作
                 */
                choicenessBeanList.clear();
                choicenessRecyclerViewAdapter.notifyDataSetChanged();//必须调用此方法
                doPost(url,map);
                sp_choiceness.setRefreshing(false);
            }
        },5000);//5秒后执行Runnable中的run方法
    }
}
