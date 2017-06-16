package com.divetym.dive.ui.fragments.diver;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.divetym.dive.R;
import com.divetym.dive.models.DiveShopCourse;
import com.divetym.dive.ui.fragments.base.DiveTymFragment;
import com.divetym.dive.ui.view.InfoLayout;
import com.divetym.dive.ui.view.RobotoTextView;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.divetym.dive.ui.activities.AddCourseActivity.EXTRA_COURSE;

/**
 * Created by kali_root on 6/16/2017.
 */

public class DiverCourseDetailsFragment extends DiveTymFragment {
    @BindView(R.id.text_name)
    RobotoTextView nameText;
    @BindView(R.id.text_offered_by)
    RobotoTextView offeredByText;
    @BindView(R.id.text_price)
    RobotoTextView priceText;
    @BindView(R.id.info_layout_what_you_learn)
    InfoLayout whatYouLearn;
    @BindView(R.id.info_layout_who_should_take)
    InfoLayout whoShouldTake;
    @BindView(R.id.info_layout_what_gear_needed)
    InfoLayout whatGearNeeded;

    private DiveShopCourse mCourse;

    public static DiverCourseDetailsFragment newInstance(DiveShopCourse course) {
        Bundle args = new Bundle();
        args.putParcelable(EXTRA_COURSE, course);
        DiverCourseDetailsFragment fragment = new DiverCourseDetailsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCourse = getArguments().getParcelable(EXTRA_COURSE);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_course_details, container, false);
        ButterKnife.bind(this, view);
        setCourse(mCourse);
        return view;
    }

    private void setCourse(DiveShopCourse course) {
        mCourse = course;
        if (mCourse != null) {
            nameText.setText(mCourse.getName());
            priceText.setText("PHP" + mCourse.getPrice().toString());
            offeredByText.setText(mCourse.getOfferedBy());
            whatYouLearn.setInfoBody(mCourse.getDescription());
        }
    }
}
