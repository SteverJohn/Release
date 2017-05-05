package cn.niit.ydkf.release.release.view.activities;


import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import cn.niit.ydkf.release.release.App;
import cn.niit.ydkf.release.release.R;
import cn.niit.ydkf.release.release.adapter.ListItemDecoration;
import cn.niit.ydkf.release.release.adapter.MusicAdapter;
import cn.niit.ydkf.release.release.model.entities.Contants;
import cn.niit.ydkf.release.release.model.entities.MusicBean;
import cn.niit.ydkf.release.release.model.interfaces.MusicService;
import cn.niit.ydkf.release.release.model.interfaces.CurrentSong;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MusicActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener, MediaPlayer.OnCompletionListener ,CurrentSong{
    @Bind(R.id.rs_main_title)
    TextView rsMainTitle;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.rv_music)
    RecyclerView rvMusic;
    @Bind(R.id.srl_music)
    SwipeRefreshLayout srlMusic;
    @Bind(R.id.pause)
    Button pause;
    @Bind(R.id.stop)
    Button stop;
    @Bind(R.id.current_song)
    TextView currentSong;
    private Handler handler;
    private int topId;
    private List<MusicBean.ShowapiResBodyBean.PagebeanBean.SonglistBeanX> musicList;
    private MusicAdapter musicAdaper;
    private MediaPlayer mp;
    private Boolean flag = true;
    private int pos;

    @Override
    public int bindLayout() {
        return R.layout.activity_music;
    }

    @Override
    public void initView() {
        initToolBar();
        //设置显示方式
        LinearLayoutManager llManager = new LinearLayoutManager(this);
        llManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvMusic.setLayoutManager(llManager);
        //添加分割线
        rvMusic.addItemDecoration(new ListItemDecoration(this, ListItemDecoration.VERTICAL_LIST));
        srlMusic.setOnRefreshListener(this);
    }

    private void initToolBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void initData() {
        Intent intent = getIntent();
        rsMainTitle.setText(intent.getStringExtra("musicTitle"));
        topId = intent.getIntExtra("topId", 0);
        handler = new Handler();
        musicList = new ArrayList<MusicBean.ShowapiResBodyBean.PagebeanBean.SonglistBeanX>();
        mp = new MediaPlayer();
        getMusicData(Contants.SHOWAIPURL, topId);
        pos = App.musicPosition;
        mp.setOnCompletionListener(this);
    }

    private void getMusicData(String baseUrl, int topId) {
        //创建Retrofit对象
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        MusicService musicService = retrofit.create(MusicService.class);
        Call<MusicBean> call = musicService.getMusic(36010, "28ae277f02634590a2e7340ef2a1ce76", topId);
        call.enqueue(new Callback<MusicBean>() {
            @Override
            public void onResponse(Call<MusicBean> call, Response<MusicBean> response) {
                musicList = response.body().getShowapi_res_body().getPagebean().getSonglist();
                //设置Adapter
                musicAdaper = new MusicAdapter(MusicActivity.this, musicList, mp,MusicActivity.this);
                rvMusic.setAdapter(musicAdaper);
                musicAdaper.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<MusicBean> call, Throwable t) {
                toast(t.getMessage());
            }
        });
    }

    @Override
    public void onRefresh() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                /**
                 *要执行的操作
                 */
                musicList.clear();
                musicAdaper.notifyDataSetChanged();//必须调用此方法
                getMusicData(Contants.SHOWAIPURL, topId);
                srlMusic.setRefreshing(false);
            }
        }, 5000);//5秒后执行Runnable中的run方法
    }


    @OnClick({R.id.pause, R.id.stop})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.pause:
                if (flag) {
                    mp.pause();
                    ((Button) findViewById(R.id.pause)).setText("继续播放");
                    flag = false;
                } else {
                    mp.start();
                    ((Button) findViewById(R.id.pause)).setText("暂停播放");
                    flag = true;
                }
                break;
            case R.id.stop:
                mp.stop();
                break;
        }
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        mp.stop();
        mp.reset();
        pos++;
        if (pos == musicList.size()) {
            pos = 0;
        }
        try {
            mp.setDataSource(musicList.get(pos).getUrl());
            mp.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
        mp.start();
        currentSong.setText("当前正在播放"+musicList.get(pos).getSingername()+"的"+musicList.get(pos).getSongname());
    }

    @Override
    protected void onDestroy() {
        mp.release();
        super.onDestroy();
    }

    @Override
    public void getCurrentSongName(String SingerName,String currentSongName) {
        currentSong.setVisibility(View.VISIBLE);
        currentSong.setText("当前正在播放"+SingerName+"的"+currentSongName);
    }

    @Override
    public void onFailureData(String response) {

    }

    @Override
    public void onSuccessData(String response) {

    }
}
