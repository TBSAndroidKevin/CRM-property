package com.shyam.crmproperty.ViewModel;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.shyam.crmproperty.API.RestClient;
import com.shyam.crmproperty.DataModel.Amenities.AmenitiesItem;
import com.shyam.crmproperty.DataModel.Amenities.ResponseAmenities;
import com.shyam.crmproperty.DataModel.User.ResponseUser;
import com.shyam.crmproperty.DataModel.User.UserItem;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AmenitiesViewModel extends ViewModel {

    private final MutableLiveData<List<AmenitiesItem>> amenitiesLiveData = new MutableLiveData<>();
    private final MutableLiveData<Boolean> loadingLiveData = new MutableLiveData<>();
    private final MutableLiveData<Boolean> lastPageLiveData = new MutableLiveData<>();
    private final MutableLiveData<Boolean> logoutEvent = new MutableLiveData<>();
    private int currentPage = 1;
    String PageSize = "10";
    String search_query = "";

    private boolean isLoading = false;

    public LiveData<List<AmenitiesItem>> getAmenitiesLiveData() {
        return amenitiesLiveData;
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

    public int getCurrentPage() {
        return currentPage;
    }

    public String getPageSize() {
        return PageSize;
    }

    public String getSearch_query() {
        return search_query;
    }

    public boolean isLoading() {
        return isLoading;
    }


    public void searchAmenity(String accessToken, String search_query) {

        currentPage = 1;
        lastPageLiveData.setValue(false);
        isLoading = false;

        this.search_query = search_query;

        amenitiesLiveData.setValue(new ArrayList<>());

        loadMoreAmenity(accessToken);

    }

    public void refreshAmenity(String accessToken) {
        currentPage = 1;
        lastPageLiveData.setValue(false);
        isLoading = false;

//        search_query = "";   // Clear the search query

        amenitiesLiveData.setValue(new ArrayList<>());

        loadMoreAmenity(accessToken);
    }

    public void loadMoreAmenity(String accessToken) {

        if (isLoading || Boolean.TRUE.equals(lastPageLiveData.getValue())) return;

        isLoading = true;
        loadingLiveData.setValue(true);

        Call<ResponseAmenities> call = RestClient.post().amenitiesList(accessToken, String.valueOf(currentPage), PageSize, search_query);

        call.enqueue(new Callback<ResponseAmenities>() {
            @Override
            public void onResponse(@NonNull Call<ResponseAmenities> call, @NonNull Response<ResponseAmenities> response) {

                loadingLiveData.setValue(false);

                ResponseAmenities responseAmenities = response.body();

                assert responseAmenities != null;
                if (responseAmenities.isSuccess()) {
                    if (responseAmenities.getData().isEmpty()) {
                        lastPageLiveData.setValue(true);
                        return;
                    }

                    List<AmenitiesItem> currentList = amenitiesLiveData.getValue();
                    if (currentList == null) currentList = new ArrayList<>();
                    currentList.addAll(responseAmenities.getData());
                    amenitiesLiveData.setValue(currentList);

                    currentPage++;

                    if (responseAmenities.getData().size() < 10) {
                        lastPageLiveData.setValue(true);
                    }

                } else {
                    if (responseAmenities.isTokenExpire()) {
                        logoutEvent.setValue(true);
                    } else {
                        lastPageLiveData.setValue(true);
                    }
                }

                isLoading = false;
            }

            @Override
            public void onFailure(@NonNull Call<ResponseAmenities> call, @NonNull Throwable t) {
                loadingLiveData.setValue(false);
                isLoading = false;
            }
        });

    }


}
