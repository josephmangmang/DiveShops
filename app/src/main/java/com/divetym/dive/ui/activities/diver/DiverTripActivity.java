package com.divetym.dive.ui.activities.diver;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;

import com.divetym.dive.R;
import com.divetym.dive.ui.activities.base.AuthenticatedActivity;
import com.divetym.dive.ui.activities.base.DiveTymActivity;
import com.divetym.dive.ui.fragments.diver.DiverTripFragment;
import com.divetym.dive.ui.view.TripFilterLayout;
import com.divetym.dive.utils.DateUtils;
import com.google.android.gms.maps.model.LatLng;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by kali_root on 6/10/2017.
 */

public class DiverTripActivity extends DiveTymActivity implements NavigationView.OnNavigationItemSelectedListener {
    @BindView(R.id.trip_filter_layout)
    TripFilterLayout mTripFilterLayout;
    @BindView(R.id.collapsing_toolbar_layout)
    CollapsingToolbarLayout collapsingToolbarLayout;
    @BindView(R.id.navigation_view)
    NavigationView navigationView;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle mDrawerToggle;
    DiverTripFragment fragment;

    private TripFilterLayout.OnFilterChangeListener onFilterChangeListener = (startDate, endDate, locationAddress, diveSite) -> {
        fragment.refreshTripList(
                DateUtils.formatServerDate(startDate),
                DateUtils.formatServerDate(endDate),
                locationAddress != null ? locationAddress.getLatLng() : new LatLng(0, 0),
                diveSite != null ? diveSite.getDiveSiteId() : -1);
    };


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diver_trip_activity);
        ButterKnife.bind(this);
        fragment = initFragment(R.id.content, new DiverTripFragment());
        fragment.refreshTripList(
                DateUtils.formatServerDate(mTripFilterLayout.getStartDate()),
                DateUtils.formatServerDate(mTripFilterLayout.getEndDate()),
                new LatLng(0, 0),
                -1);
        initializeNavigation();
        mTripFilterLayout.setOnFilterChangeListener(onFilterChangeListener);
    }

    private void initializeNavigation() {
        navigationView.inflateMenu(R.menu.drawer_login);
        mDrawerToggle = new ActionBarDrawerToggle(
                this, drawerLayout, getToolbar(), R.string.drawer_open, R.string.drawer_close);
        drawerLayout.addDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_logout:
                logOut();
                break;
        }
        return true;
    }

}
