package com.pathivu.Sections.Dialpad.UI;

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
import com.pathivu.Sections.Feeds.Rss.UpdateRSSList;
import com.pathivu.Utils.ConnectionDetector;
import com.pathivu.Utils.Logger;


/**
 * Created by vijayarajsekar on 18/1/16.
 */

public class DialpadFragment extends Fragment {

    private static DialpadFragment instance;
    private View mRootView;

    static final boolean isInstanciated() {
        return instance != null;
    }

    public static final DialpadFragment instance() {
        return instance;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        mRootView = inflater.inflate(R.layout.fragment_dialepad, null);

        return mRootView;
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