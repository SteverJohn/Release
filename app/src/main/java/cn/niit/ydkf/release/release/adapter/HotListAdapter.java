package cn.niit.ydkf.release.release.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import cn.niit.ydkf.release.release.R;


public class HotListAdapter extends BaseAdapter {
    private LayoutInflater layoutInflater;
    private int[] color;
    private String[] text;
    public HotListAdapter(Context context, int[] color, String[] text){
        this.color = color;
        this.text = text;
        layoutInflater = LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        return color.length;
    }

    @Override
    public Object getItem(int position) {
        return color[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = layoutInflater.inflate(R.layout.item_hot_list,null);
        TextView hotListName= (TextView) v.findViewById(R.id.tv_hot_list);
        LinearLayout hotListColor= (LinearLayout) v.findViewById(R.id.ll_hot_list);
        hotListName.setText(text[position]);
        hotListColor.setBackgroundColor(color[position]);
        return v;
    }
}
