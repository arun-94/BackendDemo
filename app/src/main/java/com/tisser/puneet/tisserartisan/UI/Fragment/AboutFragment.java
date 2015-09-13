package com.tisser.puneet.tisserartisan.UI.Fragment;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.tisser.puneet.tisserartisan.R;

import butterknife.Bind;
import butterknife.OnClick;

public class AboutFragment extends BaseFragment
{
    /*** We set these below from the TisserSettings Model ***/
    @Bind(R.id.about_tv_phone) TextView abouttvphone;
    @Bind(R.id.about_tv_email) TextView abouttvemail;

    @OnClick(R.id.ll_phone)
    void phoneIntent()
    {
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:+91 " + manager.settings.getContact()));
        startActivity(callIntent);
    }


    @OnClick(R.id.ll_email)
    void emailIntent()
    {
        Intent i = new Intent(Intent.ACTION_SEND);
        i.setType("message/rfc822");
        i.putExtra(Intent.EXTRA_EMAIL, new String[]{manager.settings.getEmail()});
        i.putExtra(Intent.EXTRA_SUBJECT, "Tisser Android App Enquiry");
        String content = "";
        content = content.concat("\n\n\n - Sent via Tisser Android app");
        i.putExtra(Intent.EXTRA_TEXT, content);
        try
        {
            startActivity(Intent.createChooser(i, "Send Email"));
        } catch (ActivityNotFoundException ex)
        {
            Toast.makeText(getActivity(), "There are no email clients installed.", Toast.LENGTH_SHORT).show();
        }
    }

    @OnClick(R.id.about_img_website)
    void websiteClick()
    {
        Uri uri = Uri.parse("http://www.tisserindia.com/"); // missing 'http://' will cause crashed
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }

    @OnClick(R.id.about_img_fb)
    void fbClick()
    {
        Uri uri = Uri.parse("https://www.facebook.com/TisserIndia"); // missing 'http://' will cause crashed
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }

    @OnClick(R.id.about_img_twitter)
    void twitterClick()
    {
        Uri uri = Uri.parse("https://twitter.com/tisserindia"); // missing 'http://' will cause crashed
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }

    @OnClick(R.id.about_img_instagram)
    void instagramClick()
    {
        Uri uri = Uri.parse("https://instagram.com/tisserindia/"); // missing 'http://' will cause crashed
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }

    @OnClick(R.id.about_img_pinterest)
    void pinteretClick()
    {
        Uri uri = Uri.parse("https://in.pinterest.com/Tisserindia/"); // missing 'http://' will cause crashed
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }

    @OnClick(R.id.about_img_linkedin)
    void linkedInClick()
    {
        Uri uri = Uri.parse("https://www.linkedin.com/in/tisser"); // missing 'http://' will cause crashed
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }

    @OnClick(R.id.about_img_blogspot)
    void blogspotClick()
    {
        Uri uri = Uri.parse("http://tisserindia.blogspot.in/"); // missing 'http://' will cause crashed
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }

    public static AboutFragment newInstance()
    {
        AboutFragment fragment = new AboutFragment();
        return fragment;
    }

    public AboutFragment()
    {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutResource()
    {
        return R.layout.fragment_about;
    }

    @Override
    protected String setupToolbarTitle()
    {
        return "About Tisser";
    }


    @Override
    protected void setupLayout()
    {
        abouttvphone.setText(manager.settings.getContact());
        abouttvemail.setText(manager.settings.getEmail());
    }


}
