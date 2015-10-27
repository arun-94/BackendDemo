package com.tisser.puneet.tisserartisan.HTTP;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Arun on 27-Oct-15.
 */
public class EditProductResponse
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
