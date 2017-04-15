package com.divetym.dive.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.divetym.dive.R;
import com.divetym.dive.activities.CourseDetailsActivity;
import com.divetym.dive.activities.base.DiveTymFragment;
import com.divetym.dive.models.DiveShopCourse;
import com.divetym.dive.view.RobotoTextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by kali_root on 4/13/2017.
 */

public class CourseDetailsFragment extends DiveTymFragment {
    private static final String BUNDLE_COURSE = "bundle_course";
    public static final String TAG = CourseDetailsFragment.class.getSimpleName();
    @BindView(R.id.text_body)
    RobotoTextView tvBody;
    private DiveShopCourse mCourse;

    public static CourseDetailsFragment getInstance(DiveShopCourse course) {
        CourseDetailsFragment fragment = new CourseDetailsFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(BUNDLE_COURSE, course);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null) {
            mCourse = args.getParcelable(BUNDLE_COURSE);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_course_details, container, false);
        ButterKnife.bind(this, view);
        setData();
        return view;
    }

    private void setData() {
        if (mCourse != null) {
            ((CourseDetailsActivity) mContext).setToolbarTitle(mCourse.getName());
            ((CourseDetailsActivity) mContext).setToolbarSubtitle(mCourse.getPrice().toString());
            tvBody.setText(mCourse.getDescription());
        }
    }
}
