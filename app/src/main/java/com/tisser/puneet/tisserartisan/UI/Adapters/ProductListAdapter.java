package com.tisser.puneet.tisserartisan.UI.Adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.Request;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SizeReadyCallback;
import com.bumptech.glide.request.target.Target;
import com.tisser.puneet.tisserartisan.Model.ProductDetailed;
import com.tisser.puneet.tisserartisan.R;
import com.tisser.puneet.tisserartisan.UI.Custom.ImageUtility;

import java.util.ArrayList;
import java.util.List;

public class ProductListAdapter extends RecyclerView.Adapter<ProductListAdapter.VHProduct>
{

    private final Context mContext;
    private List<ProductDetailed> mData;


    public ProductListAdapter(Context context, ArrayList<ProductDetailed> data)
    {
        mContext = context;
        if (data != null)
        {
            mData = data;
        }
        else
        {
            mData = new ArrayList<ProductDetailed>();
        }
    }

    public void add(ProductDetailed s, int position)
    {
        position = position == -1 ? getItemCount() : position;
        mData.add(position, s);
        notifyItemInserted(position);
    }

    public void remove(int position)
    {
        if (position < getItemCount())
        {
            mData.remove(position);
            notifyItemRemoved(position);
        }
    }

    public VHProduct onCreateViewHolder(ViewGroup parent, int viewType)
    {
        final View view = LayoutInflater.from(mContext).inflate(R.layout.products_grid_item, parent, false);
        return new VHProduct(view);
    }

    @Override
    public void onBindViewHolder(final VHProduct holder, final int position)
    {
        ProductDetailed product = mData.get(position);
        String imgPath = "";
        if (product.getImages() != null)
        {
            if (product.getImages().get(0).getPath().contains(" "))
            {
                Log.d("IMAGE", "There was a space");
                imgPath = product.getProductImgPaths().get(0).replace(" ", "%20");
            }

            Glide.with(mContext).load(product.getImages().get(0).getPath()).asBitmap().centerCrop().placeholder(R.drawable.logo_small).into(new Target<Bitmap>()
            {
                @Override
                public void onLoadStarted(Drawable placeholder)
                {

                }

                @Override
                public void onLoadFailed(Exception e, Drawable errorDrawable)
                {

                }

                @Override
                public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation)
                {
                        Bitmap bm = ImageUtility.getResizedBitmap(resource, 300);
                        holder.image.setImageBitmap(bm);
                }

                @Override
                public void onLoadCleared(Drawable placeholder)
                {

                }

                @Override
                public void getSize(SizeReadyCallback cb)
                {

                }

                @Override
                public void setRequest(Request request)
                {

                }

                @Override
                public Request getRequest()
                {
                    return null;
                }

                @Override
                public void onStart()
                {

                }

                @Override
                public void onStop()
                {

                }

                @Override
                public void onDestroy()
                {

                }
            });
            holder.image.setScaleType(ImageView.ScaleType.CENTER_CROP);
        }
        holder.title.setText(mData.get(position).getProductName());
        holder.price.setText("Rs. " + mData.get(position).getProductPrice());
    }

    @Override
    public int getItemCount()
    {
        return mData.size();
    }


    public static class VHProduct extends RecyclerView.ViewHolder
    {
        public final TextView title;
        public final TextView price;
        public final ImageView image;

        public VHProduct(View view)
        {
            super(view);
            title = (TextView) view.findViewById(R.id.tv_product_title);
            price = (TextView) view.findViewById(R.id.tv_product_price);
            image = (ImageView) view.findViewById(R.id.img_product_thumb);
        }
    }
}
