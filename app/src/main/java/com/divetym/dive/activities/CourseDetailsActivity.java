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
    private DiveShopCourse mCourse;

    public static void launch(DiveTymActivity context, DiveShopCourse course) {
        Intent intent = new Intent(context, CourseDetailsActivity.class);
        intent.putExtra(EXTRA_DATA, course);
        context.startActivity(intent);
    }

    @Override
    protected void onFabClicked(boolean isDiveShop) {
        if(isDiveShop){
            // edit
            AddCourseActivity.launch(this, mCourse, REQUEST_UPDATE_DETAILS);
        }else{
            // do something useful
        }
    }

    @Override
    protected void setData() {
         mCourse = getIntent().getParcelableExtra(EXTRA_DATA);
        if (mCourse != null) {
            setToolbarTitle(mCourse.getName());
            setToolbarSubtitle(mCourse.getPrice().toString());
            mBodyText.setText(mCourse.getDescription());
            Picasso.with(this)
                    .load(mCourse.getImageUrl())
                    .placeholder(R.drawable.dummy_image_preview)
                    .error(R.drawable.dummy_image_error)
                    .into(mToolbarBackgroundImage);
        }
    }
}
