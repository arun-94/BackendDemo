package com.tisser.puneet.tisserartisan.UI.Fragment;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.tisser.puneet.tisserartisan.Global.AppConstants;
import com.tisser.puneet.tisserartisan.Model.Product;
import com.tisser.puneet.tisserartisan.Model.ProductResponse;
import com.tisser.puneet.tisserartisan.R;
import com.tisser.puneet.tisserartisan.UI.Activity.BaseActivity_NavDrawer;
import com.tisser.puneet.tisserartisan.UI.Activity.Navigator;
import com.tisser.puneet.tisserartisan.UI.Activity.ProductDetailActivity;
import com.tisser.puneet.tisserartisan.UI.Adapters.ProductListAdapter;
import com.tisser.puneet.tisserartisan.UI.Custom.MarginDecoration;
import com.tisser.puneet.tisserartisan.UI.Custom.RecyclerItemClickListener;

import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.OnClick;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

import static com.tisser.puneet.tisserartisan.HTTP.RestClient.getApiService;

public class ProductListFragment extends BaseFragment
{
    @Bind(R.id.productsListRecycler) RecyclerView mRecyclerView;
    @Bind(R.id.empty_result_text) TextView mEmptyText;
    @Bind(R.id.progress_loading) ProgressBar mProgressBar;

    ProductListAdapter mAdapter;
    GridLayoutManager mLayoutManager;

    public static ProductListFragment newInstance()
    {
        ProductListFragment fragment = new ProductListFragment();
        return fragment;
    }

    public ProductListFragment()
    {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        getApiService().showMyProducts(AppConstants.ACTION_SHOW_PRODUCTS, manager.getSessionID(), new Callback<ProductResponse>()
        {
            @Override
            public void success(ProductResponse productResponse, Response response)
            {
                consumeApiData(productResponse.getProductList());
            }

            @Override
            public void failure(RetrofitError error)
            {
                //showNoInternetAlert();
            }
        });
    }

    @Override
    protected String setupToolbarTitle()
    {
        return "My Products";
    }

    @Override
    protected int getLayoutResource()
    {
        return R.layout.fragment_product_list;
    }

    @Override
    protected void setupLayout()
    {
        mLayoutManager = new GridLayoutManager(getActivity(), 2);
        mAdapter = new ProductListAdapter(getActivity(), null);

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.addItemDecoration(new MarginDecoration(getActivity()));
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), new RecyclerItemClickListener.OnItemClickListener()
        {
            @Override
            public void onItemClick(View view, int position)
            {
                ((BaseActivity_NavDrawer) getActivity()).navigator.openNewActivity(getActivity(), new ProductDetailActivity());
                manager.currentProduct = manager.productList.get(position);
                Log.d("CLICK", "Clicked on item" + position);
            }
        }));
    }

    //FAB for adding new product
    @OnClick(R.id.fab_takePhoto)
    void addProduct()
    {
        ((BaseActivity_NavDrawer) getActivity()).navigator.takePhoto(getActivity());
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void consumeApiData(ArrayList<Product> products)
    {
        if(products != null)
        {
            manager.productList.clear();
            Log.d("SEARCHRETRO", products.get(0).getProductName());
            mProgressBar.setVisibility(View.GONE);
            //Error case where there is no data!
            manager.productList.addAll(products);
            mRecyclerView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener()
            {
                @Override
                public void onGlobalLayout()
                {
                    mRecyclerView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    int viewWidth = mRecyclerView.getMeasuredWidth();
                    Log.d("WIDTH", "Value of recycler view is " + viewWidth);
                    float cardViewWidth = getView().findViewById(R.id.product_card).getMeasuredWidth();
                    int newSpanCount = (int) Math.floor(viewWidth / cardViewWidth);
                    mLayoutManager.setSpanCount(newSpanCount);
                    mLayoutManager.requestLayout();
                }
            });
            mRecyclerView.setLayoutManager(mLayoutManager);
            mAdapter = new ProductListAdapter(getActivity(), manager.productList);
            mRecyclerView.setAdapter(mAdapter);
            mEmptyText.setVisibility(View.INVISIBLE);
        }
        else
        {
            mEmptyText.setVisibility(View.VISIBLE);
        }
        mProgressBar.setVisibility(View.GONE);
    }

}
