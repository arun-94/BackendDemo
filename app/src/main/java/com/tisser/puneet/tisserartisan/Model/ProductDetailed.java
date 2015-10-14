package com.tisser.puneet.tisserartisan.Model;

import android.util.Log;

import com.tisser.puneet.tisserartisan.HTTP.ProductUploadService;
import com.tisser.puneet.tisserartisan.HTTP.ServiceGenerator;
import com.tisser.puneet.tisserartisan.UI.Activity.Navigator;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.mime.TypedFile;


/**
 * Created by Puneet on 22-07-2015.
 */
public class ProductDetailed
{
    private String productName;
    private String productCode;
    private String productSummary;
    private String productDescription;
    private String productKeypoints;
    private String productEstimatedDelivery;
    private String productEstimatedCost;
    private int productBaseColor;
    private String productColor;
    private int productPrice;
    private ArrayList<String> productImgPaths;
    private int productCategoryID;
    private int productSubcategoryID;
    private ArrayList<Review> productReviews = new ArrayList<>();

    public String getProductName()
    {
        return productName;
    }

    public void setProductName(String productName)
    {
        this.productName = productName;
    }

    public String getProductCode()
    {
        return productCode;
    }

    public void setProductCode(String productCode)
    {
        this.productCode = productCode;
    }

    public String getProductSummary()
    {
        return productSummary;
    }

    public void setProductSummary(String productSummary)
    {
        this.productSummary = productSummary;
    }

    public String getProductDescription()
    {
        return productDescription;
    }

    public void setProductDescription(String productDescription)
    {
        this.productDescription = productDescription;
    }

    public String getProductKeypoints()
    {
        return productKeypoints;
    }

    public void setProductKeypoints(String productKeypoints)
    {
        this.productKeypoints = productKeypoints;
    }

    public String getProductEstimatedDelivery()
    {
        return productEstimatedDelivery;
    }

    public void setProductEstimatedDelivery(String productEstimatedDelivery)
    {
        this.productEstimatedDelivery = productEstimatedDelivery;
    }

    public String getProductEstimatedCost()
    {
        return productEstimatedCost;
    }

    public void setProductEstimatedCost(String productEstimatedCost)
    {
        this.productEstimatedCost = productEstimatedCost;
    }

    public int getProductBaseColor()
    {
        return productBaseColor;
    }

    public void setProductBaseColor(int productBaseColor)
    {
        this.productBaseColor = productBaseColor;
    }

    public String getProductColor()
    {
        return productColor;
    }

    public void setProductColor(String productColor)
    {
        this.productColor = productColor;
    }

    public int getProductPrice()
    {
        return productPrice;
    }

    public void setProductPrice(int productPrice)
    {
        this.productPrice = productPrice;
    }

    public ArrayList<String> getProductImgPaths()
    {
        return productImgPaths;
    }

    public void setProductImgPaths(ArrayList<String> productImgPaths)
    {
        this.productImgPaths = productImgPaths;
    }

    public int getProductCategoryID()
    {
        return productCategoryID;
    }

    public void setProductCategoryID(int productCategoryID)
    {
        this.productCategoryID = productCategoryID;
    }

    public int getProductSubcategoryID()
    {
        return productSubcategoryID;
    }

    public void setProductSubcategoryID(int productSubcategoryID)
    {
        this.productSubcategoryID = productSubcategoryID;
    }

    public ArrayList<Review> getProductReviews()
    {
        return productReviews;
    }

    public void setProductReviews(ArrayList<Review> productReviews)
    {
        this.productReviews.addAll(productReviews);
    }

}
