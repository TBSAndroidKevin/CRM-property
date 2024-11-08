package com.shyam.crmproperty.DataModel.getProject;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.SerializedName;

public class GetProjectListResponse{

	@SerializedName("data")
	private List<DataItem> data;

	@SerializedName("message")
	private String message;

	@SerializedName("status")
	private String status;

	public List<DataItem> getData(){
		return data;
	}

	public String getMessage(){
		return message;
	}

	public String getStatus(){
		return status;
	}

	private ArrayList<String> projectList = new ArrayList<>();
	private ArrayList<String> projectPositionList = new ArrayList<>();

	public ArrayList<String> getProjectList() {
		projectList.clear();
		projectList.add(" -- Project List -- ");
		for (int i = 0; i < data.size(); i++) {
			projectList.add(data.get(i).getName());
		}
		return projectList;
	}

	public ArrayList<String> getProjectPositionList() {
		projectPositionList.clear();
		projectPositionList.add("0");
		for (int i = 0; i < data.size(); i++) {
			projectPositionList.add(data.get(i).getId());
		}
		return projectPositionList;
	}
}