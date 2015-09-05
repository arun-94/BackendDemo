package com.tisser.puneet.tisserartisan.Model;

import java.util.ArrayList;

public class Category
{
    private String categoryName;
    private int categoryID;
    private ArrayList<Category> subcategories;

    public Category()
    {
        this.categoryID = 0;
        this.categoryName = "";
        this.subcategories = new ArrayList<>();
    }

    public Category(String categoryName, int categoryID, ArrayList<Category> subcategories)
    {
        this.categoryName = categoryName;
        this.categoryID = categoryID;
        this.subcategories = subcategories;
    }

    public int getCategoryID()
    {
        return categoryID;
    }

    public void setCategoryID(int categoryID)
    {
        this.categoryID = categoryID;
    }

    public String getCategoryName()
    {
        return categoryName;
    }

    public void setCategoryName(String categoryName)
    {
        this.categoryName = categoryName;
    }

    public ArrayList<Category> getSubcategories()
    {
        return subcategories;
    }

    public void setSubcategories(ArrayList<Category> subcategories)
    {
        this.subcategories = subcategories;
    }

    @Override
    public boolean equals(Object o) {
        return (o instanceof Category && getCategoryID() == ((Category) o).getCategoryID());

    }
}
