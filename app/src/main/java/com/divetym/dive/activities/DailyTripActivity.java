package com.divetym.dive.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.text.Editable;
import android.util.Log;
import android.view.Menu;
import android.view.View;

import com.divetym.dive.R;
import com.divetym.dive.activities.base.AuthenticatedActivity;
import com.divetym.dive.adapters.base.BaseRecyclerAdapter;
import com.divetym.dive.fragments.SearchListFragment;
import com.divetym.dive.fragments.TripListFragment;
import com.divetym.dive.models.DailyTrip;
import com.divetym.dive.view.DateRangeLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import xyz.sahildave.widget.SearchViewLayout;

/**
 * Created by kali_root on 4/21/2017.
 */

public class DailyTripActivity extends AuthenticatedActivity {
    private static final String TAG = DailyTripActivity.class.getSimpleName();
    @BindView(R.id.search_view_container)
    SearchViewLayout mSearchViewLayout;
    @BindView(R.id.layout_date_range)
    DateRangeLayout mDateRangeLayout;
    private TripListFragment mFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_trips);
        ButterKnife.bind(this);
        showBackButton(true);

        mFragment = initFragment(R.id.content, new TripListFragment());

        mDateRangeLayout.setContext(this);

        mSearchViewLayout.setExpandedContentFragment(this, new SearchListFragment());
        mSearchViewLayout.setHint(getString(R.string.hint_select_dive_site));
        mSearchViewLayout.handleToolbarAnimation(getToolbar());
        mSearchViewLayout.setSearchListener(new SearchViewLayout.SearchListener() {
            @Override
            public void onFinished(String searchKeyword) {
                Snackbar.make(mSearchViewLayout, "Search Done " + searchKeyword, Snackbar.LENGTH_LONG).show();
            }
        });
        mSearchViewLayout.setSearchBoxListener(new SearchViewLayout.SearchBoxListener() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                Log.d(TAG, "beforeTextChanged " + s);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Log.d(TAG, "onTextChanged " + s);
            }

            @Override
            public void afterTextChanged(Editable s) {
                Log.d(TAG, "afterTextChanged ");
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_daily_trips, menu);
        return true;
    }

}
