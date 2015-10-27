package com.tisser.puneet.tisserartisan.UI.Activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.PersistableBundle;
import android.provider.MediaStore;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cocosw.bottomsheet.BottomSheet;
import com.tisser.puneet.tisserartisan.Global.AppConstants;
import com.tisser.puneet.tisserartisan.Model.Response.LoginResponse;
import com.tisser.puneet.tisserartisan.R;
import com.tisser.puneet.tisserartisan.UI.Custom.ImageUtility;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.mime.TypedFile;

import static com.tisser.puneet.tisserartisan.HTTP.RestClient.getApiService;

public class ProfileActivity extends BaseActivity
{

    @Bind(R.id.appbar) AppBarLayout mAppBarLayout;
    @Bind(R.id.profile_address_layout) ViewGroup AddressLayout;
    @Bind(R.id.profile_email_layout) LinearLayout EmailLayout;
    @Bind(R.id.profile_phone_layout) LinearLayout PhoneLayout;
    @Bind(R.id.tv_artisan_name) TextView mArtisanName;
    @Bind(R.id.tv_artisan_location) TextView mArtisanLocation;
    @Bind(R.id.tv_artisan_product_count) TextView mArtisanProductCount;
    @Bind(R.id.artisan_profile_image) CircleImageView profileImage;
    private boolean isEdited = false;
    private Bitmap bm;
    private File f = null;


    @OnClick(R.id.artisan_profile_image)
    void changeImage()
    {
        new BottomSheet.Builder(this).title("Change Profile Picture").sheet(R.menu.menu_upload_profileimage).listener(new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                switch (which)
                {
                    case R.id.uploadGallery:
                        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        intent.setType("image/*");
                        startActivityForResult(Intent.createChooser(intent, "Select File"), AppConstants.REQUEST_GALLERY);
                        break;
                    case R.id.uploadCamera:
                        intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(intent, AppConstants.REQUEST_CAMERA);
                        break;
                }
            }
        }).show();
    }


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

    @OnClick({R.id.profile_address_layout, R.id.profile_email_layout, R.id.profile_phone_layout})
    public void showDetailFillAlert(LinearLayout view)
    {

        if (view == PhoneLayout)
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
        final String initialText = textView.getText().toString().trim();
        popupEdittext.setText(initialText);

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
                    if (!initialText.equals(popupEdittext.getText().toString().trim()))
                    {
                        isEdited = true;
                    }
                    textView.setText(popupEdittext.getText().toString());
                }
            }
        });
        builder.setView(customDialogView);
        builder.create();
        builder.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK)
        {
            isEdited = true;
            if (requestCode == AppConstants.REQUEST_CAMERA)
            {
                bm = (Bitmap) data.getExtras().get("data");
                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                assert bm != null;
                bm.compress(Bitmap.CompressFormat.JPEG, 70, bytes);

                File destination = new File(Environment.getExternalStorageDirectory(), System.currentTimeMillis() + ".jpg");

                FileOutputStream fo;
                try
                {
                    destination.createNewFile();
                    fo = new FileOutputStream(destination);
                    fo.write(bytes.toByteArray());
                    fo.close();
                } catch (IOException e)
                {
                    e.printStackTrace();
                }

                profileImage.setImageBitmap(bm);

            }
            else if (requestCode == AppConstants.REQUEST_GALLERY)
            {
                Uri selectedImageUri = data.getData();
                String[] projection = {MediaStore.MediaColumns.DATA};
                Cursor cursor = managedQuery(selectedImageUri, projection, null, null, null);
                int column_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
                cursor.moveToFirst();

                String selectedImagePath = cursor.getString(column_index);


                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inJustDecodeBounds = true;
                BitmapFactory.decodeFile(selectedImagePath, options);
                final int REQUIRED_SIZE = 200;
                int scale = 1;
                while (options.outWidth / scale / 2 >= REQUIRED_SIZE && options.outHeight / scale / 2 >= REQUIRED_SIZE)
                    scale *= 2;
                options.inSampleSize = scale;
                options.inJustDecodeBounds = false;
                bm = BitmapFactory.decodeFile(selectedImagePath, options);

                profileImage.setImageBitmap(bm);
            }
            //create a file to write bitmap data
            f = ImageUtility.makeFileFromBitmap(ProfileActivity.this, bm, "profile_pic");


        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_profile, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_profile_picture)
        {
            changeImage();
            return true;
        }
        else if (id == android.R.id.home)
        {
            onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed()
    {
        if (isEdited)
        {
            String userAddress = addressText.getText().toString().trim();
            String userEmail = emailText.getText().toString().trim();
            String userPhone = phoneText.getText().toString().trim();
            Map<String, TypedFile> fileMap = new HashMap<>();

            if(f != null)
                fileMap.put("profileimage", new TypedFile("image/jpeg", f));

            getApiService().editProfile(AppConstants.ACTION_EDIT_PROFILE, "999", userAddress, userPhone, userEmail, fileMap, new Callback<LoginResponse>()
            {

                @Override
                public void success(LoginResponse loginResponse, Response response)
                {
                    //Log.d("Response", "Response string is  : " + loginResponse.getSessionID());
                    isEdited = false;
                    onBackPressed();
                    Toast.makeText(ProfileActivity.this, "Profile edited successfully.", Toast.LENGTH_SHORT).show();
                    /*if (loginResponse.getError() == 0)
                    {
                        Log.d("LoginSuccess", "Success. edit product successful");
                        isEdited = false;
                        onBackPressed();
                    }
                    else
                    {
                        Toast.makeText(ProfileActivity.this, "Failed. Unknown error", Toast.LENGTH_SHORT).show();
                    }*/
                }

                @Override
                public void failure(RetrofitError error)
                {
                    if (error.getKind().equals(RetrofitError.Kind.NETWORK))
                    {
                        showNoInternetSnackbar();
                    }
                }
            });
        }
        else this.finish();
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState)
    {

    }

    void showNoInternetSnackbar()
    {
        Snackbar.make(findViewById(android.R.id.content), "No Internet Connection", Snackbar.LENGTH_LONG).setAction("RETRY", new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                onBackPressed();
            }
        }).setActionTextColor(Color.GREEN).show();
    }
}