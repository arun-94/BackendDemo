package com.tisser.puneet.tisserartisan.UI.Activity;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.tisser.puneet.tisserartisan.R;

import java.io.File;

import butterknife.Bind;
import butterknife.OnClick;


public class FullScreenViewActivityWithDelete extends BaseActivity
{

    private int pos;

    @Bind(R.id.imgDisplay) ImageView imgDisplay;
    @OnClick(R.id.btnClose)
    void close()
    {
        FullScreenViewActivityWithDelete.this.finish();
    }

    @OnClick(R.id.deleteImage)
    void delete() {
        View.OnClickListener mOnClickListener = new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent i = new Intent();
                i.putExtra("img_pos", pos);
                setResult(RESULT_OK, i);
                finish();
            }
        };
        Snackbar.make(findViewById(android.R.id.content), "Are you sure you want to delete?", Snackbar.LENGTH_LONG)
                .setAction("Yes", mOnClickListener)
                .setActionTextColor(Color.RED)
                .show();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        pos = getIntent().getIntExtra("img_pos", -1);
    }

    @Override
    protected int getLayoutResource()
    {
        return R.layout.activity_fullscreen_image_delete;
    }

    @Override
    protected void setupToolbar()
    {

    }

    @Override
    protected void setupLayout()
    {
        if(manager.currentImagePath.startsWith("http")) {
            Picasso.with(FullScreenViewActivityWithDelete.this).load(manager.currentImagePath).into(imgDisplay);
        }
        else
        {
            Uri uri = Uri.fromFile(new File(manager.currentImagePath));
            Picasso.with(FullScreenViewActivityWithDelete.this).load(uri).into(imgDisplay);
        }
    }

    @Override
    public void onBackPressed()
    {

    }

}
