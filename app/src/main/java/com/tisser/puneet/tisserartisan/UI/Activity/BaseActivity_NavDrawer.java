package com.tisser.puneet.tisserartisan.UI.Activity;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

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
    @Bind(R.id.navigation_view) NavigationView navigationView;


    public static Intent getLaunchIntent(final Context context) {
        Intent intent = new Intent(context, BaseActivity_NavDrawer.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        getFragmentManager().beginTransaction().replace(frameLayout.getId(), ProductListFragment.newInstance(), "ProductListFragment").setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).commit();
    }

    @Override
    protected int getLayoutResource()
    {
        return R.layout.activity_base_nav_drawer;
    }

    @Override
    protected void setupToolbar()
    {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void setupLayout()
    {
        navigationView.getMenu().getItem(0).setChecked(true);

        getLayoutInflater().inflate(getLayoutResource(), frameLayout); /*** SET ACTIVITY HERE***/

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener()
        {

            // This method will trigger on item Click of navigation menu
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem)
            {
                //Checking if the item is in checked state or not, if not make it in checked state
                if (menuItem.isChecked())
                {
                    menuItem.setChecked(false);
                }
                else
                {
                    menuItem.setChecked(true);
                }
                //Closing drawer on item click
                mDrawerLayout.closeDrawers();
                Intent navIntent;
                //Check to see which item was being clicked and perform appropriate action
                switch (menuItem.getItemId())
                {
                    //Replacing the main content with ContentFragment Which is our Inbox View;
                    case R.id.nav_home:
                        openNewFragment(ProductListFragment.newInstance());
                        return true;
                    case R.id.nav_profile:
                        openNewActivity(new ProfileActivity());
                        return true;
                    case R.id.nav_add_product:
                        openNewFragment(ProductListFragment.newInstance());
                        return true;
                    case R.id.nav_about:
                        openNewFragment(AboutFragment.newInstance());
                        return true;
                    default:
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

    private void openNewFragment(Fragment fragment)
    {
        String tag = fragment.getClass().getCanonicalName();
        getFragmentManager().beginTransaction().replace(R.id.content_frame, fragment, tag).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).commit();
    }

    private void openNewActivity(Activity activity)
    {
        Intent i = new Intent(BaseActivity_NavDrawer.this, activity.getClass());
        startActivity(i);
    }

}
