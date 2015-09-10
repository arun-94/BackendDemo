package com.tisser.puneet.tisserartisan.UI.Activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.tisser.puneet.tisserartisan.Global.AppManager;
import com.tisser.puneet.tisserartisan.Module.ActivityModule;
import com.tisser.puneet.tisserartisan.Queries.AsyncResponse;
import com.tisser.puneet.tisserartisan.Queries.CategoryListQuery;
import com.tisser.puneet.tisserartisan.Queries.ProductListQuery;
import com.tisser.puneet.tisserartisan.Queries.SearchQuery;
import com.tisser.puneet.tisserartisan.Queries.SettingsQuery;
import com.tisser.puneet.tisserartisan.R;

import butterknife.Bind;
import butterknife.ButterKnife;


public abstract class BaseActivity extends AppCompatActivity implements AsyncResponse
{
    @Nullable @Bind(R.id.toolbar)
    Toolbar toolbar;
    AppManager manager;
    CategoryListQuery categoryListQuery;
    SettingsQuery settingsQuery;
    SearchQuery searchQuery;
    ProductListQuery productListQuery;

    AlertDialog dialog;
    Menu menu;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        manager = (AppManager) getApplication();
        categoryListQuery = new CategoryListQuery();
        categoryListQuery.delegate = this;
        settingsQuery = new SettingsQuery();
        settingsQuery.delegate = this;
        searchQuery = new SearchQuery();
        searchQuery.delegate = this;
        productListQuery = new ProductListQuery();
        productListQuery.delegate = this;
        setContentView(getLayoutResource());
        ButterKnife.bind(this);

        //toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null)
        {
            setSupportActionBar(toolbar);
            setupToolbar();
        }
        initializeLayout();
        setupLayout();
    }
/*
    protected ApplicationComponent getApplicationComponent() {
        return ((AppManager) getApplication()).getApplicationComponent();
    }*/

    protected ActivityModule getActivityModule() {
        return new ActivityModule(this);
    }

    protected abstract int getLayoutResource();

    protected abstract void setupToolbar();

    protected abstract void initializeLayout();

    protected abstract void setupLayout();

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings)
        {
            Intent i = new Intent(BaseActivity.this, AboutActivity.class);
            startActivity(i);
            return true;
        }
        else if (id == android.R.id.home)
        {
            onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void processFinish(String result, int type)
    {
    }

    void showNoInternetAlert()
    {
        if (!isFinishing())
        {
            dialog = new AlertDialog.Builder(this)
                    .setTitle("No Internet Connection")
                    .setMessage("Try again")
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener()
                    {
                        public void onClick(DialogInterface dialog, int which)
                        {
                            // continue with delete
                        }
                    })
                    .show();
        }
    }

    void countdownInternetAlert(long time)
    {
        if (dialog.isShowing())
        {
            dialog.setMessage("Retrying in " + time + " seconds..\nTap 'Ok' to manually retry.");
        }
    }

    void closeInternetAlert()
    {
        if (dialog.isShowing())
        {
            dialog.cancel();
        }
    }
}
