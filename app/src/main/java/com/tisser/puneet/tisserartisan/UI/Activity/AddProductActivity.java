package com.tisser.puneet.tisserartisan.UI.Activity;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.darsh.multipleimageselect.helpers.Constants;
import com.darsh.multipleimageselect.models.Image;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.tisser.puneet.tisserartisan.CustomRules.IsCategorySelected;
import com.tisser.puneet.tisserartisan.Model.ProductDetailed;
import com.tisser.puneet.tisserartisan.R;
import com.tisser.puneet.tisserartisan.UI.Adapters.GalleryImagesAdapter;
import com.tisser.puneet.tisserartisan.UI.Custom.ExpandableHeightGridView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddProductActivity extends BaseActivity implements Validator.ValidationListener
{
    @Bind(R.id.upload_button) FloatingActionButton mUploadButton;
    @Bind(R.id.gallery_images_recycler) ExpandableHeightGridView mGalleryImagesRecycler;
    @Bind(R.id.ll_select_category) View selectCategoryLL;
    @Bind(R.id.ll_select_color) View selectColorLL;

    @NotEmpty @Bind(R.id.editText_price) EditText editTextPrice;
    @NotEmpty @Bind(R.id.editText_productname) EditText editTextProductName;
    @Bind(R.id.editText_productdescription) EditText editTextProductDescription;

    @OnClick(R.id.button_save)
    void submit()
    {
        userDetailsValidator.validate();
    }


    @OnClick(R.id.upload_button)
    void uploadPhoto()
    {
        Intent intent = navigator.openGallery(AddProductActivity.this);
        startActivityForResult(intent, Constants.REQUEST_CODE);
    }

    private ArrayList<Image> images;
    private ArrayList<String> imagePaths;
    private GalleryImagesAdapter mAdapter;
    private Validator userDetailsValidator;

    ImageView categoryIcon, colorIcon;
    TextView categoryText, colorText;


    @IsCategorySelected TextView selected_categoryText;

    @IsCategorySelected TextView selected_colorText;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        Validator.registerAnnotation(IsCategorySelected.class);
        userDetailsValidator = new Validator(this);
        userDetailsValidator.setValidationListener(this);

        mGalleryImagesRecycler.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id)
            {
                Intent i = new Intent(AddProductActivity.this, FullScreenViewActivity.class);
                manager.currentImage = ((ImageView) v).getDrawable();
                AddProductActivity.this.startActivity(i);
            }
        });
    }

    @Override
    protected int getLayoutResource()
    {
        return R.layout.activity_add_product;
    }

    @Override
    protected void setupToolbar()
    {

        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        assert toolbar != null;
        toolbar.setNavigationIcon(R.drawable.ic_close_white_24dp);
        getSupportActionBar().setTitle("Add New Product");
    }

    @Override
    protected void setupLayout()
    {
        categoryText = ButterKnife.findById(selectCategoryLL, R.id.selector_text);
        colorText = ButterKnife.findById(selectColorLL, R.id.selector_text);
        selected_categoryText = ButterKnife.findById(selectCategoryLL, R.id.selected_text);
        selected_colorText = ButterKnife.findById(selectColorLL, R.id.selected_text);
        categoryIcon = ButterKnife.findById(selectCategoryLL, R.id.selector_icon);
        colorIcon = ButterKnife.findById(selectColorLL, R.id.selector_icon);

        categoryText.setText("Select Category");
        colorText.setText("Select Color(s)");
        categoryIcon.setImageDrawable(getResources().getDrawable(R.drawable.ic_help_grey_24dp));
        colorIcon.setImageDrawable(getResources().getDrawable(R.drawable.ic_palette_grey_24dp));

        mGalleryImagesRecycler.setExpanded(true);
        mAdapter = new GalleryImagesAdapter(this, null);

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        mAdapter.add(null, 0, width);
        mGalleryImagesRecycler.setNumColumns(1);
        mGalleryImagesRecycler.requestLayout();
        mGalleryImagesRecycler.setAdapter(mAdapter);

        imagePaths = new ArrayList<>();
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (requestCode == Constants.REQUEST_CODE && resultCode == RESULT_OK && data != null)
        {
            images = data.getParcelableArrayListExtra(Constants.INTENT_EXTRA_IMAGES);
            for(int i = 0; i < images.size(); i++)
                imagePaths.add(images.get(i).path);
            Display display = getWindowManager().getDefaultDisplay();
            Point size = new Point();
            display.getSize(size);
            int width = size.x;

            mAdapter.addAll(images, width - 40);
            mGalleryImagesRecycler.setNumColumns(4);
            mGalleryImagesRecycler.setAdapter(mAdapter);
        }
        else if (requestCode == com.tisser.puneet.tisserartisan.Global.Constants.REQUEST_SELECT_CATEGORY && resultCode == RESULT_OK)
        {
            selected_categoryText.setVisibility(View.VISIBLE);
            String s = data.getStringExtra(com.tisser.puneet.tisserartisan.Global.Constants.RESULT_CATEGORY_NAME);
            s = s.concat(" > " + data.getStringExtra(com.tisser.puneet.tisserartisan.Global.Constants.RESULT_SUBCATEGORY_NAME));
            s = s.concat(" > " + data.getStringExtra(com.tisser.puneet.tisserartisan.Global.Constants.RESULT_SUBSUBCATEGORY_NAME));
            selected_categoryText.setText(s);
            categoryIcon.setImageDrawable(getResources().getDrawable(R.drawable.ic_check_circle_black_24dp));
        }
        else if (requestCode == com.tisser.puneet.tisserartisan.Global.Constants.REQUEST_SELECT_COLOR && resultCode == RESULT_OK)
        {
            String stringExtra = data.getStringExtra(com.tisser.puneet.tisserartisan.Global.Constants.RESULT_COLOR_LIST);
            if (!stringExtra.equals(""))
            {
                selected_colorText.setVisibility(View.VISIBLE);
                selected_colorText.setText(stringExtra);
                colorIcon.setImageDrawable(getResources().getDrawable(R.drawable.ic_check_circle_black_24dp));
            }
            else
            {
                selected_colorText.setVisibility(View.GONE);
                colorIcon.setImageDrawable(getResources().getDrawable(R.drawable.ic_palette_grey_24dp));
            }
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();

        return id == R.id.action_settings || super.onOptionsItemSelected(item);

    }

    @OnClick(R.id.ll_select_category)
    void clickSelectCategory()
    {
        Intent i = new Intent(this, CategoryListActivity.class);
        startActivityForResult(i, com.tisser.puneet.tisserartisan.Global.Constants.REQUEST_SELECT_CATEGORY);
    }

    @OnClick(R.id.ll_select_color)
    void clickSelectColor()
    {
        Intent i = new Intent(this, ColorSelectionActivity.class);
        startActivityForResult(i, com.tisser.puneet.tisserartisan.Global.Constants.REQUEST_SELECT_COLOR);
    }

    @Override
    public void onValidationSucceeded()
    {
        String message;

        if (selected_categoryText.getVisibility() == View.GONE && selected_colorText.getVisibility() == View.GONE)
        {
            message = "Category & Color Must be Selected";
        }
        else if (selected_colorText.getVisibility() == View.GONE)
        {
            message = "Color Must be Selected";
        }
        else if (selected_categoryText.getVisibility() == View.GONE)
        {
            message = "Category Must be Selected";
        }
        else if (images == null)
        {
            message = "Please Upload Atleast 1 Photo";
        }
        else
        {
            // further logic
            ProductDetailed productDetailed = new ProductDetailed();
            productDetailed.setProductName(editTextProductName.getText().toString().trim());
            productDetailed.setProductPrice(Integer.parseInt(editTextPrice.getText().toString()));
            productDetailed.setProductColor(selected_colorText.getText().toString().trim());
            productDetailed.setProductCategoryID(manager.currentCategoryID);
            productDetailed.setProductImgPaths(imagePaths);
            productDetailed.setProductDescription(editTextProductDescription.getText().toString().trim());
            manager.currentProduct = productDetailed;
            manager.currentProduct.makePostURL();
            Intent productDetailedIntent = new Intent(this, ProductDetailActivity.class);
            startActivity(productDetailedIntent);
            return;
        }


        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);

        // Setting Dialog Title
        alertDialog.setTitle("Field Empty!");
        alertDialog.setMessage(message);
        alertDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int which)
            {
                dialog.cancel();
            }
        });

        alertDialog.show();


    }

    @Override
    public void onValidationFailed(List<ValidationError> errors)
    {
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
}
