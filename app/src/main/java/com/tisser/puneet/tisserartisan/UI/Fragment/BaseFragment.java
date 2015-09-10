package com.tisser.puneet.tisserartisan.UI.Fragment;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tisser.puneet.tisserartisan.Global.AppManager;
import com.tisser.puneet.tisserartisan.UI.Activity.BaseActivity_NavDrawer;

/**
 * A simple {@link Fragment} subclass.
 * The BaseFragment class has been modeled to be used in such a way that the
 * implementing fragment can be implemented in the same way as an activity.
 * But with minor changes
 * NOTE: SetupToolbarTitle just sets up the title, as we assume that rest is already setup
 */

public abstract class BaseFragment extends Fragment
{
    AppManager manager;

    public BaseFragment()
    {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        manager = (AppManager) getActivity().getApplication();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        return inflater.inflate(getLayoutResource(), container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState)
    {
        if(((BaseActivity_NavDrawer) getActivity()).getSupportActionBar() != null)
            ((BaseActivity_NavDrawer) getActivity()).getSupportActionBar().setTitle(setupToolbarTitle());
        initializeLayout(view);
        setupLayout();
    }

    /*** Get the title of the toolbar ***/
    protected abstract String setupToolbarTitle();

    /*** Initialize the layout passing the view to it ***/
    protected abstract void initializeLayout(View view);

    /*** Replace the frame layout with the given resource ID ***/
    protected abstract int getLayoutResource();

    /*** Layout related code ***/
    protected abstract void setupLayout();


}
