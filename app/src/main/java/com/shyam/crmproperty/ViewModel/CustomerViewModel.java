package com.shyam.crmproperty.ViewModel;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.shyam.crmproperty.API.RestClient;
import com.shyam.crmproperty.DataModel.CustomerItem;
import com.shyam.crmproperty.DataModel.DataConstant.DataConstant;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CustomerViewModel extends ViewModel {

    private final MutableLiveData<List<CustomerItem>> customerLiveData = new MutableLiveData<>();
    private final MutableLiveData<Boolean> loadingLiveData = new MutableLiveData<>();
    private final MutableLiveData<Boolean> lastPageLiveData = new MutableLiveData<>();
    private final MutableLiveData<Boolean> logoutEvent = new MutableLiveData<>();

    private int currentPage = 1;
    String PageSize = "10";
    String search_query = "";
    String date_from = "";
    String date_to = "";
    private boolean isLoading = false;

    public LiveData<List<CustomerItem>> getCustomerLiveData() {
        return customerLiveData;
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

        Call<ResponseBody> call = RestClient.post().customerList(accessToken, String.valueOf(currentPage), PageSize, date_from, date_to, search_query);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                try {
                    loadingLiveData.setValue(false);

                    JSONObject jsonObject = new JSONObject(Objects.requireNonNull(response.body()).string());
                    String status = jsonObject.getString("status");

                    if (status.equals("success")) {
                        JSONArray dataArray = jsonObject.optJSONArray("data");

                        if (dataArray == null || dataArray.length() == 0) {
                            lastPageLiveData.setValue(true);
                            return;
                        }

                        List<CustomerItem> customerItemList = new ArrayList<>();

                        for (int i = 0; i < Objects.requireNonNull(dataArray).length(); i++) {
                            JSONObject dataObject = dataArray.getJSONObject(i);
                            CustomerItem customerItem = new CustomerItem();
                            customerItem.setId(dataObject.optString("id"));
                            customerItem.setProjectName(dataObject.optString("project_name"));
                            customerItem.setSurname(dataObject.optString("surname"));
                            customerItem.setFirstName(dataObject.optString("fname"));
                            customerItem.setLastName(dataObject.optString("lname"));
                            customerItem.setDate(dataObject.optString("date"));
                            customerItem.setCity(dataObject.optString("city"));
                            customerItem.setBhk(dataObject.optString("bhk"));
                            customerItem.setMobile(dataObject.optString("mobile"));
                            customerItem.setWhatsapp(dataObject.optString("whatsapp"));
                            customerItem.setRemarks(dataObject.optString("remarks"));
                            customerItem.setBudget(dataObject.optString("budget"));
                            customerItem.setPositiveIndex(dataObject.optString("positive_index"));
                            customerItem.setType(dataObject.optString("type"));
                            customerItem.setRef(dataObject.optString("ref"));
                            customerItem.setHomeLandmark(dataObject.optString("home_landmark"));
                            customerItem.setIntLandmark(dataObject.optString("int_landmark"));
                            customerItem.setAddress(dataObject.optString("address"));
                            customerItem.setProfession(dataObject.optString("profession"));
                            customerItem.setFoCount(dataObject.optString("fo_count"));

//                            customerItem.setStatus(DataConstant.STATUS[Integer.parseInt(dataObject.optString("status"))]);

                            switch (dataObject.optString("status")) {

                                case "0":
                                    customerItem.setStatus(DataConstant.INQUIRY);
                                    break;
                                case "1":
                                    customerItem.setStatus(DataConstant.VISITED_SITE);
                                    break;
                                case "2":
                                    customerItem.setStatus(DataConstant.VISITED_INTERESTED);
                                    break;
                                case "3":
                                    customerItem.setStatus(DataConstant.PLANNING);
                                    break;
                                case "4":
                                    customerItem.setStatus(DataConstant.BOOKING_DONE);
                                    break;
                                case "5":
                                    customerItem.setStatus(DataConstant.NOT_INTERESTED);
                                    break;
                                case "6":
                                    customerItem.setStatus(DataConstant.BLACK_LISTED);
                                    break;
                            }

                            customerItemList.add(customerItem);
                        }

                        List<CustomerItem> currentList = customerLiveData.getValue();
                        if (currentList == null) currentList = new ArrayList<>();
                        currentList.addAll(customerItemList);
                        customerLiveData.setValue(currentList);

                        currentPage++;

                        if (dataArray.length() < 10) {
                            lastPageLiveData.setValue(true);
                        }

                    } else if (status.equals("error")) {
                        if (jsonObject.optString("message").equals("Record not found")) {
                            lastPageLiveData.setValue(true);
                        }

                        if (jsonObject.optString("message").equals("API key invalid")) {
                            logoutEvent.setValue(true);
                        }

                    } else {
                        lastPageLiveData.setValue(true);
                    }

                } catch (IOException | JSONException e) {
//                    e.printStackTrace();
                }

                isLoading = false;
            }

            @Override
            public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                loadingLiveData.setValue(false);
                isLoading = false;
            }
        });
    }

    public void refreshCustomers(String accessToken) {

        currentPage = 1;
        lastPageLiveData.setValue(false);
        isLoading = false;

//        search_query = "";   // Clear the search query
//        date_from = "";   // Clear the date range
//        date_to = "";   // Clear the date range

        customerLiveData.setValue(new ArrayList<>());

        loadMoreCustomers(accessToken);
    }

    public void searchCustomers(String accessToken, String search_query) {

        currentPage = 1;
        lastPageLiveData.setValue(false);
        isLoading = false;

        this.search_query = search_query;

        customerLiveData.setValue(new ArrayList<>());

        loadMoreCustomers(accessToken);
    }

    public void dateSortCustomers(String accessToken, String search_query, String dateForm, String dateTo) {

        currentPage = 1;
        lastPageLiveData.setValue(false);
        isLoading = false;

        this.search_query = search_query;
        this.date_from = convertDate(dateForm);
        this.date_to = convertDate(dateTo);

        customerLiveData.setValue(new ArrayList<>());

        loadMoreCustomers(accessToken);
    }


    public String convertDate(String inputDate) {
        try {
            SimpleDateFormat inputFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
            SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

            Date date = inputFormat.parse(inputDate);

            return outputFormat.format(date);
        } catch (Exception ignored) {
        }
        return inputDate;
    }

}

