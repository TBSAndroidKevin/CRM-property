package com.shyam.crmproperty.DataModel.User;

import android.os.Parcel;
import android.os.Parcelable;

import com.shyam.crmproperty.DataModel.DataConstant.DataConstant;

public class UserItem implements Parcelable {

    private String id;
    private String first_name;
    private String last_name;
    private String email;
    private String project_name;
    private String group_name;
    private String active;

    public UserItem() {
        // Default constructor
    }

    // Getters and setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstName() {
        return first_name;
    }

    public void setFirstName(String firstName) {
        this.first_name = firstName;
    }

    public String getLastName() {
        return last_name;
    }

    public void setLastName(String lastName) {
        this.last_name = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getProjectName() {
        return project_name;
    }

    public void setProjectName(String projectName) {
        this.project_name = projectName;
    }

    public String getGroupName() {
        return group_name;
    }

    public void setGroupName(String groupName) {
        this.group_name = groupName;
    }

    public String getActive() {
        return active.equals("1") ? DataConstant.ACTIVE : DataConstant.INQUIRY;
    }

    public void setActive(String active) {
        this.active = active;
    }

    // Parcelable implementation
    protected UserItem(Parcel in) {
        id = in.readString();
        first_name = in.readString();
        last_name = in.readString();
        email = in.readString();
        project_name = in.readString();
        group_name = in.readString();
        active = in.readString();
    }

    public static final Creator<UserItem> CREATOR = new Creator<UserItem>() {
        @Override
        public UserItem createFromParcel(Parcel in) {
            return new UserItem(in);
        }

        @Override
        public UserItem[] newArray(int size) {
            return new UserItem[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(first_name);
        dest.writeString(last_name);
        dest.writeString(email);
        dest.writeString(project_name);
        dest.writeString(group_name);
        dest.writeString(active);
    }
}
