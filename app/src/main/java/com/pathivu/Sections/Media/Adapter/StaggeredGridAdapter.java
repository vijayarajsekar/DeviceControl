package com.pathivu.Sections.Media.Adapter;

import android.content.Context;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Toast;

import com.pathivu.R;
import com.pathivu.Sections.Media.StaggeredView.Util.DynamicHeightImageView;
import com.pathivu.Sections.Media.StaggeredView.Util.DynamicHeightTextView;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
import java.util.Random;


/**
 * Created by vijayarajsekar on 3/2/16.
 */
public class StaggeredGridAdapter extends ArrayAdapter<String> {

    private static final String TAG = "SampleAdapter";

    static class ViewHolder {
        private DynamicHeightImageView txtLineOne;
    }

    private final LayoutInflater mLayoutInflater;
    private final Random mRandom;
    private Context mContext;
    private static final SparseArray<Double> sPositionHeightRatios = new SparseArray<Double>();

    public StaggeredGridAdapter(final Context context, final int textViewResourceId) {
        super(context, textViewResourceId);
        mLayoutInflater = LayoutInflater.from(context);
        mRandom = new Random();
        this.mContext = context;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {

        ViewHolder vh;
        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.item_media, parent, false);
            vh = new ViewHolder();
            vh.txtLineOne = (DynamicHeightImageView) convertView.findViewById(R.id.txt_line1);

            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }

        double positionHeight = getPositionRatio(position);

        Log.d(TAG, "getView position:" + position + " h:" + positionHeight);

        vh.txtLineOne.setHeightRatio(positionHeight);

//        vh.txtLineOne.setImageResource(Integer.parseInt(getItem(position)));
//        Picasso.with(mContext).load(getItem(position)).into(vh.txtLineOne);

        Picasso.with(mContext).load(new File(getItem(position))).into(vh.txtLineOne);

        return convertView;
    }

    private double getPositionRatio(final int position) {
        double ratio = sPositionHeightRatios.get(position, 0.0);

        if (ratio == 0) {
            ratio = getRandomHeightRatio();
            sPositionHeightRatios.append(position, ratio);
            Log.d(TAG, "getPositionRatio:" + position + " ratio:" + ratio);
        }
        return ratio;
    }

    private double getRandomHeightRatio() {
        return (mRandom.nextDouble() / 2.0) + 1.0; // height will be 1.0 - 1.5 the width
    }
}
