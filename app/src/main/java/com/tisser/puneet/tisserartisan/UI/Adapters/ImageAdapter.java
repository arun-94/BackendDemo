package com.tisser.puneet.tisserartisan.UI.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.tisser.puneet.tisserartisan.Global.AppConstants;
import com.tisser.puneet.tisserartisan.Global.AppManager;
import com.tisser.puneet.tisserartisan.Model.Response.ImageResponse;
import com.tisser.puneet.tisserartisan.R;
import com.tisser.puneet.tisserartisan.UI.Activity.AddProductActivity;
import com.tisser.puneet.tisserartisan.UI.Activity.FullScreenViewActivity;
import com.tisser.puneet.tisserartisan.UI.Custom.TouchImageView;

import java.io.File;
import java.util.ArrayList;

public class ImageAdapter extends PagerAdapter
{
    Context mContext;
    AppManager manager;
    private ArrayList<ImageResponse> imageURLS = new ArrayList<>();
    private ArrayList<String> imagePaths = new ArrayList<>();
    int flag;

    public ImageAdapter(Context context, ArrayList<ImageResponse> imageURLS, AppManager manager)
    {
        this.mContext = context;
        if(imageURLS != null)
            this.imageURLS.addAll(imageURLS);
        this.manager = manager;
    }

    @Override
    public int getCount()
    {
        if(imageURLS.size() != 0)
            return imageURLS.size();
        else
            return imagePaths.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object)
    {
        return view == ((ImageView) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position)
    {
        final TouchImageView imageView = new TouchImageView(mContext);
        imageView.setTransitionName(mContext.getString(R.string.transition_product_image));
        //int padding = context.getResources().getDimensionPixelSize(R.dimen.padding_medium);
        //imageView.setPadding(padding, padding, padding, padding);
        imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        if(imageURLS.size() != 0)
        {
            Picasso.with(mContext).load(imageURLS.get(position).getPath()).into(imageView);
        }
        else {
            Uri uri = Uri.fromFile(new File(imagePaths.get(position)));
            Picasso.with(mContext).load(uri).placeholder(R.drawable.logo_small).into(imageView);
        }
        //ImageLoader imgLoader = new ImageLoader(context);
        //imgLoader.DisplayImage("http://tisserindia.com/stores/thumb_gen.php?file=" + imageURLS.get(position), R.drawable.logo_small, imageView);
        ((ViewPager) container).addView(imageView, 0);
        imageView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                ActivityOptionsCompat options = ActivityOptionsCompat.
                        makeSceneTransitionAnimation((Activity) mContext, (View) v, mContext.getResources().getString(R.string.transition_product_image));
                Intent i = new Intent(mContext, FullScreenViewActivity.class);
                manager.currentImage = imageView.getDrawable();
                mContext.startActivity(i, options.toBundle());
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