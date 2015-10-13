package com.tisser.puneet.tisserartisan.HTTP;

import java.util.Map;

import retrofit.Callback;
import retrofit.http.Multipart;
import retrofit.http.POST;
import retrofit.http.Part;
import retrofit.http.PartMap;
import retrofit.mime.TypedFile;

public interface ProductUploadService {
    public static final String BASE_URL = "http://tisserindia.com";

    @Multipart
    @POST("/stores/mobileApi/mobileAPIArtist.php")
    void upload(@Part("action") String action,
                @Part("session_id") String sessionId,
                @Part("user_id") String userId,
                @PartMap Map<String,TypedFile> files,
                @Part("product_name") String productName,
                @Part("product_price") String productPrice,
                @Part("product_category_id") String productCategoryId,
                @Part("product_color") String productColor,
                @Part("product_description") String productDescription,
                Callback<String> cb);
}