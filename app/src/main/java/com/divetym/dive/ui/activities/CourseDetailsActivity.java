package com.divetym.dive.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.divetym.dive.R;
import com.divetym.dive.models.DiveShopCourse;
import com.divetym.dive.models.common.ThumbnailEntity;
import com.divetym.dive.ui.activities.base.DiveTymActivity;
import com.divetym.dive.ui.fragments.diver.DiverCourseDetailsFragment;
import com.divetym.dive.ui.view.ImageSlider;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.divetym.dive.ui.activities.AddCourseActivity.EXTRA_COURSE;

/**
 * Created by kali_root on 4/15/2017.
 */

public class CourseDetailsActivity extends DiveTymActivity{
    private static final String TAG = CourseDetailsActivity.class.getSimpleName();
    @BindView(R.id.image_slider)
    ImageSlider mImageSlider;
    public static void launch(DiveTymActivity context, DiveShopCourse course) {
        Intent intent = new Intent(context, CourseDetailsActivity.class);
        intent.putExtra(EXTRA_COURSE, course);
        context.startActivity(intent);
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_boat_details);
        ButterKnife.bind(this);
        showBackButton(true);
        setTitle("");
        DiveShopCourse course = getIntent().getParcelableExtra(EXTRA_COURSE);
        initFragment(R.id.content, DiverCourseDetailsFragment.newInstance(course));
        List<ThumbnailEntity> imgs = new ArrayList<>();
        imgs.add(new ThumbnailEntity("", course
                .getImageUrl()));
        mImageSlider.setDataList(imgs);
    }
}
