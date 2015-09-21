package com.tisser.puneet.tisserartisan.UI.Activity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.tisser.puneet.tisserartisan.Model.ProductDetailed;
import com.tisser.puneet.tisserartisan.Queries.ProductDetailQuery;
import com.tisser.puneet.tisserartisan.R;
import com.tisser.puneet.tisserartisan.UI.Adapters.ImageAdapter;
import com.tisser.puneet.tisserartisan.UI.Adapters.ReviewAdapter;
import com.tisser.puneet.tisserartisan.UI.Custom.DividerItemDecoration;

import java.util.concurrent.TimeUnit;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by Puneet on 18-07-2015.
 */
public class ProductDetailActivity extends BaseActivity implements BaseSliderView.OnSliderClickListener
{
    int imageheaderHeight;
    private Drawable ActionBarBackgroundDrawable;
    private ProductDetailQuery productDetailQuery;
    private String retreivingID;
    private TextView tvproducttitle;
    private TextView tvproductprice;
    private TextView tvproductabout;
    private TextView tvproductcode;
    private TextView tvproductcolors;
    private TextView tvkeypoints;
    private TextView tvdetaileddescription;
    private TextView tvreviewsplaceholder;
    //private ProgressBar mProgressBar;
    private ScrollView scrollView;
    private RecyclerView reviewsRecycler;
    private ReviewAdapter mReviewAdapter;
    LinearLayoutManager llm;

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
        getSupportActionBar().setTitle("Product Details");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void setupLayout()
    {
        this.tvdetaileddescription = (TextView) findViewById(R.id.tv_detailed_description);
        this.tvkeypoints = (TextView) findViewById(R.id.tv_keypoints);
        this.tvproductcolors = (TextView) findViewById(R.id.tv_product_colors);
        this.tvproductcode = (TextView) findViewById(R.id.tv_product_code);
        this.tvproductabout = (TextView) findViewById(R.id.tv_product_about);
        this.tvproductprice = (TextView) findViewById(R.id.tv_product_price);
        this.tvproducttitle = (TextView) findViewById(R.id.tv_product_title);
        //this.mProgressBar = (ProgressBar) findViewById(R.id.progress_loading);
        this.scrollView = (ScrollView) findViewById(R.id.scroll_view);
        this.reviewsRecycler = (RecyclerView) findViewById(R.id.reviewRecycler);
        this.tvreviewsplaceholder = (TextView) findViewById(R.id.tv_reviews_placeholder);
        llm = new org.solovyev.android.views.llm.LinearLayoutManager(ProductDetailActivity.this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        reviewsRecycler.setLayoutManager(llm);
        reviewsRecycler.addItemDecoration(new DividerItemDecoration(this, null));
        mReviewAdapter = new ReviewAdapter(this, null);
        reviewsRecycler.setAdapter(mReviewAdapter);

        //scrollView.setVisibility(View.INVISIBLE);

        Bundle extras = getIntent().getExtras();
        if (extras != null)
        {
            retreivingID = extras.getString("productID");
        }

        productDetailQuery = new ProductDetailQuery();
        productDetailQuery.delegate = ProductDetailActivity.this;
        fetchProductDetail(retreivingID);
    }


    @Override
    public void onSliderClick(BaseSliderView baseSliderView)
    {
        //Log.d("CLICKED", "Clicked on item " + sliderShow.getCurrentPosition());
        Intent i = new Intent(ProductDetailActivity.this, FullScreenViewActivity.class);
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
                            productDetailQuery = new ProductDetailQuery();
                            productDetailQuery.delegate = ProductDetailActivity.this;
                            fetchProductDetail(retreivingID);
                        }
                    }.start();
                }

            }.start();
        }
        else
        {
        }

    }

    public void fetchProductDetail(String productID)
    {
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
        manager.currentProduct = productDetailed;
        //mProgressBar.setVisibility(View.GONE);
        scrollView.setVisibility(View.VISIBLE);
        tvproducttitle.setText(manager.currentProduct.getProductName());
        tvproductabout.setText(manager.currentProduct.getProductSummary());
        tvproductprice.setText("Rs. " + manager.currentProduct.getProductPrice());
        tvproductcolors.setText(manager.currentProduct.getProductColor());
        tvkeypoints.setText(manager.currentProduct.getProductKeypoints());
        tvdetaileddescription.setText(manager.currentProduct.getProductDescription());
        tvproductcode.setText(manager.currentProduct.getProductCode());
        ViewPager viewPager = (ViewPager) findViewById(R.id.image_product_img);
        ImageAdapter adapter = new ImageAdapter(this, manager.currentProduct.getProductImgPaths(), manager);
        viewPager.setAdapter(adapter);
        if (manager.currentProduct.getProductReviews().size() != 0)
        {
            mReviewAdapter.addAll(manager.currentProduct.getProductReviews());
            tvreviewsplaceholder.setVisibility(View.GONE);
        }
    }
}