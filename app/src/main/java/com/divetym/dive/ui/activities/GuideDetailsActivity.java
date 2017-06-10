package com.divetym.dive.ui.activities;

import android.content.Intent;
import android.util.Log;

import com.divetym.dive.GlideApp;
import com.divetym.dive.R;
import com.divetym.dive.event.GuideEvent;
import com.divetym.dive.ui.activities.base.DetailsActivity;
import com.divetym.dive.ui.activities.base.DiveTymActivity;
import com.divetym.dive.models.Guide;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by kali_root on 6/4/2017.
 */

public class GuideDetailsActivity extends DetailsActivity<Guide> {

    private static final String TAG = GuideDetailsActivity.class.getSimpleName();

    public static void launch(DiveTymActivity context, Guide guide) {
        Intent intent = new Intent(context, GuideDetailsActivity.class);
        intent.putExtra(EXTRA_DATA, guide);
        context.startActivity(intent);
    }


    @Override
    protected void onFabClicked(boolean isDiveShop) {
        if (isDiveShop) {
            // edit
            AddGuideActivity.launch(this, mData, REQUEST_UPDATE_DETAILS);
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
    public void onDataChanged(GuideEvent guideEvent) {
        Log.d(TAG, "onDataChanged");
        setData(guideEvent.getGuide());
    }

    @Override
    protected void setData(Guide guide) {
        mData = guide;
        if (mData != null) {
            setToolbarTitle(mData.getName());
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
