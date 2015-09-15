package com.tisser.puneet.tisserartisan.UI.Fragment;

import android.os.Bundle;
import android.widget.TextView;

import com.tisser.puneet.tisserartisan.R;
import com.tisser.puneet.tisserartisan.UI.Activity.BaseActivity_NavDrawer;

import butterknife.Bind;
import butterknife.OnClick;

public class AboutFragment extends BaseFragment
{
    /*** We set these below from the TisserSettings Model ***/
    @Bind(R.id.about_tv_phone) TextView abouttvphone;
    @Bind(R.id.about_tv_email) TextView abouttvemail;

    @OnClick(R.id.ll_phone)
    void phoneIntent()
    {
        ((BaseActivity_NavDrawer) getActivity()).navigator.callIntent(getActivity(), manager.settings.getContact());
    }


    @OnClick(R.id.ll_email)
    void emailIntent()
    {
        ((BaseActivity_NavDrawer) getActivity()).navigator.emailIntent(getActivity(), manager.settings.getEmail());

    }

    @OnClick(R.id.about_img_website)
    void websiteClick()
    {
        ((BaseActivity_NavDrawer) getActivity()).navigator.openUrl(getActivity(), "http://www.tisserindia.com/");

    }

    @OnClick(R.id.about_img_fb)
    void fbClick()
    {
        ((BaseActivity_NavDrawer) getActivity()).navigator.openUrl(getActivity(), "https://www.facebook.com/TisserIndia");
    }

    @OnClick(R.id.about_img_twitter)
    void twitterClick()
    {
        ((BaseActivity_NavDrawer) getActivity()).navigator.openUrl(getActivity(), "https://twitter.com/tisserindia");
    }

    @OnClick(R.id.about_img_instagram)
    void instagramClick()
    {
        ((BaseActivity_NavDrawer) getActivity()).navigator.openUrl(getActivity(), "https://instagram.com/tisserindia/");
    }

    @OnClick(R.id.about_img_pinterest)
    void pinteretClick()
    {
        ((BaseActivity_NavDrawer) getActivity()).navigator.openUrl(getActivity(), "https://in.pinterest.com/Tisserindia/");
    }

    @OnClick(R.id.about_img_linkedin)
    void linkedInClick()
    {
        ((BaseActivity_NavDrawer) getActivity()).navigator.openUrl(getActivity(), "https://www.linkedin.com/in/tisser");
    }

    @OnClick(R.id.about_img_blogspot)
    void blogspotClick()
    {
        ((BaseActivity_NavDrawer) getActivity()).navigator.openUrl(getActivity(), "http://tisserindia.blogspot.in/");
    }

    public static AboutFragment newInstance()
    {
        AboutFragment fragment = new AboutFragment();
        return fragment;
    }

    public AboutFragment()
    {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutResource()
    {
        return R.layout.fragment_about;
    }

    @Override
    protected String setupToolbarTitle()
    {
        return "About Tisser";
    }


    @Override
    protected void setupLayout()
    {
        abouttvphone.setText(manager.settings.getContact());
        abouttvemail.setText(manager.settings.getEmail());
    }


}
