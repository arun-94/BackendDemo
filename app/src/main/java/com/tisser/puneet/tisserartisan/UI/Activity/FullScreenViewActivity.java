package com.tisser.puneet.tisserartisan.UI.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import com.tisser.puneet.tisserartisan.R;

import butterknife.Bind;
import butterknife.OnClick;


public class FullScreenViewActivity extends BaseActivity
{

    private int pos;

    @Bind(R.id.imgDisplay) ImageView imgDisplay;
    @OnClick(R.id.btnClose)
    void close()
    {
        FullScreenViewActivity.this.finish();
    }

    @OnClick(R.id.deleteImage)
    void delete() {
        Intent i = new Intent();
        i.putExtra("img_pos", pos);
        setResult(RESULT_OK, i);
        finish();
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

    @Override
    public void onBackPressed()
    {

    }

}
