package com.divetym.dive.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.divetym.dive.GlideApp;
import com.divetym.dive.R;
import com.divetym.dive.event.GuideEvent;
import com.divetym.dive.models.common.ThumbnailEntity;
import com.divetym.dive.ui.activities.base.DetailsActivity;
import com.divetym.dive.ui.activities.base.DiveTymActivity;
import com.divetym.dive.models.Guide;
import com.divetym.dive.ui.fragments.diver.DiverGuideDetailsFragment;
import com.divetym.dive.ui.view.ImageSlider;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.divetym.dive.ui.activities.AddGuideActivity.EXTRA_GUIDE;

/**
 * Created by kali_root on 6/4/2017.
 */

public class GuideDetailsActivity extends DiveTymActivity {

    private static final String TAG = GuideDetailsActivity.class.getSimpleName();

    @BindView(R.id.image_slider)
    ImageSlider mImageSlider;

    public static void launch(DiveTymActivity context, Guide guide) {
        Intent intent = new Intent(context, GuideDetailsActivity.class);
        intent.putExtra(EXTRA_GUIDE, guide);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_boat_details);
        ButterKnife.bind(this);
        showBackButton(true);
        setTitle("");
        Guide guide = getIntent().getParcelableExtra(EXTRA_GUIDE);
        initFragment(R.id.content, DiverGuideDetailsFragment.newInstance(guide));
        List<ThumbnailEntity> imgs = new ArrayList<>();
        imgs.add(new ThumbnailEntity("", guide
                .getImageUrl()));
        mImageSlider.setDataList(imgs);
    }
}
