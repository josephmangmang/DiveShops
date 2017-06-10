package com.divetym.dive.ui.activities;

import android.content.Intent;
import android.util.Log;

import com.divetym.dive.GlideApp;
import com.divetym.dive.R;
import com.divetym.dive.event.DiveShopCourseEvent;
import com.divetym.dive.ui.activities.base.DetailsActivity;
import com.divetym.dive.ui.activities.base.DiveTymActivity;
import com.divetym.dive.models.DiveShopCourse;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by kali_root on 4/15/2017.
 */

public class CourseDetailsActivity extends DetailsActivity<DiveShopCourse> {
    private static final String TAG = CourseDetailsActivity.class.getSimpleName();

    public static void launch(DiveTymActivity context, DiveShopCourse course) {
        Intent intent = new Intent(context, CourseDetailsActivity.class);
        intent.putExtra(EXTRA_DATA, course);
        context.startActivity(intent);
    }

    @Override
    protected void onFabClicked(boolean isDiveShop) {
        if (isDiveShop) {
            // edit
            AddCourseActivity.launch(this, mData);
        } else {
            // do something useful
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onDataChanged(DiveShopCourseEvent diveShopCourseEvent) {
        Log.d(TAG, "onDataChanged");
        setData(diveShopCourseEvent.getDiveShopCourse());
    }

    @Override
    protected void setData(DiveShopCourse data) {
        mData = data;
        if (mData != null) {
            setToolbarTitle(mData.getName());
            setToolbarSubtitle(mData.getPrice().toString());
            detailBody.setText(mData.getDescription());
            GlideApp.with(this)
                    .load(mData.getImageUrl())
                    .thumbnail(0.1f)
                    .placeholder(R.drawable.dummy_image_preview)
                    .error(R.drawable.dummy_image_error)
                    .into(toolbarBackgroundImage);
        }
    }

}
