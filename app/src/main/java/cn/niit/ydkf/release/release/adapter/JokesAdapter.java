package cn.niit.ydkf.release.release.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

import cn.niit.ydkf.release.release.R;
import cn.niit.ydkf.release.release.model.entities.JokesBean;


public class JokesAdapter extends BaseAdapter {
    private Context context;
    private List<JokesBean.ShowapiResBodyBean.PagebeanBean.ContentlistBean> list;
    public JokesAdapter(Context context,List<JokesBean.ShowapiResBodyBean.PagebeanBean.ContentlistBean> list){
        this.context=context;
        this.list=list;
    }
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView =  LayoutInflater.from(context).inflate(R.layout.item_jokes, null);
            holder = new ViewHolder();
                /*得到各个控件的对象*/
            holder.sdvUser = (SimpleDraweeView) convertView.findViewById(R.id.sdv_jokes_user);
            holder.username = (TextView) convertView.findViewById(R.id.tv_username);
            holder.time = (TextView) convertView.findViewById(R.id.tv_time); // to ItemButton
            holder.content= (TextView) convertView.findViewById(R.id.tv_content);
            convertView.setTag(holder); //绑定ViewHolder对象
        }
        else {
            holder = (ViewHolder) convertView.getTag(); //取出ViewHolder对象
        }
        Uri imageUri = Uri.parse(list.get(position).getProfile_image());
        holder.sdvUser.setImageURI(imageUri);
        holder.username.setText(list.get(position).getName());
        holder.time.setText(list.get(position).getCreate_time());
        holder.content.setText(list.get(position).getText());
        return convertView;
    }
    /*存放控件 的ViewHolder*/
    public final class ViewHolder {
        public SimpleDraweeView sdvUser;
        public TextView username;
        public TextView time;
        public TextView content;
    }
}
