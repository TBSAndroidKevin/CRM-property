package com.shyam.crmproperty.Fragment.ManageData;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
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
import android.widget.Toast;

import com.shyam.crmproperty.Activity.LoginActivity;
import com.shyam.crmproperty.Activity.MainActivity;
import com.shyam.crmproperty.Adapter.AmenitiesAdapter;
import com.shyam.crmproperty.Adapter.UserAdapter;
import com.shyam.crmproperty.Other.PrefHandler;
import com.shyam.crmproperty.R;
import com.shyam.crmproperty.ViewModel.AmenitiesViewModel;


public class ManageAmenitiesFragment extends Fragment {

    SwipeRefreshLayout ma_refreshLayout;
    EditText ma_search;
    ImageView ma_search_icon;
    AmenitiesViewModel amenitiesViewModel;
    PrefHandler prefHandler;
    Context context;
    RecyclerView ma_recycler_view;
    AmenitiesAdapter amenitiesAdapter;
    private boolean isLastPage = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_manage_amenities, container, false);

        intView(view);

        MainActivity.m_header_title.setText("Manage Amenities");

        clickListener();

        ma_refreshLayout.setOnRefreshListener(this::RefreshData);

        return view;
    }

    private void clickListener() {

        ma_search.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                performSearch();
            }
            return true;
        });

        ma_search_icon.setOnClickListener(v -> performSearch());


    }

    private void performSearch() {


        String searchQuery = ma_search.getText().toString();
        if (searchQuery.trim().isEmpty()) {
            itemSearch("");
            Toast.makeText(getContext(), "Please enter a search term", Toast.LENGTH_SHORT).show();
        } else {
            itemSearch(searchQuery);
        }


    }

    private void itemSearch(String s) {
        amenitiesViewModel.searchAmenity(prefHandler.getACCESS_TOKEN(),s);
    }

    private void RefreshData() {
        amenitiesViewModel.refreshAmenity(prefHandler.getACCESS_TOKEN());
    }

    private void intView(View view) {

        context = getContext();
        prefHandler = new PrefHandler(context);
        amenitiesAdapter = new AmenitiesAdapter(context, getActivity());

        ma_refreshLayout = view.findViewById(R.id.ma_refreshLayout);
        ma_search = view.findViewById(R.id.ma_search);
        ma_search_icon = view.findViewById(R.id.ma_search_icon);

        ma_recycler_view = view.findViewById(R.id.ma_recycler_view);

        LinearLayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        ma_recycler_view.setLayoutManager(layoutManager);

        ma_recycler_view.setAdapter(amenitiesAdapter);


        amenitiesViewModel = new ViewModelProvider(this).get(AmenitiesViewModel.class);

        amenitiesViewModel.getAmenitiesLiveData().observe((LifecycleOwner) context, customerItems -> amenitiesAdapter.updateList(customerItems));

        amenitiesViewModel.getLoadingLiveData().observe((LifecycleOwner) context, isLoading -> {
            if (isLoading) {
                amenitiesAdapter.addLoadingFooter();
            } else {
                if (ma_refreshLayout.isRefreshing()) {
                    ma_refreshLayout.setRefreshing(false);
                }
                amenitiesAdapter.removeLoadingFooter();
            }
        });

        amenitiesViewModel.getLastPageLiveData().observe((LifecycleOwner) context, lastPage -> isLastPage = lastPage);

        amenitiesViewModel.getLogoutEvent().observe((LifecycleOwner) context, logout -> {

            if (logout) {

                Toast.makeText(context, "Token Expired", Toast.LENGTH_SHORT).show();

                prefHandler.setIsLogin(false);

                startActivity(new Intent(context, LoginActivity.class));
                requireActivity().finish();
            }

        });

        amenitiesViewModel.loadMoreAmenity(prefHandler.getACCESS_TOKEN());

        ma_recycler_view.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                int visibleItemCount = layoutManager.getChildCount();
                int totalItemCount = layoutManager.getItemCount();
                int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();

                if (!isLastPage && Boolean.FALSE.equals(amenitiesViewModel.getLoadingLiveData().getValue()) &&
                        (visibleItemCount + firstVisibleItemPosition >= totalItemCount) &&
                        firstVisibleItemPosition >= 0) {
                    amenitiesViewModel.loadMoreAmenity(prefHandler.getACCESS_TOKEN());
                }
            }
        });


    }


}