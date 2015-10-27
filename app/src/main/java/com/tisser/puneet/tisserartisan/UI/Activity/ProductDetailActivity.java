package com.tisser.puneet.tisserartisan.UI.Activity;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;

import com.tisser.puneet.tisserartisan.Global.AppConstants;
import com.tisser.puneet.tisserartisan.R;
import com.tisser.puneet.tisserartisan.UI.Adapters.ImageAdapter;
import com.tisser.puneet.tisserartisan.UI.Adapters.ReviewAdapter;
import com.tisser.puneet.tisserartisan.UI.Custom.DividerItemDecoration;

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

    private ReviewAdapter mReviewAdapter;
    private LinearLayoutManager llm;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
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
        ViewPager viewPager = (ViewPager) findViewById(R.id.image_product_img);

        ImageAdapter adapter = new ImageAdapter(this, manager);

        if (manager.currentProductDetailed.getImages() != null && manager.currentProductDetailed.getImages().size() != 0)
        {
            adapter.addImagesArray(manager.currentProductDetailed.getImages());
        }
        if(manager.currentProductDetailed.getProductImgPaths() != null && manager.currentProductDetailed.getProductImgPaths().size() != 0)
        {
            ArrayList<String> tempPaths = new ArrayList<>();
            for(int i = 0; i < manager.currentProductDetailed.getProductImgPaths().size(); i++) {
                if(!manager.currentProductDetailed.getProductImgPaths().get(i).startsWith("http")) {
                    tempPaths.add(manager.currentProductDetailed.getProductImgPaths().get(i));
                }
            }
            adapter.addImagesPathArray(tempPaths);
        }
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

    @Override
    public void onBackPressed()
    {
        navigator.openNewActivity(ProductDetailActivity.this, new BaseActivity_NavDrawer());

        /*FragmentManager fm = getFragmentManager();
        if (fm.getBackStackEntryCount() > 0)
        {
            int currentBackStackCount = fm.getBackStackEntryCount();
            while (currentBackStackCount > 0)
            {
                fm.popBackStack();
                currentBackStackCount--;
            }

        }
        else
        {
            super.onBackPressed();
        }*/
    }
}
