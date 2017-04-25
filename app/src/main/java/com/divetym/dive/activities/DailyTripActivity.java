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
import com.divetym.dive.models.DiveSite;
import com.divetym.dive.view.DateRangeLayout;

import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import xyz.sahildave.widget.SearchViewLayout;

/**
 * Created by kali_root on 4/21/2017.
 */

public class DailyTripActivity extends AuthenticatedActivity implements
        TripListFragment.OnRefreshTripListener {
    private static final String TAG = DailyTripActivity.class.getSimpleName();
    @BindView(R.id.search_view_container)
    SearchViewLayout mSearchViewLayout;
    @BindView(R.id.layout_date_range)
    DateRangeLayout mDateRangeLayout;

    private DiveSite mSelectedDiveSite;
    private TripListFragment mFragment;
    private Date mStartDate;
    private Date mEndDate;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_trips);
        ButterKnife.bind(this);
        showBackButton(true);

        mFragment = initFragment(R.id.content, new TripListFragment());

        mDateRangeLayout.setContext(this);
        mDateRangeLayout.setOnRefreshTripListener(this);

        mStartDate = mDateRangeLayout.getStartCalendar().getTime();
        mEndDate = mDateRangeLayout.getEndCalendar().getTime();

        SearchListFragment searchListFragment = new SearchListFragment();
        mSearchViewLayout.setExpandedContentFragment(this, searchListFragment);
        mSearchViewLayout.setHint(getString(R.string.hint_select_dive_site));
        mSearchViewLayout.handleToolbarAnimation(getToolbar());
        mSearchViewLayout.setSearchListener(searchListFragment);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_daily_trips, menu);
        return true;
    }


    @Override
    public void onDateRangedChanged(Calendar startDate, Calendar endDate) {
        mStartDate = startDate.getTime();
        mEndDate = endDate.getTime();
        refreshTripList();
    }

    @Override
    public void onDiveSiteChanged(DiveSite diveSite) {
        mSelectedDiveSite = diveSite;
        mSearchViewLayout.collapse();
        mSearchViewLayout.setHint(diveSite.getName());
        refreshTripList();
    }

    private void refreshTripList() {
        if (mFragment != null) {
            mFragment.refreshTripList(mSelectedDiveSite, mStartDate, mEndDate);
        }
    }

}
