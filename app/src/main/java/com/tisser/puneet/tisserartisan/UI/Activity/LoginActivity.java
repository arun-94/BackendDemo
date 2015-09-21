package com.tisser.puneet.tisserartisan.UI.Activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.github.rahatarmanahmed.cpv.CircularProgressView;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Email;
import com.mobsandgeeks.saripaar.annotation.Password;
import com.tisser.puneet.tisserartisan.R;

import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity implements Validator.ValidationListener
{

    @Bind(R.id.loginButton)
    Button mLoginButton;
    @Email @Bind(R.id.editText_custid)
    EditText mCustIdEditText;
    @Password(min = 6, scheme = Password.Scheme.ALPHA_NUMERIC_MIXED_CASE) @Bind(R.id.editText_password)
    EditText mPasswordEditText;

    @OnClick(R.id.loginButton) void login() {
        loginValidator.validate();
    }

    private CircularProgressView mProgressBar;
    private Validator loginValidator;

    boolean allowLogin = true;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        final String PREFS_NAME = "MyPrefsFile";
        loginValidator = new Validator(this);
        loginValidator.setValidationListener(this);

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
    protected void setupLayout()
    {

    }

    void openNextActivity()
    {
        navigator.navigateToBaseActivity_NavDrawer(LoginActivity.this);
        /*Intent SearchIntent = new Intent(LoginActivity.this, BaseActivity_NavDrawer.class);
        SearchIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);z
        startActivity(SearchIntent);*/
    }

    @Override
    public void onValidationSucceeded()
    {
        navigator.navigateToBaseActivity_NavDrawer(LoginActivity.this);
    }

    @Override
    public void onValidationFailed(List<ValidationError> errors)
    {
        for (ValidationError error : errors) {
            View view = error.getView();
            String message = error.getCollatedErrorMessage(this);

            // Display error messages ;)
            if (view instanceof EditText) {
                ((EditText) view).setError(message);
            } else {
                Toast.makeText(this, message, Toast.LENGTH_LONG).show();
            }

            if (view instanceof TextView) {
                ((TextView) view).setError(message);
            } else {
                Toast.makeText(this, message, Toast.LENGTH_LONG).show();
            }
        }
    }
}
