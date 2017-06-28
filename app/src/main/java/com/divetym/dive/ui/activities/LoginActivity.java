package com.divetym.dive.ui.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.divetym.dive.R;
import com.divetym.dive.ui.activities.base.DiveTymActivity;
import com.divetym.dive.ui.fragments.LoginFragment;

/**
 * Created by kali_root on 3/27/2017.
 */

public class LoginActivity extends DiveTymActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        showBackButton(true);
        initFragment(R.id.content, new LoginFragment());
    }
}
