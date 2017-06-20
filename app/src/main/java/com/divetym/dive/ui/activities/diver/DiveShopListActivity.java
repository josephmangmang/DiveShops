package com.divetym.dive.ui.activities.diver;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import com.divetym.dive.R;
import com.divetym.dive.ui.activities.base.DiveTymActivity;
import com.divetym.dive.ui.fragments.DiveShopsMapFragment;
import com.divetym.dive.ui.fragments.diver.DiveShopListFragment;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by kali_root on 6/19/2017.
 */

public class DiveShopListActivity extends DiveTymActivity {
    private static final String TAG = DiveShopListActivity.class.getSimpleName();
    @BindView(R.id.button_map_switch)
    Button mapSwitchButton;
    private DiveShopListFragment mListFragment;
    private DiveShopsMapFragment mMapFragment;
    boolean mapButtonVisible = true;

    private DiveShopListFragment.OnListChangeListener onListChangeListener = new DiveShopListFragment.OnListChangeListener() {
        @Override
        public void onListChange(List list, boolean reset) {
            Log.d(TAG, "onListChange: " + list.size());
            mMapFragment.setList(list);
        }
    };

    @OnClick(R.id.button_map_switch)
    public void onMapButtonClick() {
        switchFragment();
    }

    @OnClick(R.id.button_sort_filter)
    public void onSortFilterButtonClick(View view) {

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dive_shop_list);
        ButterKnife.bind(this);
        showBackButton(true);
        mListFragment = initFragment(R.id.content, new DiveShopListFragment());
        mMapFragment = new DiveShopsMapFragment();
        mListFragment.setOnListChangeListener(onListChangeListener);
    }

    private void switchFragment() {
        if (mapButtonVisible) {
            mapButtonVisible = false;
            mapSwitchButton.setText(R.string.action_list);
            mapSwitchButton.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_list_black_24dp, 0, 0, 0);
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.content, mMapFragment, DiveShopsMapFragment.TAG)
                    .setTransition(FragmentTransaction.TRANSIT_ENTER_MASK)
                    .addToBackStack(null)
                    .commit();
        } else {
            mapButtonVisible = true;
            mapSwitchButton.setText(R.string.action_map);
            mapSwitchButton.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_map_black_24dp, 0,0,0);
            getSupportFragmentManager().popBackStackImmediate();
        }
    }

    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }
}
