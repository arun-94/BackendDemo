package com.tisser.puneet.tisserartisan.Global;

import android.app.Application;
import android.graphics.drawable.Drawable;

import com.tisser.puneet.tisserartisan.Component.ApplicationComponent;
import com.tisser.puneet.tisserartisan.Component.DaggerApplicationComponent;
import com.tisser.puneet.tisserartisan.Model.Category;
import com.tisser.puneet.tisserartisan.Model.Product;
import com.tisser.puneet.tisserartisan.Model.ProductDetailed;
import com.tisser.puneet.tisserartisan.Model.TisserColor;
import com.tisser.puneet.tisserartisan.Model.TisserSettings;
import com.tisser.puneet.tisserartisan.Module.ApplicationModule;

import java.util.ArrayList;

/**
 * Created by Puneet on 16-07-2015.
 */
public class AppManager extends Application
{

    private ApplicationComponent applicationComponent;
    private String sessionID;

    public ArrayList<Category> categoryList = new ArrayList<>();
    public ArrayList<Product> productList = new ArrayList<>();
    public Category currentCategory = new Category();
    public Category currentSubCategory = new Category();
    public Category currentSubsubCategory = new Category();
    public ProductDetailed currentProduct = new ProductDetailed();
    public TisserSettings settings = new TisserSettings();
    public ArrayList<TisserColor> colorList = new ArrayList<>();
    public String hello = "hello";
    public int currentCategoryID;
    public Drawable currentImage;

    @Override
    public void onCreate()
    {
        super.onCreate();
        initializeDependencies();

        TisserColor c = new TisserColor();
        c.setColor("Yo");
        c.setCode("#ffffff");
        colorList.add(c);
        c = new TisserColor();
        c.setColor("Yo2");
        c.setCode("#ff00ff");
        colorList.add(c);
        c = new TisserColor();
        c.setColor("Yo3");
        c.setCode("#0000ff");
        colorList.add(c);
        c = new TisserColor();
        c.setColor("Yo4");
        c.setCode("#ff0000");
        colorList.add(c);

    }

    private void initializeDependencies()
    {
        //initializing an instance of application component and not the entire component
        //use the component to access various module functions wherever injected
        this.applicationComponent = DaggerApplicationComponent.builder().applicationModule(new ApplicationModule(this)).build();
    }

    public ApplicationComponent getApplicationComponent()
    {
        return this.applicationComponent;
    }

    public String getSessionID()
    {
        return sessionID;
    }

    public void setSessionID(String sID)
    {
        sessionID = sID;
    }


}
