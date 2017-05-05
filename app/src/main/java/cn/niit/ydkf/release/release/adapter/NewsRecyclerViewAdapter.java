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
import cn.niit.ydkf.release.release.model.entities.top_news.Data;
import cn.niit.ydkf.release.release.view.activities.NewsDetailActivity;


public class NewsRecyclerViewAdapter extends RecyclerView.Adapter<NewsRecyclerViewAdapter.MyViewHolder> {
    private static Context mContext;
    private static List<Data> mListData;
    @Override
    public NewsRecyclerViewAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_news, parent, false);
        MyViewHolder mHolder = new MyViewHolder(view);
        return mHolder;
    }
    public NewsRecyclerViewAdapter(Context context,List<Data> list){
        mContext=context;
        mListData=list;
    }
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
            holder.tv__title.setText(mListData.get(position).getTitle());
            holder.tv_date.setText(mListData.get(position).getDate());
            String imgUrl = mListData.get(position).getThumbnail_pic_s();
            Uri imgUri = Uri.parse(imgUrl);
            holder.sd_img.setImageURI(imgUri);
    }

    @Override
    public int getItemCount() {
        return mListData.size();
    }



    public static class MyViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.news_tv__title)
        public TextView tv__title;
        @Bind(R.id.news_tv_date)
        public TextView tv_date;
        @Bind(R.id.news_sd_img)
        public SimpleDraweeView sd_img;
        MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(mContext, NewsDetailActivity.class);
                    intent.putExtra("url",mListData.get(getAdapterPosition()).getUrl());
                    mContext.startActivity(intent);
                }
            });
        }
    }
}
