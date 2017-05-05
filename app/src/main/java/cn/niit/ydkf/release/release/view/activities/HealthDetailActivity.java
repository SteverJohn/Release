package cn.niit.ydkf.release.release.view.activities;


import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.webkit.WebView;
import android.widget.TextView;

import java.util.HashMap;

import butterknife.Bind;
import cn.niit.ydkf.release.release.R;
import cn.niit.ydkf.release.release.model.entities.Contants;

public class HealthDetailActivity extends BaseActivity {
    @Bind(R.id.wv_health)
    WebView wvHealth;
    @Bind(R.id.rs_main_title)
    TextView rsMainTitle;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    private HashMap<String, String> keyMap;

    @Override
    public int bindLayout() {
        return R.layout.activity_detail_health;
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
        rsMainTitle.setText("健康养生");
        keyMap = new HashMap<>();
        keyMap.put("showapi_sign", "28ae277f02634590a2e7340ef2a1ce76");
        doPost(Contants.SHOWAIPURL+"96-36?"+"showapi_appid=36010"+"&id="+getIntent().getStringExtra("id"),keyMap);

    }

    @Override
    public void onFailureData(String response) {

    }

    @Override
    public void onSuccessData(String response) {
        toast(response);
    }

}
