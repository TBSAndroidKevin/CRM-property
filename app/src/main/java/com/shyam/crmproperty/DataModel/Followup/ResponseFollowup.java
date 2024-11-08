package com.shyam.crmproperty.DataModel.Followup;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class ResponseFollowup {

    @SerializedName("data")
    private List<FollowupItem> data;

    @SerializedName("message")
    private String message;

    @SerializedName("status")
    private String status;

    public List<FollowupItem> getData() {
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
