package com.konggit.appwearlists;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.wearable.view.WearableListView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class MainActivity extends Activity implements WearableListView.ClickListener {

    @Override
    public void onClick(WearableListView.ViewHolder viewHolder) {

        Integer tag = (Integer) viewHolder.itemView.getTag();
        Log.i("AppInfo", "Item Tapped: " + Integer.toString(tag));


    }

    @Override
    public void onTopEmptyRegionClick() {

    }


    private static final class MyAdapter extends WearableListView.Adapter {

        private String[] mDataset;
        private final Context mContext;
        private final LayoutInflater mInflater;

        // Provide a suitable constructor (depends on the kind of dataset)
        public MyAdapter(Context context, String[] dataset) {
            mContext = context;
            mInflater = LayoutInflater.from(context);
            mDataset = dataset;
        }

        // Provide a reference to the type of views you're using
        public static class ItemViewHolder extends WearableListView.ViewHolder {
            private TextView textView;
            public ItemViewHolder(View itemView) {
                super(itemView);
                // find the text view within the custom item's layout
                textView = (TextView) itemView.findViewById(R.id.name);
            }
        }

        // Create new views for list items
        // (invoked by the WearableListView's layout manager)
        @Override
        public WearableListView.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                              int viewType) {
            // Inflate our custom layout for list items
            return new ItemViewHolder(mInflater.inflate(R.layout.list_item,null));
        }

        // Replace the contents of a list item
        // Instead of creating new views, the list tries to recycle existing ones
        // (invoked by the WearableListView's layout manager)
        @Override
        public void onBindViewHolder(WearableListView.ViewHolder holder,
                                     int position) {
            // retrieve the text view
            ItemViewHolder itemHolder = (ItemViewHolder) holder;
            TextView view = itemHolder.textView;
            // replace text contents
            view.setText(mDataset[position]);
            // replace list item's metadata
            holder.itemView.setTag(position);
        }

        // Return the size of your dataset
        // (invoked by the WearableListView's layout manager)
        @Override
        public int getItemCount() {
            return mDataset.length;
        }
    }

    String[] elements = { "Rob", "Kirsten", "Tommy", "Ralphie" };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



//        final WatchViewStub stub = (WatchViewStub) findViewById(R.id.watch_view_stub);
//
//        stub.setOnLayoutInflatedListener(new WatchViewStub.OnLayoutInflatedListener() {
//            @Override
//            public void onLayoutInflated(WatchViewStub stub) {


                WearableListView listView =
                        (WearableListView) findViewById(R.id.wearable_list);

                // Assign an adapter to the list
                listView.setAdapter(new MyAdapter(MainActivity.this, elements));

                // Set a click listener
                listView.setClickListener(MainActivity.this);


//            }
//        });
    }
}



//    public class MyLauncherChildLayoutManager extends CurvedChildLayoutManager {
//        /** How much should we scale the icon at most. */
//        private static final float MAX_ICON_PROGRESS = 0.65f;
//
//        private float mProgressToCenter;
//
//        public OffsettingHelper() {}
//
//        @Override
//        public void updateChild(View child, WearableRecyclerView parent) {
//            super.updateChild(child, parent);
//
//            // Figure out % progress from top to bottom
//            float centerOffset = ((float) child.getHeight() / 2.0f) / (float) mParentView.getHeight();
//            float yRelativeToCenterOffset = (child.getY() / mParentView.getHeight()) + centerOffset;
//
//            // Normalize for center
//            mProgressToCenter = Math.abs(0.5f - yRelativeToCenterOffset);
//            // Adjust to the maximum scale
//            mProgressToCenter = Math.min(mProgressToCenter, MAX_ICON_PROGRESS);
//
//            child.setScaleX(1 - mProgressToCenter);
//            child.setScaleY(1 - mProgressToCenter);
//        }
//    }

