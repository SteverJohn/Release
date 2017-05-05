package cn.niit.ydkf.release.release.view.activities;


import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import cn.niit.ydkf.release.release.R;
import cn.niit.ydkf.release.release.adapter.HealthRecyclerViewAdapter;
import cn.niit.ydkf.release.release.adapter.ListItemDecoration;
import cn.niit.ydkf.release.release.model.entities.Contants;
import cn.niit.ydkf.release.release.model.entities.health.HealthBean;
import cn.niit.ydkf.release.release.model.interfaces.HealthService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HealthActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener {
    @Bind(R.id.rv_health)
    RecyclerView rvHealth;
    @Bind(R.id.id_swipe_health)
    SwipeRefreshLayout swipeHealth;
    @Bind(R.id.rs_main_title)
    TextView rsMainTitle;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    private List<HealthBean.ShowapiResBodyBean.PagebeanBean.ContentlistBean> healthList;
    private Handler handler;
    private HealthRecyclerViewAdapter healthRecyclerViewAdapter;
    @Override
    public int bindLayout() {
        return R.layout.activity_health;
    }

    @Override
    public void initView() {
        initToolBar();
        rsMainTitle.setText("健康养生");
        //设置显示方式
        LinearLayoutManager llManager = new LinearLayoutManager(this);
        llManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvHealth.setLayoutManager(llManager);
        //添加分割线
        rvHealth.addItemDecoration(new ListItemDecoration(this, ListItemDecoration.VERTICAL_LIST));
        swipeHealth.setOnRefreshListener(this);
    }

    @Override
    public void initData() {
        healthList = new ArrayList<HealthBean.ShowapiResBodyBean.PagebeanBean.ContentlistBean>();
        handler = new Handler();
        getHealthData(Contants.SHOWAIPURL);
    }

    private void initToolBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void getHealthData(String baseUrl) {
        //创建Retrofit对象
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        HealthService healthService = retrofit.create(HealthService.class);
        Call<HealthBean> call = healthService.getHealth(36010, "28ae277f02634590a2e7340ef2a1ce76");
        call.enqueue(new Callback<HealthBean>() {
            @Override
            public void onResponse(Call<HealthBean> call, Response<HealthBean> response) {
                healthList = response.body().getShowapi_res_body().getPagebean().getContentlist();
                healthRecyclerViewAdapter= new HealthRecyclerViewAdapter(HealthActivity.this,healthList);
                rvHealth.setAdapter(healthRecyclerViewAdapter);
            }

            @Override
            public void onFailure(Call<HealthBean> call, Throwable t) {
                toast(t.getMessage());
            }
        });
    }


    @Override
    public void onFailureData(String response) {

    }

    @Override
    public void onSuccessData(String response) {

    }

    @Override
    public void onRefresh() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                /**
                 *要执行的操作
                 */
                healthList.clear();
                healthRecyclerViewAdapter.notifyDataSetChanged();//必须调用此方法
                getHealthData(Contants.SHOWAIPURL);
                swipeHealth.setRefreshing(false);
            }
        },5000);//5秒后执行Runnable中的run方法
    }
}
