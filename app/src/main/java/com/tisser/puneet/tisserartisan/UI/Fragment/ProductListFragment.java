package com.tisser.puneet.tisserartisan.UI.Fragment;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.tisser.puneet.tisserartisan.Custom.MarginDecoration;
import com.tisser.puneet.tisserartisan.Custom.RecyclerItemClickListener;
import com.tisser.puneet.tisserartisan.R;
import com.tisser.puneet.tisserartisan.UI.Activity.BaseActivity_NavDrawer;
import com.tisser.puneet.tisserartisan.UI.Adapters.ProductListAdapter;

public class ProductListFragment extends BaseFragment
{
    ProductListAdapter mAdapter;
    RecyclerView mRecyclerView;
    GridLayoutManager mLayoutManager;
    TextView mEmptyText;
    private ProgressBar mProgressBar;

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
    protected void initializeLayout(View view)
    {
        mEmptyText = (TextView) view.findViewById(R.id.empty_result_text);
        mProgressBar = (ProgressBar) view.findViewById(R.id.progress_loading);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.productsListRecycler);
    }

    @Override
    protected int getLayoutResource()
    {
        return R.layout.fragment_product_list;
    }

    @Override
    protected void setupLayout()
    {
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.addItemDecoration(new MarginDecoration(getActivity()));
        mLayoutManager = new GridLayoutManager(getActivity(), 2);

        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new ProductListAdapter(getActivity(), null);
        mRecyclerView.setAdapter(mAdapter);

        mRecyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(getActivity(), new RecyclerItemClickListener.OnItemClickListener()
                {
                    @Override
                    public void onItemClick(View view, int position)
                    {
                        Log.d("CLICK", "Clicked on item" + position);
                    }
                })
        );
    }


}
