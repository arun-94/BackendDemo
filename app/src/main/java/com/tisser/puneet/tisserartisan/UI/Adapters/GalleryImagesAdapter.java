package com.tisser.puneet.tisserartisan.UI.Adapters;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.darsh.multipleimageselect.models.Image;
import com.squareup.picasso.Picasso;
import com.tisser.puneet.tisserartisan.R;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class GalleryImagesAdapter extends BaseAdapter
{

    private final Context mContext;
    private List<Image> mGalleryImages;
    private int reqWidth = 0;

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

    public void add(Image s, int position, int width)
    {
        reqWidth = width;
        position = position == -1 ? getCount() : position;
        mGalleryImages.add(position, s);
        notifyDataSetChanged();
    }

    public void addAll(ArrayList<Image> s, int reqWidth)
    {
        this.reqWidth = reqWidth;
        for (int i = 0; i < mGalleryImages.size(); i++)
        {
            if (mGalleryImages.get(i) == null) mGalleryImages.remove(i);
        }

        mGalleryImages.addAll(s);
        //mGalleryImages.add(null);
        notifyDataSetChanged();
    }


    public void remove(int position)
    {
        if (position < getCount())
        {
            mGalleryImages.remove(position);
            notifyDataSetChanged();
        }
    }

   /* public GalleryImage onCreateViewHolder(ViewGroup parent, int viewType)
    {
        final View view = LayoutInflater.from(mContext).inflate(R.layout.image_grid_item, parent, false);
        return new GalleryImage(view);
    }

    public void onBindViewHolder(GalleryImage holder, int position)
    {
        if(mGalleryImages.get(position) == null) {
            Glide.with(mContext)
                    .load(R.drawable.logo)
                    .asBitmap().fitCenter()
                    .into(holder.image);
        }
        else
        {
            Image i = mGalleryImages.get(position);
            Uri uri = Uri.fromFile(new File(i.path));
            Glide.with(mContext).load(uri).asBitmap().fitCenter().into(holder.image);
        }
    }*/

    @Override
    public int getCount()
    {
        return mGalleryImages.size();
    }

    @Override
    public Object getItem(int position)
    {
        return mGalleryImages.get(position);
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        GalleryImage viewHolder;

        if (convertView == null)
        {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            if (mGalleryImages.get(0) != null)
            {
                convertView = inflater.inflate(R.layout.image_grid_item, parent, false);
                viewHolder = new GalleryImage();
                viewHolder.image = (ImageView) convertView.findViewById(R.id.img_product_thumb);
                viewHolder.image.getLayoutParams().height = reqWidth / 4;
                viewHolder.image.getLayoutParams().width = reqWidth / 4;
            }
            else
            {
                convertView = inflater.inflate(R.layout.grid_empty_text, parent, false);
                viewHolder = new GalleryImage();
                viewHolder.text = (TextView) convertView.findViewById(R.id.empty_grid_text);
                viewHolder.text.getLayoutParams().height = reqWidth / 4;
            }
            convertView.setTag(viewHolder);
        }
        else
        {
            viewHolder = (GalleryImage) convertView.getTag();
        }


        if (mGalleryImages.get(0) != null)
        {
            Image i = mGalleryImages.get(position);
            Uri uri = Uri.fromFile(new File(i.path));
            Picasso.with(mContext).load(uri).fit().into(viewHolder.image);
        }

        return convertView;
    }


    public static class GalleryImage
    {
        public ImageView image;
        public TextView text;
    }
}
