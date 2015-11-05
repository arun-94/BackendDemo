package com.tisser.puneet.tisserartisan.Global;

import android.app.Application;
import android.graphics.drawable.Drawable;

import com.tisser.puneet.tisserartisan.Injection.Component.ApplicationComponent;
import com.tisser.puneet.tisserartisan.Injection.Component.DaggerApplicationComponent;
import com.tisser.puneet.tisserartisan.Model.Category;
import com.tisser.puneet.tisserartisan.Model.ProductDetailed;
import com.tisser.puneet.tisserartisan.Model.Response.LoginResponse;
import com.tisser.puneet.tisserartisan.Model.Subcategory;
import com.tisser.puneet.tisserartisan.Model.Subsubcategory;
import com.tisser.puneet.tisserartisan.Model.TisserColor;
import com.tisser.puneet.tisserartisan.Model.TisserSettings;
import com.tisser.puneet.tisserartisan.Module.ApplicationModule;

import java.util.ArrayList;
import java.util.Collections;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

import static com.tisser.puneet.tisserartisan.HTTP.RestClient.getApiService;

public class AppManager extends Application
{

    private ApplicationComponent applicationComponent;
    private String sessionID;
    private String userID;

    public ArrayList<Category> categoryList = new ArrayList<>();
    public ArrayList<TisserColor> colorList = new ArrayList<>();
    public ArrayList<ProductDetailed> productList = new ArrayList<>();
    public TisserSettings settings = new TisserSettings();

    // Currently selected items
    public Category currentCategory = new Category();
    public Subcategory currentSubCategory = new Subcategory();
    public Subsubcategory currentSubsubCategory = new Subsubcategory();
    public ProductDetailed currentProductDetailed = new ProductDetailed();
    public LoginResponse loginResponse = new LoginResponse();

    public int currentCategoryID;
    public String currentImagePath;
    public Drawable currentImage;

    @Override
    public void onCreate()
    {
        super.onCreate();
        initializeDependencies();

        getApiService().getColorsList(new Callback<ArrayList<TisserColor>>()
        {

            @Override
            public void success(ArrayList<TisserColor> colors, Response response)
            {
                ArrayList<TisserColor> unneededColors = new ArrayList<TisserColor>();
                for(TisserColor c : colors)
                {
                    if(!c.getCode().startsWith("#"))
                        unneededColors.add(c);
                }
                colors.removeAll(unneededColors);
                Collections.sort(colors);
                colorList = colors;
            }

            @Override
            public void failure(RetrofitError error)
            {
                //Alert for no internet!
            }
        });

        getApiService().getCategoryList(new Callback<ArrayList<Category>>()
        {

            @Override
            public void success(ArrayList<Category> categories, Response response)
            {
                categoryList = categories;
            }

            @Override
            public void failure(RetrofitError error)
            {
                //Alert for no internet!
            }
        });

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
