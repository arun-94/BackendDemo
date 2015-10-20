package com.tisser.puneet.tisserartisan.UI.Activity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;

import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.tisser.puneet.tisserartisan.Global.AppConstants;
import com.tisser.puneet.tisserartisan.Model.ProductDetailed;
import com.tisser.puneet.tisserartisan.R;
import com.tisser.puneet.tisserartisan.UI.Adapters.ImageAdapter;
import com.tisser.puneet.tisserartisan.UI.Adapters.ReviewAdapter;
import com.tisser.puneet.tisserartisan.UI.Custom.DividerItemDecoration;

import java.util.concurrent.TimeUnit;

import butterknife.Bind;

public class ProductDetailActivity extends BaseActivity implements BaseSliderView.OnSliderClickListener
{
    @Bind(R.id.tv_product_title) TextView tvproducttitle;
    @Bind(R.id.tv_product_price) TextView tvproductprice;
    @Bind(R.id.tv_product_about) TextView tvproductabout;
    @Bind(R.id.tv_product_code) TextView tvproductcode;
    @Bind(R.id.tv_product_colors) TextView tvproductcolors;
    @Bind(R.id.tv_keypoints) TextView tvkeypoints;
    @Bind(R.id.tv_detailed_description) TextView tvdetaileddescription;
    @Bind(R.id.tv_reviews_placeholder) TextView tvreviewsplaceholder;
    @Bind(R.id.scroll_view) ScrollView scrollView;
    @Bind(R.id.reviewRecycler) RecyclerView reviewsRecycler;

    private int imageheaderHeight;
    private Drawable ActionBarBackgroundDrawable;
    private String retreivingID;
    private ReviewAdapter mReviewAdapter;
    private LinearLayoutManager llm;
    //private ProgressBar mProgressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        /*reviewsRecycler.setHasFixedSize(true);
        reviewsRecycler.addItemDecoration(new MarginDecoration(this));*/
        //mReviewAdapter = new ReviewAdapter(this, null);
    }

    @Override
    protected int getLayoutResource()
    {
        return R.layout.activity_product_details;

    }

    @Override
    protected void setupToolbar()
    {
        setSupportActionBar(toolbar);
        assert getSupportActionBar() != null;
        getSupportActionBar().setTitle("Product Details");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void setupLayout()
    {
        //this.mProgressBar = (ProgressBar) findViewById(R.id.progress_loading);
        llm = new org.solovyev.android.views.llm.LinearLayoutManager(ProductDetailActivity.this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        reviewsRecycler.setLayoutManager(llm);
        reviewsRecycler.addItemDecoration(new DividerItemDecoration(this, null));
        mReviewAdapter = new ReviewAdapter(this, null);
        reviewsRecycler.setAdapter(mReviewAdapter);

        //scrollView.setVisibility(View.INVISIBLE);
/*
        Bundle extras = getIntent().getExtras();
        if (extras != null)
        {
            retreivingID = extras.getString("productID");
        }

        fetchProductDetail(retreivingID);
*/
        scrollView.setVisibility(View.VISIBLE);
        tvproducttitle.setText(manager.currentProductDetailed.getProductName());
        tvproductprice.setText("Rs. " + manager.currentProductDetailed.getProductPrice());
        tvproductcolors.setText("" + manager.currentProductDetailed.getProductColor());
        tvproductcode.setText(manager.currentProductDetailed.getProductID());
        tvproductcolors.setText(manager.currentProductDetailed.getProductColor());
        tvproductabout.setText(manager.currentProductDetailed.getProductSummary());
        tvdetaileddescription.setText(manager.currentProductDetailed.getProductDescription());
        tvkeypoints.setText(manager.currentProductDetailed.getProductKeypoints());
        ViewPager viewPager = (ViewPager) findViewById(R.id.image_product_img);
        if (manager.currentProductDetailed.getImages() != null)
        {
            ImageAdapter adapter = new ImageAdapter(this, manager.currentProductDetailed.getImages(), manager);
            viewPager.setAdapter(adapter);
        }
        else
        {
            ImageAdapter adapter = new ImageAdapter(this, manager.currentProductDetailed.getProductImgPaths(), manager, 0);
            viewPager.setAdapter(adapter);
        }
        if (manager.currentProductDetailed.getProductReviews().size() != 0)
        {
            mReviewAdapter.addAll(manager.currentProductDetailed.getProductReviews());
            tvreviewsplaceholder.setVisibility(View.GONE);
        }
    }


    @Override
    public void onSliderClick(BaseSliderView baseSliderView)
    {
        //Log.d("CLICKED", "Clicked on item " + sliderShow.getCurrentPosition());
        Intent i = new Intent(ProductDetailActivity.this, FullScreenViewActivityWithDelete.class);
        startActivity(i);
    }

    @Override
    public void processFinish(String result, int type)
    {
        if (result == null || result.equals(""))
        {
            Log.d("RESPONSERESULT", "No internet connection");
            showNoInternetAlert();
            new CountDownTimer(11000, 1000)
            {

                public void onTick(long millisUntilFinished)
                {
                    countdownInternetAlert(TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished));
                }

                public void onFinish()
                {
                    closeInternetAlert();
                    new CountDownTimer(1000, 1000)
                    {
                        @Override
                        public void onTick(long millisUntilFinished)
                        {

                        }

                        @Override
                        public void onFinish()
                        {
                            fetchProductDetail(retreivingID);
                        }
                    }.start();
                }

            }.start();
        }

    }

    public void fetchProductDetail(String productID)
    {
        //TODO
       /* getApiService().getProductDetailed(productID, new Callback<ProductDetailed>()
        {
            @Override
            public void success(ProductDetailed productDetailed, Response response)
            {
                consumeApiData(productDetailed);
            }

            @Override
            public void failure(RetrofitError error)
            {
                showNoInternetAlert();
            }
        });*/
    }

    private void consumeApiData(ProductDetailed productDetailed)
    {
        manager.currentProductDetailed = productDetailed;
        //mProgressBar.setVisibility(View.GONE);
        scrollView.setVisibility(View.VISIBLE);
        tvproducttitle.setText(manager.currentProductDetailed.getProductName());
        tvproductabout.setText(manager.currentProductDetailed.getProductSummary());
        tvproductprice.setText("Rs. " + manager.currentProductDetailed.getProductPrice());
        tvproductcolors.setText(manager.currentProductDetailed.getProductColor());
        tvkeypoints.setText(manager.currentProductDetailed.getProductKeypoints());
        tvdetaileddescription.setText(manager.currentProductDetailed.getProductDescription());
        tvproductcode.setText(manager.currentProductDetailed.getProductCode());
        ViewPager viewPager = (ViewPager) findViewById(R.id.image_product_img);
        ImageAdapter adapter = new ImageAdapter(this, manager.currentProductDetailed.getImages(), manager);
        viewPager.setAdapter(adapter);
        if (manager.currentProductDetailed.getProductReviews().size() != 0)
        {
            mReviewAdapter.addAll(manager.currentProductDetailed.getProductReviews());
            tvreviewsplaceholder.setVisibility(View.GONE);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_product_details, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_edit_product)
        {
            Bundle extras = new Bundle();
            extras.putInt(AppConstants.INTENT_IS_NEW_PRODUCT, AppConstants.EDIT_PRODUCT);
            navigator.openNewActivityWithExtras(this, new AddProductActivity(), extras);
            return true;
        }
        else if (id == android.R.id.home)
        {
            onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }
}
