package cn.niit.ydkf.release.release.view.activities;

import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import butterknife.Bind;
import cn.niit.ydkf.release.release.R;


public class JokesDetailActivity extends BaseActivity {

    @Bind(R.id.wv_jokes)
    WebView wvJokes;
    @Bind(R.id.rs_main_title)
    TextView rsMainTitle;
    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Override
    public int bindLayout() {
        return R.layout.activity_jokes_detail;
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
        rsMainTitle.setText("内涵段子");
        WebSettings webSettings = wvJokes.getSettings();
        webSettings.setJavaScriptEnabled(true);
        wvJokes.loadUrl(getIntent().getStringExtra("jokes"));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        this.finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        this.finish();
    }

    @Override
    public void onFailureData(String response) {

    }

    @Override
    public void onSuccessData(String response) {

    }
}
