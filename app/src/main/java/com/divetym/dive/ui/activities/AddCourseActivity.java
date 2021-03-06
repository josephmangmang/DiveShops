package com.divetym.dive.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.divetym.dive.R;
import com.divetym.dive.models.DiveShopCourse;
import com.divetym.dive.ui.activities.base.AuthenticatedActivity;
import com.divetym.dive.ui.activities.base.DiveTymActivity;
import com.divetym.dive.ui.fragments.AddCourseFragment;

/**
 * Created by kali_root on 5/25/2017.
 */

public class AddCourseActivity extends AuthenticatedActivity {

    public static final String EXTRA_COURSE = "com.divetym.dive.EXTRA_COURSE";

    public static void launch(DiveTymActivity context, DiveShopCourse course) {
        Intent intent = new Intent(context, AddCourseActivity.class);
        intent.putExtra(EXTRA_COURSE, course);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);
        showBackButton(true);
        DiveShopCourse course = getIntent().getParcelableExtra(EXTRA_COURSE);
        initFragment(R.id.content, AddCourseFragment.getInstance(course));
    }
}
