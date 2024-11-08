package com.shyam.crmproperty.ViewModel;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.shyam.crmproperty.API.RestClient;
import com.shyam.crmproperty.DataModel.User.ResponseUser;
import com.shyam.crmproperty.DataModel.User.UserItem;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserViewModel extends ViewModel {

    private final MutableLiveData<List<UserItem>> userLiveData = new MutableLiveData<>();
    private final MutableLiveData<Boolean> loadingLiveData = new MutableLiveData<>();
    private final MutableLiveData<Boolean> lastPageLiveData = new MutableLiveData<>();
    private final MutableLiveData<Boolean> logoutEvent = new MutableLiveData<>();
    private int currentPage = 1;
    String PageSize = "10";
    String search_query = "";

    private boolean isLoading = false;

    public LiveData<List<UserItem>> getCustomerLiveData() {
        return userLiveData;
    }

    public LiveData<Boolean> getLoadingLiveData() {
        return loadingLiveData;
    }

    public LiveData<Boolean> getLastPageLiveData() {
        return lastPageLiveData;
    }

    public LiveData<Boolean> getLogoutEvent() {
        return logoutEvent;
    }

    public void loadMoreCustomers(String accessToken) {
        if (isLoading || Boolean.TRUE.equals(lastPageLiveData.getValue())) return;

        isLoading = true;
        loadingLiveData.setValue(true);

        Call<ResponseUser> call = RestClient.post().userList(accessToken, String.valueOf(currentPage), PageSize, search_query);

        call.enqueue(new Callback<ResponseUser>() {
            @Override
            public void onResponse(@NonNull Call<ResponseUser> call, @NonNull Response<ResponseUser> response) {

                loadingLiveData.setValue(false);

                ResponseUser responseUser = response.body();

                assert responseUser != null;
                if (responseUser.isSuccess()) {
                    if (responseUser.getData().isEmpty()) {
                        lastPageLiveData.setValue(true);
                        return;
                    }

                    List<UserItem> currentList = userLiveData.getValue();
                    if (currentList == null) currentList = new ArrayList<>();
                    currentList.addAll(responseUser.getData());
                    userLiveData.setValue(currentList);

                    currentPage++;

                    if (responseUser.getData().size() < 10) {
                        lastPageLiveData.setValue(true);
                    }

                } else {
                    if (responseUser.isTokenExpire()) {
                        logoutEvent.setValue(true);
                    } else {
                        lastPageLiveData.setValue(true);
                    }
                }

                isLoading = false;
            }

            @Override
            public void onFailure(@NonNull Call<ResponseUser> call, @NonNull Throwable t) {
                loadingLiveData.setValue(false);
                isLoading = false;
            }
        });
    }

    public void refreshUsers(String accessToken) {

        currentPage = 1;
        lastPageLiveData.setValue(false);
        isLoading = false;

//        search_query = "";   // Clear the search query

        userLiveData.setValue(new ArrayList<>());

        loadMoreCustomers(accessToken);
    }

    public void searchUsers(String accessToken, String search_query) {

        currentPage = 1;
        lastPageLiveData.setValue(false);
        isLoading = false;

        this.search_query = search_query;

        userLiveData.setValue(new ArrayList<>());

        loadMoreCustomers(accessToken);
    }


}

