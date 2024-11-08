package com.shyam.crmproperty.DataModel.optionCommon;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class GetOptionCommonResponse{

	@SerializedName("data")
	private Data data;

	@SerializedName("message")
	private String message;

	@SerializedName("status")
	private String status;

	public Data getData(){
		return data;
	}

	public String getMessage(){
		return message;
	}

	public String getStatus(){
		return status;
	}


	private ArrayList<String> inquityList = new ArrayList<>();
	private ArrayList<String> inquityPositionList = new ArrayList<>();

	public ArrayList<String> getInquiryList() {
		inquityList.clear();
		inquityList.add(" -- Select Inquiry Type -- ");
		for (int i = 0; i < data.getCustomerInquiryType().size(); i++) {
			inquityList.add(data.getCustomerInquiryType().get(i).getValue());
		}
		return inquityList;
	}

	public ArrayList<String> getInquiryPositionList() {
		inquityPositionList.clear();
		inquityPositionList.add("0");
		for (int i = 0; i < data.getCustomerInquiryType().size(); i++) {
			inquityPositionList.add(data.getCustomerInquiryType().get(i).getValue());
		}
		return inquityPositionList;
	}

}