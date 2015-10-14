package com.tisser.puneet.tisserartisan.Model;

import com.google.gson.annotations.SerializedName;

import java.util.Objects;

public class TisserColor implements Comparable
{
    @SerializedName("id") private String id;
    @SerializedName("color") private String name;
    @SerializedName("code") private String code;
    private boolean selected;


    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getColor()
    {
        return name;
    }

    public void setColor(String color)
    {
        this.name = color;
    }

    public String getCode()
    {
        return code;
    }

    public void setCode(String code)
    {
        this.code = code;
    }

    public boolean isSelected()
    {
        return selected;
    }

    public void setSelected(boolean selected)
    {
        this.selected = selected;
    }

    @Override
    public int compareTo(Object compareColor)
    {
        String compareColorCode = ((TisserColor) compareColor).getCode();
        /* For Ascending order*/
        int res = String.CASE_INSENSITIVE_ORDER.compare(compareColorCode, this.code);
        if (res == 0)
        {
            res = this.name.compareTo(compareColorCode);
        }
        return res;
    }
}
