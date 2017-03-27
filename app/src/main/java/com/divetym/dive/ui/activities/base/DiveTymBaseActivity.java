package com.divetym.dive.ui.activities.base;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.divetym.dive.R;

/**
 * Created by kali_root on 3/25/2017.
 */

public class DiveTymBaseActivity extends AppCompatActivity {
    private Toolbar mToolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
    }

    protected <T extends Fragment> T initFragment(@IdRes int target, @NonNull T fragment) {
        getFragmentManager()
                .beginTransaction()
                .replace(target, fragment)
                .commit();
        return fragment;
    }

    protected void showBackButton(boolean show) {
        getSupportActionBar().setDisplayHomeAsUpEnabled(show);
    }

    protected Toolbar getToolbar() {
        return mToolbar;
    }
}
