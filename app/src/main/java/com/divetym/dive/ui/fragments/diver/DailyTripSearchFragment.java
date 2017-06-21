package com.divetym.dive.ui.fragments.diver;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.divetym.dive.R;
import com.divetym.dive.event.LocationEvent;
import com.divetym.dive.models.DiveSite;
import com.divetym.dive.models.LocationAddress;
import com.divetym.dive.ui.activities.SearchMapActivity;
import com.divetym.dive.ui.activities.diver.DiverTripActivity;
import com.divetym.dive.ui.dialog.DiveSitesDialog;
import com.divetym.dive.ui.fragments.base.DiveTymFragment;
import com.divetym.dive.ui.view.DateRangeLayout;
import com.divetym.dive.ui.view.RobotoTextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by kali_root on 6/21/2017.
 */

public class DailyTripSearchFragment extends DiveTymFragment {
    private static final String TAG = DailyTripSearchFragment.class.getSimpleName();
    public static final String BUNDLE_START_DATE = "bundle_start_date";
    public static final String BUNDLE_END_DATE = "bundle_end_date";
    public static final String BUNDLE_LOCATION = "bundle_location";
    public static final String BUNDLE_DIVE_SITE = "bundle_dive_sited";
    @BindView(R.id.text_location)
    RobotoTextView locationText;
    @BindView(R.id.layout_date_range)
    DateRangeLayout dateRangeLayout;
    private DiveSite mDiveSite;
    private LocationAddress mLocation;

    @OnClick(R.id.text_location)
    public void onLocationClick() {
        SearchMapActivity.launch(mContext, 1);
    }

    @OnClick(R.id.text_dive_site)
    public void onDiveSiteClick(View view) {
        DiveSitesDialog diveSitesDialog = new DiveSitesDialog();
        diveSitesDialog.show(mContext.getFragmentManager(), DiveSitesDialog.class.getSimpleName());
        diveSitesDialog.setMultiSelectEnable(false);
        diveSitesDialog.setSearchHint(getString(R.string.hint_search_dive_site));
        diveSitesDialog.setOnSelectionDoneListener(selectedItems -> {
            List<DiveSite> list = new ArrayList(selectedItems.values());
            if (list.size() > 0) {
                mDiveSite = list.get(0);
                if (mDiveSite != null) {
                    ((RobotoTextView) view).setText(mDiveSite.getName());
                }
            }
        });
    }

    @OnClick(R.id.button_search)
    public void onSearchClick() {
        Bundle bundle = new Bundle();
        bundle.putLong(BUNDLE_START_DATE, dateRangeLayout.getStartCalendar().getTimeInMillis());
        bundle.putLong(BUNDLE_END_DATE, dateRangeLayout.getEndCalendar().getTimeInMillis());
        bundle.putParcelable(BUNDLE_LOCATION, mLocation);
        bundle.putParcelable(BUNDLE_DIVE_SITE, mDiveSite);
        DiverTripActivity.launch(mContext, bundle);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_daily_trip_search, container, false);
        ButterKnife.bind(this, view);
        dateRangeLayout.setContext(mContext);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }


    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onLocationChanged(LocationEvent event) {
        mLocation = event.getLocationAddress();
        locationText.setText(mLocation.getFullAddress());
        EventBus.getDefault().removeStickyEvent(event);
    }
}
