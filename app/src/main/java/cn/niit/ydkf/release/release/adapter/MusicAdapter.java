package cn.niit.ydkf.release.release.adapter;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import java.io.IOException;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.niit.ydkf.release.release.App;
import cn.niit.ydkf.release.release.R;
import cn.niit.ydkf.release.release.model.entities.MusicBean;
import cn.niit.ydkf.release.release.model.interfaces.CurrentSong;


public class MusicAdapter extends RecyclerView.Adapter<MusicAdapter.MyViewHolder>  {
    private static Context mContext;
    private static List<MusicBean.ShowapiResBodyBean.PagebeanBean.SonglistBeanX> mListData;
    private static MediaPlayer mp;
    private static CurrentSong currentSong;
    public MusicAdapter(Context context, List<MusicBean.ShowapiResBodyBean.PagebeanBean.SonglistBeanX> list, MediaPlayer mp,CurrentSong currentSong){
            this.mContext=context;
            this.mListData=list;
            this.mp=mp;
            this.currentSong=currentSong;
    }

    @Override
    public MusicAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_music, parent, false);
        MusicAdapter.MyViewHolder mHolder = new MusicAdapter.MyViewHolder(view);
        return mHolder;
    }

    @Override
    public void onBindViewHolder(MusicAdapter.MyViewHolder holder, int position) {
            holder.musicName.setText(mListData.get(position).getSongname());
            holder.author.setText(mListData.get(position).getSingername());
            Uri musicUri = Uri.parse(mListData.get(position).getAlbumpic_small());
            holder.sdvMusic.setImageURI(musicUri);
    }

    @Override
    public int getItemCount() {
        return mListData.size();
    }
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.music_name)
        TextView musicName;
        @Bind(R.id.author)
        TextView author;
        @Bind(R.id.sdv_music)
        SimpleDraweeView sdvMusic;
        public MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mp != null) {
                        mp.reset();
                        try {
                            App.musicPosition=getAdapterPosition();
                            mp.setDataSource(mListData.get(getAdapterPosition()).getUrl());
                            mp.prepare();
                            mp.start();
                            currentSong.getCurrentSongName(mListData.get(getAdapterPosition()).getSingername(),mListData.get(getAdapterPosition()).getSongname());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
        }
    }
}
