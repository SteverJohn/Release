package cn.niit.ydkf.release.release.view.activities;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.niit.ydkf.release.release.R;


public class AboutActivity extends BaseActivity {
    @Bind(R.id.rl_function_introduction)
    RelativeLayout rlFunctionIntroduction;
    @Bind(R.id.rl_revisions)
    RelativeLayout rlRevisions;
    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Override
    public int bindLayout() {
        return R.layout.activity_about;
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

    @OnClick({R.id.rl_function_introduction, R.id.rl_revisions})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_function_introduction:
                goActivity(FunctionIntroductionActivity.class);
                break;
            case R.id.rl_revisions:
                goActivity(RevisionsActivity.class);
                break;
        }
    }

    @Override
    public void onFailureData(String response) {

    }

    @Override
    public void onSuccessData(String response) {

    }
}
