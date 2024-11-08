package com.shyam.crmproperty.ViewModel;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.shyam.crmproperty.API.RestClient;
import com.shyam.crmproperty.DataModel.Followup.FollowupItem;
import com.shyam.crmproperty.DataModel.Followup.ResponseFollowup;
import com.shyam.crmproperty.DataModel.User.ResponseUser;
import com.shyam.crmproperty.DataModel.User.UserItem;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FollowupViewModel extends ViewModel {


    private final MutableLiveData<Boolean> loadingLiveData = new MutableLiveData<>();
    private final MutableLiveData<Boolean> lastPageLiveData = new MutableLiveData<>();
    private final MutableLiveData<Boolean> logoutEvent = new MutableLiveData<>();
    private int currentPage = 1;
    String PageSize = "10";
    String search_query = "";
    private boolean isLoading = false;


    private final MutableLiveData<List<FollowupItem>> allFollowUpsLiveData = new MutableLiveData<>();

    private final MutableLiveData<List<FollowupItem>> todayFollowupsLiveData = new MutableLiveData<>();


    public LiveData<List<FollowupItem>> getTodayFollowupsLiveData() {
        MutableLiveData<List<FollowupItem>> todayFollowupsLiveData = new MutableLiveData<>();
        List<FollowupItem> allFollowups = allFollowUpsLiveData.getValue();

        if (allFollowups != null) {
            List<FollowupItem> todayFollowups = new ArrayList<>();
            String todayDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());

            for (FollowupItem item : allFollowups) {
//                if (item.getFtime() != null && item.getFtime().startsWith(todayDate)) {  // only testing purpose commented
                todayFollowups.add(item);
//                }
            }
            todayFollowupsLiveData.setValue(todayFollowups);
        } else {
            todayFollowupsLiveData.setValue(new ArrayList<>()); // Empty list if no data
        }

        return todayFollowupsLiveData;
    }


    public LiveData<List<FollowupItem>> getAllFollowupLiveData() {
        return allFollowUpsLiveData;
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

    public void loadMoreFollowup(String accessToken) {
        if (isLoading || Boolean.TRUE.equals(lastPageLiveData.getValue())) return;

        isLoading = true;
        loadingLiveData.setValue(true);

        Call<ResponseFollowup> call = RestClient.post().followUpList(accessToken, String.valueOf(currentPage), PageSize, search_query);

        call.enqueue(new Callback<ResponseFollowup>() {
            @Override
            public void onResponse(@NonNull Call<ResponseFollowup> call, @NonNull Response<ResponseFollowup> response) {

                loadingLiveData.setValue(false);

                ResponseFollowup responseFollowup = response.body();

                if (responseFollowup != null) {
                    if (responseFollowup.isSuccess()) {
                        if (responseFollowup.getData().isEmpty()) {
                            lastPageLiveData.setValue(true);
                            return;
                        }

                        List<FollowupItem> currentList = allFollowUpsLiveData.getValue();
                        if (currentList == null) currentList = new ArrayList<>();
                        currentList.addAll(responseFollowup.getData());
                        allFollowUpsLiveData.setValue(currentList);

                        currentPage++;

                        if (responseFollowup.getData().size() < 10) {
                            lastPageLiveData.setValue(true);
                        }

                    } else {
                        if (responseFollowup.isTokenExpire()) {
                            logoutEvent.setValue(true);
                        } else {
                            lastPageLiveData.setValue(true);
                        }
                    }

                    isLoading = false;
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseFollowup> call, @NonNull Throwable t) {
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

        allFollowUpsLiveData.setValue(new ArrayList<>());

        loadMoreFollowup(accessToken);
    }

    public void searchUsers(String accessToken, String search_query) {

        currentPage = 1;
        lastPageLiveData.setValue(false);
        isLoading = false;

        this.search_query = search_query;

        allFollowUpsLiveData.setValue(new ArrayList<>());

        loadMoreFollowup(accessToken);
    }


}

