package com.shyam.crmproperty.DataModel.optionCommon;

import com.google.gson.annotations.SerializedName;

public class CustomerInquiryTypeItem{

	@SerializedName("type")
	private String type;

	@SerializedName("value")
	private String value;

	public String getType(){
		return type;
	}

	public String getValue(){
		return value;
	}
}