package com.konggit.apptwit;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ViewHolderAdapter extends BaseAdapter {

    private Context context;
    private List<List<String>> data;

    public ViewHolderAdapter(Context context, List<List<String>> data) {
        this.context = context;
        this.data = data;
    }

    static class ViewHolder {

        TextView textItem1;
        TextView textItem2;

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

        if (convertView == null) {

            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            convertView = inflater.inflate(R.layout.contacts_list_item, parent, false);
            viewHolder = new ViewHolder();

            viewHolder.textItem1 = (TextView) convertView.findViewById(R.id.textItem1);
            viewHolder.textItem2 = (TextView) convertView.findViewById(R.id.textItem2);

            convertView.setTag(viewHolder);

        } else {

            viewHolder = (ViewHolder) convertView.getTag();

        }

        List<String> box = (List<String>) getItem(position);
        ((TextView) convertView.findViewById(R.id.textItem1)).setText(box.get(0));
        ((TextView) convertView.findViewById(R.id.textItem2)).setText(box.get(1));

        viewHolder.textItem1.setText(String.valueOf(((List<String>) getItem(position)).get(0)));
        viewHolder.textItem2.setText(String.valueOf(((List<String>) getItem(position)).get(1)));

        return convertView;
    }
}
