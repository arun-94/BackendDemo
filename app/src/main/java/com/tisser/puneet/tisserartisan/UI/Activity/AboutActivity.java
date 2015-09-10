package com.tisser.puneet.tisserartisan.UI.Activity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.tisser.puneet.tisserartisan.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class AboutActivity extends BaseActivity
{
    @Bind(R.id.headerImg)
    ImageView headerImg;
    @Bind(R.id.about_tv_contact)
    TextView abouttvcontact;
    @Bind(R.id.about_img_phone)
    ImageView aboutimgphone;
    @Bind(R.id.about_tv_phone)
    TextView abouttvphone;
    @Bind(R.id.ll_phone)
    LinearLayout llphone;
    @Bind(R.id.about_img_email)
    ImageView aboutimgemail;
    @Bind(R.id.about_tv_email)
    TextView abouttvemail;
    @Bind(R.id.ll_email)
    LinearLayout llemail;
    @Bind(R.id.about_tv_info)
    TextView abouttvinfo;
    @Bind(R.id.about_tv_info_text)
    TextView abouttvinfotext;
    @Bind(R.id.about_tv_social)
    TextView abouttvsocial;
    @Bind(R.id.about_img_website)
    ImageView aboutimgwebsite;
    @Bind(R.id.about_img_fb)
    ImageView aboutimgfb;
    @Bind(R.id.about_img_twitter)
    ImageView aboutimgtwitter;
    @Bind(R.id.about_img_instagram)
    ImageView aboutimginstagram;
    @Bind(R.id.ll_social_1)
    LinearLayout llsocial1;
    @Bind(R.id.about_img_linkedin)
    ImageView aboutimglinkedin;
    @Bind(R.id.about_img_pinterest)
    ImageView aboutimgpinterest;
    @Bind(R.id.about_img_blogspot)
    ImageView aboutimgblogspot;
    @Bind(R.id.ll_social_2)
    LinearLayout llsocial2;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }

    @Override
    protected int getLayoutResource()
    {
        return R.layout.fragment_about;
    }

    @Override
    protected void setupToolbar()
    {
        getSupportActionBar().setTitle("About Tisser");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void setupLayout()
    {
        abouttvphone.setText(manager.settings.getContact());
        abouttvemail.setText(manager.settings.getEmail());

        llphone.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:+91 " + manager.settings.getContact()));
                startActivity(callIntent);
            }
        });


        llemail.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
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
                }
                catch (ActivityNotFoundException ex)
                {
                    Toast.makeText(AboutActivity.this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        aboutimgwebsite.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Uri uri = Uri.parse("http://www.tisserindia.com/"); // missing 'http://' will cause crashed
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });

        aboutimgfb.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Uri uri = Uri.parse("https://www.facebook.com/TisserIndia"); // missing 'http://' will cause crashed
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });

        aboutimgtwitter.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Uri uri = Uri.parse("https://twitter.com/tisserindia"); // missing 'http://' will cause crashed
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });

        aboutimginstagram.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Uri uri = Uri.parse("https://instagram.com/tisserindia/"); // missing 'http://' will cause crashed
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });

        aboutimgpinterest.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Uri uri = Uri.parse("https://in.pinterest.com/Tisserindia/"); // missing 'http://' will cause crashed
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });

        aboutimglinkedin.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Uri uri = Uri.parse("https://www.linkedin.com/in/tisser"); // missing 'http://' will cause crashed
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });

        aboutimgblogspot.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Uri uri = Uri.parse("http://tisserindia.blogspot.in/"); // missing 'http://' will cause crashed
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });
    }


}
