package com.shyam.crmproperty.DataModel.getCustomer;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

public class DataItem implements Parcelable {

	@SerializedName("date")
	private String date;

	@SerializedName("whatsapp")
	private String whatsapp;

	@SerializedName("profession")
	private String profession;

	@SerializedName("fname")
	private String fname;

	@SerializedName("address")
	private String address;

	@SerializedName("bhk")
	private String bhk;

	@SerializedName("city")
	private String city;

	@SerializedName("mobile")
	private String mobile;

	@SerializedName("project_name")
	private String projectName;

	@SerializedName("type")
	private String type;

	@SerializedName("positive_index")
	private String positiveIndex;

	@SerializedName("lname")
	private String lname;

	@SerializedName("ref")
	private String ref;

	@SerializedName("surname")
	private String surname;

	@SerializedName("fo_count")
	private String foCount;

	@SerializedName("int_landmark")
	private String intLandmark;

	@SerializedName("id")
	private String id;

	@SerializedName("home_landmark")
	private String homeLandmark;

	@SerializedName("remarks")
	private String remarks;

	@SerializedName("budget")
	private String budget;

	@SerializedName("status")
	private String status;

	protected DataItem(Parcel in) {
		date = in.readString();
		whatsapp = in.readString();
		profession = in.readString();
		fname = in.readString();
		address = in.readString();
		bhk = in.readString();
		city = in.readString();
		mobile = in.readString();
		projectName = in.readString();
		type = in.readString();
		positiveIndex = in.readString();
		lname = in.readString();
		ref = in.readString();
		surname = in.readString();
		foCount = in.readString();
		intLandmark = in.readString();
		id = in.readString();
		homeLandmark = in.readString();
		remarks = in.readString();
		budget = in.readString();
		status = in.readString();
	}

	public static final Creator<DataItem> CREATOR = new Creator<DataItem>() {
		@Override
		public DataItem createFromParcel(Parcel in) {
			return new DataItem(in);
		}

		@Override
		public DataItem[] newArray(int size) {
			return new DataItem[size];
		}
	};

	public String getDate(){
		return date;
	}

	public String getWhatsapp(){
		return whatsapp;
	}

	public String getProfession(){
		return profession;
	}

	public String getFname(){
		return fname;
	}

	public String getAddress(){
		return address;
	}

	public String getBhk(){
		return bhk;
	}

	public String getCity(){
		return city;
	}

	public String getMobile(){
		return mobile;
	}

	public String getProjectName(){
		return projectName;
	}

	public String getType(){
		return type;
	}

	public String getPositiveIndex(){
		return positiveIndex;
	}

	public String getLname(){
		return lname;
	}

	public String getRef(){
		return ref;
	}

	public String getSurname(){
		return surname;
	}

	public String getFoCount(){
		return foCount;
	}

	public String getIntLandmark(){
		return intLandmark;
	}

	public String getId(){
		return id;
	}

	public String getHomeLandmark(){
		return homeLandmark;
	}

	public String getRemarks(){
		return remarks;
	}

	public String getBudget(){
		return budget;
	}

	public String getStatus(){
		return status;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(@NonNull Parcel parcel, int i) {
		parcel.writeString(date);
		parcel.writeString(whatsapp);
		parcel.writeString(profession);
		parcel.writeString(fname);
		parcel.writeString(address);
		parcel.writeString(bhk);
		parcel.writeString(city);
		parcel.writeString(mobile);
		parcel.writeString(projectName);
		parcel.writeString(type);
		parcel.writeString(positiveIndex);
		parcel.writeString(lname);
		parcel.writeString(ref);
		parcel.writeString(surname);
		parcel.writeString(foCount);
		parcel.writeString(intLandmark);
		parcel.writeString(id);
		parcel.writeString(homeLandmark);
		parcel.writeString(remarks);
		parcel.writeString(budget);
		parcel.writeString(status);
	}
}