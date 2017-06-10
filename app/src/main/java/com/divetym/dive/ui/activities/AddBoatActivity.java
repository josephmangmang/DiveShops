package com.divetym.dive.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.divetym.dive.R;
import com.divetym.dive.models.Boat;
import com.divetym.dive.ui.activities.base.AuthenticatedActivity;
import com.divetym.dive.ui.activities.base.DiveTymActivity;
import com.divetym.dive.ui.fragments.AddBoatFragment;

/**
 * Created by kali_root on 6/4/2017.
 */

public class AddBoatActivity extends AuthenticatedActivity {
    public static final String EXTRA_BOAT = "com.divetym.dive.EXTRA_BOAT";

    public static void launch(DiveTymActivity context, Boat boat) {
        Intent intent = new Intent(context, AddBoatActivity.class);
        intent.putExtra(EXTRA_BOAT, boat);
        context.startActivity(intent);
    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);
        showBackButton(true);
        Boat boat = getIntent().getParcelableExtra(EXTRA_BOAT);
        initFragment(R.id.content, AddBoatFragment.getInstance(boat));
    }
}
