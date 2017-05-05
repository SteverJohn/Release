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

public class NewsDetailActivity extends BaseActivity{
    @Bind(R.id.wv_details)
    public WebView wv_details;
    @Bind(R.id.rec_comment)
    public RecyclerView rec_comment;
    @Bind(R.id.toolbar)
    public Toolbar toolbar;
    @Bind(R.id.rs_main_title)
    public TextView mTitle;

    @Override
    public int bindLayout() {
        return R.layout.activity_news_detail;
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
        mTitle.setText("生活新闻");
        Intent intent = getIntent();
        final String url=intent.getStringExtra("url");
        WebSettings webSettings = wv_details.getSettings();
        webSettings.setJavaScriptEnabled(true);
        showProgressDialog("正在加载中");
        wv_details.loadUrl(url);
        dismisProgressDialog();
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && wv_details.canGoBack()) {
            wv_details.goBack();
            return true;
        }
        finish();
        return true;
    }

    @Override
    public void onFailureData(String response) {

    }

    @Override
    public void onSuccessData(String response) {

    }
}
