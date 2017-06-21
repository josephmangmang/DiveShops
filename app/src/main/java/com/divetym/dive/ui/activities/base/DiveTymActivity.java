package com.divetym.dive.ui.activities.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.divetym.dive.R;
import com.divetym.dive.common.SessionManager;
import com.divetym.dive.models.User;
import com.divetym.dive.ui.activities.DiveShopActivity;
import com.divetym.dive.ui.activities.LoginActivity;
import com.divetym.dive.ui.activities.diver.DailyTripSearchActivity;

/**
 * Created by kali_root on 3/25/2017.
 */

public class DiveTymActivity extends AppCompatActivity {
    private Toolbar mToolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void reloadToolbar() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        if (mToolbar == null) {
            throw new RuntimeException("Toolbar not found in Base Activity layout");
        }
        setSupportActionBar(mToolbar);
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        reloadToolbar();
    }

    @Override
    public void setContentView(View view) {
        super.setContentView(view);
        reloadToolbar();
    }

    @Override
    public void setContentView(View view, ViewGroup.LayoutParams params) {
        super.setContentView(view, params);
        reloadToolbar();
    }

    protected <T extends Fragment> T initFragment(@IdRes int target, @NonNull T fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(target, fragment, fragment.getClass().getSimpleName())
                .commit();
        return fragment;
    }

    protected void startActivity(Class<?> cls) {
        startActivity(new Intent(this, cls));
    }

    protected void showBackButton(boolean show) {
        getSupportActionBar().setDisplayHomeAsUpEnabled(show);
    }

    public Toolbar getToolbar() {
        if (mToolbar == null) {
            mToolbar = (Toolbar) findViewById(R.id.toolbar);
        }
        return mToolbar;
    }

    public void setTitle(String title) {
        super.setTitle(title);
        getSupportActionBar().setTitle(title);
    }

    public void setTitle(@StringRes int title) {
        super.setTitle(title);
        getSupportActionBar().setTitle(title);
    }

    public void setSubtitle(String subtitle) {
        getSupportActionBar().setSubtitle(subtitle);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void switchScreen() {
        if (SessionManager.getInstance(this).isLogin()) {
            if (SessionManager.getInstance(this).getAccountType() == User.AccountType.Dive_Shop) {
                startActivity(new Intent(this, DiveShopActivity.class));
            } else {
                startActivity(new Intent(this, DailyTripSearchActivity.class));
            }
            finish();
        }
    }

    public void logOut() {
        SessionManager.getInstance(this).logout();
    }

    public void logIn() {
        startActivity(LoginActivity.class);
    }
}
