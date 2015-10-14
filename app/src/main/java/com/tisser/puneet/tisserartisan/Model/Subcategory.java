package com.tisser.puneet.tisserartisan.Model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Subcategory
{

    @SerializedName("subcategory_name") private String subcategoryName;
    @SerializedName("subcategory_id") private int subcategoryId;
    @SerializedName("subsubcategories") private ArrayList<Subsubcategory> subsubcategories = new ArrayList<Subsubcategory>();

    public String getCategoryName()
    {
        return subcategoryName;
    }

    public int getCategoryID()
    {
        return subcategoryId;
    }

    public ArrayList<Subsubcategory> getSubcategories()
    {
        return subsubcategories;
    }

    public void setSubcategories(ArrayList<Subsubcategory> subsubcategories)
    {
        this.subsubcategories = subsubcategories;
    }

}
