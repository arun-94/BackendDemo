package com.tisser.puneet.tisserartisan.UI.Activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.tisser.puneet.tisserartisan.R;


public class FullScreenViewActivity extends BaseActivity
{

    ImageView imgDisplay;
    Button btnClose;

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
        imgDisplay = (ImageView) findViewById(R.id.imgDisplay);
        imgDisplay.setImageDrawable(manager.currentImage);
        btnClose = (Button) findViewById(R.id.btnClose);
        btnClose.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                FullScreenViewActivity.this.finish();
            }
        });
    }

}
