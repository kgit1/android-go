package com.konggit.appwazup;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class UserViewHolderAdapter extends BaseAdapter{

    Context context;
    List<String> data;

    public UserViewHolderAdapter(Context context, List<String> data) {
        this.context = context;
        this.data = data;
    }

    static class ViewHolder{

        TextView item;

    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder;

        if(convertView == null){

            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.userlist_item, parent, false);
            viewHolder = new ViewHolder();

            viewHolder.item = (TextView) convertView.findViewById(R.id.textViewItem);
            convertView.setTag(viewHolder);

        }else{

            viewHolder = (ViewHolder) convertView.getTag();

        }

        viewHolder.item.setText(String.valueOf(getItem(position)));

        return convertView;
    }
}
