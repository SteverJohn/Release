package cn.niit.ydkf.release.release.view.activities;


import android.net.Uri;
import android.view.KeyEvent;
import android.widget.MediaController;
import android.widget.VideoView;

import butterknife.Bind;
import cn.niit.ydkf.release.release.R;

public class VideoPlayActivity extends BaseActivity {
    @Bind(R.id.video_play)
    VideoView videoPlay;

    @Override
    public void onFailureData(String response) {

    }

    @Override
    public void onSuccessData(String response) {

    }

    @Override
    public int bindLayout() {
        return R.layout.activity_video_play;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {
        Uri uri = Uri.parse( getIntent().getStringExtra("videoUrl"));
        videoPlay.setMediaController(new MediaController(this));
        videoPlay.setVideoURI(uri);
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
           this.finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
