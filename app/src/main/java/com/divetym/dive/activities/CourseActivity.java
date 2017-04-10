package com.divetym.dive.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.divetym.dive.R;
import com.divetym.dive.activities.base.AuthenticatedActivity;

/**
 * Created by kali_root on 4/9/2017.
 */

public class CourseActivity extends AuthenticatedActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }
}
