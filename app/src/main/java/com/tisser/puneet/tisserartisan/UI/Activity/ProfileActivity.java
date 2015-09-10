package com.tisser.puneet.tisserartisan.UI.Activity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.tisser.puneet.tisserartisan.R;

public class ProfileActivity extends BaseActivity
{

    private AppBarLayout mAppBarLayout;
    private ImageView mProfileImage;
    private CollapsingToolbarLayout mCollapsingToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutResource()
    {
        return R.layout.activity_profile;
    }

    @Override
    protected void setupToolbar()
    {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void initializeLayout()
    {
        mAppBarLayout = (AppBarLayout) findViewById(R.id.appbar);
        mProfileImage = (ImageView) findViewById(R.id.profile_image);
    }

    @Override
    protected void setupLayout()
    {
    }
}