package com.divetym.dive.activities;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.divetym.dive.R;
import com.divetym.dive.activities.base.AuthenticatedActivity;
import com.divetym.dive.common.SessionManager;
import com.divetym.dive.fragments.DiveShopFragment;
import com.divetym.dive.view.RobotoTextView;
import com.divetym.dive.view.ToastAlert;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DiveShopActivity extends AuthenticatedActivity implements NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = DiveShopActivity.class.getSimpleName();
    @BindView(R.id.collapsing_toolbar_layout)
    CollapsingToolbarLayout mCollapsingToolbarLayout;
    @BindView(R.id.navigation_view)
    NavigationView mNavigationView;
    @BindView(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;
    ActionBarDrawerToggle mDrawerToggle;
    @BindView(R.id.text_subtitle)
    RobotoTextView mToolbarSubtitle;
    @BindView(R.id.image_collapsing_toolbar_background)
    ImageView mToolbarBackgroundImage;
    private View.OnClickListener mToolbarBackgrounClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            AlertDialog.Builder optionDialog = new AlertDialog.Builder(DiveShopActivity.this);
            optionDialog.setTitle(R.string.dialog_title_change_profile_cover);
            optionDialog.setItems(R.array.change_profile_cover_options, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Log.d(TAG, "user click option " +i);
                    // TODO: 6/7/2017 implement change profile cover image
                    switch (i) {
                        case 0:
                            break;
                        case 1:
                            break;
                        case 2:
                            break;
                    }
                }
            });
            optionDialog.show();

        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dive_shop);
        ButterKnife.bind(this);
        loadScreen();
        initializeNavigation();
        mToolbarBackgroundImage.setOnClickListener(mToolbarBackgrounClickListener);
    }

    private void initializeNavigation() {
        mNavigationView.inflateMenu(R.menu.drawer_login);
        mDrawerToggle = new ActionBarDrawerToggle(
                this, mDrawerLayout, getToolbar(), R.string.drawer_open, R.string.drawer_close);
        mDrawerLayout.addDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();
        mNavigationView.setNavigationItemSelectedListener(this);
    }

    private void loadScreen() {
        switch (SessionManager.getInstance(this).getAccountType()) {
            case Diver:
                // TODO: 4/6/2017 implement diver profile screen
                Log.i(TAG, "loading Diver's screen..");
                break;
            case Dive_Shop:
                Log.i(TAG, "loading Dive_Shop's screen..");
                initFragment(R.id.content, new DiveShopFragment());
                break;
            default:
                new ToastAlert(this)
                        .setMessage(R.string.error_account_not_valid)
                        .show();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_daily_trips:
                startActivity(DailyTripActivity.class);
                break;
            case R.id.nav_courses:
                CourseListActivity.launch(this, null, true);
                break;
            case R.id.nav_boats:
                BoatListActivity.launch(this, null, true);
                break;
            case R.id.nav_guides:
                GuideListActivity.launch(this, null, true);
                break;
            case R.id.nav_logout:
                logOut();
                break;
        }
        mDrawerLayout.closeDrawers();
        return false;
    }

    public void setToolbarTitle(String title) {
        mCollapsingToolbarLayout.setTitle(title);
    }

    public void setToolbarSubtitle(String subtitle) {
        mToolbarSubtitle.setText(subtitle);
    }

    public void setToolbarBackground(String imgUrl) {
        Picasso.with(this)
                .load(imgUrl)
                .placeholder(R.drawable.dummy_image_preview)
                .error(R.drawable.dummy_image_error)
                .into(mToolbarBackgroundImage);
    }
}
