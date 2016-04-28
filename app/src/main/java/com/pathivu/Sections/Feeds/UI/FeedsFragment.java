package com.pathivu.Sections.Feeds.UI;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pathivu.R;
import com.pathivu.Sections.Feeds.Model.TNews;
import com.pathivu.Sections.Feeds.Rss.UpdateRSSList;
import com.pathivu.Utils.ConnectionDetector;
import com.pathivu.Utils.Logger;


/**
 * Created by vijayarajsekar on 18/1/16.
 */

public class FeedsFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private static FeedsFragment instance;
    private View mRootView;

    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mChannelsList;

    private RecyclerView.LayoutManager mLayoutManager;
    private Intent mIntent;

    static final boolean isInstanciated() {
        return instance != null;
    }

    public static final FeedsFragment instance() {
        return instance;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        mRootView = inflater.inflate(R.layout.fragment_feeds, null);

        mSwipeRefreshLayout = (SwipeRefreshLayout) mRootView.findViewById(R.id.swipe_refresh_layout);
        mSwipeRefreshLayout.setOnRefreshListener(this);

        mChannelsList = (RecyclerView) mRootView.findViewById(R.id.listView);
        mChannelsList.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(getActivity());
        mChannelsList.setLayoutManager(mLayoutManager);
        mChannelsList.setItemAnimator(new DefaultItemAnimator());

        /**
         * Showing Swipe Refresh animation on activity create
         * As animation won't start on onCreate, post runnable is used
         */

        mSwipeRefreshLayout.post(new Runnable() {

            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(true);

                if (new ConnectionDetector(getActivity()).isConnectingToInternet()) {
                    UpdateFeeds();
                } else {
                    Logger.NoInternet(getActivity());
                    mSwipeRefreshLayout.setRefreshing(false);
                }
            }
        });

        return mRootView;
    }

    @Override
    public void onRefresh() {

        if (new ConnectionDetector(getActivity()).isConnectingToInternet()) {
            UpdateFeeds();
        } else {
            Logger.NoInternet(getActivity());
        }
    }

    public void UpdateFeeds() {
        mSwipeRefreshLayout.setRefreshing(true);
        new UpdateRSSList(getActivity(), mChannelsList, mSwipeRefreshLayout).execute();
    }

    public void ClickedItem(Context mContext, View view, int position, TNews tNews) {

        System.out.println("~~~~~ACT~~~~~~~~~~" + position);
        System.out.println("~~~~~ DATA ~~~~~~~~~~" + tNews.getTitle());
        System.out.println("~~~~~ DATA ~~~~~~~~~~" + tNews.getDescription());
        System.out.println("~~~~~ DATA ~~~~~~~~~~" + tNews.getLink());

        if (new ConnectionDetector(mContext).isConnectingToInternet()) {
            LoadFeedsLink(mContext, tNews.getTitle(), tNews.getLink());
        } else {
            Logger.NoInternet(getActivity());
        }
    }

    private void LoadFeedsLink(Context ctx, String title, String link) {
        ctx.startActivity(new Intent(ctx, LoadFeedDetails.class).putExtra("TITLE", title).putExtra("LINK", link));
    }

    @Override
    public void onResume() {
        instance = this;
        super.onResume();
    }

    @Override
    public void onPause() {
        instance = null;
        super.onPause();
    }
}