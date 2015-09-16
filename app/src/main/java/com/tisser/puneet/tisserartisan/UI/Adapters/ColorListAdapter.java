package com.tisser.puneet.tisserartisan.UI.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.tisser.puneet.tisserartisan.Model.TisserColor;
import com.tisser.puneet.tisserartisan.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.Bind;

public class ColorListAdapter extends RecyclerView.Adapter<ColorListAdapter.VHColorItem>
{

    private final Context mContext;
    private List<TisserColor> mData;

    private SparseBooleanArray selectedItems;

    public ColorListAdapter(Context context, ArrayList<TisserColor> data)
    {
        mContext = context;
        if (data != null)
        {
            mData = new ArrayList<TisserColor>(data);
        }
        else
        {
            mData = new ArrayList<TisserColor>();
        }
        TisserColor c = new TisserColor();
        c.setColor("Yo");
        c.setCode("#ffffff");
        mData.add(c);
        c = new TisserColor();
        c.setColor("Yo2");
        c.setCode("#ff00ff");
        mData.add(c);
        c = new TisserColor();
        c.setColor("Yo3");
        c.setCode("#0000ff");
        mData.add(c);
        c = new TisserColor();
        c.setColor("Yo4");
        c.setCode("#ff0000");
        mData.add(c);
        selectedItems = new SparseBooleanArray(mData.size());
    }

    public void addItems(ArrayList<TisserColor> newStores)
    {
        mData.clear();
        mData.addAll(newStores);
        this.notifyDataSetChanged();
    }

    public void add(TisserColor s, int position)
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


    public VHColorItem onCreateViewHolder(ViewGroup parent, int viewType)
    {
        final View view = LayoutInflater.from(mContext).inflate(R.layout.color_list_item, parent, false);
        return new VHColorItem(view);
    }

    @Override
    public void onBindViewHolder(final VHColorItem holder, final int position)
    {
        TisserColor store = mData.get(position);

        if (store != null)
        {
            holder.storeName.setText("" + store.getColor());
            holder.storeCategoryImage.getBackground().setColorFilter(Color.parseColor(store.getCode()), PorterDuff.Mode.SRC_ATOP);
            //holder.storeCategoryImage.getDrawable().setColorFilter(mContext.getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);
        }
    }

    @Override
    public int getItemCount()
    {
        return mData.size();
    }

    public boolean toggleSelection(int pos)
    {
        if (selectedItems.get(pos, false))
        {
            selectedItems.delete(pos);
        }
        else
        {
            Log.d("POS", "selected position " + pos);
            selectedItems.put(pos, true);
        }
        notifyItemChanged(pos);
        return selectedItems.valueAt(pos);
    }

    public void clearSelections()
    {
        selectedItems.clear();
        notifyDataSetChanged();
    }

    public int getSelectedItemCount()
    {
        return selectedItems.size();
    }

    public SparseBooleanArray getSelectedItemValues()
    {
        return selectedItems;
    }

    public List<Integer> getSelectedItems()
    {
        List<Integer> items = new ArrayList<Integer>(selectedItems.size());
        for (int i = 0; i < selectedItems.size(); i++)
        {
            items.add(selectedItems.keyAt(i));
        }
        return items;
    }

    public List<String> getSelectedItemNames()
    {
        List<Integer> items = getSelectedItems();
        List<String> names = new ArrayList<>(items.size());
        for (int i = 0; i < items.size(); i++)
        {
            Log.d("NAME", mData.get(items.get(i)).getColor());
            names.add(mData.get(items.get(i)).getColor());
        }
        return names;
    }


    public class VHColorItem extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        public final TextView storeName;
        public final ImageView storeCategoryImage;
        //@Bind(R.id.color_list_layout) View parentLayout;
        public final View parentLayout;

        public VHColorItem(View view)
        {
            super(view);
            storeName = (TextView) view.findViewById(R.id.color_name);
            storeCategoryImage = (ImageView) view.findViewById(R.id.color_image);
            parentLayout = view;
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view)
        {
            if (toggleSelection(getAdapterPosition()))
            {
                storeCategoryImage.setImageDrawable(mContext.getResources().getDrawable(R.drawable.transparent));
            }
            else
            {
                storeCategoryImage.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_check_circle_black_24dp));
            }
        }
    }
}
