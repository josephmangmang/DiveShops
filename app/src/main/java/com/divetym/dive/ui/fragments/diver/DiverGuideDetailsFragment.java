package com.divetym.dive.ui.fragments.diver;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.divetym.dive.R;
import com.divetym.dive.models.Guide;
import com.divetym.dive.ui.fragments.base.DiveTymFragment;
import com.divetym.dive.ui.view.InfoLayout;
import com.divetym.dive.ui.view.RobotoTextView;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.divetym.dive.ui.activities.AddGuideActivity.EXTRA_GUIDE;

/**
 * Created by kali_root on 6/16/2017.
 */

public class DiverGuideDetailsFragment extends DiveTymFragment {
    @BindView(R.id.text_name)
    RobotoTextView nameText;
    @BindView(R.id.info_layout_about_me)
    InfoLayout aboutMe;

    private Guide mGuide;

    public static DiverGuideDetailsFragment newInstance(Guide guide) {
        Bundle args = new Bundle();
        args.putParcelable(EXTRA_GUIDE, guide);
        DiverGuideDetailsFragment fragment = new DiverGuideDetailsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mGuide = getArguments().getParcelable(EXTRA_GUIDE);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_guide_details, container, false);
        ButterKnife.bind(this, view);
        setGuide(mGuide);
        return view;
    }

    public void setGuide(Guide guide) {
        mGuide = guide;
        if (mGuide != null) {
            nameText.setText(mGuide.getName());
            aboutMe.setInfoBody(mGuide.getDescription());
        }
    }
}
