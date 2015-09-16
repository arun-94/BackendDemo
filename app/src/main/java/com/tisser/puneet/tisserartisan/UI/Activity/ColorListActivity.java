package com.tisser.puneet.tisserartisan.UI.Activity;

import android.content.Intent;
import android.media.Image;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.tisser.puneet.tisserartisan.Custom.RecyclerItemClickListener;
import com.tisser.puneet.tisserartisan.Global.Constants;
import com.tisser.puneet.tisserartisan.Model.TisserColor;
import com.tisser.puneet.tisserartisan.R;
import com.tisser.puneet.tisserartisan.UI.Adapters.ColorListAdapter;

import java.util.ArrayList;

public class ColorListActivity extends BaseActivity
{
    private SearchView searchView;
    private RecyclerView mRecyclerView;
    private ColorListAdapter mAdapter;
    private LinearLayoutManager llm;

    private ArrayList<TisserColor> storeItems;

    private Boolean exit = false;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutResource()
    {
        return R.layout.activity_color_list;
    }

    @Override
    protected void setupToolbar()
    {
        getSupportActionBar().setTitle("Select Colors");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void setupLayout()
    {
        storeItems = new ArrayList<>();
        mRecyclerView = (RecyclerView) findViewById(R.id.storeListRecycler);

        //Layout Manager
        llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);

        //Adapter
        mAdapter = new ColorListAdapter(this, manager.colorList);

        //Recycler View
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(llm);
        mRecyclerView.setAdapter(mAdapter);
        /*mRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(this, new RecyclerItemClickListener.OnItemClickListener()
        {
            @Override
            public void onItemClick(View view, int position)
            {
                int idx = mRecyclerView.getChildLayoutPosition(view);
                ImageView colorImage = (ImageView) view.findViewById(R.id.color_image);
                if (mAdapter.toggleSelection(idx))
                {
                    colorImage.setImageDrawable(getResources().getDrawable(R.drawable.transparent));
                }
                else
                {
                    colorImage.setImageDrawable(getResources().getDrawable(R.drawable.ic_check_circle_black_24dp));
                }

            }
        }));*/
    }

    @Override
    public void onBackPressed()
    {
        Intent i = new Intent();
        i.putExtra(Constants.RESULT_COLOR_LIST, mAdapter.getSelectedItemNames().toArray(new String[mAdapter.getSelectedItemNames().size()]));
        setResult(RESULT_OK, i);
        finish();
    }
}
