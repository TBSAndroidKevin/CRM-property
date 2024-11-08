package com.shyam.crmproperty.API;

import com.shyam.crmproperty.DataModel.Amenities.ResponseAmenities;
import com.shyam.crmproperty.DataModel.Followup.ResponseFollowup;
import com.shyam.crmproperty.DataModel.User.ResponseUser;
import com.shyam.crmproperty.DataModel.getCustomer.GetCustomerResponse;
import com.shyam.crmproperty.DataModel.getProject.GetProjectListResponse;
import com.shyam.crmproperty.DataModel.optionCommon.GetOptionCommonResponse;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface ApiService {

    String BASEURL = "https://mypropertycrm.com/api/";

    // Other Routes

    @POST("Auth/dashboard")
    Call<ResponseBody> getDashboard(
            @Header("x-api-key") String apiKey
    );

    // Verification Routes

    @FormUrlEncoded
    @POST("Auth/login")
    Call<ResponseBody> login(
            @Field("email") String email,
            @Field("password") String password
    );

    @FormUrlEncoded
    @POST("Auth/Generate_otp")
    Call<ResponseBody> generateOtp(
            @Field("email") String email
    );

    @FormUrlEncoded
    @POST("Auth/OTP_Verification")
    Call<ResponseBody> otpVerification(
            @Field("email") String email,
            @Field("otp") String otp
    );

    @FormUrlEncoded
    @POST("Auth/forgot_password")
    Call<ResponseBody> forgotPassword(
            @Field("email") String email,
            @Field("otp") String otp,
            @Field("password") String password
    );

    // Amenities Routes

    @FormUrlEncoded
    @POST("Amenity/list")
    Call<ResponseAmenities> amenitiesList(
            @Header("x-api-key") String apiKey,
            @Field("page") String page,
            @Field("per_page") String per_page,
            @Field("Search") String search
    );

    @FormUrlEncoded
    @POST("Amenity/list")
    Call<ResponseAmenities> amenitiesDetail(
            @Header("x-api-key") String apiKey,
            @Field("id") String id
    );

    // User Routes

    @FormUrlEncoded
    @POST("User/list")
    Call<ResponseUser> userDetail(
            @Header("x-api-key") String apiKey,
            @Field("id") String id
    );


    @FormUrlEncoded
    @POST("User/list")
    Call<ResponseUser> userList(
            @Header("x-api-key") String apiKey,
            @Field("page") String page,
            @Field("per_page") String per_page,
            @Field("Search") String search
    );


    // Customer Routes

    @FormUrlEncoded
    @POST("Customer/list")
    Call<ResponseBody> customerDetail(
            @Header("x-api-key") String apiKey,
            @Field("id") String id
    );

    @FormUrlEncoded
    @POST("Customer/list")
    Call<GetCustomerResponse> getCustomerList(
            @Header("x-api-key") String apiKey,
            @Field("id") String id
    );

    // Update Customer
    @FormUrlEncoded
    @POST("Customer/insert_udpate")
    Call<ResponseBody> customerUpdate(
            @Header("x-api-key") String apiKey,
            @Field("id") String id,
            @Field("project_id") String project_id,
            @Field("type") String type,
            @Field("date") String date,
            @Field("surname") String surname,
            @Field("fname") String fname,
            @Field("lname") String lname,
            @Field("city") String city,
            @Field("bhk") String bhk,
            @Field("mobile") String mobile,
            @Field("whatsapp") String whatsapp,
            @Field("remarks") String remarks,
            @Field("visit_reason") String visit_reason,
            @Field("status") String status
    );


    @FormUrlEncoded
    @POST("Customer/list")
    Call<ResponseBody> customerList(
            @Header("x-api-key") String apiKey,
            @Field("page") String page,
            @Field("per_page") String per_page,
            @Field("date_from") String date_from,
            @Field("date_to") String date_to,
            @Field("Search") String search
    );

    @FormUrlEncoded
    @POST("Customer/delete")
    Call<ResponseBody> deleteCustomer(
            @Header("x-api-key") String apiKey,
            @Field("id") String id
    );


    // Optional Routs

    @FormUrlEncoded
    @POST("Auth/option_common")
    Call<GetOptionCommonResponse>   getOption_common(
            @Header("x-api-key") String apiKey,
            @Field("id") String id
    );

    @FormUrlEncoded
    @POST("Customer/project_list")
    Call<GetProjectListResponse> getProjectList(
            @Header("x-api-key") String apiKey,
            @Field("id") String id
    );

    // FollowUp Routes

    @FormUrlEncoded
    @POST("Followup/list")
    Call<ResponseFollowup> followUpList(
            @Header("x-api-key") String apiKey,
            @Field("page") String page,
            @Field("per_page") String per_page,
            @Field("Search") String  search
    );


}





