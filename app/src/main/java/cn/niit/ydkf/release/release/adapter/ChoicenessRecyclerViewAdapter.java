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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.niit.ydkf.release.release.R;
import cn.niit.ydkf.release.release.model.entities.choiceness.Newslist;
import cn.niit.ydkf.release.release.view.activities.ChoinessDetailActivity;
import cn.niit.ydkf.release.release.view.activities.NewsDetailActivity;


public class ChoicenessRecyclerViewAdapter extends RecyclerView.Adapter<ChoicenessRecyclerViewAdapter.MyViewHolder> {
    private static Context mContext;
    private static List<Newslist> mListData;
    @Override
    public ChoicenessRecyclerViewAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_choiceness, parent, false);
        MyViewHolder mHolder = new MyViewHolder(view);
        return mHolder;
    }
    public ChoicenessRecyclerViewAdapter(Context context, List<Newslist> list){
        mContext=context;
        mListData=list;
    }
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
            holder.tv__title.setText(mListData.get(position).getTitle());

            holder.tv_date.setText(ConverDateToString(mListData.get(position).getCtime()));
            String imgUrl = mListData.get(position).getPicurl();
            Uri imgUri = Uri.parse(imgUrl);
            holder.sd_img.setImageURI(imgUri);
    }
    //把日期转为字符串
    public static String ConverDateToString(Date date)
    {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

        return df.format(date);
    }
    @Override
    public int getItemCount() {
        return mListData.size();
    }



    public static class MyViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.choiceness_tv__title)
        public TextView tv__title;
        @Bind(R.id.choiceness_tv_date)
        public TextView tv_date;
        @Bind(R.id.choiceness_sd_img)
        public SimpleDraweeView sd_img;
        MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(mContext, ChoinessDetailActivity.class);
                    intent.putExtra("url",mListData.get(getAdapterPosition()).getUrl());
                    mContext.startActivity(intent);
                }
            });
        }
    }
}
