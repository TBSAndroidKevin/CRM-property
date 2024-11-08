package com.shyam.crmproperty.DataModel.optionCommon;

import com.google.gson.annotations.SerializedName;

public class AmenitiesItem{

	@SerializedName("name")
	private String name;

	@SerializedName("id")
	private String id;

	public String getName(){
		return name;
	}

	public String getId(){
		return id;
	}
}