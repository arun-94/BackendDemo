package com.tisser.puneet.tisserartisan.Queries;

import android.os.AsyncTask;
import android.util.Log;

import com.tisser.puneet.tisserartisan.HTTP.HTTPGetCall;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class SearchQuery extends AsyncTask<String, Void, String>
{
    String temp;
    public AsyncResponse delegate = null;

    @Override
    protected String doInBackground(String... params)
    {
        String query = params[0].trim();

        String url = "";
        try
        {
            url = "http://tisserindia.com/stores/mobileApi/mobileAPI.php?action=searchList&q=" + URLEncoder.encode(query, "UTF-8");
        }
        catch (UnsupportedEncodingException e)
        {
            e.printStackTrace();
        }

        // make Call to the url

        //Make call returns the whole JSON as is, in the form of a string
        temp = HTTPGetCall.makeCall(url);

        Log.d("ERTY", "URL: " + url);
        Log.d("ERTY", "RESPONSE " + temp);

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
            delegate.processFinish(result, 1);
            // all things went right
        }
    }
}

