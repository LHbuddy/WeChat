package com.jju.edu.wechat.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.jju.edu.wechat.R;
import com.jju.edu.wechat.Utils.FriendUtil;

import java.util.List;

/**
 * Created by 凌浩 on 2016/11/9.
 */

public class FriendAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private Context context;
    private List<FriendUtil> list;

    public FriendAdapter(Context context, List<FriendUtil> list) {
        this.context = context;
        this.list = list;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.listview_item_layout, null);
            holder.name = (TextView) convertView.findViewById(R.id.name);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.name.setText(list.get(position).getName());
        return convertView;
    }

    class ViewHolder {
        TextView name;
    }
}
