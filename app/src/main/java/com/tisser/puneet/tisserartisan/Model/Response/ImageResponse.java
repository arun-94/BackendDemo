package com.tisser.puneet.tisserartisan.Model.Response;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Arun on 20-Oct-15.
 */
public class ImageResponse
{
    public String getPath()
    {
        if(path != "" && path != null)
        {
            String newPath = "http://www.tisserindia.com/stores/" + path.substring(3);
            return newPath;
        }
        else
            return "";
    }

    public void setPath(String path)
    {
        this.path = path;
    }

    @SerializedName("path") private String path;
}
