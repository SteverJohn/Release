package cn.niit.ydkf.release.release.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import cn.niit.ydkf.release.release.R;


public class LifeAdapter extends BaseAdapter {
    private LayoutInflater layoutInflater;
    private int[] images;
    private String[] text;
   public LifeAdapter(Context context, int[] images, String[] text){
       this.images = images;
       this.text = text;
       layoutInflater = LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        return images.length;
    }

    @Override
    public Object getItem(int position) {
        return images[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = layoutInflater.inflate(R.layout.item_life_list,null);
        ImageView iv = (ImageView) v.findViewById(R.id.iv_life);
        TextView tv = (TextView) v.findViewById(R.id.tv_life);
        iv.setImageResource(images[position]);
        tv.setText(text[position]);
        return v;
    }
}
