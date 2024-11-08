package com.shyam.crmproperty.DataModel.Amenities;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseAmenities {

    @SerializedName("data")
    private List<AmenitiesItem> data;

    @SerializedName("message")
    private String message;

    @SerializedName("status")
    private String status;

    public List<AmenitiesItem> getData() {
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
