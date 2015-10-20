package com.tisser.puneet.tisserartisan.Model.Response;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Arun on 20-Oct-15.
 */
public class ImageResponse
{
    public String getPath()
    {
        return path;
    }

    public void setPath(String path)
    {
        this.path = path;
    }

    @SerializedName("path") private String path;
}
