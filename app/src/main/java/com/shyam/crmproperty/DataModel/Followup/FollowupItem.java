package com.shyam.crmproperty.DataModel.Followup;

import android.os.Parcel;
import android.os.Parcelable;

import com.shyam.crmproperty.DataModel.DataConstant.DataConstant;

public class FollowupItem implements Parcelable {

    String id;
    String cust_id;
    String ftime;
    String notify;
    String remarks;
    String status;
    String time;
    String user_id;

    public FollowupItem() {}

    protected FollowupItem(Parcel in) {
        id = in.readString();
        cust_id = in.readString();
        ftime = in.readString();
        notify = in.readString();
        remarks = in.readString();
        status = in.readString();
        time = in.readString();
        user_id = in.readString();
    }

    public static final Creator<FollowupItem> CREATOR = new Creator<FollowupItem>() {
        @Override
        public FollowupItem createFromParcel(Parcel in) {
            return new FollowupItem(in);
        }

        @Override
        public FollowupItem[] newArray(int size) {
            return new FollowupItem[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(cust_id);
        parcel.writeString(ftime);
        parcel.writeString(notify);
        parcel.writeString(remarks);
        parcel.writeString(status);
        parcel.writeString(time);
        parcel.writeString(user_id);
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCust_id() {
        return cust_id;
    }

    public void setCust_id(String cust_id) {
        this.cust_id = cust_id;
    }

    public String getFtime() {
        return ftime;
    }

    public void setFtime(String ftime) {
        this.ftime = ftime;
    }

    public String getNotify() {
        return notify;
    }

    public void setNotify(String notify) {
        this.notify = notify;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getStatus() {
        return status;
    }



    public void setStatus(String status) {
        this.status = status;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getActive() {
        if (status.equals("1")){
            return DataConstant.ACTIVE;
        }else {
            return DataConstant.INACTIVE;
        }
    }
}
