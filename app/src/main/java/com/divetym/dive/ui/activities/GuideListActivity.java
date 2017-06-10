package com.divetym.dive.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;

import com.divetym.dive.R;
import com.divetym.dive.ui.activities.base.AuthenticatedActivity;
import com.divetym.dive.ui.activities.base.DiveTymActivity;
import com.divetym.dive.ui.fragments.GuideListFragment;
import com.divetym.dive.models.Guide;

import java.util.ArrayList;
import java.util.List;

import static com.divetym.dive.ui.fragments.base.DiveTymListFragment.EXTRA_ADD_BUTTON;
import static com.divetym.dive.ui.fragments.base.DiveTymListFragment.EXTRA_LIST;

/**
 * Created by kali_root on 5/24/2017.
 */

public class GuideListActivity extends AuthenticatedActivity {

    public static void launch(DiveTymActivity context, @Nullable List guides, boolean showFab) {
        Intent intent = new Intent(context, GuideListActivity.class);
        intent.putParcelableArrayListExtra(EXTRA_LIST, (ArrayList<? extends Parcelable>) guides);
        intent.putExtra(EXTRA_ADD_BUTTON, showFab);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        showBackButton(true);
        initFragment(R.id.content, GuideListFragment.getInstance(getIntent().getExtras()));
    }
}
