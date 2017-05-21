package com.divetym.dive.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.util.Log;

import com.divetym.dive.R;
import com.divetym.dive.activities.base.AuthenticatedActivity;
import com.divetym.dive.activities.base.DiveTymActivity;
import com.divetym.dive.fragments.BoatListFragment;
import com.divetym.dive.fragments.CourseListFragment;
import com.divetym.dive.models.Boat;
import com.divetym.dive.models.DiveShopCourse;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kali_root on 4/15/2017.
 */

public class BoatListActivity extends AuthenticatedActivity {

    private static final String TAG = BoatListActivity.class.getSimpleName();
    public static final String EXTRA_BOATS = "com.divetym.dive.EXTRA_BOATS";

    public static<Data extends Boat> void launch(DiveTymActivity context, @Nullable List<Data> courses) {
        Log.d(TAG, "lauching...");
        Intent intent = new Intent(context, BoatListActivity.class);
        intent.putParcelableArrayListExtra(EXTRA_BOATS, (ArrayList<? extends Parcelable>) courses);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        showBackButton(true);
        ArrayList<Boat> previewBoats = getIntent().getParcelableArrayListExtra(EXTRA_BOATS);
        initFragment(R.id.content, BoatListFragment.getInstance(previewBoats));
    }
}
