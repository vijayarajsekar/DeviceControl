package com.pathivu.Preferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.pathivu.R;


/**
 * Created by VijayarajSekar on 16/3/15.
 */

public class Preferences {

    private static final String TAG = Preferences.class.getSimpleName();

    private static SharedPreferences mPreferences;
    private Editor mEditor;
    private Context mContext;
    private int PRIVATE_MODE = 0;

    private String mIsRead;

    /**
     * Constructor
     */

    private static Preferences instance;

    public Preferences(Context ctx) {
        this.mContext = ctx;
        mPreferences = mContext.getSharedPreferences(ctx.getString(R.string.str_pref_name), PRIVATE_MODE);
        mEditor = mPreferences.edit();
    }

    public boolean getTutorialStatus() {
        return mPreferences.getBoolean(mIsRead, false);
    }

    public void setTutorialStatus(boolean values) {
        mEditor.putBoolean(mIsRead, values);
        mEditor.commit();
    }
}
