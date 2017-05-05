package cn.niit.ydkf.release.release.view.activities;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.niit.ydkf.release.release.R;


public class RevisionsActivity extends BaseActivity {
    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Override
    public int bindLayout() {
        return R.layout.activity_revisions;
    }

    @Override
    public void initView() {
initToolBar();
    }

    @Override
    public void initData() {

    }
    private void initToolBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void onFailureData(String response) {

    }

    @Override
    public void onSuccessData(String response) {

    }
}
