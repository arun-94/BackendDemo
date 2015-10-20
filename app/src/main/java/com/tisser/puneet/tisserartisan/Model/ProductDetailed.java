package com.tisser.puneet.tisserartisan.Model;

import com.google.gson.annotations.SerializedName;
import com.tisser.puneet.tisserartisan.Model.Response.ImageResponse;

import java.util.ArrayList;

public class ProductDetailed
{
    @SerializedName("name") private String productName;
    @SerializedName("product_code") private String productCode;
    @SerializedName("mobile_product_id") private String productID;
    @SerializedName("price") private double productPrice;
    @SerializedName("quantity") private int productQuantity;
    @SerializedName("product_color") private String productColor;
    @SerializedName("short_desc") private String productSummary;
    @SerializedName("prod_desc") private String productDescription;
    @SerializedName("keyword") private String productKeypoints;
    @SerializedName("basecolor") private String productBaseColor;
    @SerializedName("images") private ArrayList<ImageResponse> images;
    private String productImgPath;
    private ArrayList<String> productImgPaths;
    ArrayList<String> productImagePathsArray = new ArrayList<>();
    private int productCategoryID;
    private int productSubcategoryID;
    private int productSubsubcategoryID;
    private String productEstimatedDelivery;
    private String productEstimatedCost;
    private ArrayList<Review> productReviews = new ArrayList<>();


/*    public ProductDetailed()
    {
        this.productName = "";
        this.productID = "";
        this.productCode = "";
        this.productColor = "0";
        this.productPrice = 0;
        this.productImgPath = "";
        this.productCategoryID = 0;
    }*/

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

    public String getProductBaseColor()
    {
        return productBaseColor;
    }

    public void setProductBaseColor(String productBaseColor)
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

    public double getProductPrice()
    {
        return productPrice;
    }

    public void setProductPrice(double productPrice)
    {
        this.productPrice = productPrice;
    }

    public ArrayList<String> getProductImgPaths()
    {
        if (productImagePathsArray != null && productImagePathsArray.size() != 0)
        {
            return productImagePathsArray;
        }
        else
        {
            return null;/*
        String[] data = productImgPath.split(";");
        ArrayList<String> imgPaths = new ArrayList<>();
        imgPaths.addAll(Arrays.asList(data));
        return imgPaths;*/
        }
    }

    public void setProductImgPathsArray(ArrayList<String> productImgPaths)
    {
        this.productImagePathsArray = productImgPaths;
    }

    public void setProductImgPath(String path)
    {
        this.productImgPath = path;
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

    public int getProductQuantity()
    {
        return productQuantity;
    }

    public void setProductQuantity(int productQuantity)
    {
        this.productQuantity = productQuantity;
    }

    public ArrayList<ImageResponse> getImages()
    {
        return images;
    }

    public void setImages(ArrayList<ImageResponse> images)
    {
        this.images = images;
    }

    public String getProductID()
    {
        return productID;
    }

    public void setProductID(String productID)
    {
        this.productID = productID;
    }
}
