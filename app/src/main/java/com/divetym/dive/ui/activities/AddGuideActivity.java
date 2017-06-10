package com.divetym.dive.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.divetym.dive.R;
import com.divetym.dive.ui.activities.base.AuthenticatedActivity;
import com.divetym.dive.ui.activities.base.DiveTymActivity;
import com.divetym.dive.ui.fragments.AddGuideFragment;
import com.divetym.dive.ui.fragments.base.DiveTymFragment;
import com.divetym.dive.models.Guide;

/**
 * Created by kali_root on 6/4/2017.
 */

public class AddGuideActivity extends AuthenticatedActivity{
    public static final String EXTRA_GUIDE = "com.divetym.dive.EXTRA_GUIDE";

    public static void launch(DiveTymActivity context, Guide guide) {
        Intent intent = new Intent(context, AddGuideActivity.class);
        intent.putExtra(EXTRA_GUIDE, guide);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);
        showBackButton(true);
        Guide guide = getIntent().getParcelableExtra(EXTRA_GUIDE);
        initFragment(R.id.content, AddGuideFragment.getInstance(guide));
    }
}
