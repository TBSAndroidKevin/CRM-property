package com.shyam.crmproperty.DataModel.getCustomer;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class GetCustomerResponse{

	@SerializedName("per_page")
	private int perPage;

	@SerializedName("data")
	private List<DataItem> data;

	@SerializedName("total_records")
	private String totalRecords;

	@SerializedName("page")
	private int page;

	@SerializedName("message")
	private String message;

	@SerializedName("status")
	private String status;

	public int getPerPage(){
		return perPage;
	}

	public List<DataItem> getData(){
		return data;
	}

	public String getTotalRecords(){
		return totalRecords;
	}

	public int getPage(){
		return page;
	}

	public String getMessage(){
		return message;
	}

	public String getStatus(){
		return status;
	}
}