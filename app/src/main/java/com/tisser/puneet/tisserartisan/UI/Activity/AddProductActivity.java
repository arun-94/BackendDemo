package com.tisser.puneet.tisserartisan.UI.Activity;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.text.util.Rfc822Tokenizer;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.ex.chips.BaseRecipientAdapter;
import com.android.ex.chips.RecipientEditTextView;
import com.darsh.multipleimageselect.helpers.Constants;
import com.darsh.multipleimageselect.models.Image;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.DecimalMax;
import com.mobsandgeeks.saripaar.annotation.DecimalMin;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.tisser.puneet.tisserartisan.CustomRules.IsCategorySelected;
import com.tisser.puneet.tisserartisan.Global.AppConstants;
import com.tisser.puneet.tisserartisan.Model.Category;
import com.tisser.puneet.tisserartisan.Model.ProductDetailed;
import com.tisser.puneet.tisserartisan.Model.Response.AddProductResponse;
import com.tisser.puneet.tisserartisan.Model.Response.ImageResponse;
import com.tisser.puneet.tisserartisan.Model.Subcategory;
import com.tisser.puneet.tisserartisan.Model.Subsubcategory;
import com.tisser.puneet.tisserartisan.R;
import com.tisser.puneet.tisserartisan.UI.Adapters.GalleryImagesAdapter;
import com.tisser.puneet.tisserartisan.UI.Custom.ExpandableHeightGridView;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.mime.TypedFile;

import static com.tisser.puneet.tisserartisan.HTTP.RestClient.getApiService;

public class AddProductActivity extends BaseActivity implements Validator.ValidationListener
{
    @Bind(R.id.upload_button) FloatingActionButton mUploadButton;
    @Bind(R.id.gallery_images_recycler) ExpandableHeightGridView mGalleryImagesRecycler;
    @Bind(R.id.ll_select_category) View selectCategoryLL;
    @Bind(R.id.ll_select_color) View selectColorLL;

    @NotEmpty @Bind(R.id.editText_price) EditText editTextPrice;
    @NotEmpty @Bind(R.id.editText_productname) EditText editTextProductName;
    @DecimalMin(value = 0, message = "Min Quantity 0") @DecimalMax(value = 100, message = "Max Quantity 100") @NotEmpty @Bind(R.id.editText_quantity) EditText editTextQuantity;
    @Bind(R.id.editText_product_long_description) EditText editTextProductDescription;
    @Bind(R.id.editText_product_short_description) EditText editTextShortDescription;
    @Bind(R.id.editText_product_tags) RecipientEditTextView editTextProductTags;

    private ProgressDialog mProgress;

    private ArrayList<Image> images;
    private ArrayList<String> imagePaths;
    private GalleryImagesAdapter mAdapter;
    private Validator userDetailsValidator;
    private int intentType = AppConstants.NEW_PRODUCT;

    ImageView categoryIcon, colorIcon;
    TextView categoryText, colorText;

    @IsCategorySelected TextView selected_categoryText;
    @IsCategorySelected TextView selected_colorText;

    ProductDetailed productDetailed;

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
                if (v instanceof ImageView)
                {
                    Intent i = new Intent(AddProductActivity.this, FullScreenViewActivityWithDelete.class);
                    i.putExtra("img_pos", position);
                    manager.currentImagePath = imagePaths.get(position);
                    AddProductActivity.this.startActivityForResult(i, AppConstants.RESULT_IMAGE_FULLSCREEN);
                }
                else if (v instanceof TextView)
                {
                    Intent intent = navigator.openGallery(AddProductActivity.this);
                    startActivityForResult(intent, Constants.REQUEST_CODE);
                }
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

        Bundle productBundle = getIntent().getExtras();

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
        images = new ArrayList<>();
        imagePaths = new ArrayList<>();

        editTextProductTags.setTokenizer(new Rfc822Tokenizer());

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        mAdapter.add(null, 0, width);
        mGalleryImagesRecycler.setNumColumns(1);
        mGalleryImagesRecycler.requestLayout();
        mGalleryImagesRecycler.setAdapter(mAdapter);

        mProgress = new ProgressDialog(AddProductActivity.this);
        mProgress.setMessage("Saving New Product");

