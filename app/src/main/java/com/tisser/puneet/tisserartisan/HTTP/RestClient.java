package com.tisser.puneet.tisserartisan.HTTP;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit.RestAdapter;

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
