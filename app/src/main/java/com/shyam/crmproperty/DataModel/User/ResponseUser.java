package com.shyam.crmproperty.DataModel.User;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseUser {

    @SerializedName("data")
    private List<UserItem> data;

    @SerializedName("message")
    private String message;

    @SerializedName("status")
    private String status;

    public List<UserItem> getData() {
        return data;
    }

    public String getMessage() {
        return message;
    }

    public String getStatus() {
        return status;
    }

    public boolean isSuccess(){
        return status.equals("success");
    }

    public boolean isTokenExpire(){
        return message.equals("API key invalid");
    }

}
