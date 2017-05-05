package cn.niit.ydkf.release.release.view.activities;

import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import cn.niit.ydkf.release.release.R;
import cn.niit.ydkf.release.release.adapter.JestRecyclerViewAdapter;
import cn.niit.ydkf.release.release.adapter.ListItemDecoration;
import cn.niit.ydkf.release.release.adapter.NewsRecyclerViewAdapter;
import cn.niit.ydkf.release.release.model.entities.Contants;
import cn.niit.ydkf.release.release.model.entities.JestBean;

public class JestActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener{
    @Bind(R.id.rs_main_title)
    TextView rsMainTitle;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.rv_jest)
    RecyclerView rvJest;
    @Bind(R.id.srl_jest)
    SwipeRefreshLayout srlJest;
    private List<JestBean.ShowapiResBodyBean.ContentlistBean> jestList;
    private Handler handler;
    private String jesturl;
    private HashMap<String, String> keyMap;
    private JestRecyclerViewAdapter jestRecyclerViewAdapter;


    @Override
    public int bindLayout() {
        return R.layout.activity_jest;
    }

    @Override
    public void initView() {
        initToolBar();
        rsMainTitle.setText("动态搞笑");
        //设置显示方式
        LinearLayoutManager llManager = new LinearLayoutManager(this);
        llManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvJest.setLayoutManager(llManager);
        //添加分割线
        rvJest.addItemDecoration(new ListItemDecoration(this, ListItemDecoration.VERTICAL_LIST));
        srlJest.setOnRefreshListener(this);
    }

    private void initToolBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void initData() {
        handler = new Handler();
        jestList = new ArrayList<>();
        getJestData();
    }

    public void getJestData() {
        jesturl = Contants.JESTURL + "showapi_appid=36010" + "&page=1" + "&maxResult=20";
        keyMap = new HashMap<>();
        keyMap.put("showapi_sign", "28ae277f02634590a2e7340ef2a1ce76");
        doPost(jesturl, keyMap);
    }

    @Override
    public void onFailureData(String response) {

    }

    @Override
    public void onSuccessData(String response) {
        Gson gson = new Gson();
        JestBean jestBean = gson.fromJson(response, JestBean.class);
        jestList = jestBean.getShowapi_res_body().getContentlist();
        //设置Adapter
        jestRecyclerViewAdapter = new JestRecyclerViewAdapter(this,jestList);
        rvJest.setAdapter(jestRecyclerViewAdapter);
        jestRecyclerViewAdapter.notifyDataSetChanged();
    }

    @Override
    public void onRefresh() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                /**
                 *要执行的操作
                 */
                jestList.clear();
                jestRecyclerViewAdapter.notifyDataSetChanged();//必须调用此方法
                doPost(jesturl, keyMap);
                srlJest.setRefreshing(false);
            }
        },5000);//5秒后执行Runnable中的run方法
    }
}
