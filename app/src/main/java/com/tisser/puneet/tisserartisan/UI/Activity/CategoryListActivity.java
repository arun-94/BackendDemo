package com.tisser.puneet.tisserartisan.UI.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;


import com.tisser.puneet.tisserartisan.Custom.DividerItemDecoration;
import com.tisser.puneet.tisserartisan.Custom.RecyclerItemClickListener;
import com.tisser.puneet.tisserartisan.Custom.SimpleSectionedRecyclerViewAdapter;
import com.tisser.puneet.tisserartisan.Custom.SlidingTabLayout;
import com.tisser.puneet.tisserartisan.Global.Constants;
import com.tisser.puneet.tisserartisan.Model.Category;
import com.tisser.puneet.tisserartisan.Model.TisserSettings;
import com.tisser.puneet.tisserartisan.Queries.CategoryListQuery;
import com.tisser.puneet.tisserartisan.Queries.TisserParser;
import com.tisser.puneet.tisserartisan.R;
import com.tisser.puneet.tisserartisan.UI.Adapters.CategoryAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class CategoryListActivity extends BaseActivity
{

    private ViewPager mViewPager;
    private SlidingTabLayout mSlidingTabLayout;
    private CategoryPagerAdapter mPagerAdapter;
    private Context mContext;
    private ProgressBar mProgressBar;
    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        categoryListQuery.execute();

        mContext = this;

        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        mViewPager.setVisibility(View.INVISIBLE);
        // Give the SlidingTabLayout the ViewPager, this must be done AFTER the ViewPager has had
        // it's PagerAdapter set.
        mSlidingTabLayout = (SlidingTabLayout) findViewById(R.id.sliding_tabs);
        mSlidingTabLayout.setVisibility(View.GONE);

        mProgressBar = (ProgressBar) findViewById(R.id.progress_loading);

        //settingsQuery.execute();
        /*getApiService().getSettings(new Callback<TisserSettings>()
        {
            @Override
            public void success(TisserSettings tisserSettings, Response response)
            {
                consumeApiData(tisserSettings);
            }

            @Override
            public void failure(RetrofitError error)
            {
                showNoInternetAlert();
            }
        });*/

        /*getApiService().getCategoryList(new Callback<ArrayList<Category>>()
        {

            @Override
            public void success(ArrayList<Category> categories, Response response)
            {
                consumeApiData(categories);
            }

            @Override
            public void failure(RetrofitError error)
            {
                showNoInternetAlert();
            }
        });*/
    }

    @Override
    protected int getLayoutResource()
    {
        return R.layout.activity_category_list;
    }

    @Override
    protected void setupToolbar()
    {
        getSupportActionBar().setTitle("Tisser India");
        toolbar.setNavigationIcon(R.drawable.ic_action_logo_1);
        toolbar.canShowOverflowMenu();
    }

    @Override
    protected void setupLayout()
    {

    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_search, menu);
        searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
