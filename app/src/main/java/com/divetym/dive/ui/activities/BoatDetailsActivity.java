package com.divetym.dive.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.divetym.dive.GlideApp;
import com.divetym.dive.R;
import com.divetym.dive.event.BoatEvent;
import com.divetym.dive.models.Boat;
import com.divetym.dive.models.common.ThumbnailEntity;
import com.divetym.dive.ui.activities.base.DetailsActivity;
import com.divetym.dive.ui.activities.base.DiveTymActivity;
import com.divetym.dive.ui.fragments.diver.DiverBoatDetailsFragment;
import com.divetym.dive.ui.view.ImageSlider;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.divetym.dive.ui.activities.AddBoatActivity.EXTRA_BOAT;

/**
 * Created by kali_root on 4/21/2017.
 */

public class BoatDetailsActivity extends DiveTymActivity {

    private static final String TAG = BoatDetailsActivity.class.getSimpleName();
    @BindView(R.id.image_slider)
    ImageSlider mImageSlider;

    public static void launch(DiveTymActivity context, Boat boat) {
        Intent intent = new Intent(context, BoatDetailsActivity.class);
        intent.putExtra(EXTRA_BOAT, boat);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_boat_details);
        ButterKnife.bind(this);
        showBackButton(true);
        setTitle("");
        Boat boat = getIntent().getParcelableExtra(EXTRA_BOAT);
        initFragment(R.id.content, DiverBoatDetailsFragment.newInstance(boat));
        List<ThumbnailEntity> imgs = new ArrayList<>();
        imgs.add(new ThumbnailEntity("", boat
                .getImageUrl()));
        mImageSlider.setDataList(imgs);
    }
}
