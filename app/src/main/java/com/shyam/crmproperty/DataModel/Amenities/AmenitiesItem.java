package com.shyam.crmproperty.DataModel.Amenities;

import android.os.Parcel;
import android.os.Parcelable;

public class AmenitiesItem implements Parcelable {

    private String id;
    private String cat;
    private String name;
    private String remarks;

    public AmenitiesItem() {
        // Default constructor
    }

    protected AmenitiesItem(Parcel in) {
        id = in.readString();
        cat = in.readString();
        name = in.readString();
        remarks = in.readString();
    }

    public static final Creator<AmenitiesItem> CREATOR = new Creator<AmenitiesItem>() {
        @Override
        public AmenitiesItem createFromParcel(Parcel in) {
            return new AmenitiesItem(in);
        }

        @Override
        public AmenitiesItem[] newArray(int size) {
            return new AmenitiesItem[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCat() {
        return cat;
    }

    public void setCat(String cat) {
        this.cat = cat;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(cat);
        dest.writeString(name);
        dest.writeString(remarks);
    }
}
