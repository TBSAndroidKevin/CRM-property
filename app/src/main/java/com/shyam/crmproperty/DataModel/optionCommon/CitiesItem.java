package com.shyam.crmproperty.DataModel.optionCommon;

import com.google.gson.annotations.SerializedName;

public class CitiesItem{

	@SerializedName("city")
	private String city;

	@SerializedName("id")
	private String id;

	public String getCity(){
		return city;
	}

	public String getId(){
		return id;
	}
}