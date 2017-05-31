package com.divetym.dive.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.divetym.dive.R;
import com.divetym.dive.activities.base.AuthenticatedActivity;
import com.divetym.dive.fragments.AddCourseFragment;

/**
 * Created by kali_root on 5/25/2017.
 */

public class AddCourseActivity extends AuthenticatedActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);
        showBackButton(true);
        initFragment(R.id.content, new AddCourseFragment());
    }
}