        if(productBundle != null && productBundle.getInt(AppConstants.INTENT_IS_NEW_PRODUCT) == AppConstants.EDIT_PRODUCT)
        {
            intentType = productBundle.getInt(AppConstants.INTENT_IS_NEW_PRODUCT);
            ProductDetailed productDetailed = manager.currentProductDetailed;

            editTextProductName.setText(productDetailed.getProductName());
            editTextPrice.setText(productDetailed.getProductPrice() + "");
            selected_colorText.setVisibility(View.VISIBLE);
            selected_colorText.setText(productDetailed.getProductColor());
            colorIcon.setImageDrawable(getResources().getDrawable(R.drawable.ic_check_circle_black_24dp));
            selected_categoryText.setVisibility(View.VISIBLE);
            selected_categoryText.setText(getCategoryString(productDetailed));
            categoryIcon.setImageDrawable(getResources().getDrawable(R.drawable.ic_check_circle_black_24dp));
            if(productDetailed.getProductQuantity() != 0)
                editTextQuantity.setText("" + productDetailed.getProductQuantity());
            int id = productDetailed.getProductCategoryID();

            String keywordList = productDetailed.getProductKeypoints();

            String[] keywords =  keywordList.split(",");
            BaseRecipientAdapter adapter = new BaseRecipientAdapter(this) {};
            editTextProductTags.setAdapter(adapter);
            for(int i = 0; i < keywords.length; i++)
            {
                editTextProductTags.append(keywords[i]);
            }
            //selected_categoryText.setText(productDetailed.getProductCategoryID());
            if(productDetailed.getImages() != null)
            {
                ArrayList<ImageResponse> productImages = productDetailed.getImages();
                for (int i = 0; i < productImages.size(); i++)
                {
                    Image img = new Image(i, "" + i, productImages.get(i).getPath(), true);
                    images.add(img);
                    imagePaths.add(img.path);
                }
            }
            else {
                ArrayList<String> productImagesPaths = productDetailed.getProductImgPaths();
                for (int i = 0; i < productImagesPaths.size(); i++)
                {
                    Image img = new Image(i, "File Image", productImagesPaths.get(i), true);
                    images.add(img);
                    imagePaths.add(img.path);
                }
            }

            mAdapter.addAll(images, width - 40);
            mGalleryImagesRecycler.setNumColumns(4);
            mGalleryImagesRecycler.requestLayout();
            mGalleryImagesRecycler.setAdapter(mAdapter);

            editTextShortDescription.setText(productDetailed.getProductSummary());
            editTextProductDescription.setText(productDetailed.getProductDescription());

        }
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (requestCode == Constants.REQUEST_CODE && resultCode == RESULT_OK && data != null)
        {
            ArrayList<Image> tempImages = data.getParcelableArrayListExtra(Constants.INTENT_EXTRA_IMAGES);
            ArrayList<Image> temp1Images = new ArrayList<>();
            for (int i = 0; i < tempImages.size(); i++)
            {
                if (tempImages.get(i) == null) tempImages.remove(i);
            }

            int j = 0;
            while (images.size() < AppConstants.GALLERY_NUM_IMGS_TO_SELECT && j < tempImages.size())
            {
                images.add(tempImages.get(j));
                temp1Images.add(tempImages.get(j));
                j++;
            }
            if (j < tempImages.size())
            {
                Toast.makeText(AddProductActivity.this, "You can only add upto " + AppConstants.GALLERY_NUM_IMGS_TO_SELECT + " images", Toast.LENGTH_SHORT).show();
            }
            for (int i = 0; i < temp1Images.size(); i++)
            {
                imagePaths.add(temp1Images.get(i).path);
            }

            Display display = getWindowManager().getDefaultDisplay();
            Point size = new Point();
            display.getSize(size);
            int width = size.x;

            mAdapter.addAll(temp1Images, width - 40);
            mGalleryImagesRecycler.setNumColumns(4);
            mGalleryImagesRecycler.setAdapter(mAdapter);
        }
        else if (requestCode == AppConstants.REQUEST_SELECT_CATEGORY && resultCode == RESULT_OK)
        {
            selected_categoryText.setVisibility(View.VISIBLE);
            String s = data.getStringExtra(AppConstants.RESULT_CATEGORY_NAME);
            s = s.concat(" > " + data.getStringExtra(AppConstants.RESULT_SUBCATEGORY_NAME));
            s = s.concat(" > " + data.getStringExtra(AppConstants.RESULT_SUBSUBCATEGORY_NAME));
            selected_categoryText.setText(s);
            categoryIcon.setImageDrawable(getResources().getDrawable(R.drawable.ic_check_circle_black_24dp));
        }
        else if (requestCode == AppConstants.REQUEST_SELECT_COLOR && resultCode == RESULT_OK)
        {
            String stringExtra = data.getStringExtra(AppConstants.RESULT_COLOR_LIST);
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
        else if (requestCode == AppConstants.RESULT_IMAGE_FULLSCREEN && resultCode == RESULT_OK)
        {
            int imagePos = data.getIntExtra("img_pos", -1);

            if (imagePos != -1)
            {
                mAdapter.remove(imagePos);
                images.remove(imagePos);
                imagePaths.remove(imagePos);

                if(images.size() == 0)
                {
                    Display display = getWindowManager().getDefaultDisplay();
                    Point size = new Point();
                    display.getSize(size);
                    int width = size.x;
                    mAdapter.add(null, 0, width);
                    mGalleryImagesRecycler.setNumColumns(1);
                    mGalleryImagesRecycler.requestLayout();
                    mGalleryImagesRecycler.setAdapter(mAdapter);
                }
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

        return super.onOptionsItemSelected(item);

    }

    @OnClick(R.id.ll_select_category)
    void clickSelectCategory()
    {
        navigator.openNewActivityForResult(this, new CategoryListActivity(), AppConstants.REQUEST_SELECT_CATEGORY);
    }

    @OnClick(R.id.ll_select_color)
    void clickSelectColor()
    {
        navigator.openNewActivityForResult(this, new ColorSelectionActivity(), AppConstants.REQUEST_SELECT_COLOR);
    }

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
        else if (images == null || images.size() == 0)
        {
            message = "Please Upload Atleast 1 Photo";
        }
        else if(editTextProductTags.getSelectedRecipients().size() > 3) {
            message = "Please Upload Max 3 Tags";
        }
        else if(editTextProductTags.getSelectedRecipients().size() == 0) {
            message = "Please Upload Atleast 1 Tag";
        }
        else
        {
            // further logic
            productDetailed = new ProductDetailed();
            productDetailed.setProductName(editTextProductName.getText().toString().trim());
            productDetailed.setProductPrice(Double.parseDouble(String.format("%.2f", Double.parseDouble(editTextPrice.getText().toString()))));
            productDetailed.setProductColor(selected_colorText.getText().toString().trim());
            productDetailed.setProductQuantity(Integer.parseInt(editTextQuantity.getText().toString()));
            productDetailed.setProductCategoryID(manager.currentCategory.getCategoryID());
            productDetailed.setProductSubcategoryID(manager.currentSubCategory.getCategoryID());
            productDetailed.setProductSubsubcategoryID(manager.currentSubsubCategory.getCategoryID());
            productDetailed.setProductImgPathsArray(imagePaths);
            productDetailed.setProductSummary(editTextShortDescription.getText().toString().trim());
            productDetailed.setProductDescription(editTextProductDescription.getText().toString().trim());
            productDetailed.setProductKeypoints(editTextProductTags.getSelectedRecipients());
            manager.currentProductDetailed = productDetailed;
            if(intentType == AppConstants.EDIT_PRODUCT)
            {
                try
                {
                    editProduct(productDetailed);
                } catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
            else
                addNewProduct(productDetailed);
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

    public void addNewProduct(final ProductDetailed p)
    {
        String action = "AddNewProduct";
        ArrayList<String> imagePaths = p.getProductImgPaths();
        Map<String, TypedFile> files = new HashMap<String, TypedFile>();
        Map<String, String> colorIDs = new HashMap<String, String>();
        mProgress.show();

        for (int i = 0; i < imagePaths.size(); i++)
        {
            files.put("image" + i, new TypedFile("image/jpeg", new File(imagePaths.get(i))));
        }

        getApiService().addNewProduct(AppConstants.ACTION_ADD_PRODUCT, manager.getSessionID(), files, p.getProductName(), p.getProductPrice(), p.getProductQuantity(), manager.currentCategory.getCategoryID(), manager.currentSubCategory.getCategoryID(), manager.currentSubsubCategory.getCategoryID(), p.getProductColor(), p.getProductDescription(), p.getProductSummary(), p.getProductKeypoints(), new Callback<AddProductResponse>()
        {
            @Override
            public void success(AddProductResponse responseObj, Response response)
            {
                mProgress.dismiss();
                Log.d("Upload", "success");
                Log.d("Data", "" + responseObj.getError());
                if (responseObj.getError() == 0)
                {
                    Toast.makeText(AddProductActivity.this, responseObj.getStatus(), Toast.LENGTH_LONG).show();
                    manager.currentProductDetailed = p;
                    navigator.openNewActivity(AddProductActivity.this, new ProductDetailActivity());
                }
                else
                    Toast.makeText(AddProductActivity.this, responseObj.getStatus(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void failure(RetrofitError error)
            {
                if (error.getKind().equals(RetrofitError.Kind.NETWORK))
                {
                    Toast.makeText(AddProductActivity.this, "No internet Connection!", Toast.LENGTH_LONG).show();
                }
                else
                    Toast.makeText(AddProductActivity.this, "Failed to Add Product", Toast.LENGTH_LONG).show();
                mProgress.dismiss();
                Log.e("Upload", "error");
                Log.e("Data", "" + error);
            }
        });

    }


    private void editProduct(final ProductDetailed p) throws IOException
    {

        String action = "EditProduct";
        ArrayList<String> imagePaths = p.getProductImgPaths();
        Map<String, TypedFile> files = new HashMap<String, TypedFile>();
        Map<String, String> colorIDs = new HashMap<String, String>();
        mProgress.show();


       /* for (int i = 0; i < imagePaths.size(); i++)
        {
            if(imagePaths.get(i).startsWith("http")) {
                URL imageurl = new URL(imagePaths.get(i));
                Bitmap bitmap = BitmapFactory.decodeStream(imageurl.openConnection().getInputStream());
                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);

                File f = new File(Environment.getExternalStorageDirectory()
                        + File.separator + "temp.jpg");
                Boolean isFileCreated = f.createNewFile();
                FileOutputStream fo = new FileOutputStream(f);
                fo.write(bytes.toByteArray());
                fo.close();

                files.put("image" + i, new TypedFile("image/jpeg", f));
            }
            else
                files.put("image" + i, new TypedFile("image/jpeg", new File(imagePaths.get(i))));
        }

        getApiService().editProduct(AppConstants.ACTION_EDIT_PRODUCT ,manager.getSessionID(), files, p.getProductID() , p.getProductName(), p.getProductPrice(), p.getProductQuantity(), manager.currentCategory.getCategoryID(), manager.currentSubCategory.getCategoryID(), manager.currentSubsubCategory.getCategoryID(),p.getProductColor(), p.getProductDescription(), p.getProductSummary(), p.getProductKeypoints(), new Callback<EditProductResponse>()
        {
            @Override
            public void success(EditProductResponse responseObj, Response response)
            {
                mProgress.dismiss();
                Log.d("Upload", "success");
                Log.d("Data", "" + responseObj.getError());
                if(responseObj.getError() == 0)
                {
                    Toast.makeText(AddProductActivity.this, responseObj.getStatus(), Toast.LENGTH_LONG).show();
                    manager.currentProductDetailed = p;
                    navigator.openNewActivity(AddProductActivity.this, new ProductDetailActivity());
                }
                else
                    Toast.makeText(AddProductActivity.this, responseObj.getStatus(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void failure(RetrofitError error)
            {
                if (error.getKind().equals(RetrofitError.Kind.NETWORK))
                {
                    Toast.makeText(AddProductActivity.this, "No internet Connection!", Toast.LENGTH_LONG).show();
                }
                else
                    Toast.makeText(AddProductActivity.this, "Failed to Add Product", Toast.LENGTH_LONG).show();
                mProgress.dismiss();
                Log.e("Upload", "error");
                Log.e("Data", "" + error);
            }
        });*/
        Toast.makeText(AddProductActivity.this, "Edit Product API not available", Toast.LENGTH_SHORT).show();
    }




    String getCategoryString(ProductDetailed pD)
    {
        String categoryString = "";
        for(Category c : manager.categoryList)
        {
            if(c.getCategoryID() == pD.getProductCategoryID())
            {
                categoryString = categoryString.concat(c.getCategoryName());
                for(Subcategory sc : c.getSubcategories())
                {
                    if(sc.getCategoryID() == pD.getProductSubcategoryID())
                    {
                        categoryString = categoryString.concat(" > " + sc.getCategoryName());
                        for(Subsubcategory ssc : sc.getSubcategories())
                        {
                            if(ssc.getCategoryID() == pD.getProductSubsubcategoryID())
                            {
                                categoryString = categoryString.concat(" > " + ssc.getCategoryName());
                                break;
                            }
                        }
                    }
                }
            }
        }
        return categoryString;
    }
}
