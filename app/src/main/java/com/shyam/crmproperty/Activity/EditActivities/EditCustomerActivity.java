package com.shyam.crmproperty.Activity.EditActivities;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.shyam.crmproperty.API.RestClient;
import com.shyam.crmproperty.Activity.BaseActivity;
import com.shyam.crmproperty.DataModel.getCustomer.DataItem;
import com.shyam.crmproperty.DataModel.getProject.GetProjectListResponse;
import com.shyam.crmproperty.DataModel.optionCommon.GetOptionCommonResponse;
import com.shyam.crmproperty.Other.PrefHandler;
import com.shyam.crmproperty.R;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class EditCustomerActivity extends BaseActivity {

    EditText etSurname, etFirstname, etLastname, etDate, etcity, etBhk, etMobile, etWhatsapp, etRemark, etVisitReason;
    Spinner spType, spStatus, spProject;
    CardView cvBtnAdd;
    TextView tvSubmit;
    PrefHandler prefHandler;
    private DataItem customerData;
    Context context;
    private ArrayList<String> TypePositionList = new ArrayList<>();
    private ArrayList<String> TypeList = new ArrayList<>();
    private ArrayList<String> projectPositionList = new ArrayList<>();
    private ArrayList<String> projectList = new ArrayList<>();
    private boolean isDie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_edit_customer);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        prefHandler = new PrefHandler(getBaseContext());
        initToolbar();
        findViewById();
        setListeners();
        getExtras();
        getInquiryType();
        getProjectList();
    }

    private void getExtras() {
        if (getIntent().getExtras() != null) {
            setTitle(getResources().getString(R.string.edit_customer));
            tvSubmit.setText(getResources().getString(R.string.edit));

            customerData = getIntent().getExtras().getParcelable("customerMasterList");
            Log.e("customerData", " : " + customerData.getProjectName());
            etSurname.setText(customerData.getSurname());
            etFirstname.setText(customerData.getFname());
            etLastname.setText(customerData.getLname());
            etDate.setText(customerData.getDate());
            etcity.setText(customerData.getCity());
            etBhk.setText(customerData.getBhk());
            etMobile.setText(customerData.getMobile());
            etWhatsapp.setText(customerData.getWhatsapp());
            etRemark.setText(customerData.getRemarks());
            etVisitReason.setText(customerData.getRemarks());

        } else {
            setTitle(getResources().getString(R.string.add_customer));
            tvSubmit.setText(getResources().getString(R.string.add));
        }
    }

    @Override
    public void findViewById() {

        spProject = findViewById(R.id.spProject);
        etSurname = findViewById(R.id.etSurname);
        etFirstname = findViewById(R.id.etFname);
        etLastname = findViewById(R.id.etLName);
        etDate = findViewById(R.id.etDate);
        etcity = findViewById(R.id.etCity);
        etBhk = findViewById(R.id.etBhk);
        etMobile = findViewById(R.id.etMobile);
        etWhatsapp = findViewById(R.id.etWtsup);
        etVisitReason = findViewById(R.id.etVisitReason);
        etRemark = findViewById(R.id.etRemarks);
        spStatus = findViewById(R.id.spStatus);
        spType = findViewById(R.id.spType);
        cvBtnAdd = findViewById(R.id.cv_btn_add);
        tvSubmit = findViewById(R.id.tvSubmit);
    }

    @Override
    public void setListeners() {
        spType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (customerData != null && !spType.getSelectedItem().toString().equals(customerData.getType()) && !isDie) {
                    for (int i = 0; i < TypeList.size(); i++) {
                        if (TypeList.get(i).equals(customerData.getType())) {
                            spType.setSelection(i);
                            isDie = true;
                        }
                    }
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    @Override
    protected boolean isValidate() {
        return false;
    }


    private synchronized void getInquiryType() {

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
                        Log.e("size", " :" + response.body().getInquiryList());

                        if (customerData != null) {
                            for (int i = 0; i < response.body().getInquiryList().size(); i++) {
                                if (TypeList.get(i).equals(customerData.getType())) {
                                    spType.setSelection(i);
                                }
                            }
                        }
                    } else {
                        Toast.makeText(context, "else", Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception e) {
                    Toast.makeText(context, "d" + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<GetOptionCommonResponse> call, @NonNull Throwable t) {
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
                        if (customerData != null) {
                            for (int i = 0; i < response.body().getProjectList().size(); i++) {

                                if (projectList.get(i).equals(customerData.getProjectName())) {
                                    spProject.setSelection(i);

                                }
                            }
                        }

                    } else {
                        Toast.makeText(context, "else", Toast.LENGTH_SHORT).show();

                    }

                } catch (Exception e) {
                    Toast.makeText(context, "d" + e.getMessage(), Toast.LENGTH_SHORT).show();

                    Log.d("exception", " ::::::::: ");
                }
            }

            @Override
            public void onFailure(@NonNull Call<GetProjectListResponse> call, @NonNull Throwable t) {
            }
        });
    }
}