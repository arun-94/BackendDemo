package com.tisser.puneet.tisserartisan.UI.Activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Email;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.tisser.puneet.tisserartisan.Global.AppConstants;
import com.tisser.puneet.tisserartisan.Model.Response.LoginResponse;
import com.tisser.puneet.tisserartisan.R;

import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

import static com.tisser.puneet.tisserartisan.HTTP.RestClient.getApiService;

public class LoginActivity extends BaseActivity implements Validator.ValidationListener
{


    //TODO Handle Retrofit Timeout Error

    @Bind(R.id.loginButton) Button mLoginButton;
    @Email @Bind(R.id.editText_custid) EditText mCustIdEditText;
    /*@Password(min = 6, scheme = Password.Scheme.ALPHA_NUMERIC_MIXED_CASE)*/
    @NotEmpty @Bind(R.id.editText_password) EditText mPasswordEditText;
    @Bind(R.id.progress_login) ProgressBar mProgressLogin;

    @OnClick(R.id.loginButton)
    void login()
    {
        mLoginButton.setText("");
        mProgressLogin.setVisibility(View.VISIBLE);
        loginValidator.validate();
    }

    //private CircularProgressView mProgressBar;
    private Validator loginValidator;
    private SharedPreferences settings;

    //boolean allowLogin = true;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        loginValidator = new Validator(this);
        loginValidator.setValidationListener(this);

        settings = getSharedPreferences(AppConstants.PREFS_NAME, 0);

        if (settings.getBoolean(AppConstants.PREFS_IS_LOGGED_IN, false))
        {
            //openNextActivity();
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
    protected void setupLayout()
    {

    }

    void openNextActivity()
    {
        navigator.navigateToBaseActivity_NavDrawer(LoginActivity.this);
        mProgressLogin.setVisibility(View.INVISIBLE);
        mLoginButton.setText("LOG IN");
    }

    @Override
    public void onValidationSucceeded()
    {
        settings.edit().putBoolean(AppConstants.PREFS_IS_LOGGED_IN, true).commit();
        loginPostCall(mCustIdEditText.getText().toString(), mPasswordEditText.getText().toString());
    }

    private void loginPostCall(String userId, String password)
    {
        getApiService().validateLogin(AppConstants.ACTION_VALIDATE_USER, userId, password, new Callback<LoginResponse>()
        {
            @Override
            public void success(LoginResponse loginData, Response response)
            {
                Log.d("Response", "Response string is  : " + loginData.getSessionID());
                if (loginData.getError() == 0)
                {
                    Log.d("LoginSuccess", "Success. Session Id is : " + loginData.getSessionID());
                    manager.setSessionID(loginData.getSessionID());
                    openNextActivity();
                }
                else
                {
                    Toast.makeText(LoginActivity.this, loginData.getSessionID(), Toast.LENGTH_SHORT).show();
                    resetButton();
                }
            }

            @Override
            public void failure(RetrofitError error)
            {
                resetButton();
                Log.e("Login", "error");
                Log.e("Data", "" + error);
            }
        });
    }

    @Override
    public void onValidationFailed(List<ValidationError> errors)
    {
        mProgressLogin.setVisibility(View.INVISIBLE);
        mLoginButton.setText("LOG IN");
        for (ValidationError error : errors)
        {
            View view = error.getView();
            String message = error.getCollatedErrorMessage(this);

            // Display error messages ;)
            if (view instanceof EditText)
            {
                ((EditText) view).setError(message);
            }
            else
            {
                Toast.makeText(this, message, Toast.LENGTH_LONG).show();
            }

            if (view instanceof TextView)
            {
                ((TextView) view).setError(message);
            }
            else
            {
                Toast.makeText(this, message, Toast.LENGTH_LONG).show();
            }
        }
    }

    void resetButton()
    {
        mProgressLogin.setVisibility(View.INVISIBLE);
        mLoginButton.setText("LOG IN");
    }
}
