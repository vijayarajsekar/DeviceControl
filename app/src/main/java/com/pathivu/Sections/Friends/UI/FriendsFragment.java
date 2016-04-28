package com.pathivu.Sections.Friends.UI;

import android.content.ContentResolver;
import android.content.DialogInterface;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.preference.DialogPreference;
import android.provider.ContactsContract;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.support.v4.app.Fragment;
import android.widget.ProgressBar;

import com.pathivu.R;
import com.pathivu.Sections.Friends.Adapter.ContactsItemsAdapter;
import com.pathivu.Sections.Friends.Adapter.FriendsListAdapter;
import com.pathivu.Sections.Friends.Interfaces.ContactsQuery;
import com.pathivu.Sections.Friends.Model.Contacts;
import com.pathivu.Utils.Logger;
import com.pathivu.Utils.Utils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


/**
 * Created by vijayarajsekar on 18/1/16.
 */

public class FriendsFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>, AdapterView.OnItemClickListener {

    private String TAG = FriendsFragment.class.getSimpleName();

    private static FriendsFragment instance;

    private View mRootView;

    private ListView mContactsList;

    private List<Contacts> mContactsPojo;

    private FriendsListAdapter mFriendsListAdapter;

    private ProgressBar mProgressBar;

    private String mContactId, mContactName;

    private Uri mContactUri;

    private Cursor mContactCursor;

    private ArrayList<String> mPhoneEmail;

    private Set<String> mStringSet;

    private ContentResolver mResolver;

    private String mEmailAddress;

    private String mSearchTerm;
    private boolean mIsSearchResultView = false;

    private AlertDialog mBuilder;

    static final boolean isInstanciated() {
        return instance != null;
    }

    public static final FriendsFragment instance() {
        return instance;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        mRootView = inflater.inflate(R.layout.fragment_friends, null);

        mContactsList = (ListView) mRootView.findViewById(R.id.listView2);
        mContactsList.setOnItemClickListener(this);

        mContactsPojo = new ArrayList<Contacts>();
        mStringSet = new HashSet<String>();

        mBuilder = new AlertDialog.Builder(getActivity()).create();

        mResolver = getActivity().getContentResolver();

        mProgressBar = (ProgressBar) mRootView.findViewById(R.id.loading_progress);

        getLoaderManager().initLoader(ContactsQuery.QUERY_ID, null, this);

        return mRootView;
    }

    public void setSearchQuery(String query) {
        if (TextUtils.isEmpty(query)) {
            mIsSearchResultView = false;
        } else {
            mSearchTerm = query;
            mIsSearchResultView = true;
        }
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

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {

        if (id == ContactsQuery.QUERY_ID) {

            Uri contentUri;

            if (mSearchTerm == null) {
                contentUri = ContactsQuery.CONTENT_URI;
            } else {
                contentUri =
                        Uri.withAppendedPath(ContactsQuery.FILTER_URI, Uri.encode(mSearchTerm));
            }

            return new CursorLoader(getActivity(),
                    contentUri,
                    ContactsQuery.PROJECTION,
                    ContactsQuery.SELECTION,
                    null,
                    ContactsQuery.SORT_ORDER);
        }

        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor _cursor) {

        try {

            if (loader.getId() == ContactsQuery.QUERY_ID) {

                if (_cursor != null) {

                    this.mContactCursor = _cursor;

                    while (_cursor.moveToNext()) {

                        mContactId = _cursor.getString(ContactsQuery.ID);
                        mContactName = _cursor.getString(ContactsQuery.DISPLAY_NAME);

                        mContactsPojo.add(new Contacts(mContactId, mContactName));
                    }
                }

                if (mContactsPojo.size() != 0) {

                    mFriendsListAdapter = new FriendsListAdapter(getActivity(), mContactsPojo, mContactCursor);
                    mContactsList.setAdapter(mFriendsListAdapter);

                    mContactsList.setVisibility(View.VISIBLE);
//                    mProgressBar.setVisibility(View.GONE);

                    mFriendsListAdapter.notifyDataSetChanged();
                } else {
                    mContactsList.setVisibility(View.INVISIBLE);
//                    mProgressBar.setVisibility(View.GONE);
                }
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        mContactCursor.moveToPosition(position);

        if (Utils.hasHoneycomb()) {

            mContactUri = ContactsContract.Contacts.getLookupUri(
                    mContactCursor.getLong(ContactsQuery.ID),
                    mContactCursor.getString(ContactsQuery.LOOKUP_KEY));

        } else {

            mContactUri = ContactsContract.Contacts.lookupContact(getActivity().getContentResolver(),
                    mContactUri);
        }

        ShowReqDialog(mContactsPojo.get(position).getNAME(), position);
    }

    private void ShowReqDialog(final String _name, final int _pos) {

        mBuilder.setTitle("Invite " + _name);
        mBuilder.setMessage("Your friend is not yet on Pathivugal, send an invite ? ");

        mBuilder.setButton(AlertDialog.BUTTON_POSITIVE, "Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();

                if (GetPhoneEmailDetails(mContactsPojo.get(_pos).getID()).size() != 0) {
                    ShowContactsDialog(GetPhoneEmailDetails(mContactsPojo.get(_pos).getID()), _name);
                } else {
                    Logger.ShowToast(getActivity(), "No Details Found");
                }
            }
        });

        mBuilder.setButton(AlertDialog.BUTTON_NEGATIVE, "Later", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        mBuilder.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                mBuilder.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(getResources().getColor(R.color.negati_btn_clr));
            }
        });

        mBuilder.show();
    }

    private void ShowContactsDialog(final ArrayList<String> _items, String _name) {

        new AlertDialog.Builder(getActivity()).setTitle("Invite " + _name)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })

                .setAdapter(new ContactsItemsAdapter(getActivity(), _items), new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int item) {
                        dialog.dismiss();
                    }
                }).show();
    }

    private ArrayList<String> GetPhoneEmailDetails(String contactId) {

        // ----------------------------------- Read Contact Details -------------------------------------- //

        try {

            mPhoneEmail = new ArrayList<String>();

            Cursor mPhone = mResolver.query(
                    ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                    null,
                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                    new String[]{contactId}, null);

            while (mPhone.moveToNext())

            {
                mPhoneEmail.add(mPhone.getString(mPhone.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)));
            }

            mPhone.close();

            // ----------------------------------- Read Email Details -------------------------------------- //

            Cursor mEmail = mResolver.query(
                    ContactsContract.CommonDataKinds.Email.CONTENT_URI,
                    null,
                    ContactsContract.CommonDataKinds.Email.CONTACT_ID
                            + " = " + contactId, null, null);

            if (mEmail.getCount() > 0) {

                while (mEmail.moveToNext()) {

                    mEmailAddress = mEmail.getString(mEmail.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA));

                    mPhoneEmail.add(mEmailAddress);
                }

                mEmail.close();

            } else {
                Logger.Print(TAG, "No Email Id");
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }


        if (mPhoneEmail != null && mPhoneEmail.size() != 0) {
            mStringSet.addAll(mPhoneEmail);
            mPhoneEmail.clear();
            mPhoneEmail.addAll(mStringSet);
            mStringSet.clear();
        }

        return mPhoneEmail;
    }
}