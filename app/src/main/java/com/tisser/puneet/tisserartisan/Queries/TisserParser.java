package com.tisser.puneet.tisserartisan.Queries;

import android.content.Context;
import android.util.Log;


import com.tisser.puneet.tisserartisan.Model.Category;
import com.tisser.puneet.tisserartisan.Model.Product;
import com.tisser.puneet.tisserartisan.Model.ProductDetailed;
import com.tisser.puneet.tisserartisan.Model.Review;
import com.tisser.puneet.tisserartisan.Model.TisserSettings;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Pattern;

public class TisserParser
{
    public static ArrayList<Category> parseTisserCategory(String response, Context context)
    {

        ArrayList<Category> temp = new ArrayList<>();
        try
        {
            // make an jsonObject in order to parse the response
            JSONArray jsonArray = new JSONArray(response);
            Log.d("PARSER", "Length of array is : " + jsonArray.length());
            for (int i = 0; i < jsonArray.length(); i++)
            {
                Category newCategory = new Category();
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                newCategory.setCategoryName(jsonObject.getString("category_name"));
                newCategory.setCategoryID(jsonObject.getInt("category_id"));
                JSONArray subcategoryJsonArray = jsonObject.getJSONArray("subcategories");

                //The subcategory
                ArrayList<Category> subcategoryArray = new ArrayList<>();
                for (int j = 0; j < subcategoryJsonArray.length(); j++)
                {
                    Category newSubcategory = new Category();
                    JSONObject jsonSubObject = subcategoryJsonArray.getJSONObject(j);
                    newSubcategory.setCategoryName(jsonSubObject.getString("subcategory_name"));
                    newSubcategory.setCategoryID(jsonSubObject.getInt("subcategory_id"));
                    JSONArray subsubcategoryJsonArray = jsonSubObject.getJSONArray("subsubcategories");

                    //The sub sub categories
                    ArrayList<Category> subsubcategoryArray = new ArrayList<>();
                    Category selfSubsubCategory = new Category();
                    selfSubsubCategory.setCategoryName("All " + newSubcategory.getCategoryName());
                    selfSubsubCategory.setCategoryID(newSubcategory.getCategoryID());
                    subsubcategoryArray.add(selfSubsubCategory);
                    for (int k = 0; k < subsubcategoryJsonArray.length(); k++)
                    {
                        Category newSubsubcategory = new Category();
                        JSONObject jsonSubsubObject = subsubcategoryJsonArray.getJSONObject(k);
                        newSubsubcategory.setCategoryName(jsonSubsubObject.getString("subsubcategory_name"));
                        newSubsubcategory.setCategoryID(jsonSubsubObject.getInt("subsubcategory_id"));
                        subsubcategoryArray.add(newSubsubcategory);
                    }
                    //Added the list of subsubcategory to the subcategory object
                    newSubcategory.setSubcategories(subsubcategoryArray);
                    //Adding the subcategory to the list of subcategories
                    subcategoryArray.add(newSubcategory);
                }
                //Finally we add the list of subcategories to the category
                newCategory.setSubcategories(subcategoryArray);
                temp.add(newCategory);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return new ArrayList<>();
        }
        return temp;

    }

    public static ArrayList<Product> parseTisserProductList(final String response, final int categoryID)
    {
        ArrayList<Product> temp = new ArrayList<>();
        try
        {
            // make an jsonObject in order to parse the response
            JSONArray jsonArray = new JSONArray(response);
            Log.d("PARSER", "Length of array is : " + jsonArray.length());
            for (int i = 0; i < jsonArray.length(); i++)
            {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String name = jsonObject.getString("product_name");
                String code = jsonObject.getString("product_code");
                String productID = jsonObject.getString("product_id");

                int price = jsonObject.getInt("product_price");
                String imagePath = jsonObject.getString("product_img");

                if (code.equals("NA") || code.equals("N\\A"))
                {
                    code = imagePath.split(Pattern.quote("."))[0];
                }
                Product newProduct = new Product(name, code, price, imagePath, categoryID, productID);
                temp.add(newProduct);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return new ArrayList<>();
        }
        return temp;
    }

    public static ProductDetailed parseTisserProductDetails(final String response)
    {
        JSONObject jsonObject;
        ProductDetailed detailedProduct = new ProductDetailed();
        try
        {
            jsonObject = new JSONObject(response);
            String name = jsonObject.getString("product_name");
            String code = jsonObject.getString("product_code");

            int categoryID = jsonObject.getInt("product_category");
            int subcategoryID = jsonObject.getInt("product_subcategory");

            JSONObject productAbout = jsonObject.getJSONObject("product_about");

            //Color related
            int basecolor = jsonObject.getInt("product_basecolor");
            String color = jsonObject.getString("product_color");

            int price = jsonObject.getInt("product_price");

            //Image adding to array of image paths
            ArrayList<String> images = new ArrayList<>();
            images.add(jsonObject.getString("product_img").replaceAll(" ", "%20"));
            if (!jsonObject.getString("product_subimg").equals("") || jsonObject.getString("product_subimg") == null)
            {
                images.addAll(Arrays.asList(jsonObject.getString("product_subimg").replaceAll(" ", "%20").split(";")));
            }

            //Shipping details
            JSONObject shipping = jsonObject.getJSONObject("product_shipping");
            String estimatedDelivery = shipping.getString("estimated_delivery");
            String estimatedCost = shipping.getString("estimated_cost");

            //Product Reviews
            JSONArray reviewsJSONArray = jsonObject.getJSONArray("product_reviews");
            ArrayList<Review> reviews = new ArrayList<>();
            for (int i = 0; i < reviewsJSONArray.length(); i++)
            {
                JSONObject reviewJSONObject = reviewsJSONArray.getJSONObject(i);
                if (reviewJSONObject.getString("review_id") != null && !reviewJSONObject.getString("review_id").equals(""))
                {
                    Review r = new Review(reviewJSONObject.getString("review_id"),
                            reviewJSONObject.getString("review_date"),
                            reviewJSONObject.getString("review_text"),
                            reviewJSONObject.getString("review_user"),
                            reviewJSONObject.getString("review_user_img"));
                    reviews.add(r);
                }
            }

            detailedProduct.setProductCode(code);
            detailedProduct.setProductName(name);
            detailedProduct.setProductCategoryID(categoryID);
            detailedProduct.setProductSubcategoryID(subcategoryID);
            detailedProduct.setProductBaseColor(basecolor);
            detailedProduct.setProductColor(color);
            detailedProduct.setProductPrice(price);
            detailedProduct.setProductPrice(price);
            detailedProduct.setProductImgPaths(images);
            detailedProduct.setProductEstimatedCost(estimatedCost);
            detailedProduct.setProductEstimatedDelivery(estimatedDelivery);
            detailedProduct.setProductSummary(productAbout.getString("product_summary").trim());
            detailedProduct.setProductDescription(productAbout.getString("product_description").trim());
            detailedProduct.setProductKeypoints(productAbout.getString("product_keypoints").replaceAll("__", "\n"));
            detailedProduct.setProductReviews(reviews);
        }
        catch (JSONException e)
        {
            e.printStackTrace();
            return null;
        }
        return detailedProduct;

    }

    public static TisserSettings parseTisserSettings(final String response)
    {
        TisserSettings settings = new TisserSettings();
        try
        {
            JSONObject jsonObject = new JSONObject(response);
            settings.setEmail(jsonObject.getString("email"));
            settings.setContact(jsonObject.getString("contactNumber"));
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return new TisserSettings();
        }
        return settings;
    }
}
