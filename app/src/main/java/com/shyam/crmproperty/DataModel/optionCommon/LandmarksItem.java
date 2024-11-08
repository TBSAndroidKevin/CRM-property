package com.shyam.crmproperty.DataModel.optionCommon;

import com.google.gson.annotations.SerializedName;

public class LandmarksItem{

	@SerializedName("id")
	private String id;

	@SerializedName("landmark")
	private String landmark;

	public String getId(){
		return id;
	}

	public String getLandmark(){
		return landmark;
	}
}