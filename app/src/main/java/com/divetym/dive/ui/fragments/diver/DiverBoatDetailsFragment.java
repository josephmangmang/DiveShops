package com.divetym.dive.ui.fragments.diver;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.divetym.dive.R;
import com.divetym.dive.models.Boat;
import com.divetym.dive.models.common.ThumbnailEntity;
import com.divetym.dive.ui.fragments.base.DiveTymFragment;
import com.divetym.dive.ui.view.ImageSlider;
import com.divetym.dive.ui.view.InfoLayout;
import com.divetym.dive.ui.view.RobotoTextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.divetym.dive.ui.activities.AddBoatActivity.EXTRA_BOAT;

/**
 * Created by kali_root on 6/16/2017.
 */

public class DiverBoatDetailsFragment extends DiveTymFragment {

    @BindView(R.id.text_name)
    RobotoTextView nameText;
    @BindView(R.id.info_layout_description)
    InfoLayout description;
    @BindView(R.id.info_layout_additional_information)
    InfoLayout additionalInfo;

    private Boat mBoat;

    public static DiverBoatDetailsFragment newInstance(Boat boat) {
        Bundle args = new Bundle();
        args.putParcelable(EXTRA_BOAT, boat);
        DiverBoatDetailsFragment fragment = new DiverBoatDetailsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBoat = getArguments().getParcelable(EXTRA_BOAT);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_boat_details, container, false);
        ButterKnife.bind(this, view);
        setBoat(mBoat);
        return view;
    }

    private void setBoat(Boat boat) {
        mBoat = boat;
        if (mBoat == null) return;
        nameText.setText(mBoat.getName());
        description.setInfoBody(mBoat.getDescription());
        //additionalInfo.setInfoTitle("");// TODO: 6/16/2017 add additional info and list of boat image on boat field
    }
}
