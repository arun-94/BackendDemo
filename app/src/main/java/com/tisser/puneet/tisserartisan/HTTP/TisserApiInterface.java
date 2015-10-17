package com.tisser.puneet.tisserartisan.HTTP;

import com.google.gson.JsonElement;
import com.tisser.puneet.tisserartisan.Model.Category;
import com.tisser.puneet.tisserartisan.Model.LoginData;
import com.tisser.puneet.tisserartisan.Model.Product;
import com.tisser.puneet.tisserartisan.Model.TisserColor;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Map;

import retrofit.Callback;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.Multipart;
import retrofit.http.POST;
import retrofit.http.Part;
import retrofit.http.PartMap;
import retrofit.http.Query;
import retrofit.mime.TypedFile;

public interface TisserApiInterface
{
    /*    @GET("/mobileAPI.php?action=settings")
        void getSettings(Callback<TisserSettings> cb);

        @GET("/mobileAPI.php?action=searchList")
        void getSearchList(@Query("q") String query, Callback<ArrayList<Product>> cb);

        @GET("/mobileAPI.php?action=productDetails")
        void getProductDetailed(@Query("id") String productID, Callback<ProductDetailed> cb);*/

    @GET("/mobileAPI.php?action=categoryList")
    void getCategoryList(Callback<ArrayList<Category>> cb);

    @GET("/mobileAPI.php?action=productColors")
    void getColorsList(Callback<ArrayList<TisserColor>> colors);

    @GET("/mobileAPI.php?action=categoryDetails")
    void getProductList(@Query("id") int categoryID, Callback<ArrayList<Product>> cb);

    @GET("/mobileAPI.php?action=ShowMyProducts")
    void showMyProducts(@Query("session_id") String sessionID, Callback<ArrayList<Product>> cb);

    @Multipart
    @POST("/mobileAPIArtist.php?action=AddNewProduct")
    void addNewProduct(@Query("session_id") String sessionID, @Part("session_id") String sessionId, @PartMap Map<String, TypedFile> files, @Part("product_name") String productName, @Part("product_price") double productPrice, @Part("product_quantity") int productQuantity, @Part("product_category_id") int productCategoryId, @Part("product_color") String productColor, @Part("product_description") String productDescription, Callback<String> cb);

    @FormUrlEncoded
    @POST("/mobileAPIArtist.php?action=validateUser")
    void validateLogin(@Field("user_id") String userId, @Field("password") String password, Callback<LoginData> cb);
}
