package com.divetym.dive.activities;

import android.content.Intent;

import com.divetym.dive.R;
import com.divetym.dive.activities.base.DetailsActivity;
import com.divetym.dive.activities.base.DiveTymActivity;
import com.divetym.dive.models.Boat;
import com.squareup.picasso.Picasso;

/**
 * Created by kali_root on 4/21/2017.
 */

public class BoatDetailsActivity extends DetailsActivity {

    private Boat mBoat;

    public static void launch(DiveTymActivity context, Boat course) {
        Intent intent = new Intent(context, BoatDetailsActivity.class);
        intent.putExtra(EXTRA_DATA, course);
        context.startActivity(intent);
    }

    @Override
    protected void onFabClicked(boolean isDiveShop) {
        if (isDiveShop) {
            AddBoatActivity.launch(this, mBoat, REQUEST_UPDATE_DETAILS);
        }
    }

    @Override
    protected void setData() {
        mBoat = getIntent().getParcelableExtra(EXTRA_DATA);
        if (mBoat != null) {
            setToolbarTitle(mBoat.getName());
            mBodyText.setText(mBoat.getDescription());
            Picasso.with(this)
                    .load(mBoat.getImageUrl())
                    .placeholder(R.drawable.dummy_image_preview)
                    .error(R.drawable.dummy_image_error)
                    .into(mToolbarBackgroundImage);
        }
    }
}
