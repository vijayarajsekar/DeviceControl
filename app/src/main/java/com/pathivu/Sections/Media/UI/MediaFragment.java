package com.pathivu.Sections.Media.UI;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Toast;

import com.pathivu.R;
import com.pathivu.Sections.Feeds.Rss.UpdateRSSList;
import com.pathivu.Sections.Media.Adapter.StaggeredGridAdapter;
import com.pathivu.Sections.Media.StaggeredView.StaggeredGridView;
import com.pathivu.Utils.ConnectionDetector;
import com.pathivu.Utils.Logger;

import java.util.ArrayList;


/**
 * Created by vijayarajsekar on 18/1/16.
 */

public class MediaFragment extends Fragment implements AbsListView.OnItemClickListener {

    private static MediaFragment instance;
    private View mRootView;

    private static final String TAG = "StaggeredGridActivity";
    public static final String SAVED_DATA_KEY = "SAVED_DATA";

    private StaggeredGridView mGridView;
    private boolean mHasRequestedMore;
    private StaggeredGridAdapter mAdapter;

    private ArrayList<String> mData;

    static final boolean isInstanciated() {
        return instance != null;
    }

    public static final MediaFragment instance() {
        return instance;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        mRootView = inflater.inflate(R.layout.fragment_media, null);

        mGridView = (StaggeredGridView) mRootView.findViewById(R.id.grid_view);

        mAdapter = new StaggeredGridAdapter(getActivity(), R.id.txt_line1);

        // do we have saved data?
        if (savedInstanceState != null) {
            mData = savedInstanceState.getStringArrayList(SAVED_DATA_KEY);
        }

        if (mData == null) {
            mData = getAllShownImagesPath(getActivity());
        }

        for (String data : mData) {
            mAdapter.add(data);
        }

        mGridView.setAdapter(mAdapter);
        mGridView.setOnItemClickListener(this);

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

    public static ArrayList<String> generateSampleData() {
        final ArrayList<String> data = new ArrayList<String>(30);

        for (int i = 0; i < 30; i++) {
            data.add("" + R.drawable.ic_vij);
        }

        return data;
    }

    @Override
    public void onSaveInstanceState(final Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putStringArrayList(SAVED_DATA_KEY, mData);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        Toast.makeText(getActivity(), "Item Clicked: " + position, Toast.LENGTH_SHORT).show();
    }

    public ArrayList<String> getAllShownImagesPath(FragmentActivity activity) {

        Uri uri;

        Cursor cursor;
        int column_index_data, column_index_folder_name, column_index_name, ext_uri, int_uri;
        ArrayList<String> listOfAllImages = new ArrayList<String>();
        String absolutePathOfImage = null;

        uri = android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI;

        if (uri == null) {
            uri = MediaStore.Images.Media.INTERNAL_CONTENT_URI;
        }

        String[] projection = {MediaStore.MediaColumns.DATA,
                MediaStore.Images.Media.BUCKET_DISPLAY_NAME, MediaStore.Images.Media.DISPLAY_NAME};

        cursor = activity.getContentResolver().query(uri, projection, null,
                null, null);

        column_index_data = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);

        column_index_folder_name = cursor
                .getColumnIndexOrThrow(MediaStore.Images.Media.BUCKET_DISPLAY_NAME);

        column_index_name = cursor
                .getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME);

        while (cursor.moveToNext()) {
//            absolutePathOfImage = cursor.getString(column_index_data);

//            absolutePathOfImage = cursor.getString(column_index_data) + "" + cursor.getString(column_index_folder_name) + "" + cursor.getString(column_index_name);

            absolutePathOfImage = cursor.getString(column_index_data);

            Logger.Print("Image Data", cursor.getString(column_index_data) + "" + cursor.getString(column_index_folder_name) + "" + cursor.getString(column_index_name));

            listOfAllImages.add(absolutePathOfImage);
        }

        return listOfAllImages;
    }
}