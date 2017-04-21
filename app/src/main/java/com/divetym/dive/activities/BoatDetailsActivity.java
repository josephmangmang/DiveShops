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

    public static void launch(DiveTymActivity context, Boat course) {
        Intent intent = new Intent(context, BoatDetailsActivity.class);
        intent.putExtra(EXTRA_DATA, course);
        context.startActivity(intent);
    }
    @Override
    protected void setData() {
        Boat boat = getIntent().getParcelableExtra(EXTRA_DATA);
        if (boat != null) {
            setToolbarTitle(boat.getName());
            tvBody.setText(boat.getDescription());
            Picasso.with(this)
                    .load(boat.getImageUrl())
                    .placeholder(R.drawable.dummy_image_preview)
                    .error(R.drawable.dummy_image_error)
                    .into(ivToolbarBackground);
        }
    }
}
