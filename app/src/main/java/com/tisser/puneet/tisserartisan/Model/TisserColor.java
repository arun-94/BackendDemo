package com.tisser.puneet.tisserartisan.Model;

/**
 * Created by Puneet on 16-09-2015.
 */
public class TisserColor
{
    private String id;
    private String name;
    private String code;
    private boolean selected;


    public String getId()
    {
        return id;
    }

    /**
     * @param id The id
     */
    public void setId(String id)
    {
        this.id = id;
    }

    /**
     * @return The color
     */
    public String getColor()
    {
        return name;
    }

    /**
     * @param color The color
     */
    public void setColor(String color)
    {
        this.name = color;
    }

    /**
     * @return The code
     */
    public String getCode()
    {
        return code;
    }

    /**
     * @param code The code
     */
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
}
