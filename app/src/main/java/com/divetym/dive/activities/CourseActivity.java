package com.divetym.dive.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.util.Log;

import com.divetym.dive.R;
import com.divetym.dive.activities.base.AuthenticatedActivity;
import com.divetym.dive.activities.base.DiveTymActivity;
import com.divetym.dive.fragments.CourseListFragment;
import com.divetym.dive.models.DiveShopCourse;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kali_root on 4/9/2017.
 */

public class CourseActivity extends AuthenticatedActivity {

    private static final String TAG = CourseActivity.class.getSimpleName();

    public static void launch(DiveTymActivity context, @Nullable List<DiveShopCourse> courses) {
        Log.d(TAG, "lauching...");
        Intent intent = new Intent(context, CourseActivity.class);
        intent.putParcelableArrayListExtra(DiveShopCourse.TAG, (ArrayList<? extends Parcelable>) courses);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        showBackButton(true);
        ArrayList<DiveShopCourse> previewCourses = getIntent().getParcelableArrayListExtra(DiveShopCourse.TAG);
        initFragment(R.id.content, CourseListFragment.getInstance(previewCourses));
    }
}
