package com.pathivu.Sections.Messages.UI;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pathivu.R;


/**
 * Created by vijayarajsekar on 18/1/16.
 */

public class MessagesFragment extends Fragment {

    private static MessagesFragment instance;
    private View mRootView;

    static final boolean isInstanciated() {
        return instance != null;
    }

    public static final MessagesFragment instance() {
        return instance;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        mRootView = inflater.inflate(R.layout.fragment_messages, null);

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