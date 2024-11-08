package com.shyam.crmproperty.DataModel.optionCommon;

import com.google.gson.annotations.SerializedName;

public class Reporting{

	@SerializedName("0")
	private String jsonMember0;

	@SerializedName("sms")
	private String sms;

	@SerializedName("tele")
	private String tele;

	@SerializedName("both")
	private String both;

	public String getJsonMember0(){
		return jsonMember0;
	}

	public String getSms(){
		return sms;
	}

	public String getTele(){
		return tele;
	}

	public String getBoth(){
		return both;
	}
}