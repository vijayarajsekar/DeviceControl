package com.pathivu.Sections.Friends.Adapter;

import android.app.Activity;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AlphabetIndexer;
import android.widget.BaseAdapter;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.pathivu.R;
import com.pathivu.Sections.Friends.Interfaces.ContactsQuery;
import com.pathivu.Sections.Friends.Model.Contacts;
import com.pathivu.Utils.RoundedImageView;

import java.io.ByteArrayInputStream;
import java.util.List;

/**
 * Created by vijayarajsekar on 28/1/16.
 */
public class FriendsListAdapter extends BaseAdapter implements SectionIndexer {

    private String TAG = FriendsListAdapter.class.getSimpleName();

    private Context mContext;

    private ViewHolder holder;
    private LayoutInflater l_Inflater;
    public static List<Contacts> mContactList = null;
    private AlphabetIndexer mAlphabetIndexer;

    private Cursor mCursor;

    public FriendsListAdapter(Activity context, List<Contacts> mList, Cursor mContactCursor) {

        this.mContext = context;

        l_Inflater = LayoutInflater.from(mContext);
        mAlphabetIndexer = new AlphabetIndexer(null, ContactsQuery.SORT_KEY, context.getString(R.string.alphabet));
        this.mContactList = mList;
        this.mCursor = mContactCursor;
        mAlphabetIndexer.setCursor(mCursor);
    }

    @Override
    public int getCount() {
        return this.mContactList.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {

        if (convertView == null) {

            holder = new ViewHolder();

            convertView = l_Inflater.inflate(R.layout.item_contact, parent, false);

            holder = new ViewHolder();

            holder.mImage_profile = (RoundedImageView) convertView.findViewById(R.id.profile_image);

            holder.mText_UserName = (TextView) convertView.findViewById(R.id.profile_name);

            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.mText_UserName.setText(mContactList.get(position).getNAME());

        if (mContactList.get(position).getID() != null && GetContactImage(Long.parseLong(mContactList.get(position).getID())) != null) {
            holder.mImage_profile.setImageBitmap(GetContactImage(Long.parseLong(mContactList.get(position).getID())));
        } else {
            holder.mImage_profile.setImageResource(R.drawable.ic_default_person);
        }

        return convertView;
    }

    @Override
    public Object[] getSections() {
        return mAlphabetIndexer.getSections();
    }

    @Override
    public int getPositionForSection(int i) {

        if (mCursor == null) {
            return 0;
        }
        return mAlphabetIndexer.getPositionForSection(i);
    }

    @Override
    public int getSectionForPosition(int i) {
        try {

            if (mCursor == null) {
                return 0;
            }

            return mAlphabetIndexer.getSectionForPosition(i);

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return 0;
    }

    private static class ViewHolder {

        private RoundedImageView mImage_profile;
        private TextView mText_UserName;
    }

    private Bitmap GetContactImage(long contactId) {

        Uri contactUri = ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, contactId);
        Uri photoUri = Uri.withAppendedPath(contactUri, ContactsContract.Contacts.Photo.CONTENT_DIRECTORY);
        Cursor cursor = mContext.getContentResolver().query(photoUri,
                new String[]{ContactsContract.Contacts.Photo.PHOTO}, null, null, null);
        if (cursor == null) {
            return null;
        }
        try {
            if (cursor.moveToFirst()) {
                byte[] data = cursor.getBlob(0);
                if (data != null) {
                    return BitmapFactory.decodeStream(new ByteArrayInputStream(data));
                }
            }
        } finally {
            cursor.close();
        }
        return null;
    }
}