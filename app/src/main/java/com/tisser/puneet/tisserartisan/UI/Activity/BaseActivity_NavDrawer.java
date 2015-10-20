package com.tisser.puneet.tisserartisan.UI.Activity;

import android.annotation.TargetApi;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.tisser.puneet.tisserartisan.Global.AppConstants;
import com.tisser.puneet.tisserartisan.Queries.AsyncResponse;
import com.tisser.puneet.tisserartisan.R;
import com.tisser.puneet.tisserartisan.UI.Fragment.AboutFragment;
import com.tisser.puneet.tisserartisan.UI.Fragment.ProductListFragment;

import butterknife.Bind;
import butterknife.ButterKnife;


public class BaseActivity_NavDrawer extends BaseActivity implements AsyncResponse
{
    @Bind(R.id.content_frame) FrameLayout frameLayout;
    @Bind(R.id.drawer_layout) DrawerLayout mDrawerLayout;

    public NavigationView getNavigationView()
    {
        return navigationView;
    }

    public void resetDrawer() {
        navigationView.getMenu().getItem(0).setChecked(true);
    }

    @Bind(R.id.navigation_view) NavigationView navigationView;

    private Boolean exit = false;
    private int currentCheckedItem;


    public static Intent getLaunchIntent(final Context context)
    {
        Intent intent;
        intent = new Intent(context, BaseActivity_NavDrawer.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            currentCheckedItem =
                    savedInstanceState.getInt("STATE_SELECTED_POSITION");
        }
        ButterKnife.bind(this);
        navigator.openNewProductFragment(BaseActivity_NavDrawer.this, frameLayout);
    }

    @Override
    protected int getLayoutResource()
    {
        return R.layout.activity_base_nav_drawer;
    }

    @Override
    protected void setupToolbar()
    {
        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void setupLayout()
    {
        //navigationView.getMenu().getItem(0).setChecked(true);
        resetDrawer();

        getLayoutInflater().inflate(getLayoutResource(), frameLayout); /*** SET ACTIVITY HERE***/

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener()
        {

            // This method will trigger on item Click of navigation menu
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem)
            {
                //Checking if the item is in checked state or not, if not make it in checked state
               /* if (menuItem.isChecked())
                {
                    menuItem.setChecked(false);
                }
                else
                {
                    menuItem.setChecked(true);
                }*/
                menuItem.setChecked(true);
                //Closing drawer on item click
                mDrawerLayout.closeDrawers();
                //Check to see which item was being clicked and perform appropriate action
                switch (menuItem.getItemId())
                {
                    //Replacing the main content with ContentFragment Which is our Inbox View;
                    case R.id.nav_home:
                        navigator.openNewFragment(BaseActivity_NavDrawer.this, ProductListFragment.newInstance());
                        currentCheckedItem = 0;
                        return true;
                    case R.id.nav_profile:
                        navigator.openNewActivity(BaseActivity_NavDrawer.this, new ProfileActivity());
                        currentCheckedItem = 1;
                        return true;
                    case R.id.nav_add_product:
                        navigator.openNewActivity(BaseActivity_NavDrawer.this, new AddProductActivity());
                        currentCheckedItem = 2;
                        return true;
                    case R.id.nav_about:
                        navigator.openNewFragment(BaseActivity_NavDrawer.this, AboutFragment.newInstance());
                        currentCheckedItem = 3;
                        return true;
                    case R.id.nav_logout:
                        SharedPreferences preferences = getSharedPreferences(AppConstants.PREFS_NAME, 0);
                        preferences.edit().remove(AppConstants.PREFS_IS_LOGGED_IN).commit();
                        navigator.openNewActivity(BaseActivity_NavDrawer.this, new LoginActivity());
                        currentCheckedItem = 4;
                        return true;
                    default:
                        currentCheckedItem = 5;
                        Toast.makeText(getApplicationContext(), "Somethings Wrong", Toast.LENGTH_SHORT).show();
                        return true;

                }
            }
        });

        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.openDrawer, R.string.closeDrawer)
        {

            @Override
            public void onDrawerClosed(View drawerView)
            {
                // Code here will be triggered once the drawer closes as we dont want anything to happen so we leave this blank
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView)
            {
                // Code here will be triggered once the drawer open as we dont want anything to happen so we leave this blank
                super.onDrawerOpened(drawerView);
            }
        };

        //Setting the actionbarToggle to drawer layout
        mDrawerLayout.setDrawerListener(actionBarDrawerToggle);

        //calling sync state is necessay or else your hamburger icon wont show up
        actionBarDrawerToggle.syncState();
    }

    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("STATE_SELECTED_POSITION", currentCheckedItem);
    }

    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        currentCheckedItem = savedInstanceState.getInt("STATE_SELECTED_POSITION", 0);
        Menu menu = navigationView.getMenu();
        menu.getItem(currentCheckedItem).setChecked(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == android.R.id.home)
        {
            mDrawerLayout.openDrawer(GravityCompat.START);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onBackPressed()
    {
        FragmentManager fm = getFragmentManager();
        if (fm.getBackStackEntryCount() > 0)
        {
            int currentBackStackCount = fm.getBackStackEntryCount();
            FragmentManager.BackStackEntry lastEntry = fm.getBackStackEntryAt(fm.getBackStackEntryCount() - 1);

            switch (lastEntry.getName())
            {
                case "com.tisser.puneet.tisserartisan.UI.Fragment.ProductListFragment":
                case "ProductListFragment":
                    if (exit)
                    {
                        this.finishAffinity();
                    }
                    else
                    {
                        while (currentBackStackCount > 1)
                        {
                            fm.popBackStack();
                            currentBackStackCount--;
                        }

                        Toast.makeText(this, "Press Back again to Exit.", Toast.LENGTH_SHORT).show();
                        exit = true;
                        new Handler().postDelayed(new Runnable()
                        {
                            @Override
                            public void run()
                            {
                                exit = false;
                            }
                        }, 3 * 1000);
                    }

                    break;
                default:
                    while (currentBackStackCount > 0)
                    {
                        fm.popBackStack();
                        currentBackStackCount--;
                    }

                    navigator.openNewProductFragment(BaseActivity_NavDrawer.this, frameLayout);
            }
        }
        else
        {
            super.onBackPressed();
        }
    }
}
