package cn.niit.ydkf.release.release.view.activities;


import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import butterknife.Bind;
import cn.niit.ydkf.release.release.R;
import cn.niit.ydkf.release.release.adapter.HotListAdapter;

public class HotListActivity extends BaseActivity implements AdapterView.OnItemClickListener{
    @Bind(R.id.rs_main_title)
    TextView rsMainTitle;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.gv_hot_list)
    GridView gvHotList;
    private String[] text = {"内地","热歌","港台","欧美"};
    private int[] topId={5,26,6,3};
    private int[] color={Color.parseColor("#FFD700"),Color.parseColor("#FF0000"),Color.parseColor("#0000FF"),Color.parseColor("#008000")};
    @Override
    public int bindLayout() {
        return R.layout.activity_hot_list;
    }

    @Override
    public void initView() {
        initToolBar();
        gvHotList.setOnItemClickListener(this);
    }

    private void initToolBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void initData() {
        rsMainTitle.setText("热门榜单");
        gvHotList.setAdapter(new HotListAdapter(this,color,text));
    }



    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent=new Intent(this,MusicActivity.class);
        intent.putExtra("musicTitle",text[position]);
        intent.putExtra("topId",topId[position]);
        startActivity(intent);
    }

    @Override
    public void onFailureData(String response) {

    }

    @Override
    public void onSuccessData(String response) {

    }
}
