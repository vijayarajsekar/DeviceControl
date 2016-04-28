//
//package com.pathivu.Sections.Friends.UI;
//
//import android.database.Cursor;
//import android.net.Uri;
//import android.os.AsyncTask;
//import android.os.Bundle;
//import android.provider.ContactsContract;
//import android.support.v4.app.Fragment;
//import android.support.v4.app.LoaderManager;
//import android.support.v4.content.CursorLoader;
//import android.support.v4.content.Loader;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ListView;
//import android.widget.ProgressBar;
//
//import com.pathivu.R;
//import com.pathivu.Sections.Friends.Adapter.FriendsListAdapter;
//import com.pathivu.Sections.Friends.Interfaces.ContactsQuery;
//import com.pathivu.Sections.Friends.Model.Contacts;
//import com.pathivu.Utils.Logger;
//
//import java.util.ArrayList;
//import java.util.HashSet;
//import java.util.List;
//import java.util.ListIterator;
//import java.util.Set;
//
//
///**
// * Created by vijayarajsekar on 18/1/16.
// */
//
//
//public class FriendsFragment_demo extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {
//
//    private String TAG = FriendsFragment_demo.class.getSimpleName();
//
//    private static FriendsFragment_demo instance;
//
//    private View mRootView;
//
//    private ListView mContactsList;
//
//    private List<Contacts> mContactsPojo;
//
//    private ListIterator mListIterator;
//
//    private FriendsListAdapter mFriendsListAdapter;
//
//    private ProgressBar mProgressBar;
//
//    private Cursor mContactCursor;
//
//    private String mContactId, mContactName, mContactNumber;
//    private Uri mContactPhotoUri;
//
//    private Uri mContentUri;
//
//    static final boolean isInstanciated() {
//        return instance != null;
//    }
//
//    public static final FriendsFragment_demo instance() {
//        return instance;
//    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//
//        mRootView = inflater.inflate(R.layout.fragment_friends, null);
//
//        mContactsList = (ListView) mRootView.findViewById(R.id.listView2);
//        mContactsPojo = new ArrayList<Contacts>();
//
//        mProgressBar = (ProgressBar) mRootView.findViewById(R.id.loading_progress);
//        mProgressBar.setVisibility(View.INVISIBLE);
//
//        mContentUri = ContactsQuery.CONTENT_URI;
//
//        getLoaderManager().initLoader(ContactsQuery.QUERY_ID, null, this);
//
//        return mRootView;
//    }
//
//
//    @Override
//    public void onResume() {
//        instance = this;
//        super.onResume();
//    }
//
//    @Override
//    public void onPause() {
//        instance = null;
//        super.onPause();
//    }
//
//    @Override
//    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
//
//        if (id == ContactsQuery.QUERY_ID) {
//
//            return new CursorLoader(getActivity(),
//                    mContentUri,
//                    ContactsQuery.PROJECTION,
//                    ContactsQuery.SELECTION,
//                    null,
//                    ContactsQuery.SORT_ORDER);
//        }
//
//        return null;
//    }
//
//    @Override
//    public void onLoadFinished(Loader<Cursor> loader, Cursor _cursor) {
//
//        try {
//
//            if (loader.getId() == ContactsQuery.QUERY_ID) {
//
//                ArrayList<String> maaaa = new ArrayList<String>();
//
//                if (_cursor != null) {
//
//                    this.mContactCursor = _cursor;
//
//                    while (_cursor.moveToNext()) {
//
//                        mContactId = _cursor.getString(ContactsQuery.ID);
//                        mContactName = _cursor.getString(ContactsQuery.DISPLAY_NAME);
//
//                        mContactsPojo.add(new Contacts(mContactId, mContactName));
//
//                        maaaa.add(mContactId);
//                        maaaa.add(mContactName);
//                    }
//
//                    Logger.Print(TAG, "" + maaaa.size());
//                    Logger.Print(TAG, maaaa.toString());
//                }
//
//                if (mContactsPojo.size() != 0) {
//
//                    mFriendsListAdapter = new FriendsListAdapter(getActivity(), mContactsPojo, mContactCursor);
//                    mContactsList.setAdapter(mFriendsListAdapter);
//
//                    mContactsList.setVisibility(View.VISIBLE);
//                    mProgressBar.setVisibility(View.GONE);
//
//                    mFriendsListAdapter.notifyDataSetChanged();
//                } else {
//                    mContactsList.setVisibility(View.INVISIBLE);
//                    mProgressBar.setVisibility(View.GONE);
//                }
//            }
//
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
//    }
//
//    @Override
//    public void onLoaderReset(Loader<Cursor> loader) {
//
//    }
//}
