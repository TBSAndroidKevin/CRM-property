package com.shyam.crmproperty.Fragment.DetailData;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.shyam.crmproperty.API.RestClient;
import com.shyam.crmproperty.Activity.EditActivities.EditAmenitiesActivity;
import com.shyam.crmproperty.Activity.EditActivities.EditUserActivity;
import com.shyam.crmproperty.DataModel.Amenities.AmenitiesItem;
import com.shyam.crmproperty.DataModel.Amenities.ResponseAmenities;
import com.shyam.crmproperty.Other.PrefHandler;
import com.shyam.crmproperty.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailAmenityFragment extends Fragment {

    Context context;
    PrefHandler prefHandler;
    TextView da_tab_name_value,da_tab_category_value,da_tab_remark_value;
    CardView edit_btn,delete_btn;
    String id;
    AmenitiesItem amenitiesItem;


    public DetailAmenityFragment(String id) {
        this.id = id;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail_amenity, container, false);

        intView(view);

        clickListener();

        loadData();



        return view;
    }

    private void loadData() {

        Call<ResponseAmenities> call = RestClient.post().amenitiesDetail(prefHandler.getACCESS_TOKEN(),id);

        call.enqueue(new Callback<ResponseAmenities>() {
            @Override
            public void onResponse(@NonNull Call<ResponseAmenities> call, @NonNull Response<ResponseAmenities> response) {

                assert response.body() != null;

                ResponseAmenities responseAmenities = response.body();

                if (responseAmenities.isSuccess()){

//                    Log.d("csdjkhbjuefrifhjvfdfgvgrvbfdnrtfet", new Gson().toJson(responseUser));

                    amenitiesItem = responseAmenities.getData().get(0);

                    setData();

                }else {
                    Log.e("csdjkhbjuefrifhjvfdfgvgrvbfdnrtfet","Else");
                }

            }

            @Override
            public void onFailure(Call<ResponseAmenities> call, Throwable t) {Log.e("csdjkhbjuefrifhjvfdfgvgrvbfdnrtfet","Error" + "  " + t.getMessage() );}
        });


    }

    private void setData() {

        da_tab_name_value.setText(amenitiesItem.getName());
        da_tab_category_value.setText(amenitiesItem.getCat());
        da_tab_remark_value.setText(amenitiesItem.getRemarks());

    }

    private void clickListener() {

        edit_btn.setOnClickListener(v->{
            Intent intent = new Intent(getActivity(), EditAmenitiesActivity.class);
            intent.putExtra("AmenityDataItem",amenitiesItem);
            startActivity(intent);
        });

    }

    private void intView(View view) {

        context = getContext();
        prefHandler = new PrefHandler(context);

        da_tab_name_value = view.findViewById(R.id.da_tab_name_value);
        da_tab_category_value = view.findViewById(R.id.da_tab_category_value);
        da_tab_remark_value = view.findViewById(R.id.da_tab_remark_value);
        edit_btn = view.findViewById(R.id.da_edit_btn);
        delete_btn = view.findViewById(R.id.da_delete_btn);

    }
}