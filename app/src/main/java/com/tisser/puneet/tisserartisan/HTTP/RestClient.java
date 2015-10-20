package com.tisser.puneet.tisserartisan.HTTP;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import retrofit.RestAdapter;
import retrofit.client.OkClient;

public class RestClient
{
    private static final String BASE_URL = "http://tisserindia.com/stores/mobileApi";
    private static TisserApiInterface apiService;

    public static TisserApiInterface getApiService()
    {
        Gson gson = new GsonBuilder()
                .create();
        if (apiService == null)
        {
            RestAdapter restAdapter = new RestAdapter.Builder()
                    .setLogLevel(RestAdapter.LogLevel.FULL)
                    .setEndpoint(BASE_URL)
                    .setConverter(new LenientGsonConverter(gson))
                    .build();

            apiService = restAdapter.create(TisserApiInterface.class);
        }
        return apiService;
    }
}
