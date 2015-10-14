package com.tisser.puneet.tisserartisan.UI.Adapters;


import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.tisser.puneet.tisserartisan.Model.TisserColor;
import com.tisser.puneet.tisserartisan.R;

import java.util.ArrayList;
import java.util.Locale;

public class ColorSelectionAdapter extends BaseAdapter
{

    Context mContext;
    LayoutInflater inflater;
    private ArrayList<TisserColor> mainDataList = null;
    private ArrayList<TisserColor> arraylist;

    public ColorSelectionAdapter(Context context, ArrayList<TisserColor> mainDataList)
    {
        mContext = context;
        this.mainDataList = mainDataList;
        inflater = LayoutInflater.from(mContext);
        this.arraylist = new ArrayList<>(mainDataList);
    }

    @Override
    public int getCount()
    {
        return mainDataList.size();
    }

    @Override
    public TisserColor getItem(int position)
    {
        return mainDataList.get(position);
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    public View getView(final int position, View view, ViewGroup parent)
    {
        final ViewHolder holder;
        if (view == null)
        {
            holder = new ViewHolder();
            view = inflater.inflate(R.layout.color_list_row_item, null);

            holder.colorName = (TextView) view.findViewById(R.id.color_name);
            holder.colorCode = (TextView) view.findViewById(R.id.color_code);
            holder.colorCheck = (CheckBox) view.findViewById(R.id.color_check_box);
            holder.colorImage = (ImageView) view.findViewById(R.id.color_image);


            view.setTag(holder);
            view.setTag(R.id.color_name, holder.colorName);
            view.setTag(R.id.color_check_box, holder.colorCheck);

            holder.colorCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
            {

                @Override
                public void onCheckedChanged(CompoundButton vw, boolean isChecked)
                {
                    int getPosition = (Integer) vw.getTag();
                    mainDataList.get(getPosition).setSelected(vw.isChecked());
                }
            });

        }
        else
        {
            holder = (ViewHolder) view.getTag();
        }

        holder.colorCheck.setTag(position);

        holder.colorName.setText(mainDataList.get(position).getColor());
        //holder.colorCode.setText(mainDataList.get(position).getCode());
        holder.colorCheck.setChecked(mainDataList.get(position).isSelected());
        holder.colorImage.setColorFilter(Color.parseColor(mainDataList.get(position).getCode()), PorterDuff.Mode.SRC_IN);

        return view;
    }

    public void filter(String charText)
    {
        charText = charText.toLowerCase(Locale.getDefault());
        mainDataList.clear();
        if (charText.length() == 0)
        {
            mainDataList.addAll(arraylist);
        }
        else
        {
            for (TisserColor wp : arraylist)
            {
                if (wp.getColor().toLowerCase(Locale.getDefault()).contains(charText))
                {
                    mainDataList.add(wp);
                }
            }
        }
        notifyDataSetChanged();
    }

    static class ViewHolder
    {
        protected CheckBox colorCheck;
        protected TextView colorName;
        protected TextView colorCode;
        protected ImageView colorImage;
    }

}