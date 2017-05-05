package cn.niit.ydkf.release.release.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.niit.ydkf.release.release.R;
import cn.niit.ydkf.release.release.model.entities.VideoBean;
import cn.niit.ydkf.release.release.view.activities.VideoPlayActivity;


public class VideoAdapter extends RecyclerView.Adapter <VideoAdapter.VideoViewHolder>{
    private static Context mContext;
    private static List<VideoBean.ShowapiResBodyBean.PagebeanBean.ContentlistBean> videoList;
    public VideoAdapter(Context mContext,List<VideoBean.ShowapiResBodyBean.PagebeanBean.ContentlistBean> videoList){
        this.mContext=mContext;
        this.videoList=videoList;
    }
    @Override
    public VideoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_video, parent, false);
        VideoAdapter.VideoViewHolder mHolder = new VideoAdapter.VideoViewHolder(view);
        return mHolder;
    }

    @Override
    public void onBindViewHolder(VideoAdapter.VideoViewHolder holder, int position) {
        holder.sdvVideo.setImageURI(Uri.parse(videoList.get(position).getProfile_image()));
        holder.tvVideoTitle.setText(videoList.get(position).getText());
        holder.tvName.setText(videoList.get(position).getName());
        holder.tvVideoTime.setText(videoList.get(position).getCreate_time());
    }


    @Override
    public int getItemCount() {
        return videoList.size();
    }
    public static class VideoViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.sdv_video)
        public SimpleDraweeView sdvVideo;
        @Bind(R.id.tv__video_title)
        public TextView tvVideoTitle;
        @Bind(R.id.tv_video_time)
        public TextView tvVideoTime;
        @Bind(R.id.tv_name)
        public TextView tvName;

        public VideoViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                Intent intent=new Intent(mContext, VideoPlayActivity.class);
                intent.putExtra("videoUrl",videoList.get(getAdapterPosition()).getVideo_uri());
                mContext.startActivity(intent);
                }
            });
        }
    }
}
