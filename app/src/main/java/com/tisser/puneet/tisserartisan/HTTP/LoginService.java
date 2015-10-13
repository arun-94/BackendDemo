package com.tisser.puneet.tisserartisan.HTTP;

import retrofit.Callback;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;

/**
 * Created by Arun on 13-Oct-15.
 */
public interface LoginService
{
    public static final String BASE_URL = "http://tisserindia.com";

    @FormUrlEncoded
    @POST("/stores/mobileApi/mobileAPIArtist.php")
    void validate(
            @Field("action") String action,
            @Field("user_id") String userId,
            @Field("password") String password,
            Callback<String> cb);
}
