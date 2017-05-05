package cn.niit.ydkf.release.release.view.activities;


import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import cn.niit.ydkf.release.release.R;
import cn.niit.ydkf.release.release.adapter.JokesAdapter;
import cn.niit.ydkf.release.release.model.entities.Contants;
import cn.niit.ydkf.release.release.model.entities.JokesBean;
import cn.niit.ydkf.release.release.model.interfaces.JokesService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class JokesActivity extends BaseActivity implements AdapterView.OnItemClickListener {
    @Bind(R.id.lv_jokes)
    ListView lvJokes;
    @Bind(R.id.rs_main_title)
    TextView rsMainTitle;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
private List<JokesBean.ShowapiResBodyBean.PagebeanBean.ContentlistBean> jokesBeanList;
    @Override
    public int bindLayout() {
        return R.layout.activity_jokes;
    }

    @Override
    public void initView() {
        initToolBar();
        lvJokes.setOnItemClickListener(this);
    }

    @Override
    public void initData() {
        rsMainTitle.setText("内涵段子");
        jokesBeanList=new ArrayList<JokesBean.ShowapiResBodyBean.PagebeanBean.ContentlistBean>();
        getJokesData(Contants.SHOWAIPURL, 29, 1);
    }

    private void initToolBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void getJokesData(String baseUrl, int type, int page) {
        //创建Retrofit对象
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        JokesService jokesService = retrofit.create(JokesService.class);
        Call<JokesBean> call = jokesService.getJokes(36010, "28ae277f02634590a2e7340ef2a1ce76", type, page);
        call.enqueue(new Callback<JokesBean>() {
            @Override
            public void onResponse(Call<JokesBean> call, Response<JokesBean> response) {
                    jokesBeanList=response.body().getShowapi_res_body().getPagebean().getContentlist();
                    lvJokes.setAdapter(new JokesAdapter(JokesActivity.this,jokesBeanList));
            }

            @Override
            public void onFailure(Call<JokesBean> call, Throwable t) {
                toast(t.getMessage());
            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent=new Intent(this,JokesDetailActivity.class);
        intent.putExtra("jokes",jokesBeanList.get(position).getWeixin_url());
        startActivity(intent);
    }

    @Override
    public void onFailureData(String response) {

    }

    @Override
    public void onSuccessData(String response) {

    }
}
