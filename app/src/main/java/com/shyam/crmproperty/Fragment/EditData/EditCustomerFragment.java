package com.shyam.crmproperty.Fragment.EditData;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.shyam.crmproperty.API.RestClient;
import com.shyam.crmproperty.Activity.LoginActivity;
import com.shyam.crmproperty.Activity.MainActivity;
import com.shyam.crmproperty.DataModel.CustomerItem;
import com.shyam.crmproperty.Other.PrefHandler;
import com.shyam.crmproperty.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditCustomerFragment extends Fragment {

    EditText etSurname, etFirstname, etLastname, etDate, etcity, etBhk, etMobile, etWhatsapp, etRemark, etVisitReason;
    Spinner spType, spStatus, spProject;
    CustomerItem customerItem = new CustomerItem();
    String customer_id;
    PrefHandler prefHandler;
    Context context;
    private ArrayList<String> TypePositionList = new ArrayList<>();
    private ArrayList<String> TypeList = new ArrayList<>();
    private ArrayList<String> projectPositionList = new ArrayList<>();
    private ArrayList<String> projectList = new ArrayList<>();

    public EditCustomerFragment(String customer_id) {
        this.customer_id = customer_id;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_customer, container, false);

        MainActivity.m_header_title.setText("Add Customer Details");
        intView(view);
//        getInquiryType();
//        getProjectList();

        return view;
    }

    private void loadData() {

        Call<ResponseBody> call = RestClient.post().customerDetail(prefHandler.getACCESS_TOKEN(), customer_id);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                try {

                    JSONObject jsonObject = new JSONObject(response.body().string());
                    String status = jsonObject.getString("status");

                    if (status.equals("success")) {

                        JSONArray dataArray = jsonObject.optJSONArray("data");

                        JSONObject dataObject = dataArray.getJSONObject(0);
                        customerItem.setId(dataObject.optString("id"));
                        customerItem.setProjectName(dataObject.optString("project_name"));
                        customerItem.setSurname(dataObject.optString("surname"));
                        customerItem.setFirstName(dataObject.optString("fname"));
                        customerItem.setLastName(dataObject.optString("lname"));
                        customerItem.setDate(dataObject.optString("date"));
                        customerItem.setCity(dataObject.optString("city"));
                        customerItem.setBhk(dataObject.optString("bhk"));
                        customerItem.setMobile(dataObject.optString("mobile"));
                        customerItem.setWhatsapp(dataObject.optString("whatsapp"));
                        customerItem.setRemarks(dataObject.optString("remarks"));
                        customerItem.setBudget(dataObject.optString("budget"));
                        customerItem.setPositiveIndex(dataObject.optString("positive_index"));
                        customerItem.setType(dataObject.optString("type"));
                        customerItem.setRef(dataObject.optString("ref"));
                        customerItem.setHomeLandmark(dataObject.optString("home_landmark"));
                        customerItem.setIntLandmark(dataObject.optString("int_landmark"));
                        customerItem.setAddress(dataObject.optString("address"));
                        customerItem.setProfession(dataObject.optString("profession"));
                        customerItem.setFoCount(dataObject.optString("fo_count"));

//                        setData();

                    } else if (status.equals("error")) {

                        Toast.makeText(context, "Token Expired", Toast.LENGTH_SHORT).show();

                        prefHandler.setIsLogin(false);

                        startActivity(new Intent(context, LoginActivity.class));
                        requireActivity().finish();

                    } else {
                        Toast.makeText(context, "Invalid Error", Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception e) {
//                    throw new RuntimeException(e);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {}
        });
    }

  /* private void setData() {

        etProjectName.setText(customerItem.getProjectName());
        projectName.setText(customerItem.getProjectName());
        surName.setText(customerItem.getSurname());
        firstName.setText(customerItem.getFirstName());
        lastName.setText(customerItem.getLastName());
        date.setText(customerItem.getDate());
        city.setText(customerItem.getCity());
        bhk.setText(customerItem.getBhk());
        mobile.setText(customerItem.getMobile());
        whatsapp.setText(customerItem.getWhatsapp());
        remarks.setText(customerItem.getRemarks());
        budget.setText(customerItem.getBudget());
        positiveIndex.setText(customerItem.getPositiveIndex());
        status.setText(customerItem.getStatus());
        type.setText(customerItem.getType());
        ref.setText(customerItem.getRef());
        homeLandmark.setText(customerItem.getHomeLandmark());
        internalLandmark.setText(customerItem.getIntLandmark());
        address.setText(customerItem.getAddress());
        profession.setText(customerItem.getProfession());
        foCount.setText(customerItem.getFoCount());


    }*/

    private void intView(View view) {
        context = getContext();
        prefHandler = new PrefHandler(context);

        spProject = view.findViewById(R.id.spProject);
        etSurname = view.findViewById(R.id.etSurname);
        etFirstname = view.findViewById(R.id.etFname);
        etLastname = view.findViewById(R.id.etLName);
        etDate = view.findViewById(R.id.etDate);
        etcity = view.findViewById(R.id.etCity);
        etBhk = view.findViewById(R.id.etBhk);
        etMobile = view.findViewById(R.id.etMobile);
        etWhatsapp = view.findViewById(R.id.etWtsup);
        etVisitReason = view.findViewById(R.id.etVisitReason);
        etRemark = view.findViewById(R.id.etRemarks);
        spStatus = view.findViewById(R.id.spStatus);
        spType = view.findViewById(R.id.spType);
    }

 /*   private synchronized void getInquiryType() {

        Call<GetOptionCommonResponse> call = RestClient.post().getOption_common(prefHandler.getACCESS_TOKEN(), "1");
        call.enqueue(new Callback<GetOptionCommonResponse>() {
            @Override
            public void onResponse(@NonNull Call<GetOptionCommonResponse> call, @NonNull Response<GetOptionCommonResponse> response) {
                try {
                    assert response.body() != null;

                    if (response.body().getStatus().equals("success")) {
                        spType.setAdapter(getSpinnerAdapter(response.body().getInquiryList()));
                        TypePositionList = response.body().getInquiryPositionList();
                        TypeList = response.body().getInquiryList();

                    } else {
                        Toast.makeText(context, "else", Toast.LENGTH_SHORT).show();

                    }

                } catch (Exception e) {
                    Toast.makeText(context, "d" + e.getMessage(), Toast.LENGTH_SHORT).show();

                    Log.d("exception", " ::::::::: ");
//                    throw new RuntimeException(e);
                }
            }

            @Override
            public void onFailure
                    (@NonNull Call<GetOptionCommonResponse> call, @NonNull Throwable t) {
            }
        });
    }

    private synchronized void getProjectList() {

        Call<GetProjectListResponse> call = RestClient.post().getProjectList(prefHandler.getACCESS_TOKEN(), "1");
        call.enqueue(new Callback<GetProjectListResponse>() {
            @Override
            public void onResponse(@NonNull Call<GetProjectListResponse> call, @NonNull Response<GetProjectListResponse> response) {
                try {
                    assert response.body() != null;

                    if (response.body().getStatus().equals("success")) {
                        spProject.setAdapter(getSpinnerAdapter(response.body().getProjectList()));
                        projectPositionList = response.body().getProjectPositionList();
                        projectList = response.body().getProjectList();

                    } else {
                        Toast.makeText(context, "else", Toast.LENGTH_SHORT).show();

                    }

                } catch (Exception e) {
                    Toast.makeText(context, "d" + e.getMessage(), Toast.LENGTH_SHORT).show();

                    Log.d("exception", " ::::::::: ");
                }
            }

            @Override
            public void onFailure
                    (@NonNull Call<GetProjectListResponse> call, @NonNull Throwable t) {
            }
        });
    }
*/

}