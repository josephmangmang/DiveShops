package com.divetym.dive.activities;

import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.view.Menu;
import android.view.View;

import com.divetym.dive.R;
import com.divetym.dive.activities.base.AuthenticatedActivity;
import com.divetym.dive.fragments.CreateTripFragment;
import com.divetym.dive.fragments.SearchListFragment;
import com.divetym.dive.fragments.TripListFragment;
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
    @BindView(R.id.fab_add)
    FloatingActionButton mFabAdd;

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

        mDateRangeLayout.setContext(this);
        mDateRangeLayout.setOnRefreshTripListener(this);

        mStartDate = mDateRangeLayout.getStartCalendar().getTime();
        mEndDate = mDateRangeLayout.getEndCalendar().getTime();

        mFragment = initFragment(R.id.content, TripListFragment.getInstance(mStartDate, mEndDate));


        mFabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(CreateTripActivity.class);
            }
        });
        SearchListFragment searchListFragment = new SearchListFragment();
        mSearchViewLayout.setExpandedContentFragment(this, searchListFragment);
        mSearchViewLayout.setHint(getString(R.string.hint_select_dive_site));
        mSearchViewLayout.handleToolbarAnimation(getToolbar());
        mSearchViewLayout.setSearchListener(searchListFragment);
        mSearchViewLayout.setSearchClearOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSearchViewLayout.showSearchClearIcon(false);
                mSelectedDiveSite = null;
                mSearchViewLayout.setHint(getString(R.string.hint_select_dive_site));
                refreshTripList();
            }
        });
        mSearchViewLayout.setOnToggleAnimationListener(new SearchViewLayout.OnToggleAnimationListener() {
            @Override
            public void onStart(boolean expanding) {
                if (expanding) {
                    mFabAdd.hide();
                    if (mSelectedDiveSite != null)
                        mSearchViewLayout.setExpandedText(mSelectedDiveSite.getName());
                } else {
                    mFabAdd.show();
                }
            }

            @Override
            public void onFinish(boolean expanded) {

            }
        });
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
        mSearchViewLayout.showSearchClearIcon(true);
        mSelectedDiveSite = diveSite;
        mSearchViewLayout.collapse();
        mSearchViewLayout.setCollapsedHint(diveSite.getName());
        refreshTripList();
    }

    private void refreshTripList() {
        if (mFragment != null) {
            mFragment.refreshTripList(mSelectedDiveSite, mStartDate, mEndDate);
        }
    }

}
