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
import android.widget.ImageView;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.pathivu.R;
import com.pathivu.Sections.Friends.Interfaces.ContactsQuery;
import com.pathivu.Sections.Friends.Model.Contacts;
import com.pathivu.Utils.RoundedImageView;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by vijayarajsekar on 28/1/16.
 */
public class ContactsItemsAdapter extends BaseAdapter {

    private String TAG = ContactsItemsAdapter.class.getSimpleName();

    private Context mContext;

    private ViewHolder holder;
    private LayoutInflater l_Inflater;
    public ArrayList<String> mContactList = null;

    public ContactsItemsAdapter(Activity context, ArrayList<String> mList) {

        this.mContext = context;

        l_Inflater = LayoutInflater.from(mContext);
        this.mContactList = mList;
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

            convertView = l_Inflater.inflate(R.layout.contact_list_items, parent, false);

            holder = new ViewHolder();

            holder.mImage_profile = (ImageView) convertView.findViewById(R.id.image_icon_type);

            holder.mText_UserName = (TextView) convertView.findViewById(R.id.text_contact_type);

            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.mText_UserName.setText(mContactList.get(position).toString());

        if (mContactList.get(position).toString().contains("@") || mContactList.get(position).toString().toString().endsWith(".com") ||
                mContactList.get(position).toString().toString().endsWith(".org") || mContactList.get(position).toString().toString().endsWith(".net")) {
            holder.mImage_profile.setImageResource(R.drawable.ic_email_dialog);
        } else {
            holder.mImage_profile.setImageResource(R.drawable.ic_message_dialog);
        }

        return convertView;
    }

    private static class ViewHolder {

        private ImageView mImage_profile;
        private TextView mText_UserName;
    }
}