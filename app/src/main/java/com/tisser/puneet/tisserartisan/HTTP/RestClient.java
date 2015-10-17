package com.tisser.puneet.tisserartisan.HTTP;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.tisser.puneet.tisserartisan.Model.Product;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Map;

import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;

public class RestClient
{
    private static final String BASE_URL = "http://tisserindia.com/stores/mobileApi";
    private static TisserApiInterface apiService;

    public static TisserApiInterface getApiService()
    {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Product.class, new ProductTypeAdapter())
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

    public static class ProductTypeAdapter implements JsonDeserializer<Product>
    {
        @Override
        public Product deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException
        {
            Gson gson = new Gson();
            Product  productList = new Product();
            return productList;
        }

    }
}
