package com.tisser.puneet.tisserartisan.UI.Activity;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ScrollView;
import android.widget.TextView;

import com.tisser.puneet.tisserartisan.Global.AppConstants;
import com.tisser.puneet.tisserartisan.Model.Response.ImageResponse;
import com.tisser.puneet.tisserartisan.R;
import com.tisser.puneet.tisserartisan.UI.Adapters.ImageAdapter;
import com.tisser.puneet.tisserartisan.UI.Adapters.ReviewAdapter;
import com.tisser.puneet.tisserartisan.UI.Custom.DividerItemDecoration;
import com.tisser.puneet.tisserartisan.UI.Custom.TransitionUtils;

import java.util.ArrayList;

import butterknife.Bind;

public class ProductDetailActivity extends BaseActivity
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
    @Bind(R.id.image_product_img) ViewPager viewPager;
    @Bind(R.id.cardview_product_details_1) CardView cardView1;

    private ReviewAdapter mReviewAdapter;
    private LinearLayoutManager llm;
    private Bundle intentBundle;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        postponeEnterTransition();
        getWindow().setEnterTransition(TransitionUtils.makeFadeEnterTransition());
        getWindow().setExitTransition(TransitionUtils.makeFadeEnterTransition());
        super.onCreate(savedInstanceState);
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
        llm = new org.solovyev.android.views.llm.LinearLayoutManager(ProductDetailActivity.this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        reviewsRecycler.setLayoutManager(llm);
        reviewsRecycler.addItemDecoration(new DividerItemDecoration(this, null));
        mReviewAdapter = new ReviewAdapter(this, null);
        reviewsRecycler.setAdapter(mReviewAdapter);

        scrollView.setVisibility(View.VISIBLE);
        tvproducttitle.setText(manager.currentProductDetailed.getProductName());
        tvproductprice.setText("Rs. " + manager.currentProductDetailed.getProductPrice());
        tvproductcolors.setText("" + manager.currentProductDetailed.getProductColor());
        tvproductcode.setText(manager.currentProductDetailed.getProductID());
        tvproductcolors.setText(manager.currentProductDetailed.getProductColor());
        tvproductabout.setText(manager.currentProductDetailed.getProductSummary());
        tvdetaileddescription.setText(manager.currentProductDetailed.getProductDescription());
        tvkeypoints.setText(manager.currentProductDetailed.getProductKeypoints());

        if (manager.currentProductDetailed.getImages() != null)
        {
            ImageAdapter adapter = new ImageAdapter(this, manager.currentProductDetailed.getImages(), manager);
            viewPager.setAdapter(adapter);
        }
        else if (manager.currentProductDetailed.getProductImgPaths() != null)
        {
            ArrayList<ImageResponse> localImageList = new ArrayList<>();
            for(int i = 0; i < manager.currentProductDetailed.getProductImgPaths().size(); i++)
            {
                ImageResponse newImage = new ImageResponse();
                newImage.setPath(manager.currentProductDetailed.getProductImgPaths().get(i));
                localImageList.add(newImage);
            }
            ImageAdapter adapter = new ImageAdapter(this, localImageList, manager);
            viewPager.setAdapter(adapter);
        }
        if (manager.currentProductDetailed.getProductReviews().size() != 0)
        {
            mReviewAdapter.addAll(manager.currentProductDetailed.getProductReviews());
            tvreviewsplaceholder.setVisibility(View.GONE);
        }

        scheduleStartPostponedTransition(viewPager);

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

    private void scheduleStartPostponedTransition(final View sharedElement)
    {
        sharedElement.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener()
        {
            @Override
            public boolean onPreDraw()
            {
                sharedElement.getViewTreeObserver().removeOnPreDrawListener(this);
                startPostponedEnterTransition();
                return true;
            }
        });
    }

    @Override
    public void onBackPressed()
    {
        intentBundle = getIntent().getExtras();
        if (intentBundle != null)
        {
            if (intentBundle.getInt(AppConstants.INTENT_FROM_ADD_PRODUCT, AppConstants.FROM_ADD) == AppConstants.FROM_ADD)
            {
                navigator.openNewActivity(ProductDetailActivity.this, new BaseActivity_NavDrawer());
            }
        }
        super.onBackPressed();
    }
}
