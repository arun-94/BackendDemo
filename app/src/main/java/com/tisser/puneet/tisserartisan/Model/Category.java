package com.tisser.puneet.tisserartisan.Model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Category
{
    @SerializedName("category_name") private String categoryName;
    @SerializedName("category_id") private int categoryID;
    @SerializedName("subcategories") private ArrayList<Subcategory> subcategories;

    public Category()
    {
        this.categoryID = 0;
        this.categoryName = "";
        this.subcategories = new ArrayList<>();
    }

    public Category(String categoryName, int categoryID, ArrayList<Subcategory> subcategories)
    {
        this.categoryName = categoryName;
        this.categoryID = categoryID;
        this.subcategories = subcategories;
    }

    public int getCategoryID()
    {
        return categoryID;
    }

    public String getCategoryName()
    {
        return categoryName;
    }

    public ArrayList<Subcategory> getSubcategories()
    {
        return subcategories;
    }

    public void setSubcategories(ArrayList<Subcategory> subcategories)
    {
        this.subcategories = subcategories;
    }

    @Override
    public boolean equals(Object o)
    {
        return (o instanceof Category && getCategoryID() == ((Category) o).getCategoryID());
    }


}
