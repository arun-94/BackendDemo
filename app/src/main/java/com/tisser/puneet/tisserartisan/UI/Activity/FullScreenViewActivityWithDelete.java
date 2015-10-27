package com.tisser.puneet.tisserartisan.UI.Activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.tisser.puneet.tisserartisan.Global.AppConstants;
import com.tisser.puneet.tisserartisan.R;
import com.tisser.puneet.tisserartisan.UI.Custom.ImageUtility;

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
        onBackPressed();
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
            Picasso.with(FullScreenViewActivityWithDelete.this).load(manager.currentImagePath).placeholder(R.drawable.logo_small).into(imgDisplay);
        }
        else
        {
            Uri uri = Uri.fromFile(new File(manager.currentImagePath));
            Picasso.with(FullScreenViewActivityWithDelete.this).load(uri).placeholder(R.drawable.logo_small).into(new Target()
            {
                @Override
                public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from)
                {
                    Bitmap bm = ImageUtility.getResizedBitmap(bitmap, AppConstants.IMAGE_RESIZE_DIMEN);
                    imgDisplay.setImageBitmap(bm);
                }

                @Override
                public void onBitmapFailed(Drawable errorDrawable)
                {

                }

                @Override
                public void onPrepareLoad(Drawable placeHolderDrawable)
                {

                }
            });
        }
    }

    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
    }

}
