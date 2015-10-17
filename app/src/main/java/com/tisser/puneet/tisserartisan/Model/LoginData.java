package com.tisser.puneet.tisserartisan.Model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Puneet on 14-10-2015.
 */
public class LoginData
{
    @SerializedName("status") String sessionID;
    @SerializedName("error") int errorCode;

    public String getSessionID()
    {
        return sessionID;
    }

    public int getError()
    {
        return errorCode;
    }

}