/*
        searchView.setIconified(true);
*/

        /*searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener()
        {
            @Override
            public boolean onQueryTextSubmit(String query)
            {
                if (menu.findItem(R.id.action_search) != null)
                {
                    menu.findItem(R.id.action_search).collapseActionView();
                    searchView.setIconified(true);
                    searchView.clearFocus();
                    Intent searchIntent = new Intent(CategoryListActivity.this, ProductListActivity.class);
                    searchIntent.putExtra("type", 1);
                    searchIntent.putExtra("query", query);
                    startActivity(searchIntent);
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText)
            {
                return true;
            }
        });*/
        return true;
    }

    @Override
    public void processFinish(String result, int type)
    {
        if (result == null || result.equals(""))
        {
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
                            categoryListQuery = new CategoryListQuery();
                            categoryListQuery.delegate = CategoryListActivity.this;
                            categoryListQuery.execute();
                        }
                    }.start();
                }

            }.start();
        }
        else if (type == 0)
        {
            manager.categoryList = TisserParser.parseTisserCategory(result, mContext);
            mProgressBar.setVisibility(View.GONE);
            mSlidingTabLayout.setVisibility(View.VISIBLE);
            mViewPager.setVisibility(View.VISIBLE);
            mViewPager.setAdapter(new CategoryPagerAdapter());
            mSlidingTabLayout.setViewPager(mViewPager);
        }
    }

    private void consumeApiData(TisserSettings tisserSettings)
    {
        manager.settings = tisserSettings;
    }

    private void consumeApiData(ArrayList<Category> categories)
    {
        manager.categoryList = categories;
        mProgressBar.setVisibility(View.GONE);
        mSlidingTabLayout.setVisibility(View.VISIBLE);
        mViewPager.setVisibility(View.VISIBLE);
        mViewPager.setAdapter(new CategoryPagerAdapter());
        mSlidingTabLayout.setViewPager(mViewPager);
    }

    class CategoryPagerAdapter extends PagerAdapter
    {
        @Override
        public int getCount()
        {
            return manager.categoryList.size();
        }

        /**
         * Instantiate the {@link View} which should be displayed at {@code position}. Here we
         * inflate a layout from the apps resources and then change the text view to signify the position.
         */
        @Override
        public Object instantiateItem(ViewGroup container, final int position)
        {
            // Inflate a new layout from our resources
            View view = getLayoutInflater().inflate(R.layout.category_recycler, container, false);
            container.addView(view);

            RecyclerView mRecyclerView = (RecyclerView) view.findViewById(R.id.categoryRecycler);
            mRecyclerView.setHasFixedSize(true);
            LinearLayoutManager llm = new LinearLayoutManager(getApplicationContext());
            llm.setOrientation(LinearLayoutManager.VERTICAL);
            mRecyclerView.setLayoutManager(llm);
            mRecyclerView.addItemDecoration(new DividerItemDecoration(mContext, null));

            ArrayList<Category> subcategories = manager.categoryList.get(position).getSubcategories();
            final ArrayList<Category> subsubcategories = new ArrayList<>();

            CategoryAdapter mAdapter = new CategoryAdapter(mContext, null);

            for (int i = 0; i < subcategories.size(); i++)
            {
                subsubcategories.addAll(subcategories.get(i).getSubcategories());
            }
            mAdapter.addAll(subsubcategories);

            final List<SimpleSectionedRecyclerViewAdapter.Section> sections = new ArrayList<SimpleSectionedRecyclerViewAdapter.Section>();
            int count = 0;
            for (int i = 0; i < subcategories.size(); i++)
            {
                sections.add(new SimpleSectionedRecyclerViewAdapter.Section(count, subcategories.get(i).getCategoryName()));
                count += subcategories.get(i).getSubcategories().size();
            }
            //Sections
                /*sections.add(new SimpleSectionedRecyclerViewAdapter.Section(14,"Section 4"));
                sections.add(new SimpleSectionedRecyclerViewAdapter.Section(20,"Section 5"));*/

            SimpleSectionedRecyclerViewAdapter.Section[] dummy = new SimpleSectionedRecyclerViewAdapter.Section[sections.size()];
            SimpleSectionedRecyclerViewAdapter mSectionedAdapter = new SimpleSectionedRecyclerViewAdapter(mContext, R.layout.section, R.id.section_text, mAdapter);
            mSectionedAdapter.setSections(sections.toArray(dummy));

            //Apply this adapter to the RecyclerView
            mRecyclerView.setAdapter(mSectionedAdapter);

            mRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(mContext, new RecyclerItemClickListener.OnItemClickListener()
            {
                @Override
                public void onItemClick(View view, int itempos)
                {
                    int offset = 0;
                    for (int i = 0; i < sections.size(); i++)
                    {
                        if (itempos > sections.get(i).sectionedPosition)
                        {
                                    /*Log.d("SECTION", "First: " + sections.get(i).firstPosition);
                                    Log.d("SECTION", "Sectioned: " + sections.get(i).sectionedPosition);
                                    Log.d("SECTION", "Title: " + sections.get(i).getTitle());*/
                            offset += 1;
                        }
                        else
                        {
                            break;
                        }
                    }
                    //Log.d("ONCLICK", "Offset is " + offset);
                    itempos -= offset;
                    manager.currentCategory = manager.categoryList.get(position);
                    for (int i = 0; i < manager.categoryList.get(position).getSubcategories().size(); i++)
                    {
                        Category currentSubcategory = manager.categoryList.get(position).getSubcategories().get(i);
                        if (currentSubcategory.getSubcategories().contains(subsubcategories.get(itempos)))
                        {
                            manager.currentSubCategory = currentSubcategory;
                            break;
                        }
                    }
                    manager.currentSubsubCategory = subsubcategories.get(itempos);
                            /*Intent productListIntent = new Intent(CategoryListActivity.this, ProductListActivity.class);
                            startActivity(productListIntent);*/

                    /*** USE OTTO/EVENTBUS HERE ***/

                    Intent intent = new Intent();
                    intent.putExtra(Constants.RESULT_CATEGORY_NAME, manager.currentCategory.getCategoryName());
                    intent.putExtra(Constants.RESULT_SUBCATEGORY_NAME, manager.currentSubCategory.getCategoryName());
                    intent.putExtra(Constants.RESULT_SUBSUBCATEGORY_NAME, manager.currentSubsubCategory.getCategoryName());
                    setResult(RESULT_OK, intent);
                    finish();
                }
            }));


            return view;

            // Retrieve a TextView from the inflated View, and update it's text
           /* TextView title = (TextView) view.findViewById(R.id.item_title);
            title.setText(String.valueOf(position + 1));

            Log.i(LOG_TAG, "instantiateItem() [position: " + position + "]");*/

            // Return the View
        }

        /**
         * Destroy the item from the {@link android.support.v4.view.ViewPager}. In our case this is simply removing the
         * {@link View}.
         */
        @Override
        public void destroyItem(ViewGroup container, int position, Object object)
        {
            container.removeView((View) object);
        }

        /**
         * @return true if the value returned from {@link #instantiateItem(ViewGroup, int)} is the
         * same object as the {@link View} added to the {@link android.support.v4.view.ViewPager}.
         */
        @Override
        public boolean isViewFromObject(View view, Object o)
        {
            return o == view;
        }

        @Override
        public CharSequence getPageTitle(int position)
        {
            return manager.categoryList.get(position).getCategoryName();
        }
    }
}
