package com.tisser.puneet.tisserartisan.Model;


import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Arrays;

public class Product
{
    @SerializedName("name") private String productName;

    @SerializedName("product_code") private String productCode;

    @SerializedName("product_id") private String productID;

    private int productColor;

    @SerializedName("price") private int productPrice;

    @SerializedName("images") private String productImgPath;

    @SerializedName("quantity") private int productQuantity;

    public int getProductQuantity()
    {
        return productQuantity;
    }

    public void setProductQuantity(int productQuantity)
    {
        this.productQuantity = productQuantity;
    }


    private int productCategoryID;

    public Product()
    {
        this.productName = "";
        this.productID = "";
        this.productCode = "";
        this.productColor = 0;
        this.productPrice = 0;
        this.productImgPath = "";
        this.productCategoryID = 0;
    }

    public Product(String productName, String productCode, int productColor, int productPrice, String productImgPath)
    {
        this.productName = productName;
        this.productCode = productCode;
        this.productColor = productColor;
        this.productPrice = productPrice;
        this.productImgPath = productImgPath;
    }

    public Product(String productName, String productCode, int productColor, int productPrice, String productImgPath, int productCategory, String productID)
    {
        this.productName = productName;
        this.productCode = productCode;
        this.productColor = productColor;
        this.productPrice = productPrice;
        this.productImgPath = productImgPath;
        this.productCategoryID = productCategoryID;
        this.productID = productID;
    }

    public Product(String productName, String productCode, int productPrice, String productImgPath, int productCategoryID)
    {
        this.productName = productName;
        this.productCode = productCode;
        this.productPrice = productPrice;
        this.productImgPath = productImgPath;
        this.productCategoryID = productCategoryID;
    }

    public Product(String productName, String productCode, int productPrice, String productImgPath, int productCategoryID, String productID)
    {
        this.productName = productName;
        this.productCode = productCode;
        this.productPrice = productPrice;
        this.productImgPath = productImgPath;
        this.productCategoryID = productCategoryID;
        this.productID = productID;
    }

    public ArrayList<String> getProductImgPaths()
    {
        String[] data = productImgPath.split(";");
        ArrayList<String> imgPaths = new ArrayList<>();
        imgPaths.addAll(Arrays.asList(data));
        return imgPaths;
    }

    public void setProductImgPath(String productImgPath)
    {
        this.productImgPath = productImgPath;
    }

    public String getProductCode()
    {
        return productCode;
    }

    public void setProductCode(String productCode)
    {
        this.productCode = productCode;
    }

    public int getProductColor()
    {
        return productColor;
    }

    public void setProductColor(int productColor)
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

    public String getProductName()
    {
        return productName;
    }

    public void setProductName(String productName)
    {
        this.productName = productName;
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
