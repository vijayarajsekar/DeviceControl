package com.pathivu.Sections.Feeds.Rss;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.pathivu.R;
import com.pathivu.Sections.Feeds.Interfaces.OnItemClickListener;
import com.pathivu.Sections.Feeds.Model.TNews;

import java.util.Collections;
import java.util.List;


/**
 * Created by vijayarajsekar on 18/1/16.
 */


public class RSSAdapter extends RecyclerView.Adapter<RSSAdapter.MyViewHolder> {

    List<TNews> mFeedData = Collections.emptyList();
    private LayoutInflater inflater;
    private Context context;

    private OnItemClickListener mItemClickListener;

    public RSSAdapter(Context ctx, List<TNews> data) {
        this.context = ctx;
        inflater = LayoutInflater.from(context);
        this.mFeedData = data;
    }

    public RSSAdapter(Context ctx) {
        this.context = ctx;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.feeds_list_items, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        TNews mFeed = mFeedData.get(position);

        holder.mTitle.setText(mFeed.getTitle());

        if (mFeed.getDescription() == null || mFeed.getDescription().equals("null")) {
            holder.mDesc.setText("");
        } else {
            holder.mDesc.setText(mFeed.getDescription());
        }

        holder.mDate.setText(mFeed.getDate());
        holder.mParent.setText(mFeed.getParent());
    }

    @Override
    public int getItemCount() {
        return mFeedData.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView mTitle;
        private TextView mDesc;
        private TextView mDate;
        private TextView mParent;

        public MyViewHolder(View itemView) {
            super(itemView);

            mTitle = (TextView) itemView.findViewById(R.id.title);
            mDesc = (TextView) itemView.findViewById(R.id.content);
            mDate = (TextView) itemView.findViewById(R.id.rssDate);
            mParent = (TextView) itemView.findViewById(R.id.rssParentTitle);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mItemClickListener.onItemClick(v, getPosition(), mFeedData.get(getPosition()));
        }
    }

    public void SetOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }
}