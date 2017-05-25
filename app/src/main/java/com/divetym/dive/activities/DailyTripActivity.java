package com.divetym.dive.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.Menu;
import android.view.View;

import com.divetym.dive.R;
import com.divetym.dive.activities.base.AuthenticatedActivity;
import com.divetym.dive.activities.common.Mode;
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
    public static final int REQUEST_REFRESH = 1;
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

        mDateRangeLayout.setContext(this);
        mDateRangeLayout.setOnRefreshTripListener(this);

        mStartDate = mDateRangeLayout.getStartCalendar().getTime();
        mEndDate = mDateRangeLayout.getEndCalendar().getTime();

        mFragment = initFragment(R.id.content, TripListFragment.getInstance(mStartDate, mEndDate));


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
                    mFragment.showFab(false);
                    if (mSelectedDiveSite != null)
                        mSearchViewLayout.setExpandedText(mSelectedDiveSite.getName());
                } else {
                    mFragment.showFab(true);
                }
            }

            @Override
            public void onFinish(boolean expanded) {

            }
        });
    }


    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        menu.clear();
        if (mFragment != null) {
            mFragment.inflateToolbar(menu, getMenuInflater());
        }
        return super.onPrepareOptionsMenu(menu);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_REFRESH && resultCode == RESULT_OK) {
            refreshTripList();
        }
    }
}
