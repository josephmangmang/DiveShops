package com.divetym.dive.activities;

import android.content.Intent;

import com.divetym.dive.R;
import com.divetym.dive.activities.base.DetailsActivity;
import com.divetym.dive.activities.base.DiveTymActivity;
import com.divetym.dive.models.DiveShopCourse;
import com.squareup.picasso.Picasso;

/**
 * Created by kali_root on 4/15/2017.
 */

public class CourseDetailsActivity extends DetailsActivity {
    private static final String TAG = CourseDetailsActivity.class.getSimpleName();

    public static void launch(DiveTymActivity context, DiveShopCourse course) {
        Intent intent = new Intent(context, CourseDetailsActivity.class);
        intent.putExtra(EXTRA_DATA, course);
        context.startActivity(intent);
    }

    @Override
    protected void setData() {
        DiveShopCourse course = getIntent().getParcelableExtra(EXTRA_DATA);
        if (course != null) {
            setToolbarTitle(course.getName());
            setToolbarSubtitle(course.getPrice().toString());
            tvBody.setText(course.getDescription());
            Picasso.with(this)
                    .load(course.getImageUrl())
                    .placeholder(R.drawable.dummy_image_preview)
                    .error(R.drawable.dummy_image_error)
                    .into(ivToolbarBackground);
        }
    }
}
