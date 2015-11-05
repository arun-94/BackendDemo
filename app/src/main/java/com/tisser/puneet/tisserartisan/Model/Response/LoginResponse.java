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
        return firstName;
    }

    public String getLastName()
    {
        return lastName;
    }

    public String getEmail()
    {
        return email;
    }

    public String getMobile()
    {
        return mobile;
    }

    public String getLandline()
    {
        return landline;
    }

    public String getCompany()
    {
        return company;
    }

    public String getAddress1()
    {
        return address1;
    }

    public String getAddress2()
    {
        return address2;
    }

    public String getCity()
    {
        return city;
    }

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
        return profileImage.substring(2);
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
