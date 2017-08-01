package com.konggit.apprecyclerviewandanimatedtoolbar;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private String[] data;

    public RecyclerViewAdapter(String[] data) {
        this.data = data;
    }

    //viewHolder class which saves inside link to every item on the list
    static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView textView;

        ViewHolder(View view) {
            super(view);
            textView = (TextView)view.findViewById(R.id.textView);
            //view.setTag(view);
        }
    }

    //creates new views (called by layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.item, parent, false);
        return new ViewHolder(itemView);
    }

    //switches content of individual view(called by layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {

        viewHolder.textView.setText(data[position]);
    }

    @Override
    public int getItemCount() {
        return data.length;
    }

}
