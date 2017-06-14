package com.divetym.dive.ui.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Menu;

import com.divetym.dive.R;
import com.divetym.dive.event.DailyTripEvent;
import com.divetym.dive.models.DiveSite;
import com.divetym.dive.ui.activities.base.AuthenticatedActivity;
import com.divetym.dive.ui.fragments.SearchListFragment;
import com.divetym.dive.ui.fragments.TripListFragment;
import com.divetym.dive.ui.view.DateRangeLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import xyz.sahildave.widget.SearchViewLayout;

/**
 * Created by kali_root on 4/21/2017.
 */

public class DailyTripActivity extends AuthenticatedActivity implements DateRangeLayout.OnDateRangeChangeListener,
        SearchListFragment.OnDiveSiteChangeListener {
    private static final String TAG = DailyTripActivity.class.getSimpleName();
    public static final int REQUEST_REFRESH = 1;
    @BindView(R.id.search_view_container)
    SearchViewLayout searchViewLayout;
    @BindView(R.id.layout_date_range)
    DateRangeLayout dateRangeLayout;

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

        dateRangeLayout.setContext(this);
        dateRangeLayout.setOnDateRangeChangeListener(this);

        mStartDate = dateRangeLayout.getStartCalendar().getTime();
        mEndDate = dateRangeLayout.getEndCalendar().getTime();

        mFragment = initFragment(R.id.content, TripListFragment.getInstance(mStartDate, mEndDate));


        SearchListFragment searchListFragment = new SearchListFragment();
        searchListFragment.setOnDiveSiteChangeListener(this);

        searchViewLayout.setExpandedContentFragment(this, searchListFragment);
        searchViewLayout.setHint(getString(R.string.hint_select_dive_site));
        searchViewLayout.handleToolbarAnimation(getToolbar());
        searchViewLayout.setSearchListener(searchListFragment);
        searchViewLayout.setSearchClearOnClickListener(view -> {
            searchViewLayout.showSearchClearIcon(false);
            mSelectedDiveSite = null;
            searchViewLayout.setHint(getString(R.string.hint_select_dive_site));
            refreshTripList();
        });
        searchViewLayout.setOnToggleAnimationListener(new SearchViewLayout.OnToggleAnimationListener() {
            @Override
            public void onStart(boolean expanding) {
                if (expanding) {
                    mFragment.showFab(false);
                    if (mSelectedDiveSite != null)
                        searchViewLayout.setExpandedText(mSelectedDiveSite.getName());
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
    protected void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
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
    public void onDateRangeChanged(Calendar startDate, Calendar endDate) {
        mStartDate = startDate.getTime();
        mEndDate = endDate.getTime();
        refreshTripList();
    }
    @Override
    public void onDiveSiteChanged(DiveSite diveSite) {
        searchViewLayout.showSearchClearIcon(true);
        mSelectedDiveSite = diveSite;
        searchViewLayout.collapse();
        searchViewLayout.setCollapsedHint(diveSite.getName());
        refreshTripList();
    }

    private void refreshTripList() {
        if (mFragment != null) {
            mFragment.refreshTripList(mSelectedDiveSite, mStartDate, mEndDate);
        }
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onDailyTripChanged(DailyTripEvent event) {
        Log.d(TAG, "onDailyTripChanged: " + event);
        refreshTripList();
        EventBus.getDefault().removeStickyEvent(event);
    }
}
