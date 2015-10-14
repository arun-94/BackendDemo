package com.tisser.puneet.tisserartisan.Model;

import com.google.gson.annotations.SerializedName;

public class Subsubcategory
{

    @SerializedName("subsubcategory_name") private String subsubcategoryName;
    @SerializedName("subsubcategory_id") private int subsubcategoryId;

    public String getCategoryName()
    {
        return subsubcategoryName;
    }

    public int getCategoryID()
    {
        return subsubcategoryId;
    }
}
