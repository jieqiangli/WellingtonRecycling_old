package com.fuzz.wellingtonrecycling.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by admin on 10/07/2014.
 */
public class RecyclingSearchResult implements Parcelable {
    public enum RECYCLING_TYPE {GLASS_CRATE, WHEELIE_BIN}

    private String key;
    private String street;
    private String suburb;
    private RECYCLING_TYPE recyclingType;

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(key);
        dest.writeString(street);
        dest.writeString(suburb);
    }

    public RecyclingSearchResult(String key, String value) {
        this.key = key;
        int commaIndex = value.indexOf(',');
        this.street = value.substring(0, commaIndex);
        this.suburb = value.substring(commaIndex + 1, value.length());
    }

    public String getKey() {
        return key;
    }

    public String getStreet() {
        return street;
    }

    public String getSuburb() {
        return suburb;
    }

    public RECYCLING_TYPE getRecyclingType() {
        return recyclingType;
    }
}
