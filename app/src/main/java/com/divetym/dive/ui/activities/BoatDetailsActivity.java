package com.divetym.dive.ui.activities;

import android.content.Intent;
import android.util.Log;

import com.divetym.dive.GlideApp;
import com.divetym.dive.R;
import com.divetym.dive.event.BoatEvent;
import com.divetym.dive.models.Boat;
import com.divetym.dive.ui.activities.base.DetailsActivity;
import com.divetym.dive.ui.activities.base.DiveTymActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by kali_root on 4/21/2017.
 */

public class BoatDetailsActivity extends DetailsActivity<Boat> {

    private static final String TAG = BoatDetailsActivity.class.getSimpleName();

    public static void launch(DiveTymActivity context, Boat boat) {
        Intent intent = new Intent(context, BoatDetailsActivity.class);
        intent.putExtra(EXTRA_DATA, boat);
        context.startActivity(intent);
    }

    @Override
    protected void onFabClicked(boolean isDiveShop) {
        if (isDiveShop) {
            AddBoatActivity.launch(this, mData);
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
    public void onDataChanged(BoatEvent boatEvent) {
        Log.d(TAG, "onDataChanged");
        setData(boatEvent.getBoat());
    }

    @Override
    protected void setData(Boat data) {
        mData = data;
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
