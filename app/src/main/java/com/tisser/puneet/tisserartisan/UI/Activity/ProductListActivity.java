package com.tisser.puneet.tisserartisan.UI.Activity;

import android.content.res.TypedArray;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.tisser.puneet.tisserartisan.Custom.MarginDecoration;
import com.tisser.puneet.tisserartisan.Custom.RecyclerItemClickListener;
import com.tisser.puneet.tisserartisan.Model.Product;
import com.tisser.puneet.tisserartisan.Queries.ProductListQuery;
import com.tisser.puneet.tisserartisan.R;
import com.tisser.puneet.tisserartisan.UI.Adapters.ProductListAdapter;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import butterknife.Bind;

public class ProductListActivity extends BaseActivity_NavDrawer
{
    @Bind(R.id.productsListRecycler) RecyclerView mRecyclerView;
    @Bind(R.id.empty_result_text) TextView mEmptyText;
    @Bind(R.id.progress_loading) ProgressBar mProgressBar;

    ProductListAdapter mAdapter;
    GridLayoutManager mLayoutManager;
    static int TYPE_SEARCH_QUERY = 1;
    int type = 0;
    String query;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        final TypedArray styledAttributes = getTheme().obtainStyledAttributes(new int[]{android.R.attr.actionBarSize});

        Bundle extras = getIntent().getExtras();
        if (extras != null)
        {
            type = extras.getInt("type");
            query = extras.getString("query");
            getSupportActionBar().setTitle("\"" + query + "\"");
        }
        Log.d("TYPEOFINTENT", "Type is = " + type);

        //fetchProductList(manager.currentSubsubCategory.getCategoryID());
    }

    @Override
    protected int getLayoutResource()
    {
        return R.layout.activity_product_list;
    }

    @Override
    protected void setupToolbar()
    {
    }

    @Override
    protected void setupLayout()
    {
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.addItemDecoration(new MarginDecoration(this));
        mLayoutManager = new GridLayoutManager(this, 2);

        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new ProductListAdapter(this, null);
        mRecyclerView.setAdapter(mAdapter);

        mRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(this, new RecyclerItemClickListener.OnItemClickListener()
                {
                    @Override
                    public void onItemClick(View view, int position)
                    {
                        Log.d("CLICK", "Clicked on item" + position);
                    }
                }));


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
                            productListQuery = new ProductListQuery();
                            productListQuery.delegate = ProductListActivity.this;
                            fetchProductList(manager.currentSubsubCategory.getCategoryID());
                        }
                    }.start();
                }

            }.start();
        }
        else
        {
            if (type == 0)
            {
                Log.d("RESPONSERESULT", "Got category list");
            }
            else if (type == 1)
            {
                ArrayList<Product> productArrayList = new ArrayList<>();// = TisserParser.parseTisserProductList(result, manager.currentCategoryID);
                manager.productList.clear();
                mProgressBar.setVisibility(View.GONE);

                //Error case where there is no data!
                if (productArrayList.size() == 0)
                {
                    if (type == TYPE_SEARCH_QUERY)
                    {
                        mEmptyText.setText("Your search - \"" + query + "\" - did not match any products.");
                    }
                    else
                    {
                        mEmptyText.setText("NO PRODUCTS AVAILABLE IN THIS CATEGORY");
                    }

                    mEmptyText.setVisibility(View.VISIBLE);
                }
                else
                {
                    manager.productList.addAll(productArrayList);
                    mRecyclerView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener()
                            {
                                @Override
                                public void onGlobalLayout()
                                {
                                    mRecyclerView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                                    int viewWidth = mRecyclerView.getMeasuredWidth();
                                    Log.d("WIDTH", "Value of recycler view is " + viewWidth);
                                    float cardViewWidth = findViewById(R.id.product_card).getMeasuredWidth();
                                    int newSpanCount = (int) Math.floor(viewWidth / cardViewWidth);
                                    mLayoutManager.setSpanCount(newSpanCount);
                                    mLayoutManager.requestLayout();
                                }
                            });
                    mRecyclerView.setLayoutManager(mLayoutManager);
                    mAdapter = new ProductListAdapter(this, manager.productList);
                    mRecyclerView.setAdapter(mAdapter);
                    mProgressBar.setVisibility(View.GONE);
                }
            }
        }
    }

    public void fetchProductList(int categoryID)
    {
        manager.currentCategoryID = categoryID;
        if (type == TYPE_SEARCH_QUERY)
        {
            searchQuery.execute(query);
        }
        else
        {
            productListQuery.execute("" + categoryID);
        }
    }

}
