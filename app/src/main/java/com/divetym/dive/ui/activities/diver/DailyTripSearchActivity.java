package com.divetym.dive.ui.activities.diver;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.util.Log;
import android.view.MenuItem;

import com.divetym.dive.BuildConfig;
import com.divetym.dive.R;
import com.divetym.dive.common.SessionManager;
import com.divetym.dive.event.SessionEvent;
import com.divetym.dive.models.response.DiveSiteListResponse;
import com.divetym.dive.rest.ApiClient;
import com.divetym.dive.ui.activities.base.DiveTymActivity;
import com.divetym.dive.ui.view.ImageSlider;
import com.divetym.dive.ui.view.TripFilterLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by kali_root on 6/21/2017.
 */

public class DailyTripSearchActivity extends DiveTymActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = DailyTripSearchActivity.class.getSimpleName();
    public static final String BUNDLE_START_DATE = "bundle_start_date";
    public static final String BUNDLE_END_DATE = "bundle_end_date";
    public static final String BUNDLE_LOCATION = "bundle_location";
    public static final String BUNDLE_DIVE_SITE = "bundle_dive_sited";
    @BindView(R.id.navigation_view)
    NavigationView navigationView;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    @BindView(R.id.image_slider)
    ImageSlider imageSlider;
    @BindView(R.id.trip_filter_layout)
    TripFilterLayout mTripFilterLayout;

    private ActionBarDrawerToggle mDrawerToggle;
    private MenuItem loginItem;

    private TripFilterLayout.OnActionClickListener onActionClickListener = (startDate, endDate, locationAddress, diveSite) -> {
        Bundle bundle = new Bundle();
        bundle.putLong(BUNDLE_START_DATE, startDate != null ? startDate.getTime() : 0);
        bundle.putLong(BUNDLE_END_DATE, endDate != null ? endDate.getTime() : 0);
        bundle.putParcelable(BUNDLE_LOCATION, locationAddress);
        bundle.putParcelable(BUNDLE_DIVE_SITE, diveSite);
        DiverTripActivity.launch(DailyTripSearchActivity.this, bundle);
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diver_trip_search_activity);
        ButterKnife.bind(this);
        mTripFilterLayout.setOnActionClickListener(onActionClickListener);
        initializeNavigation();
        requestImages();
    }

    private void initializeNavigation() {
        navigationView.inflateMenu(R.menu.drawer_login);
        loginItem = navigationView.getMenu().findItem(R.id.nav_login);
        if (BuildConfig.DEBUG) {
            navigationView.inflateMenu(R.menu.server_source);
            MenuItem s = navigationView.getMenu().findItem(R.id.nav_server_source);
            boolean serverLocal = PreferenceManager.getDefaultSharedPreferences(this).getBoolean("server_local", true);
            ApiClient.sServerLocal = serverLocal;
            s.setTitle(serverLocal ? "Server Local" : "Server Online");
        }
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

    private void requestImages() {
        ApiClient.getApiInterface().getSites("", 2, 5)
                .enqueue(new Callback<DiveSiteListResponse>() {
                    @Override
                    public void onResponse(Call<DiveSiteListResponse> call, Response<DiveSiteListResponse> response) {
                        DiveSiteListResponse body = response.body();
                        if (body != null && !body.isError()) {
                            imageSlider.setDataList(body.getDivesites());
                        }
                    }

                    @Override
                    public void onFailure(Call<DiveSiteListResponse> call, Throwable t) {
                        if (BuildConfig.DEBUG) {
                            Log.e(TAG, call.request().toString());
                            t.printStackTrace();
                        }
                        Log.e(TAG, "getData onFailiure: " + t.getMessage());
                    }
                });
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
            case R.id.nav_server_source:
                if (item.getTitle().equals("Server Local")) {
                    item.setTitle("Server Online");
                    PreferenceManager.getDefaultSharedPreferences(this)
                            .edit().putBoolean("server_local", false).commit();
                    ApiClient.sServerLocal = false;
                } else {
                    item.setTitle("Server Local");
                    PreferenceManager.getDefaultSharedPreferences(this)
                            .edit().putBoolean("server_local", true).commit();
                    ApiClient.sServerLocal = true;
                }
                ApiClient.reCreate();
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
