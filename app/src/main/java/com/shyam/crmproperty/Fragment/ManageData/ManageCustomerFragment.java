package com.shyam.crmproperty.Fragment.ManageData;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.shyam.crmproperty.Activity.LoginActivity;
import com.shyam.crmproperty.Adapter.CustomerAdapter;
import com.shyam.crmproperty.Other.PrefHandler;
import com.shyam.crmproperty.R;
import com.shyam.crmproperty.ViewModel.CustomerViewModel;

import java.util.Calendar;

public class ManageCustomerFragment extends Fragment {

    String PageSize = "10";
    String currentPage = "1";
    Context context;
    RelativeLayout mc_st_date_main_two, mc_st_date_main;
    TextView mc_st_date_txt, mc_ed_date_txt;
    ImageView mc_search_icon;
    CardView mc_ok_btn, mc_reload_btn, mc_download_btn;
    String dateFormat = "dd-mm-yyyy";
    EditText mc_search;
    PrefHandler prefHandler;
    RecyclerView mc_recycler_view;
    CustomerAdapter customerAdapter;
    SwipeRefreshLayout mc_refreshLayout;
    private CustomerViewModel customerViewModel;
    private boolean isLastPage = false;
    String preSearchStr = "";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_manage_customer, container, false);

        intView(view);
        clickListener();
        mc_refreshLayout.setOnRefreshListener(this::RefreshData);

        return view;
    }

    private void RefreshData() {
        customerViewModel.refreshCustomers(prefHandler.getACCESS_TOKEN());
    }

    private void clickListener() {

        mc_st_date_main.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);
            DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),
                    (view, year1, monthOfYear, dayOfMonth) -> mc_st_date_txt.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year1),
                    year, month, day);
            Calendar minDate = Calendar.getInstance();
            minDate.set(2024, Calendar.JANUARY, 1);
            datePickerDialog.getDatePicker().setMinDate(minDate.getTimeInMillis());
            datePickerDialog.getDatePicker().setMaxDate(calendar.getTimeInMillis() - 1000);
            datePickerDialog.show();
        });

        mc_st_date_main_two.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);
            DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),
                    (view, year12, monthOfYear, dayOfMonth) -> mc_ed_date_txt.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year12),
                    year, month, day);
            Calendar minDate = Calendar.getInstance();
            minDate.set(2024, Calendar.JANUARY, 1);
            datePickerDialog.getDatePicker().setMinDate(minDate.getTimeInMillis());
            datePickerDialog.getDatePicker().setMaxDate(calendar.getTimeInMillis() - 1000);
            datePickerDialog.show();
        });

        mc_ok_btn.setOnClickListener(v -> {

            String startDate = mc_st_date_txt.getText().toString();
            String endDate = mc_ed_date_txt.getText().toString();

            boolean isStartDateValid = !startDate.equals(dateFormat);
            boolean isEndDateValid = !endDate.equals(dateFormat);

            if (isStartDateValid || isEndDateValid) {
                customerViewModel.dateSortCustomers(prefHandler.getACCESS_TOKEN(), mc_search.getText().toString(), isStartDateValid ? startDate : "", isEndDateValid ? endDate : "");
            }

        });

        mc_reload_btn.setOnClickListener(v -> {
            if (!mc_st_date_txt.getText().toString().equals(dateFormat)) {
                mc_st_date_txt.setText(dateFormat);
            }
            if (!mc_ed_date_txt.getText().toString().equals(dateFormat)) {
                mc_ed_date_txt.setText(dateFormat);
            }

            customerViewModel.dateSortCustomers(prefHandler.getACCESS_TOKEN(), "", "", "");
        });

        mc_download_btn.setOnClickListener(v -> {

        });

        mc_search.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                performSearch();
            }
            return true;
        });

        mc_search_icon.setOnClickListener(v -> performSearch());

    }

    private void performSearch() {
        String searchQuery = mc_search.getText().toString();
        if (searchQuery.trim().isEmpty()) {
            itemSearch("");
            Toast.makeText(getContext(), "Please enter a search term", Toast.LENGTH_SHORT).show();
        } else {
            itemSearch(searchQuery);
        }
    }

    private void itemSearch(String searchQuery) {
        customerViewModel.searchCustomers(prefHandler.getACCESS_TOKEN(), searchQuery);
    }

    private void intView(View view) {
        context = getContext();

        prefHandler = new PrefHandler(context);
        customerAdapter = new CustomerAdapter(context, getActivity());

        mc_recycler_view = view.findViewById(R.id.mc_recycler_view);

        LinearLayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        mc_recycler_view.setLayoutManager(layoutManager);

        mc_recycler_view.setAdapter(customerAdapter);

        mc_refreshLayout = view.findViewById(R.id.mc_refreshLayout);
        mc_st_date_txt = view.findViewById(R.id.mc_st_date_txt);
        mc_ed_date_txt = view.findViewById(R.id.mc_ed_date_txt);
        mc_ok_btn = view.findViewById(R.id.mc_ok_btn);
        mc_reload_btn = view.findViewById(R.id.mc_reload_btn);
        mc_st_date_main_two = view.findViewById(R.id.mc_st_date_main_two);
        mc_st_date_main = view.findViewById(R.id.mc_st_date_main);
        mc_download_btn = view.findViewById(R.id.mc_download_btn);
        mc_search = view.findViewById(R.id.mc_search);
        mc_search_icon = view.findViewById(R.id.mc_search_icon);

        customerViewModel = new ViewModelProvider(this).get(CustomerViewModel.class);

        customerViewModel.getCustomerLiveData().observe((LifecycleOwner) context, customerItems -> customerAdapter.updateList(customerItems));

        customerViewModel.getLoadingLiveData().observe((LifecycleOwner) context, isLoading -> {
            if (isLoading) {
                customerAdapter.addLoadingFooter();
            } else {
                if (mc_refreshLayout.isRefreshing()) {
                    mc_refreshLayout.setRefreshing(false);
                }
                customerAdapter.removeLoadingFooter();
            }
        });

        customerViewModel.getLastPageLiveData().observe((LifecycleOwner) context, lastPage -> isLastPage = lastPage);

        customerViewModel.getLogoutEvent().observe((LifecycleOwner) context, logout -> {

            if (logout) {

                Toast.makeText(context, "Token Expired", Toast.LENGTH_SHORT).show();

                prefHandler.setIsLogin(false);

                startActivity(new Intent(context, LoginActivity.class));
                requireActivity().finish();
            }
        });

        customerViewModel.loadMoreCustomers(prefHandler.getACCESS_TOKEN());

        mc_recycler_view.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                int visibleItemCount = layoutManager.getChildCount();
                int totalItemCount = layoutManager.getItemCount();
                int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();

                if (!isLastPage && Boolean.FALSE.equals(customerViewModel.getLoadingLiveData().getValue()) &&
                        (visibleItemCount + firstVisibleItemPosition >= totalItemCount) &&
                        firstVisibleItemPosition >= 0) {
                    customerViewModel.loadMoreCustomers(prefHandler.getACCESS_TOKEN());
                }
            }
        });

    }

}