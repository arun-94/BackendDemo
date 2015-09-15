package com.tisser.puneet.tisserartisan.UI.Activity;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.Toast;

import com.tisser.puneet.tisserartisan.Qualifier.ActivityContext;

import javax.inject.Inject;

/**
 * Created by Arun on 13-Sep-15.
 */
public class Navigator
{

    private Context activityContext;


    @Inject
    public void Navigator(@ActivityContext Context activityContext) {
        this.activityContext = activityContext;
    }

    public void navigateToBaseActivity_NavDrawer(Context mContext)
    {
        activityContext = mContext;
        Intent intent = BaseActivity_NavDrawer.getLaunchIntent(mContext);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    public void callIntent(Context mContext, String contact) {
        activityContext = mContext;
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:+91 " + contact));
        startActivity(callIntent);
    }

    public void emailIntent(Context mContext, String email) {
        activityContext = mContext;
        Intent i = new Intent(Intent.ACTION_SEND);
        i.setType("message/rfc822");
        i.putExtra(Intent.EXTRA_EMAIL, new String[]{email});
        i.putExtra(Intent.EXTRA_SUBJECT, "Tisser Android App Enquiry");
        String content = "";
        content = content.concat("\n\n\n - Sent via Tisser Android app");
        i.putExtra(Intent.EXTRA_TEXT, content);
        try
        {
            startActivity(Intent.createChooser(i, "Send Email"));
        } catch (ActivityNotFoundException ex)
        {
            Toast.makeText(mContext, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
        }
    }

    public void openUrl(Context mContext, String url) {
        activityContext = mContext;
        Uri uri = Uri.parse(url); // missing 'http://' will cause crashed
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }


    private void startActivity(Intent intent) {
        activityContext.startActivity(intent);
    }

}
