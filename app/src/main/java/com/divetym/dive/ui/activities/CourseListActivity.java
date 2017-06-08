package com.divetym.dive.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.util.Log;

import com.divetym.dive.R;
import com.divetym.dive.ui.activities.base.AuthenticatedActivity;
import com.divetym.dive.ui.activities.base.DiveTymActivity;
import com.divetym.dive.ui.fragments.CourseListFragment;
import com.divetym.dive.models.DiveShopCourse;

import java.util.ArrayList;
import java.util.List;

import static com.divetym.dive.ui.fragments.base.DiveTymListFragment.EXTRA_ADD_BUTTON;
import static com.divetym.dive.ui.fragments.base.DiveTymListFragment.EXTRA_LIST;

/**
 * Created by kali_root on 4/9/2017.
 */

public class CourseListActivity extends AuthenticatedActivity {

    private static final String TAG = CourseListActivity.class.getSimpleName();

    public static void launch(DiveTymActivity context, @Nullable List<DiveShopCourse> courses, boolean showFab) {
        Log.d(TAG, "lauching...");
        Intent intent = new Intent(context, CourseListActivity.class);
        intent.putParcelableArrayListExtra(EXTRA_LIST, (ArrayList<? extends Parcelable>) courses);
        intent.putExtra(EXTRA_ADD_BUTTON, showFab);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        showBackButton(true);
        initFragment(R.id.content, CourseListFragment.getInstance(getIntent().getExtras()));
    }
}
