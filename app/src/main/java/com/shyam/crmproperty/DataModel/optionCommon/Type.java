package com.shyam.crmproperty.DataModel.optionCommon;

import com.google.gson.annotations.SerializedName;

public class Type{

	@SerializedName("c")
	private String c;

	@SerializedName("t")
	private String t;

	@SerializedName("rh")
	private String rh;

	@SerializedName("rl")
	private String rl;

	public String getC(){
		return c;
	}

	public String getT(){
		return t;
	}

	public String getRh(){
		return rh;
	}

	public String getRl(){
		return rl;
	}
}