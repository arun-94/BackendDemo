package com.tisser.puneet.tisserartisan.UI.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.tisser.puneet.tisserartisan.Global.AppConstants;
import com.tisser.puneet.tisserartisan.Model.Category;
import com.tisser.puneet.tisserartisan.Model.Subcategory;
import com.tisser.puneet.tisserartisan.Model.Subsubcategory;
import com.tisser.puneet.tisserartisan.R;
import com.tisser.puneet.tisserartisan.UI.Adapters.CategoryAdapter;
import com.tisser.puneet.tisserartisan.UI.Custom.DividerItemDecoration;
import com.tisser.puneet.tisserartisan.UI.Custom.RecyclerItemClickListener;
import com.tisser.puneet.tisserartisan.UI.Custom.SimpleSectionedRecyclerViewAdapter;
import com.tisser.puneet.tisserartisan.UI.Custom.SlidingTabLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

import static com.tisser.puneet.tisserartisan.HTTP.RestClient.getApiService;


public class CategoryListActivity extends BaseActivity
{

    @Bind(R.id.viewpager) ViewPager mViewPager;
    @Bind(R.id.sliding_tabs) SlidingTabLayout mSlidingTabLayout;
    //private CategoryPagerAdapter mPagerAdapter;
    private Context mContext;
    @Bind(R.id.progress_loading) ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        //categoryListQuery.execute();
        mContext = this;

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

        if(manager.categoryList == null || manager.categoryList.size() == 0)
        {
            getApiService().getCategoryList(new Callback<ArrayList<Category>>()
            {

                @Override
                public void success(ArrayList<Category> categories, Response response)
                {
                    consumeApiData(categories, AppConstants.TYPE_NEW_CATEGORY_LIST);
                }

                @Override
                public void failure(RetrofitError error)
                {
                    showNoInternetAlert();
                }
            });
        }
        else
            consumeApiData(manager.categoryList, AppConstants.TYPE_OLD_CATEGORY_LIST);
    }

    @Override
    protected int getLayoutResource()
    {
        return R.layout.activity_category_list;
    }

    @Override
    protected void setupToolbar()
    {
        assert getSupportActionBar() != null;
        getSupportActionBar().setTitle("Select Category");
        assert toolbar != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.canShowOverflowMenu();
    }

    @Override
    protected void setupLayout()
    {
        mViewPager.setVisibility(View.INVISIBLE);
        mSlidingTabLayout.setVisibility(View.GONE);
    }

/*    @Override
    public boolean onCreateOptionsMenu(final Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_search, menu);
        final SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();

        searchView.setIconified(true);


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener()
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
        });
        return true;
    }*/

    private void consumeApiData(ArrayList<Category> categories, int type)
    {
        if(type == AppConstants.TYPE_NEW_CATEGORY_LIST)
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

            ArrayList<Subcategory> subcategories = manager.categoryList.get(position).getSubcategories();
            final ArrayList<Subsubcategory> subsubcategories = new ArrayList<>();

            CategoryAdapter mAdapter = new CategoryAdapter(mContext, null);

            for (int i = 0; i < subcategories.size(); i++)
            {
                subsubcategories.addAll(subcategories.get(i).getSubcategories());
            }
            mAdapter.addAll(subsubcategories);

            final List<SimpleSectionedRecyclerViewAdapter.Section> sections = new ArrayList<>();
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
                    manager.currentCategoryID = manager.currentCategory.getCategoryID();
                    for (int i = 0; i < manager.categoryList.get(position).getSubcategories().size(); i++)
                    {
                        Subcategory currentSubcategory = manager.categoryList.get(position).getSubcategories().get(i);
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
                    intent.putExtra(AppConstants.RESULT_CATEGORY_NAME, manager.currentCategory.getCategoryName());
                    intent.putExtra(AppConstants.RESULT_SUBCATEGORY_NAME, manager.currentSubCategory.getCategoryName());
                    intent.putExtra(AppConstants.RESULT_SUBSUBCATEGORY_NAME, manager.currentSubsubCategory.getCategoryName());
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
