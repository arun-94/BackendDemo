package com.tisser.puneet.tisserartisan.UI.Adapters;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.darsh.multipleimageselect.models.Image;
import com.tisser.puneet.tisserartisan.R;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class GalleryImagesAdapter extends RecyclerView.Adapter<GalleryImagesAdapter.GalleryImage>
{

    private final Context mContext;
    private List<Image> mGalleryImages;

    public GalleryImagesAdapter(Context context, ArrayList<Image> data)
    {
        mContext = context;
        if (data != null)
        {
            mGalleryImages = new ArrayList<>(data);
        }
        else
        {
            mGalleryImages = new ArrayList<>();
        }
    }

    public void add(Image s, int position)
    {
        position = position == -1 ? getItemCount() : position;
        mGalleryImages.add(position, s);
        notifyItemInserted(position);
    }

    public void addAll(ArrayList<Image> s)
    {
        mGalleryImages.addAll(s);
        notifyDataSetChanged();
    }

    public void remove(int position)
    {
        if (position < getItemCount())
        {
            mGalleryImages.remove(position);
            notifyItemRemoved(position);
        }
    }

    public GalleryImage onCreateViewHolder(ViewGroup parent, int viewType)
    {
        final View view = LayoutInflater.from(mContext).inflate(R.layout.products_grid_item, parent, false);
        return new GalleryImage(view);
    }

    @Override
    public void onBindViewHolder(GalleryImage holder, int position)
    {
        Image i = mGalleryImages.get(position);
        Uri uri = Uri.fromFile(new File(i.path));
        Glide.with(mContext)
                .load(uri)
                .asBitmap()
                .centerCrop()
                .into(holder.image);

        /*File imgFile = new File(i.path);

        if(imgFile.exists()){

            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            holder.image.setImageBitmap(myBitmap);

        }*/
    }

    @Override
    public int getItemCount()
    {
        return mGalleryImages.size();
    }


    public static class GalleryImage extends RecyclerView.ViewHolder
    {
        public final ImageView image;

        public GalleryImage(View view)
        {
            super(view);
            image = (ImageView) view.findViewById(R.id.img_product_thumb);
        }
    }
}
