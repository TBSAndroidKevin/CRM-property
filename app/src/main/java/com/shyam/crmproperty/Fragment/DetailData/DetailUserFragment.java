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
import com.shyam.crmproperty.Activity.EditActivities.EditUserActivity;
import com.shyam.crmproperty.Activity.MainActivity;
import com.shyam.crmproperty.DataModel.User.ResponseUser;
import com.shyam.crmproperty.DataModel.User.UserItem;
import com.shyam.crmproperty.Other.PrefHandler;
import com.shyam.crmproperty.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class DetailUserFragment extends Fragment {

    Context context;
    PrefHandler prefHandler;
    String user_id;
    UserItem userItem;
    TextView firstName,lastName,email,projectName,groupName,active;
    CardView edit_btn,delete_btn;


    public DetailUserFragment(String user_id){
        this.user_id = user_id;
    }

    @Override
    public void onStart() {
        super.onStart();
        MainActivity.m_header_title.setText("View User Details");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_detail_user, container, false);

        intView(view);

//        du_refreshLayout.setOnRefreshListener(this::loadData);

        loadData();

        clickListener();

        return view;
    }

    private void clickListener() {

        edit_btn.setOnClickListener(v->{

            Intent intent = new Intent(getActivity(), EditUserActivity.class);
            intent.putExtra("UserItemData",userItem);
            startActivity(intent);

        });

    }

    private void loadData(){

        Call<ResponseUser> call = RestClient.post().userDetail(prefHandler.getACCESS_TOKEN(),user_id);

        call.enqueue(new Callback<ResponseUser>() {
            @Override
            public void onResponse(@NonNull Call<ResponseUser> call, @NonNull Response<ResponseUser> response) {

                assert response.body() != null;

                ResponseUser responseUser = response.body();

                if (responseUser.isSuccess()){

//                    Log.d("csdjkhbjuefrifhjvfdfgvgrvbfdnrtfet", new Gson().toJson(responseUser));

                    userItem = responseUser.getData().get(0);

                    setData();

                }else {
                    Log.e("csdjkhbjuefrifhjvfdfgvgrvbfdnrtfet","Else");
                }

            }

            @Override
            public void onFailure(Call<ResponseUser> call, Throwable t) {Log.e("csdjkhbjuefrifhjvfdfgvgrvbfdnrtfet","Error" + "  " + t.getMessage() );}
        });

    }

    private void setData() {

        projectName.setText(userItem.getProjectName());
        firstName.setText(userItem.getFirstName());
        lastName.setText(userItem.getLastName());
        email.setText(userItem.getEmail());
        groupName.setText(userItem.getGroupName());
        active.setText(userItem.getActive());

    }


    private void intView(View view) {
        context = getContext();
        prefHandler = new PrefHandler(context);
        userItem = new UserItem();

//        du_refreshLayout = view.findViewById(R.id.du_refreshLayout);

        firstName = view.findViewById(R.id.du_tab_fname_value);
        lastName = view.findViewById(R.id.du_tab_lname_value);
        email = view.findViewById(R.id.du_tab_email_value);
        projectName = view.findViewById(R.id.du_tab_project_name_value);
        groupName = view.findViewById(R.id.du_tab_group_value);
        active = view.findViewById(R.id.du_tab_active_value);

        edit_btn = view.findViewById(R.id.du_edit_btn);
        delete_btn = view.findViewById(R.id.du_delete_btn);

    }
}