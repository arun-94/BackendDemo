package com.tisser.puneet.tisserartisan.Model.Response;

import com.google.gson.annotations.SerializedName;
import com.tisser.puneet.tisserartisan.Model.ProductDetailed;

import java.util.ArrayList;

public class ProductResponse
{
    @SerializedName("status") private String status;
    @SerializedName("error") private int error;
    @SerializedName("products") private ArrayList<ProductDetailed> productList;

    public String getStatus()
    {
        return status;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }

    public int getError()
    {
        return error;
    }

    public void setError(int error)
    {
        this.error = error;
    }

    public ArrayList<ProductDetailed> getProductList()
    {
        return productList;
    }

    public void setProductList(ArrayList<ProductDetailed> productList)
    {
        this.productList = productList;
    }
}
