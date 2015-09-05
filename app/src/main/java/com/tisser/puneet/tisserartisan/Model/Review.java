package com.tisser.puneet.tisserartisan.Model;

/**
 * Created by Puneet on 22-07-2015.
 */
public class Review
{
    private String reviewID;
    private String reviewDate;
    private String reviewText;
    private String reviewUser;

    public Review(String reviewID, String reviewDate, String reviewText, String reviewUser, String reviewUserImgPath)
    {
        this.reviewID = reviewID;
        this.reviewDate = reviewDate;
        this.reviewText = reviewText;
        this.reviewUser = reviewUser;
        this.reviewUserImgPath = reviewUserImgPath;
    }

    public String getReviewUserImgPath()
    {
        return reviewUserImgPath;
    }

    public void setReviewUserImgPath(String reviewUserImgPath)
    {
        this.reviewUserImgPath = reviewUserImgPath;
    }

    public String getReviewID()
    {
        return reviewID;
    }

    public void setReviewID(String reviewID)
    {
        this.reviewID = reviewID;
    }

    public String getReviewDate()
    {
        return reviewDate;
    }

    public void setReviewDate(String reviewDate)
    {
        this.reviewDate = reviewDate;
    }

    public String getReviewText()
    {
        return reviewText;
    }

    public void setReviewText(String reviewText)
    {
        this.reviewText = reviewText;
    }

    public String getReviewUser()
    {
        return reviewUser;
    }

    public void setReviewUser(String reviewUser)
    {
        this.reviewUser = reviewUser;
    }

    private String reviewUserImgPath;
}
