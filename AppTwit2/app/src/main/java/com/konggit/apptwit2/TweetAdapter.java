package com.konggit.apptwit2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class TweetAdapter extends BaseAdapter {

    private List<Tweet> list;
    private Context context;

    public TweetAdapter(List<Tweet> list, Context context) {
        this.list = list;
        this.context = context;
    }

    private static class ViewHolder {

        TextView textViewUsername;
        TextView textViewTweet;
        TextView textViewCreatedAt;
        TextView textViewChangedAt;
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

        if (convertView == null) {

            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            convertView = inflater.inflate(R.layout.tweet_item, parent, false);
            viewHolder = new ViewHolder();

            viewHolder.textViewUsername = (TextView) convertView.findViewById(R.id.textViewUser);
            viewHolder.textViewTweet = (TextView) convertView.findViewById(R.id.textViewTweet);
            viewHolder.textViewCreatedAt = (TextView) convertView.findViewById(R.id.textViewCreatedAt);
            viewHolder.textViewChangedAt = (TextView) convertView.findViewById(R.id.textViewChangedAt);

            convertView.setTag(viewHolder);

        } else {

            viewHolder = (ViewHolder) convertView.getTag();

        }

        Tweet tweet = list.get(position);

        if (tweet != null) {

            ((TextView) convertView.findViewById(R.id.textViewUser)).setText(tweet.getUsername());
            ((TextView) convertView.findViewById(R.id.textViewTweet)).setText(tweet.getTweet());
            ((TextView) convertView.findViewById(R.id.textViewCreatedAt)).setText(tweet.getCreatedAt());
            ((TextView) convertView.findViewById(R.id.textViewChangedAt)).setText(tweet.getChangedAt());

        }

        viewHolder.textViewUsername.setText(((Tweet) getItem(position)).getUsername());
        viewHolder.textViewTweet.setText(((Tweet) getItem(position)).getTweet());
        viewHolder.textViewCreatedAt.setText(((Tweet) getItem(position)).getCreatedAt());
        viewHolder.textViewChangedAt.setText(((Tweet) getItem(position)).getChangedAt());

        return convertView;
    }
}
