package com.tisser.puneet.tisserartisan.UI.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


import com.tisser.puneet.tisserartisan.Global.AppManager;
import com.tisser.puneet.tisserartisan.LoadImageFromURL.ImageLoader;
import com.tisser.puneet.tisserartisan.R;
import com.tisser.puneet.tisserartisan.UI.Activity.FullScreenViewActivity;
import com.tisser.puneet.tisserartisan.UI.Custom.TouchImageView;

import java.util.ArrayList;

public class ImageAdapter extends PagerAdapter
{
    Context context;
    AppManager manager;
    private ArrayList<String> imageURLS = new ArrayList<>();

    public ImageAdapter(Context context, ArrayList<String> imageURLS, AppManager manager)
    {
        this.context = context;
        if(imageURLS != null)
            this.imageURLS.addAll(imageURLS);
        this.manager = manager;
    }

    @Override
    public int getCount()
    {
        return imageURLS.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object)
    {
        return view == ((ImageView) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position)
    {
        final TouchImageView imageView = new TouchImageView(context);
        //int padding = context.getResources().getDimensionPixelSize(R.dimen.padding_medium);
        //imageView.setPadding(padding, padding, padding, padding);
        imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        ImageLoader imgLoader = new ImageLoader(context);
        imgLoader.DisplayImage("http://tisserindia.com/stores/thumb_gen.php?file=" + imageURLS.get(position), R.drawable.logo_small, imageView);
        ((ViewPager) container).addView(imageView, 0);
        imageView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent i = new Intent(context, FullScreenViewActivity.class);
                //i.putExtra("itemURL", image);
                manager.currentImage = imageView.getDrawable();
                context.startActivity(i);
            }
        });
        imageView.getLayoutParams().width = 300;
        imageView.getLayoutParams().height = 300;
        imageView.requestLayout();
        return imageView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object)
    {
        ((ViewPager) container).removeView((ImageView) object);
    }
}