package com.tisser.puneet.tisserartisan.UI.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.tisser.puneet.tisserartisan.Global.Constants;
import com.tisser.puneet.tisserartisan.Model.TisserColor;
import com.tisser.puneet.tisserartisan.R;
import com.tisser.puneet.tisserartisan.UI.Adapters.ColorSelectionAdapter;

import java.util.ArrayList;

public class ColorSelectionActivity extends BaseActivity
{


    private static ArrayList<TisserColor> tisserColors;
    private static ArrayList<TisserColor> requiredTisserColors;
    private SearchView searchView;
    private ColorSelectionAdapter objAdapter;
    private ListView lv = null;
    private LinearLayout llContainer = null;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);


    }

    @Override
    protected int getLayoutResource()
    {
        return R.layout.activity_color_selection;
    }

    @Override
    protected void setupToolbar()
    {
        getSupportActionBar().setTitle("Select Color");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void setupLayout()
    {
        llContainer = (LinearLayout) findViewById(R.id.data_container);
        requiredTisserColors = new ArrayList<>();
        tisserColors = manager.colorList;

        for (int i = 0; i < tisserColors.size(); i++)
        {
            requiredTisserColors.add(tisserColors.get(i));
        }

        objAdapter = new ColorSelectionAdapter(this, tisserColors);

        lv = new ListView(this);
        //lv.setDivider(getResources().getDrawable(R.layout.divider));
        llContainer.addView(lv);
        lv.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
        lv.setAdapter(objAdapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                CheckBox chk = (CheckBox) view.findViewById(R.id.color_check_box);
                TisserColor bean = tisserColors.get(position);
                if (bean.isSelected())
                {
                    bean.setSelected(false);
                    chk.setChecked(false);
                }
                else
                {
                    bean.setSelected(true);
                    chk.setChecked(true);
                }

            }
        });

    }

    private String getSelectedColorNames()
    {
        StringBuffer sb = new StringBuffer();

        for (int i = 0; i < requiredTisserColors.size(); i++)
        {
            if (requiredTisserColors.get(i).isSelected())
            {
                sb.append(requiredTisserColors.get(i).getColor());
                sb.append(",");
            }
        }

        String s = sb.toString().trim();
        if (s.length() > 0) s = s.substring(0, s.length() - 1);

        return s;
    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_search, menu);

        searchView = (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.action_search));
        searchView.setIconified(false);
        searchView.clearFocus();
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
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText)
            {
                objAdapter.filter(newText);
                return true;
            }
        });

        return true;
    }

    @Override
    public void onBackPressed()
    {
        Intent i = new Intent();
        i.putExtra(Constants.RESULT_COLOR_LIST, getSelectedColorNames());
        setResult(RESULT_OK, i);
        finish();
    }

}
