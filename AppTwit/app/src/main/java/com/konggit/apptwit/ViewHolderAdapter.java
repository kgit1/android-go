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

/*
public class ListAdapter extends ArrayAdapter<Item> {

    public ListAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
    }

    public ListAdapter(Context context, int resource, List<Item> items) {
        super(context, resource, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;

        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.itemlistrow, null);
        }

        Item p = getItem(position);

        if (p != null) {
            TextView tt1 = (TextView) v.findViewById(R.id.id);
            TextView tt2 = (TextView) v.findViewById(R.id.categoryId);
            TextView tt3 = (TextView) v.findViewById(R.id.description);

            if (tt1 != null) {
                tt1.setText(p.getId());
            }

            if (tt2 != null) {
                tt2.setText(p.getCategory().getId());
            }

            if (tt3 != null) {
                tt3.setText(p.getDescription());
            }
        }

        return v;
    }

}
This is a class I had used for my project. You need to have a collection of your items which you want to display, in my case it's <Item>. You need to override View getView(int position, View convertView, ViewGroup parent) method.

R.layout.itemlistrow defines the row of the ListView.

<?xml version="1.0" encoding="utf-8"?>
<TableLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_height="wrap_content" android:orientation="vertical"
    android:layout_width="fill_parent">

    <TableRow android:layout_width="fill_parent"
              android:id="@+id/TableRow01"
              android:layout_height="wrap_content">

        <TextView android:textColor="#FFFFFF"
                  android:id="@+id/id"
                  android:layout_width="fill_parent"
                  android:layout_height="wrap_content"
                  android:text="id" android:textStyle="bold"
                  android:gravity="left"
                  android:layout_weight="1"
                  android:typeface="monospace"
                  android:height="40sp" />
    </TableRow>

    <TableRow android:layout_height="wrap_content"
              android:layout_width="fill_parent">

        <TextView android:textColor="#FFFFFF"
                  android:id="@+id/categoryId"
                  android:layout_width="fill_parent"
                  android:layout_height="wrap_content"
                  android:text="categoryId"
                  android:layout_weight="1"
                  android:height="20sp" />

        <TextView android:layout_height="wrap_content"
                  android:layout_width="fill_parent"
                  android:layout_weight="1"
                  android:textColor="#FFFFFF"
                  android:gravity="right"
                  android:id="@+id/description"
                  android:text="description"
                  android:height="20sp" />
    </TableRow>

</TableLayout>
In the MainActivity define ListViewlike this,

ListView yourListView = (ListView) findViewById(R.id.itemListView);

// get data from the table by the ListAdapter
ListAdapter customAdapter = new ListAdapter(this, R.layout.itemlistrow, List<yourItem>);

yourListView .setAdapter(customAdapter);
 */