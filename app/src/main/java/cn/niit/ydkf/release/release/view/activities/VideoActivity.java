package cn.niit.ydkf.release.release.view.activities;


import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import cn.niit.ydkf.release.release.R;
import cn.niit.ydkf.release.release.adapter.ListItemDecoration;
import cn.niit.ydkf.release.release.adapter.VideoAdapter;
import cn.niit.ydkf.release.release.model.entities.Contants;
import cn.niit.ydkf.release.release.model.entities.VideoBean;

public class VideoActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener{
    private static final int ROWNUM = 2;
    @Bind(R.id.rv_video)
    RecyclerView rvVideo;
    @Bind(R.id.srl_video)
    SwipeRefreshLayout srlVideo;
    @Bind(R.id.rs_main_title)
    TextView rsMainTitle;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    private List<VideoBean.ShowapiResBodyBean.PagebeanBean.ContentlistBean> videoList;
    private Handler handler;
    private String videoUrl;
    private HashMap<String, String> keyMap;
    private VideoAdapter videoAdapter;


    @Override
    public int bindLayout() {
        return R.layout.activity_video;
    }

    @Override
    public void initView() {
        initToolBar();
        //设置显示方式
        GridLayoutManager glManager = new GridLayoutManager(this, ROWNUM);
        glManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvVideo.setLayoutManager(glManager);
        //添加分割线
        rvVideo.addItemDecoration(new ListItemDecoration(this, ListItemDecoration.HORIZONTAL_LIST));
        srlVideo.setOnRefreshListener(this);
    }

    private void initToolBar() {
        setSupportActionBar(toolbar);
        rsMainTitle.setText("搞笑视频");
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void initData() {
        videoList = new ArrayList<>();
        handler = new Handler();
        getVideoData();
    }

    private void getVideoData() {
        videoUrl = Contants.VIDEOURL + "showapi_appid=36010" + "&type=41" + "&page=1";
        keyMap = new HashMap<>();
        keyMap.put("showapi_sign", "28ae277f02634590a2e7340ef2a1ce76");
        doPost(videoUrl, keyMap);
    }

    @Override
    public void onFailureData(String response) {
        toast(response.toString());
    }

    @Override
    public void onSuccessData(String response) {
        Gson gson = new Gson();
        VideoBean videoBean = gson.fromJson(response, VideoBean.class);
        videoList = videoBean.getShowapi_res_body().getPagebean().getContentlist();
        //设置Adapter
        videoAdapter = new VideoAdapter(this,videoList);
        rvVideo.setAdapter(videoAdapter);
        videoAdapter.notifyDataSetChanged();
    }


    @Override
    public void onRefresh() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                /**
                 *要执行的操作
                 */
                videoList.clear();
                videoAdapter.notifyDataSetChanged();//必须调用此方法
                doPost(videoUrl, keyMap);
                srlVideo.setRefreshing(false);
            }
        },5000);//5秒后执行Runnable中的run方法
    }
}
