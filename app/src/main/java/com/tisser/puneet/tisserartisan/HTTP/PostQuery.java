package com.tisser.puneet.tisserartisan.HTTP;

import android.os.AsyncTask;
import android.util.Log;

import com.tisser.puneet.tisserartisan.HTTP.HTTPPostCall;


public class PostQuery extends AsyncTask<String, Void, String>
{
    // the foursquare client_id and the client_secret

    // ============== BELOW KEYS ARE FOR PUNEET FB ID ====================//
    //final String CLIENT_ID = "44H4B0QVKH5GEQXLEAXVLDHHXW4DIJXRYSWJELREON5EEKAV";
    //final String CLIENT_SECRET = "TB2IENCF3QWG5DHPFHPNBYKTWZAIQN4LCFHRDZXJV4VUO02R";
    // we will need to take the latitude and the longitude from a certain point
    // this is the center of New York

    String temp;

    @Override
    protected String doInBackground(String... params)
    {
        // make Call to the url
        //Make call returns the whole JSON as is, in the form of a string
        //temp = HTTPCall.makeCall("https://api.foursquare.com/v2/venues/search?client_id=" + CLIENT_ID + "&client_secret=" + CLIENT_SECRET + "&categoryId=4d4b7105d754a06374d81259" + "&radius=800&intent=browse" + "&v=20130815&ll=" + latitude + "," + longitude);
        //temp = HTTPCall.makeCall("https://api.foursquare.com/v2/venues/"+venueID+"?client_id=" + CLIENT_ID + "&client_secret=" + CLIENT_SECRET +"&v=20130815&ll=" + latitude + "," + longitude);
        temp =  HTTPPostCall.makeCall(params[0]);
        Log.d("POSTURLLOG", temp);
        return "";
    }

    @Override
    protected void onPreExecute()
    {
        // we can start a progress bar here
    }

    @Override
    protected void onPostExecute(String result)
    {
        if (temp == null)
        {
            // we have an error to the call
            // we can also stop the progress bar
        }
        else
        {
            result = temp;
            // all things went right
        }
    }
}