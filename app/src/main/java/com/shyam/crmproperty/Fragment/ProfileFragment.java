package com.shyam.crmproperty.Fragment;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.shyam.crmproperty.Activity.LoginActivity;
import com.shyam.crmproperty.Adapter.CustomerAdapter;
import com.shyam.crmproperty.Other.PrefHandler;
import com.shyam.crmproperty.R;
import com.shyam.crmproperty.ViewModel.CustomerViewModel;

import java.util.Calendar;

public class ProfileFragment extends Fragment {

    Context context;
    Button btnLogout;
    PrefHandler prefHandler;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        intView(view);
        clickListener();

        return view;
    }

    private void clickListener() {
        btnLogout.setOnClickListener(view -> {
            prefHandler.setIsLogin(false);
            startActivity(new Intent(getContext(), LoginActivity.class));
        });
    }

    private void intView(View view) {
        context = getContext();
        prefHandler = new PrefHandler(context);
        btnLogout = view.findViewById(R.id.btnLogout);
    }

}