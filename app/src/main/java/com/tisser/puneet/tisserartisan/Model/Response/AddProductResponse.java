package com.tisser.puneet.tisserartisan.Model.Response;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Puneet on 20-10-2015.
 */
public class AddProductResponse
{
    @SerializedName("status") private String status;
    @SerializedName("error") private int error;

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
}
