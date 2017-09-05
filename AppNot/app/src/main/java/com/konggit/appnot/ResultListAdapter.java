package com.konggit.appnot;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Date;
import java.util.Map;

public class ResultListAdapter extends BaseAdapter{

    Context context;
    List<Map.Entry<Date,Results>> list;

    public ResultListAdapter(Context context, Map<Date,Results> data){

        this.context = context;

        list = new ArrayList<>();
        list.addAll(data.entrySet());

    }

    static class ViewHolder{

        TextView date;
        TextView result;
        TextView defaultResult;

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

        ViewHolder viewHolder;

        if(convertView==null){

            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            convertView = inflater.inflate(R.layout.results_list_item, parent, false);
            viewHolder = new ViewHolder();

            //save link to textViews in viewHolder
            viewHolder.date = (TextView) convertView.findViewById(R.id.dateTextView);
            viewHolder.result = (TextView) convertView.findViewById(R.id.resultTextView);
            viewHolder.defaultResult = (TextView) convertView.findViewById(R.id.defaultTextView);

            //save link to viewHolder in view
            convertView.setTag(viewHolder);

        }else{

            viewHolder = (ViewHolder)convertView.getTag();

        }

        viewHolder.date.setText(String.valueOf(list.get(position).getKey()));
        viewHolder.result.setText(String.valueOf(list.get(position).getValue().getNumbers()));
        viewHolder.defaultResult.setText(String.valueOf(list.get(position).getValue().getDayDefaultNumber()));

        return convertView;
    }
}
