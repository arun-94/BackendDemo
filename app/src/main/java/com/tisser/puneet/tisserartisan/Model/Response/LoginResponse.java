package com.tisser.puneet.tisserartisan.Model.Response;

import com.google.gson.annotations.SerializedName;

import java.io.File;

/**
 * Created by Puneet on 14-10-2015.
 */
public class LoginResponse
{
    @SerializedName("status") String sessionID = "";
    @SerializedName("error") int errorCode = 0;
    @SerializedName("id") String userId;
    @SerializedName("firstName") String firstName;
    @SerializedName("lastName") String lastName;
    @SerializedName("email") String email;
    @SerializedName("mobile") String mobile;
    @SerializedName("landline") String landline;
    @SerializedName("company") String company;
    @SerializedName("address1") String address1;
    @SerializedName("address2") String address2;
    @SerializedName("city") String city;
    @SerializedName("postcode") String postcode;
    @SerializedName("country") String country;
    @SerializedName("region") String region;
    @SerializedName("password") String password;
    @SerializedName("profile_image") String profileImage;
    @SerializedName("activationtoken") String activationToken;
    @SerializedName("active") String active;
    @SerializedName("artistmobileSession") String artistMobileSession;
    private File profileLocalFile = null;


    public String getFullName() {
        return getFirstName() + " " + getLastName();
    }

    public String getSessionID()
    {
        return sessionID;
    }

    public int getError()
    {
        return errorCode;
    }

    public int getErrorCode()
    {
        return errorCode;
    }

    public String getUserId()
    {
        return userId;
    }

    public String getFirstName()
    {
        if(firstName != null)
            return firstName;
        else
            return "";
    }

    public String getLastName()
    {
        if(lastName != null)
            return lastName;
        else
            return "";
    }

    public String getEmail()
    {
        if(email != null)
            return email;
        else
            return "";
    }

    public String getMobile()
    {
        if(mobile != null)
            return mobile;
        else
            return "";
    }

    public String getLandline()
    {
        if(landline != null)
            return landline;
        else
            return "";
    }

    public String getCompany()
    {
        if(company != null)
            return company;
        else
            return "";
    }

    public String getAddress1()
    {
        if(address1 != null)
            return address1;
        else
            return "";    }

    public String getAddress2()
    {
        if(address2 != null)
            return address2;
        else
            return "";    }

    public String getCity()
    {
        if(city != null)
            return city;
        else
            return "";    }

    public String getPostcode()
    {
        return postcode;
    }

    public String getCountry()
    {
        return country;
    }

    public String getRegion()
    {
        return region;
    }

    public String getPassword()
    {
        return password;
    }

    public String getProfileImage()
    {
        if(profileImage != null && !profileImage.equals("") && profileImage.length() > 2)
            return profileImage.substring(2);
        else
            return profileImage;
    }

    public String getActivationToken()
    {
        return activationToken;
    }

    public String getActive()
    {
        return active;
    }

    public String getArtistMobileSession()
    {
        return artistMobileSession;
    }

    public File getProfileLocalFile()
    {
        return profileLocalFile;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public void setMobile(String mobile)
    {
        this.mobile = mobile;
    }

    public void setAddress1(String address1)
    {
        this.address1 = address1;
    }

    public void setProfileImage(String profileImage)
    {
        this.profileImage = profileImage;
    }

    public void setProfileLocalFile(File profileLocalFile)
    {
        this.profileLocalFile = profileLocalFile;
    }
}
