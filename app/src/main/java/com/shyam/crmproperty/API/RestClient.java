package com.shyam.crmproperty.API;

import android.content.Context;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RestClient {

    public static Context context;
    private static ApiService REST_CLIENT;

    static {
        setupRestClient();
    }
    public RestClient() {}
    public static ApiService get() {
        return REST_CLIENT;
    }

    public static ApiService post() {
        return REST_CLIENT;
    }

    private static void setupRestClient()
    {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).readTimeout(1, TimeUnit.MINUTES)
//                .addInterceptor(new SharedObjects())
                .connectTimeout(1, TimeUnit.MINUTES).build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiService.BASEURL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        REST_CLIENT = retrofit.create(ApiService.class);
    }

}
