package com.divetym.dive.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.divetym.dive.R;
import com.divetym.dive.activities.base.AuthenticatedActivity;
import com.divetym.dive.fragments.CreateTripFragment;

/**
 * Created by kali_root on 5/9/2017.
 */

public class CreateTripActivity extends AuthenticatedActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        showBackButton(true);
        initFragment(R.id.content, new CreateTripFragment());
    }
}
