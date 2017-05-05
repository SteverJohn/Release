package cn.niit.ydkf.release.release.view.activities;


import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.webkit.WebView;
import android.widget.TextView;

import butterknife.Bind;
import cn.niit.ydkf.release.release.R;

public class LifeActivity extends BaseActivity {
    @Bind(R.id.rs_main_title)
    TextView rsMainTitle;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.rv_life)
    RecyclerView rvLife;
    @Override
    public int bindLayout() {
        return R.layout.activity_life;
    }

    @Override
    public void initView() {
        initToolBar();
    }

    private void initToolBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void initData() {
        Intent intent = getIntent();
        String lifeTitle=intent.getStringExtra("lifeTitle");
        rsMainTitle.setText(lifeTitle);
    }

    @Override
    public void onFailureData(String response) {

    }

    @Override
    public void onSuccessData(String response) {

    }
}
