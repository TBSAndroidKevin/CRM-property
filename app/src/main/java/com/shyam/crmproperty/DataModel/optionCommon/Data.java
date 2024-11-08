package com.shyam.crmproperty.DataModel.optionCommon;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class Data{

	@SerializedName("amenities")
	private List<AmenitiesItem> amenities;

	@SerializedName("cities")
	private List<CitiesItem> cities;

	@SerializedName("cat")
	private Cat cat;

	@SerializedName("groups")
	private List<GroupsItem> groups;

	@SerializedName("customer_inquiry_type")
	private List<CustomerInquiryTypeItem> customerInquiryType;

	@SerializedName("landmarks")
	private List<LandmarksItem> landmarks;

	@SerializedName("type")
	private Type type;

	@SerializedName("reporting")
	private Reporting reporting;

	public List<AmenitiesItem> getAmenities(){
		return amenities;
	}

	public List<CitiesItem> getCities(){
		return cities;
	}

	public Cat getCat(){
		return cat;
	}

	public List<GroupsItem> getGroups(){
		return groups;
	}

	public List<CustomerInquiryTypeItem> getCustomerInquiryType(){
		return customerInquiryType;
	}

	public List<LandmarksItem> getLandmarks(){
		return landmarks;
	}

	public Type getType(){
		return type;
	}

	public Reporting getReporting(){
		return reporting;
	}
}