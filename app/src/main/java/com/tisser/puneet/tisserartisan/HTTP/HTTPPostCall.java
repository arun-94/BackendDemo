package com.tisser.puneet.tisserartisan.HTTP;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.ByteArrayBuffer;

import java.io.BufferedInputStream;
import java.io.InputStream;

/**
 * Created by Puneet-The Admin on 1/29/2015.
 */
public class HTTPPostCall
{
    public static String makeCall(String url)
    {

        // string buffers the url
        StringBuffer buffer_string = new StringBuffer(url);
        String replyString = "";

        // instanciate an HttpClient
        HttpClient httpclient = new DefaultHttpClient();
        // instanciate an HtppPost
        HttpPost httppost = new HttpPost(buffer_string.toString());

        try
        {
            // get the responce of the httpclient execution of the url
            HttpResponse response = httpclient.execute(httppost);
            InputStream is = response.getEntity().getContent();

            // buffer input stream the result
            BufferedInputStream bis = new BufferedInputStream(is);
            ByteArrayBuffer baf = new ByteArrayBuffer(20);
            int current = 0;
            while ((current = bis.read()) != -1)
            {
                baf.append((byte) current);
            }
            // the result as a string is ready for parsing
            replyString = new String(baf.toByteArray());
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        // trim the whitespaces
        return replyString.trim();
    }
}
