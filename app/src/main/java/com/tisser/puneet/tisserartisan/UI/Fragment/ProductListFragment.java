package com.tisser.puneet.tisserartisan.UI.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.tisser.puneet.tisserartisan.Custom.MarginDecoration;
import com.tisser.puneet.tisserartisan.Custom.RecyclerItemClickListener;
import com.tisser.puneet.tisserartisan.R;
import com.tisser.puneet.tisserartisan.UI.Activity.AddProductActivity;
import com.tisser.puneet.tisserartisan.UI.Adapters.ProductListAdapter;

import butterknife.Bind;
import butterknife.OnClick;

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
                Log.d("CLICK", "Clicked on item" + position);
            }
        }));
    }

    //FAB for adding new product
    @OnClick(R.id.fab_takePhoto)
    void addProduct()
    {
        Intent i = new Intent(getActivity(), AddProductActivity.class);
        startActivity(i);
    }


}
