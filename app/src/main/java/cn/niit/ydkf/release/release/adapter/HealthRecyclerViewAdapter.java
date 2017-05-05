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
import cn.niit.ydkf.release.release.model.entities.health.HealthBean;
import cn.niit.ydkf.release.release.view.activities.HealthDetailActivity;


public class HealthRecyclerViewAdapter extends RecyclerView.Adapter<HealthRecyclerViewAdapter.HealthViewHolder> {
    private static Context mContext;
    private static List<HealthBean.ShowapiResBodyBean.PagebeanBean.ContentlistBean> mListData;
    public HealthRecyclerViewAdapter(Context mContext, List<HealthBean.ShowapiResBodyBean.PagebeanBean.ContentlistBean> mListData){
        this.mContext=mContext;
        this.mListData=mListData;
    }

    @Override
    public HealthViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_health, parent, false);
        HealthRecyclerViewAdapter.HealthViewHolder mHolder = new HealthRecyclerViewAdapter.HealthViewHolder(view);
        return mHolder;
    }

    @Override
    public void onBindViewHolder(HealthViewHolder holder, int position) {
        holder.sdvHealth.setImageURI(Uri.parse(mListData.get(position).getImg()));
        holder.healthTitle.setText(mListData.get(position).getTitle());
        holder.healthTime.setText(mListData.get(position).getTime());
        holder.healthAuthor.setText(mListData.get(position).getAuthor());
    }


    @Override
    public int getItemCount() {
        return mListData.size();
    }
    public static class HealthViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.sdv_health)
        SimpleDraweeView sdvHealth;
        @Bind(R.id.tv_health_title)
        TextView healthTitle;
        @Bind(R.id.tv_health_time)
        TextView healthTime;
        @Bind(R.id.health_author)
        TextView healthAuthor;
        public HealthViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            view.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(mContext,HealthDetailActivity.class);
                    intent.putExtra("id",mListData.get(getAdapterPosition()).getId());
                    mContext.startActivity(intent);
                }
            });
        }
    }
}
