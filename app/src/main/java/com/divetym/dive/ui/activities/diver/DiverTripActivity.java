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
import com.divetym.dive.common.SessionManager;
import com.divetym.dive.event.SessionEvent;
import com.divetym.dive.ui.activities.base.DiveTymActivity;
import com.divetym.dive.ui.fragments.diver.DiverTripFragment;
import com.divetym.dive.ui.view.TripFilterLayout;
import com.divetym.dive.utils.DateUtils;
import com.google.android.gms.maps.model.LatLng;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

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
    private MenuItem loginItem;

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

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    private void initializeNavigation() {
        navigationView.inflateMenu(R.menu.drawer_login);
        loginItem = navigationView.getMenu().findItem(R.id.nav_login);
        invalidateLoginButton();
        mDrawerToggle = new ActionBarDrawerToggle(
                this, drawerLayout, getToolbar(), R.string.drawer_open, R.string.drawer_close);
        drawerLayout.addDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void invalidateLoginButton() {
        if (SessionManager.getInstance(this).isLogin()) {
            loginItem.setTitle(R.string.title_logout);
            //loginItem.setIcon(R.drawable.logout); // TODO: 6/16/2017 change loginItem icon
        } else {
            loginItem.setTitle(R.string.title_login);
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        drawerLayout.closeDrawers();
        switch (item.getItemId()) {
            case R.id.nav_login:
                if (SessionManager.getInstance(this).isLogin()) {
                    logOut();
                } else {
                    logIn();
                }
                invalidateLoginButton();
                break;
            case R.id.nav_dive_shops:
                startActivity(DiveShopListActivity.class);
                break;
        }
        return true;
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onSessionChange(SessionEvent event) {
        EventBus.getDefault().removeStickyEvent(event);
        invalidateLoginButton();
    }
}
