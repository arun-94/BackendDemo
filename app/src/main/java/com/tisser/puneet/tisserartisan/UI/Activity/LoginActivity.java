package com.tisser.puneet.tisserartisan.UI.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.github.rahatarmanahmed.cpv.CircularProgressView;
import com.tisser.puneet.tisserartisan.R;

public class LoginActivity extends BaseActivity
{

    private Button mLoginButton;
    private EditText mUserNameEditText, mPasswordEditText;
    private CircularProgressView mProgressBar;
    boolean allowLogin = true;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        final String PREFS_NAME = "MyPrefsFile";

        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);

        if (settings.getBoolean("my_first_time", true))
        {
            openNextActivity();
            settings.edit().putBoolean("my_first_time", false).commit();
        }
    }

    @Override
    protected int getLayoutResource()
    {
        return R.layout.activity_login;

    }

    @Override
    protected void setupToolbar()
    {
    }

    @Override
    protected void initializeLayout()
    {
        mLoginButton = (Button) findViewById(R.id.loginButton);
        mUserNameEditText = (EditText) findViewById(R.id.editText_custid);
        mPasswordEditText = (EditText) findViewById(R.id.editText_password);
        mProgressBar = (CircularProgressView) findViewById(R.id.progress_view);
    }

    @Override
    protected void setupLayout()
    {
        mLoginButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                openNextActivity();
            }
        });
    }

    void openNextActivity()
    {
        Intent SearchIntent = new Intent(LoginActivity.this, BaseActivity_NavDrawer.class);
        SearchIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(SearchIntent);
    }
}
