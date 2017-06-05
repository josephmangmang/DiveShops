package com.divetym.dive.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.divetym.dive.R;
import com.divetym.dive.activities.base.AuthenticatedActivity;
import com.divetym.dive.activities.base.DiveTymActivity;
import com.divetym.dive.fragments.AddGuideFragment;
import com.divetym.dive.fragments.base.DiveTymFragment;
import com.divetym.dive.models.Guide;

/**
 * Created by kali_root on 6/4/2017.
 */

public class AddGuideActivity extends AuthenticatedActivity{
    public static final String EXTRA_GUIDE = "com.divetym.dive.EXTRA_GUIDE";

    public static void launch(DiveTymActivity context, Guide guide, int requestCode) {
        Intent intent = new Intent(context, AddGuideActivity.class);
        intent.putExtra(EXTRA_GUIDE, guide);
        context.startActivityForResult(intent, requestCode);
    }
    public static void launch(DiveTymFragment context, Guide guide, int requestCode) {
        Intent intent = new Intent(context.getActivity(), AddGuideActivity.class);
        intent.putExtra(EXTRA_GUIDE, guide);
        context.startActivityForResult(intent, requestCode);
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
