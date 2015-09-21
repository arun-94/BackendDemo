package com.tisser.puneet.tisserartisan.UI.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.tisser.puneet.tisserartisan.LoadImageFromURL.ImageLoader;
import com.tisser.puneet.tisserartisan.Model.Review;
import com.tisser.puneet.tisserartisan.R;

import java.util.ArrayList;
import java.util.List;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.VHCategory>
{

    private final Context mContext;
    private List<Review> mData;

    public ReviewAdapter(Context context, ArrayList<Review> data)
    {
        mContext = context;
        if (data != null)
        {
            mData = new ArrayList<Review>(data);
        }
        else
        {
            mData = new ArrayList<Review>();
        }
    }

    public void add(Review s, int position)
    {
        position = position == -1 ? getItemCount() : position;
        mData.add(position, s);
        notifyItemInserted(position);
    }

    public void addAll(ArrayList<Review> s)
    {
        Log.d("DETAILS", "1 Size is " + mData.size());
        mData.addAll(s);
        Log.d("DETAILS", "2 Size is " + mData.size());

        notifyDataSetChanged();
    }

    public void remove(int position)
    {
        if (position < getItemCount())
        {
            mData.remove(position);
            notifyItemRemoved(position);
        }
    }

    public VHCategory onCreateViewHolder(ViewGroup parent, int viewType)
    {
        final View view = LayoutInflater.from(mContext).inflate(R.layout.product_review_item, parent, false);
        return new VHCategory(view);
    }

    @Override
    public void onBindViewHolder(VHCategory holder, final int position)
    {
        holder.reviewtext.setText(mData.get(position).getReviewText());
        holder.reviewtime.setText(mData.get(position).getReviewDate());
        holder.username.setText(mData.get(position).getReviewUser());
        ImageLoader imgLoader = new ImageLoader(mContext);
        imgLoader.DisplayImage("http://tisserindia.com/stores/thumb_gen.php?file=" + mData.get(position).getReviewUserImgPath() + "&maxw=70&maxh=70", R.mipmap.ic_launcher, holder.userImg);
    }

    @Override
    public int getItemCount()
    {
        return mData.size();
    }


    public static class VHCategory extends RecyclerView.ViewHolder
    {
        public final TextView username, reviewtime, reviewtext;
        public final ImageView userImg;

        public VHCategory(View view)
        {
            super(view);
            username = (TextView) view.findViewById(R.id.review_user_name);
            userImg = (ImageView) view.findViewById(R.id.review_user_img);
            reviewtime = (TextView) view.findViewById(R.id.review_time);
            reviewtext = (TextView) view.findViewById(R.id.review_text);
        }
    }
}