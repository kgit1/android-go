package com.konggit.appmvpdaggerted.common;

import android.app.Activity;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.konggit.appmvpdaggerted.model.ItemTalk;

import java.util.List;

public class TalkListAdapter extends BaseAdapter {

    private List<ItemTalk> itemTalkList;
    private LayoutInflater layoutInflater;
    private Activity activity;
    protected int totalListSize;

    public static final int VIEW_TYPE_LOADING = 0;
    public static final int VIEW_TYPE_ACTIVITY = 1;


    public TalkListAdapter (Activity activity, List<ItemTalk> itemTalkList, int totalListSize) {
        this.itemTalkList = itemTalkList;
        this.layoutInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.activity = activity;
        this.totalListSize = totalListSize;
    }

    @Override
    public boolean isEnabled(int position) {
        return getItemViewType(position) == VIEW_TYPE_ACTIVITY;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getCount() {
        return itemTalkList.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        return (position >= itemTalkList.size()) ? VIEW_TYPE_LOADING : VIEW_TYPE_ACTIVITY;
    }

    @Override
    public ItemTalk getItem(int position) {
        return (getItemViewType(position) == VIEW_TYPE_ACTIVITY) ? itemTalkList.get(position) : null;
    }

    @Override
    public long getItemId(int position) {
        return (getItemViewType(position) == VIEW_TYPE_ACTIVITY) ? position : -1;
    }

    @Override
    public  View getView(int position, View convertView, ViewGroup parent){
        if (getItemViewType(position) == VIEW_TYPE_LOADING) {
            // возвращаем вместо строки с данными футер с прогрессбаром
            return getFooterView(position, convertView, parent);
        }
        View view = convertView;
        if (view == null) {
            view = layoutInflater.inflate(R.layout.item_talk_list, parent, false);
            ViewHolder holder = new ViewHolder();
            holder.nameText = (TextView)view.findViewById(R.id.nameTextView);
            holder.descText = (TextView)view.findViewById(R.id.descTextView);
            holder.publishedText = (TextView)view.findViewById(R.id.publishedTextView);
            view.setTag(holder);
        }
        ViewHolder holder = (ViewHolder)view.getTag();
        ItemTalk itemTalk = getItem(position);
        holder.nameText.setText(itemTalk.getName());
        holder.descText.setText(itemTalk.getDescription());
        holder.publishedText.setText(itemTalk.getPublished());
        return view;
    }

    public void add(List<ItemTalk> itemTalks) {
        itemTalkList.addAll(itemTalks);
        notifyDataSetChanged();
    }

    public View getFooterView(int position, View convertView, ViewGroup parent) {
        if (position >= totalListSize && totalListSize > 0) {
            // the ListView has reached the last row
            TextView tvLastRow = new TextView(activity);
            tvLastRow.setHint("Reached the last row.");
            tvLastRow.setGravity(Gravity.CENTER);
            return tvLastRow;
        }
        View row = convertView;
        if (row == null) {
            row = layoutInflater.inflate(R.layout.list_footer, parent, false);
        }
        return row;
    }

    static class ViewHolder {
        TextView nameText;
        TextView descText;
        TextView publishedText;
    }
}
