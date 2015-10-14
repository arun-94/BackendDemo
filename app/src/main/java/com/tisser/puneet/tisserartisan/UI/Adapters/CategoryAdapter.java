package com.tisser.puneet.tisserartisan.UI.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tisser.puneet.tisserartisan.Model.Category;
import com.tisser.puneet.tisserartisan.Model.Subsubcategory;
import com.tisser.puneet.tisserartisan.R;

import java.util.ArrayList;
import java.util.List;


public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.VHCategory>
{

    private final Context mContext;
    private List<Subsubcategory> mData;

    public CategoryAdapter(Context context, ArrayList<Subsubcategory> data)
    {
        mContext = context;
        if (data != null)
        {
            mData = new ArrayList<Subsubcategory>(data);
        }
        else
        {
            mData = new ArrayList<Subsubcategory>();
        }
    }

    public void add(Subsubcategory s, int position)
    {
        position = position == -1 ? getItemCount() : position;
        mData.add(position, s);
        notifyItemInserted(position);
    }

    public void addAll(ArrayList<Subsubcategory> s)
    {
        mData.addAll(s);
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
        final View view = LayoutInflater.from(mContext).inflate(R.layout.category_item, parent, false);
        return new VHCategory(view);
    }

    @Override
    public void onBindViewHolder(VHCategory holder, final int position)
    {
        holder.title.setText(mData.get(position).getCategoryName());
        /*holder.title.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Toast.makeText(mContext, "Position =" + position, Toast.LENGTH_SHORT).show();
            }
        });*/
    }

    @Override
    public int getItemCount()
    {
        return mData.size();
    }


    public static class VHCategory extends RecyclerView.ViewHolder
    {
        public final TextView title;

        public VHCategory(View view)
        {
            super(view);
            title = (TextView) view.findViewById(R.id.category_name);
        }
    }
}