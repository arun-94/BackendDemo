package com.tisser.puneet.tisserartisan.UI.Activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.tisser.puneet.tisserartisan.R;

import butterknife.Bind;
import butterknife.OnClick;


public class FullScreenViewActivity extends BaseActivity
{

    @Bind(R.id.imgDisplay) ImageView imgDisplay;

    @OnClick(R.id.btnClose)
    void close()
    {
        onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutResource()
    {
        return R.layout.activity_fullscreen_image;
    }

    @Override
    protected void setupToolbar()
    {

    }

    @Override
    protected void setupLayout()
    {
        imgDisplay.setImageDrawable(manager.currentImage);
    }

}
