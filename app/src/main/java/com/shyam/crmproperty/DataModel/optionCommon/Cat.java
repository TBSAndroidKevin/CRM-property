package com.shyam.crmproperty.DataModel.optionCommon;

import com.google.gson.annotations.SerializedName;

public class Cat{

	@SerializedName("s")
	private String s;

	@SerializedName("i")
	private String i;

	@SerializedName("co")
	private String co;

	public String getS(){
		return s;
	}

	public String getI(){
		return i;
	}

	public String getCo(){
		return co;
	}
}