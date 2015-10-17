package com.tisser.puneet.tisserartisan.Model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ProductResponse
{
    @SerializedName("status") private String status;
    @SerializedName("error") private int error;
    @SerializedName("products") private ArrayList<Product> productList;

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

    public ArrayList<Product> getProductList()
    {
        return productList;
    }

    public void setProductList(ArrayList<Product> productList)
    {
        this.productList = productList;
    }
}
