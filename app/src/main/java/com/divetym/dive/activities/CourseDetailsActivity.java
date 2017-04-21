package com.divetym.dive.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;

import com.divetym.dive.R;
import com.divetym.dive.activities.base.AuthenticatedActivity;
import com.divetym.dive.activities.base.DiveTymActivity;
import com.divetym.dive.fragments.CourseDetailsFragment;
import com.divetym.dive.models.DiveShopCourse;
import com.divetym.dive.view.RobotoTextView;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by kali_root on 4/15/2017.
 */

public class CourseDetailsActivity extends AuthenticatedActivity {
    public static final String EXTRA_COURSE = "com.divetym.dive.EXTRA_COURSE";
    private static final String TAG = CourseDetailsActivity.class.getSimpleName();
    @BindView(R.id.button_book_now)
    FloatingActionButton fabBuyNow;
    @BindView(R.id.collapsing_toolbar_layout)
    CollapsingToolbarLayout mCollapsingLayout;
    @BindView(R.id.text_subtitle)
    RobotoTextView mToolbarSubtitle;
    @BindView(R.id.text_body)
    RobotoTextView tvBody;
    @BindView(R.id.image_collapsing_toolbar_background)
    ImageView ivToolbarBackground;

    public static void launch(DiveTymActivity context, DiveShopCourse course) {
        Intent intent = new Intent(context, CourseDetailsActivity.class);
        intent.putExtra(EXTRA_COURSE, course);
        context.startActivity(intent);
    }

    @OnClick(R.id.button_book_now)
    public void onBookNowClick() {
        Log.d(TAG, "onBookNowClick");
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_details);
        showBackButton(true);
        ButterKnife.bind(this);
        DiveShopCourse course = getIntent().getParcelableExtra(EXTRA_COURSE);
        if (course != null) {
            setToolbarTitle(course.getName());
            setToolbarSubtitle(course.getPrice().toString());
            tvBody.setText(course.getDescription());
            Picasso.with(this)
                    .load(course.getPhotoCoverUrl())
                    .placeholder(R.drawable.dummy_image_preview)
                    .error(R.drawable.dummy_image_error)
                    .into(ivToolbarBackground);
        }
    }

    public void setToolbarTitle(String title) {
        mCollapsingLayout.setTitle(title);
    }

    public void setToolbarSubtitle(String subtitle) {
        mToolbarSubtitle.setText(subtitle);
    }
}
