package cn.niit.ydkf.release.release.adapter;

import android.content.Context;
import android.graphics.drawable.Animatable;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.niit.ydkf.release.release.R;
import cn.niit.ydkf.release.release.model.entities.JestBean;


public class JestRecyclerViewAdapter extends RecyclerView.Adapter<JestRecyclerViewAdapter.JestViewHolder> {
    private static Context mContext;
    private static List<JestBean.ShowapiResBodyBean.ContentlistBean> mListData;
    public JestRecyclerViewAdapter(Context mContext,List<JestBean.ShowapiResBodyBean.ContentlistBean> mListData){
        this.mContext=mContext;
        this.mListData=mListData;
    }

    @Override
    public JestViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_jest, parent, false);
        JestRecyclerViewAdapter.JestViewHolder mHolder = new JestRecyclerViewAdapter.JestViewHolder(view);
        return mHolder;
    }

    @Override
    public void onBindViewHolder(JestViewHolder holder, int position) {
        holder.tvTitle.setText(mListData.get(position).getTitle());
        holder.sdvJest.setImageURI(Uri.parse(mListData.get(position).getImg()));
        holder.jestTime.setText(mListData.get(position).getCt());
    }


    @Override
    public int getItemCount() {
        return mListData.size();
    }
    public static class JestViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.tv_title)
        public TextView tvTitle;
        @Bind(R.id.sdv_jest)
        public SimpleDraweeView sdvJest;
        @Bind(R.id.img_pause)
        public ImageView img_pause;
        @Bind(R.id.tv_jest_time)
        public TextView jestTime;
        public JestViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            view.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    Animatable animatable = sdvJest.getController().getAnimatable();
                    if (animatable != null) {
                        if (animatable.isRunning()){
                            img_pause.setVisibility(View.VISIBLE);
                            animatable.stop();
                        }else {
                            img_pause.setVisibility(View.INVISIBLE);
                            Uri gifUri = Uri.parse(mListData.get(getAdapterPosition()).getImg());
                            DraweeController mDraweeController = Fresco.newDraweeControllerBuilder()
                                    .setUri(gifUri)//设置uri
                                    .setAutoPlayAnimations(true)
                                    .build();
                            //设置Controller
                            sdvJest.setController(mDraweeController);
                        }
                    }
                }
            });
        }
    }
}
