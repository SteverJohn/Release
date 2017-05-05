package cn.niit.ydkf.release.release.view.activities;


import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.TextView;

import butterknife.Bind;
import cn.niit.ydkf.release.release.R;

public class ChoinessDetailActivity extends BaseActivity {
    @Bind(R.id.rs_main_title)
    TextView rsMainTitle;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.wv_choiness_details)
    WebView wvChoinessDetails;
    @Bind(R.id.rec_comment)
    RecyclerView recComment;

    @Override
    public int bindLayout() {
        return R.layout.activity_choiness_detail;
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
        rsMainTitle.setText("精选生活");
        Intent intent = getIntent();
        final String url=intent.getStringExtra("url");
        WebSettings webSettings = wvChoinessDetails.getSettings();
        webSettings.setJavaScriptEnabled(true);
        showProgressDialog("正在加载中");
        wvChoinessDetails.loadUrl(url);
        dismisProgressDialog();
    }

    @Override
    public void onFailureData(String response) {

    }

    @Override
    public void onSuccessData(String response) {

    }
}
