package com.tisser.puneet.tisserartisan.UI.Activity;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.widget.ImageView;

import com.tisser.puneet.tisserartisan.R;

import butterknife.Bind;

public class ProfileActivity extends BaseActivity
{

    @Bind(R.id.appbar)
    AppBarLayout mAppBarLayout;
    @Bind(R.id.artisan_profile_image)
    ImageView mProfileImage;

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
    protected void setupLayout()
    {
    }
}