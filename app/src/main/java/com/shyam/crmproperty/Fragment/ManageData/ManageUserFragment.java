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
import com.shyam.crmproperty.Adapter.UserAdapter;
import com.shyam.crmproperty.Other.PrefHandler;
import com.shyam.crmproperty.R;

import com.shyam.crmproperty.ViewModel.UserViewModel;

public class ManageUserFragment extends Fragment {

    Context context;
    RecyclerView mu_recycler_view;
    SwipeRefreshLayout mu_refreshLayout;
    PrefHandler prefHandler;
    UserViewModel userViewModel;
    UserAdapter userAdapter;
    EditText mu_search;
    ImageView mu_search_icon;
    private boolean isLastPage = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_manage_user, container, false);

        MainActivity.m_header_title.setText("Manage Users");

        intView(view);

        clickListener();

        mu_refreshLayout.setOnRefreshListener(this::RefreshData);

        return view;
    }

    private void RefreshData() {
        userViewModel.refreshUsers(prefHandler.getACCESS_TOKEN());
    }


    private void clickListener() {

        mu_search.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                performSearch();
            }
            return true;
        });

        mu_search_icon.setOnClickListener(v -> performSearch());

    }

    private void performSearch() {

        String searchQuery = mu_search.getText().toString();
        if (searchQuery.trim().isEmpty()) {
            itemSearch("");
            Toast.makeText(getContext(), "Please enter a search term", Toast.LENGTH_SHORT).show();
        } else {
            itemSearch(searchQuery);
        }

    }

    private void itemSearch(String s) {

        userViewModel.searchUsers(prefHandler.getACCESS_TOKEN(),s);

    }


    private void intView(View view) {
        context = getContext();
        prefHandler = new PrefHandler(context);
        userAdapter = new UserAdapter(context,getActivity());

        mu_refreshLayout = view.findViewById(R.id.mu_refreshLayout);
        mu_search = view.findViewById(R.id.mu_search);
        mu_search_icon = view.findViewById(R.id.mu_search_icon);

        mu_recycler_view = view.findViewById(R.id.mu_recycler_view);

        LinearLayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        mu_recycler_view.setLayoutManager(layoutManager);

        mu_recycler_view.setAdapter(userAdapter);

        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);

        userViewModel.getCustomerLiveData().observe((LifecycleOwner) context, customerItems -> userAdapter.updateList(customerItems));

        userViewModel.getLoadingLiveData().observe((LifecycleOwner) context, isLoading -> {
            if (isLoading) {
                userAdapter.addLoadingFooter();
            } else {
                if (mu_refreshLayout.isRefreshing()) {
                    mu_refreshLayout.setRefreshing(false);
                }
                userAdapter.removeLoadingFooter();
            }
        });

        userViewModel.getLastPageLiveData().observe((LifecycleOwner) context, lastPage -> isLastPage = lastPage);

        userViewModel.getLogoutEvent().observe((LifecycleOwner) context, logout -> {

            if (logout){

                Toast.makeText(context, "Token Expired", Toast.LENGTH_SHORT).show();

                prefHandler.setIsLogin(false);

                startActivity(new Intent(context, LoginActivity.class));
                requireActivity().finish();
            }

        });

        userViewModel.loadMoreCustomers(prefHandler.getACCESS_TOKEN());

        mu_recycler_view.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                int visibleItemCount = layoutManager.getChildCount();
                int totalItemCount = layoutManager.getItemCount();
                int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();

                if (!isLastPage && Boolean.FALSE.equals(userViewModel.getLoadingLiveData().getValue()) &&
                        (visibleItemCount + firstVisibleItemPosition >= totalItemCount) &&
                        firstVisibleItemPosition >= 0) {
                    userViewModel.loadMoreCustomers(prefHandler.getACCESS_TOKEN());
                }
            }
        });


    }

}