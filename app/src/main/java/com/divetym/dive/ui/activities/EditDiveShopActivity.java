package com.divetym.dive.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.divetym.dive.R;
import com.divetym.dive.ui.activities.base.AuthenticatedActivity;
import com.divetym.dive.ui.activities.base.DiveTymActivity;
import com.divetym.dive.ui.fragments.EditDiveShopFragment;
import com.divetym.dive.ui.fragments.base.DiveTymFragment;
import com.divetym.dive.models.DiveShop;

import static com.divetym.dive.ui.fragments.DiveShopFragment.EXTRA_DIVE_SHOP;

/**
 * Created by kali_root on 6/5/2017.
 */

public class EditDiveShopActivity extends AuthenticatedActivity {

    public static void launch(DiveTymActivity context, DiveShop diveShop, int requestCode) {
        Intent intent = new Intent(context, EditDiveShopActivity.class);
        intent.putExtra(EXTRA_DIVE_SHOP, diveShop);
        context.startActivityForResult(intent, requestCode);
    }

    public static void launch(DiveTymFragment context, DiveShop diveShop, int requestCode) {
        Intent intent = new Intent(context.getActivity(), EditDiveShopActivity.class);
        intent.putExtra(EXTRA_DIVE_SHOP, diveShop);
        context.startActivityForResult(intent, requestCode);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);
        showBackButton(true);
        DiveShop diveShop = getIntent().getParcelableExtra(EXTRA_DIVE_SHOP);
        initFragment(R.id.content, EditDiveShopFragment.newInstance(diveShop));
    }
}
