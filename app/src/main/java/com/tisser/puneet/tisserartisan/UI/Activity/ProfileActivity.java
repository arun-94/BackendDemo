package com.tisser.puneet.tisserartisan.UI.Activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.tisser.puneet.tisserartisan.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ProfileActivity extends BaseActivity
{

    @Bind(R.id.appbar) AppBarLayout mAppBarLayout;
    @Bind(R.id.artisan_profile_image) ImageView mProfileImage;
    @Bind(R.id.profile_address_layout) ViewGroup AddressLayout;
    @Bind(R.id.profile_email_layout) LinearLayout EmailLayout;
    @Bind(R.id.profile_phone_layout) LinearLayout PhoneLayout;
    @Bind(R.id.tv_artisan_name) TextView mArtisanName;
    @Bind(R.id.tv_artisan_location) TextView mArtisanLocation;
    @Bind(R.id.tv_artisan_product_count) TextView mArtisanProductCount;

    ImageView phoneIcon, addressIcon, emailIcon;
    TextView phoneText, addressText, emailText;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutResource()
    {
        return R.layout.activity_profile;
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
        phoneText = ButterKnife.findById(PhoneLayout, R.id.profile_item_text);
        addressText = ButterKnife.findById(AddressLayout, R.id.profile_item_text);
        emailText = ButterKnife.findById(EmailLayout, R.id.profile_item_text);
        phoneText.setText("+91 9819954448");
        addressText.setText("XYZ Building, ABC Apartments, Shivaji Nagar, Pune 411-020, Maharashtra, India ");
        emailText.setText("punkohl@gmail.com");

        phoneIcon = ButterKnife.findById(PhoneLayout, R.id.profile_item_image);
        emailIcon = ButterKnife.findById(EmailLayout, R.id.profile_item_image);
        addressIcon = ButterKnife.findById(AddressLayout, R.id.profile_item_image);
        phoneIcon.setImageDrawable(getResources().getDrawable(R.drawable.ic_action_call));
        emailIcon.setImageDrawable(getResources().getDrawable(R.drawable.ic_action_email));
        addressIcon.setImageDrawable(getResources().getDrawable(R.drawable.ic_map_grey_24dp));
    }

    String field = "";
    TextView textView = null;
    @OnClick({ R.id.profile_address_layout, R.id.profile_email_layout, R.id.profile_phone_layout })
    public void showDetailFillAlert(LinearLayout view)
    {

        if(view == PhoneLayout)
        {
            field = "Phone number";
            textView = phoneText;
        }
        else if (view == AddressLayout)
        {
            field = "Address";
            textView = addressText;
        }
        else if (view == EmailLayout)
        {
            field = "Email";
            textView = emailText;
        }

        //AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);

        // Setting Dialog Title
        LayoutInflater inflater = LayoutInflater.from(ProfileActivity.this);
        AlertDialog.Builder builder = new AlertDialog.Builder(ProfileActivity.this);
        builder.setTitle("Edit " + field);
        View customDialogView = inflater.inflate(R.layout.profile_popup_edit_details, null, false);
        final EditText popupEdittext = (EditText) customDialogView.findViewById(R.id.popup_editText);
        popupEdittext.setText(textView.getText());
        builder.setPositiveButton("Save", new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int whichButton)
            {
                if (popupEdittext.getText().toString().trim().length() == 0)
                {
                    Toast.makeText(ProfileActivity.this, "You can not leave this field Blank!", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    textView.setText(popupEdittext.getText().toString());
                }
            }
        });
        builder.setView(customDialogView);
        builder.create();
        builder.show();
    }
}