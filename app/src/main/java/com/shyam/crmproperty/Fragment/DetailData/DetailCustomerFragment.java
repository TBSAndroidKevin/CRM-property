package com.shyam.crmproperty.Fragment.DetailData;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.shyam.crmproperty.API.RestClient;
import com.shyam.crmproperty.Activity.EditActivities.EditCustomerActivity;
import com.shyam.crmproperty.Activity.MainActivity;
import com.shyam.crmproperty.DataModel.CustomerItem;
import com.shyam.crmproperty.DataModel.getCustomer.DataItem;
import com.shyam.crmproperty.DataModel.getCustomer.GetCustomerResponse;
import com.shyam.crmproperty.Other.PrefHandler;
import com.shyam.crmproperty.R;

import org.json.JSONObject;

import java.util.List;
import java.util.Objects;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailCustomerFragment extends Fragment {

    TextView projectName, surName, firstName, lastName, date, city, bhk, mobile, whatsapp, remarks, budget, positiveIndex, status, type, ref, homeLandmark, internalLandmark, address, profession, foCount;
    PrefHandler prefHandler;
    String customer_id;
    Context context;
    CardView dc_edit_btn, dc_delete_btn;
    SwipeRefreshLayout dc_refreshLayout;
    CustomerItem customerItem = new CustomerItem();
    List<DataItem> dataItems;

    public DetailCustomerFragment(String customer_id) {
        this.customer_id = customer_id;
    }

    @Override
    public void onStart() {
        super.onStart();
        MainActivity.m_header_title.setText("View Customer Details");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_detail_customer, container, false);

        intView(view);
        getInquiryType();

//        dc_refreshLayout.setOnRefreshListener(this::loadData);

        clickListener();

        return view;
    }

    private void clickListener() {

        dc_edit_btn.setOnClickListener(v -> {
//            startActivity(new Intent(getActivity(), EditCustomerActivity.class));
            Intent intent = new Intent(getActivity(), EditCustomerActivity.class);
            intent.putExtra("customerMasterList", dataItems.get(0));
            startActivity(intent);
        });

        dc_delete_btn.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("Delete Customer");
            builder.setMessage("Are you sure you want to delete this Customer ?");

            builder.setPositiveButton("Delete", (dialog, which) -> {
                deleteItem();
                dialog.dismiss();
            });

            builder.setNegativeButton("Cancel", (dialog, which) -> {
                dialog.dismiss();
            });

            AlertDialog dialog = builder.create();
            dialog.show();
        });
    }

    private void deleteItem() {

        Call<ResponseBody> call = RestClient.post().deleteCustomer(prefHandler.getACCESS_TOKEN(), customer_id);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                try {
                    JSONObject jsonObject = new JSONObject(response.body().string());
                    String status = jsonObject.getString("status");
    
                    if (status.equals("success")) {
                        requireActivity().onBackPressed();
                        Toast.makeText(context, "Customer Deleted", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(context, "Customer Deleting Error", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception ignored) {}
            }

            @Override
            public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {}
        });


    }




/*
    private void loadData() {

        dc_refreshLayout.setRefreshing(true);

        Call<ResponseBody> call = RestClient.post().customerDetail(prefHandler.getACCESS_TOKEN(), customer_id);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                try {

                    JSONObject jsonObject = new JSONObject(response.body().string());
                    String status = jsonObject.getString("status");


                    if (status.equals("success")) {

                        dc_refreshLayout.setRefreshing(false);

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

                        setData();

                    } else if (status.equals("error")) {

                        Toast.makeText(context, "Token Expired", Toast.LENGTH_SHORT).show();

                        prefHandler.setIsLogin(false);

                        startActivity(new Intent(context, LoginActivity.class));
                        requireActivity().finish();

                    } else {
                        Toast.makeText(context, "Invalid Error", Toast.LENGTH_SHORT).show();
                    }

                    dc_refreshLayout.setRefreshing(false);

                } catch (Exception e) {
//                    throw new RuntimeException(e);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                dc_refreshLayout.setRefreshing(true);
            }
        });

    }*/

    private synchronized void getInquiryType() {

        Call<GetCustomerResponse> call = RestClient.post().getCustomerList(prefHandler.getACCESS_TOKEN(), customer_id);
        call.enqueue(new Callback<GetCustomerResponse>() {
            @Override
            public void onResponse(@NonNull Call<GetCustomerResponse> call, @NonNull Response<GetCustomerResponse> response) {
                try {
                    assert response.body() != null;

                    if (response.body().getStatus().equals("success")) {

                        dataItems = response.body().getData();
                        setData();

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
                    (@NonNull Call<GetCustomerResponse> call, @NonNull Throwable t) {
            }
        });
    }

    private void setData() {
        projectName.setText(dataItems.get(0).getProjectName());
        surName.setText(dataItems.get(0).getSurname());
        firstName.setText(dataItems.get(0).getFname());
        lastName.setText(dataItems.get(0).getLname());
        date.setText(dataItems.get(0).getDate());
        city.setText(dataItems.get(0).getCity());
        bhk.setText(dataItems.get(0).getBhk());
        mobile.setText(dataItems.get(0).getMobile());
        whatsapp.setText(dataItems.get(0).getWhatsapp());
        remarks.setText(dataItems.get(0).getRemarks());
        budget.setText(dataItems.get(0).getBudget());
        positiveIndex.setText(dataItems.get(0).getPositiveIndex());
        status.setText(dataItems.get(0).getStatus());
        type.setText(dataItems.get(0).getType());
        ref.setText(dataItems.get(0).getRef());
        homeLandmark.setText(dataItems.get(0).getHomeLandmark());
        internalLandmark.setText(dataItems.get(0).getIntLandmark());
        address.setText(dataItems.get(0).getAddress());
        profession.setText(dataItems.get(0).getProfession());
        foCount.setText(dataItems.get(0).getFoCount());
    }

    private void intView(View view) {
        context = getContext();
        prefHandler = new PrefHandler(context);
//        dc_refreshLayout = view.findViewById(R.id.dc_refreshLayout);
        dc_edit_btn = view.findViewById(R.id.dc_edit_btn);
        dc_delete_btn = view.findViewById(R.id.dc_delete_btn);

        projectName = view.findViewById(R.id.dc_tab_project_name_value);
        surName = view.findViewById(R.id.dc_tab_surname_value);
        firstName = view.findViewById(R.id.dc_tab_fname_value);
        lastName = view.findViewById(R.id.dc_tab_lname_value);
        date = view.findViewById(R.id.dc_tab_date_value);
        city = view.findViewById(R.id.dc_tab_city_value);
        bhk = view.findViewById(R.id.dc_tab_bhk_value);
        mobile = view.findViewById(R.id.dc_tab_mobile_value);
        whatsapp = view.findViewById(R.id.dc_tab_whatsapp_value);
        remarks = view.findViewById(R.id.dc_tab_remarks_value);
        budget = view.findViewById(R.id.dc_tab_budget_value);
        positiveIndex = view.findViewById(R.id.dc_tab_positive_index_value);
        status = view.findViewById(R.id.dc_tab_status_value);
        type = view.findViewById(R.id.dc_tab_type_value);
        ref = view.findViewById(R.id.dc_tab_ref_value);
        homeLandmark = view.findViewById(R.id.dc_tab_home_landmark_value);
        internalLandmark = view.findViewById(R.id.dc_tab_internal_landmark_value);
        address = view.findViewById(R.id.dc_tab_address_value);
        profession = view.findViewById(R.id.dc_tab_profession_value);
        foCount = view.findViewById(R.id.dc_tab_fo_count_value);


    }
}