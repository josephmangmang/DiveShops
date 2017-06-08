package com.divetym.dive.activities;

import android.content.Intent;

import com.divetym.dive.GlideApp;
import com.divetym.dive.R;
import com.divetym.dive.activities.base.DetailsActivity;
import com.divetym.dive.activities.base.DiveTymActivity;
import com.divetym.dive.models.Guide;

/**
 * Created by kali_root on 6/4/2017.
 */

public class GuideDetailsActivity extends DetailsActivity {
    private Guide mGuide;

    public static void launch(DiveTymActivity context, Guide guide) {
        Intent intent = new Intent(context, GuideDetailsActivity.class);
        intent.putExtra(EXTRA_DATA, guide);
        context.startActivity(intent);
    }


    @Override
    protected void onFabClicked(boolean isDiveShop) {
        if (isDiveShop) {
            // edit
            AddGuideActivity.launch(this, mGuide, REQUEST_UPDATE_DETAILS);
        } else {
            // do something useful
        }
    }

    @Override
    protected void setData() {
        mGuide = getIntent().getParcelableExtra(EXTRA_DATA);
        if (mGuide != null) {
            setToolbarTitle(mGuide.getName());
            mBodyText.setText(mGuide.getDescription());
            GlideApp.with(this)
                    .load(mGuide.getImageUrl())
                    .thumbnail(0.1f)
                    .placeholder(R.drawable.dummy_image_preview)
                    .error(R.drawable.dummy_image_error)
                    .into(mToolbarBackgroundImage);
        }
    }
}
