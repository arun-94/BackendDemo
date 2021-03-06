package com.tisser.puneet.tisserartisan.UI.Activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.tisser.puneet.tisserartisan.Injection.Component.ApplicationComponent;
import com.tisser.puneet.tisserartisan.Global.AppManager;
import com.tisser.puneet.tisserartisan.Model.Response.LoginResponse;
import com.tisser.puneet.tisserartisan.Queries.AsyncResponse;
import com.tisser.puneet.tisserartisan.R;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;


public abstract class BaseActivity extends AppCompatActivity implements AsyncResponse
{
    @Inject public Navigator navigator;
    @Nullable @Bind(R.id.toolbar) Toolbar toolbar;
    AppManager manager;

    AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        this.getApplicationComponent().inject(this);
        manager = (AppManager) getApplication();
        setContentView(getLayoutResource());
        ButterKnife.bind(this);

        if (toolbar != null)
        {
            setSupportActionBar(toolbar);
            setupToolbar();
        }
        setupLayout();
    }

    protected ApplicationComponent getApplicationComponent()
    {
        return ((AppManager) getApplication()).getApplicationComponent();
    }


    protected abstract int getLayoutResource();

    protected abstract void setupToolbar();

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

        if (id == android.R.id.home)
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
            dialog = new AlertDialog.Builder(this).setTitle("No Internet Connection").setMessage("Try again").setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener()
            {
                public void onClick(DialogInterface dialog, int which)
                {
                    // continue with delete
                }
            }).show();
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
