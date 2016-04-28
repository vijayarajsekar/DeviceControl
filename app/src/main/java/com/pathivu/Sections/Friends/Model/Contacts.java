package com.pathivu.Sections.Friends.Model;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by vijayarajsekar on 28/1/16.
 */
public class Contacts implements Parcelable {

    private String _id;
    private String _name;

    public Contacts(String id, String name) {

        this._id = id;
        this._name = name;
    }

    public String getID() {
        return _id;
    }

    public String getNAME() {
        return _name;
    }


    public Contacts(Parcel in) {
        String[] data = new String[2];

        in.readStringArray(data);
        this._id = data[0];
        this._name = data[1];
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringArray(new String[]{this._id,
                this._name
        });
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Contacts createFromParcel(Parcel in) {
            return new Contacts(in);
        }

        public Contacts[] newArray(int size) {
            return new Contacts[size];
        }
    };
}
