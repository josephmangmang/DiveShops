package com.divetym.dive.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.divetym.dive.R;
import com.divetym.dive.ui.activities.base.AuthenticatedActivity;
import com.divetym.dive.ui.activities.base.DiveTymActivity;
import com.divetym.dive.ui.fragments.AddBoatFragment;
import com.divetym.dive.ui.fragments.base.DiveTymFragment;
import com.divetym.dive.models.Boat;

/**
 * Created by kali_root on 6/4/2017.
 */

public class AddBoatActivity extends AuthenticatedActivity {
    public static final String EXTRA_BOAT = "com.divetym.dive.EXTRA_BOAT";

    public static void launch(DiveTymActivity context, Boat boat, int requestCode) {
        Intent intent = new Intent(context, AddBoatActivity.class);
        intent.putExtra(EXTRA_BOAT, boat);
        context.startActivityForResult(intent, requestCode);
    }
    public static void launch(DiveTymFragment context, Boat boat, int requestCode) {
        Intent intent = new Intent(context.getActivity(), AddBoatActivity.class);
        intent.putExtra(EXTRA_BOAT, boat);
        context.startActivityForResult(intent, requestCode);
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
