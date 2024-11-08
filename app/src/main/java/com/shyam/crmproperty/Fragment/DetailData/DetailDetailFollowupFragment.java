package com.shyam.crmproperty.Fragment.DetailData;

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
import com.shyam.crmproperty.Adapter.FollowupAdapter;
import com.shyam.crmproperty.DataModel.DataConstant.DataConstant;
import com.shyam.crmproperty.Other.PrefHandler;
import com.shyam.crmproperty.R;
import com.shyam.crmproperty.ViewModel.FollowupViewModel;

public class DetailDetailFollowupFragment extends Fragment {

    String TYPE;
    Context context;
    RecyclerView recyclerView;
    SwipeRefreshLayout swipeRefreshLayout;
    PrefHandler prefHandler;
    EditText search;
    ImageView search_icon;
    private boolean isLastPage = false;
    FollowupViewModel followupViewModel;
    FollowupAdapter followupAdapter;


    public DetailDetailFollowupFragment(String type) {
        this.TYPE = type;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_detail_detail_followup, container, false);

        intView(view);

        clickListener();

        swipeRefreshLayout.setOnRefreshListener(this::RefreshData);

        return view;
    }

    private void RefreshData() {
        followupViewModel.refreshUsers(prefHandler.getACCESS_TOKEN());
    }


    private void clickListener() {

        search.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                performSearch();
            }
            return true;
        });

        search_icon.setOnClickListener(v -> performSearch());


    }

    private void performSearch() {

        String searchQuery = search.getText().toString();
        if (searchQuery.trim().isEmpty()) {
            itemSearch("");
            Toast.makeText(getContext(), "Please enter a search term", Toast.LENGTH_SHORT).show();
        } else {
            itemSearch(searchQuery);
        }

    }

    private void itemSearch(String s) {

        followupViewModel.searchUsers(prefHandler.getACCESS_TOKEN(),s);

    }

    private void intView(View view) {

        context = getContext();
        prefHandler = new PrefHandler(context);
        followupAdapter = new FollowupAdapter(context, requireActivity());
        swipeRefreshLayout = view.findViewById(R.id.ddf_refresh_layout);
        search = view.findViewById(R.id.ddf_search);
        search_icon = view.findViewById(R.id.ddf_search_icon);

        recyclerView = view.findViewById(R.id.ddf_recycler_view);

        LinearLayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setAdapter(followupAdapter);

        followupViewModel = new ViewModelProvider(this).get(FollowupViewModel.class);


        loadFollowUps();


        followupViewModel.getLoadingLiveData().observe((LifecycleOwner) context, isLoading -> {
            if (isLoading) {
                followupAdapter.addLoadingFooter();
            } else {
                if (swipeRefreshLayout.isRefreshing()) {
                    swipeRefreshLayout.setRefreshing(false);
                }
                followupAdapter.removeLoadingFooter();
            }
        });

        followupViewModel.getLastPageLiveData().observe((LifecycleOwner) context, lastPage -> isLastPage = lastPage);

        followupViewModel.getLogoutEvent().observe((LifecycleOwner) context, logout -> {

            if (logout) {

                Toast.makeText(context, "Token Expired", Toast.LENGTH_SHORT).show();

                prefHandler.setIsLogin(false);

                startActivity(new Intent(context, LoginActivity.class));
                requireActivity().finish();
            }

        });

        followupViewModel.loadMoreFollowup(prefHandler.getACCESS_TOKEN());

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                int visibleItemCount = layoutManager.getChildCount();
                int totalItemCount = layoutManager.getItemCount();
                int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();

                if (!isLastPage && Boolean.FALSE.equals(followupViewModel.getLoadingLiveData().getValue()) &&
                        (visibleItemCount + firstVisibleItemPosition >= totalItemCount) &&
                        firstVisibleItemPosition >= 0) {
                    followupViewModel.loadMoreFollowup(prefHandler.getACCESS_TOKEN());
                }
            }
        });


    }

    private void loadFollowUps() {

        switch (TYPE) {
            case DataConstant.FollowupAll:
                loadAllFollowup();
                return;
            case DataConstant.FollowupToday:
                loadTodayFollowup();
                return;
            case DataConstant.FollowupOverdue:
                loadOverdueFollowup();
                return;
            case DataConstant.FollowupIn7day:
                loadFollowupIn7Day();
                return;
            case DataConstant.FollowupMoreThen7day:
                loadFollowupMoreThen7Day();
                return;
        }

    }

    private void loadFollowupMoreThen7Day() {


    }

    private void loadFollowupIn7Day() {


    }

    private void loadOverdueFollowup() {


    }

    private void loadTodayFollowup() {

        followupViewModel.getTodayFollowupsLiveData().observe((LifecycleOwner) context, customerItems -> followupAdapter.updateList(customerItems));

    }

    private void loadAllFollowup() {

        followupViewModel.getAllFollowupLiveData().observe((LifecycleOwner) context, customerItems -> followupAdapter.updateList(customerItems));


    }


}