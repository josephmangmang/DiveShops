package com.divetym.dive.ui.view;

import android.content.Context;
import android.content.ContextWrapper;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;

import com.divetym.dive.R;
import com.divetym.dive.event.LocationEvent;
import com.divetym.dive.models.DiveSite;
import com.divetym.dive.models.LocationAddress;
import com.divetym.dive.ui.activities.SearchMapActivity;
import com.divetym.dive.ui.activities.base.DiveTymActivity;
import com.divetym.dive.ui.dialog.DiveSitesDialog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by kali_root on 6/11/2017.
 */

public class TripFilterLayout extends CardView {
    @BindView(R.id.layout_date_range)
    DateRangeLayout dateRangeLayout;
    @BindView(R.id.text_result_filter)
    Button showFIlterButton;
    @BindView(R.id.edit_location)
    ClearableEditText locationEditText;
    @BindView(R.id.edit_dive_site)
    ClearableEditText diveSiteEditText;
    @BindView(R.id.layout_filter_option)
    View filterOptionLayout;
    private DiveTymActivity mContext;
    private LocationAddress mLocation;
    private DiveSite mDiveSite;
    private Date mStartDate;
    private Date mEndDate;
    private OnFilterChangeListener mOnFilterChangeListener;

    public TripFilterLayout(Context context) {
        super(context);
        init(context);
    }

    public TripFilterLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        inflate(context, R.layout.view_trip_filter, this);
        ButterKnife.bind(this, this);
        if (context instanceof ContextWrapper) {
            mContext = (DiveTymActivity) ((ContextWrapper) context).getBaseContext();
        }
        mStartDate = getStartDate();
        mEndDate = getEndDate();
        locationEditText.setHint(context.getString(R.string.hint_location));
        diveSiteEditText.setHint(context.getString(R.string.hint_dive_site));
        locationEditText.setOnClearListener(() -> {
            mLocation = null;
            notifyFilterChanged();
        });
        diveSiteEditText.setOnClearListener(() -> {
            mDiveSite = null;
            notifyFilterChanged();
        });

        showFIlterButton.setOnClickListener(view1 -> {
            if (filterOptionLayout.isShown()) {
                filterOptionLayout.setVisibility(GONE);
            } else {
                filterOptionLayout.setVisibility(VISIBLE);
            }
        });

        locationEditText.setOnClickListener(view1 -> {
            if (mContext != null) {
                SearchMapActivity.launch(mContext, 1);
            }
        });

        diveSiteEditText.setOnClickListener(view1 -> {
            DiveSitesDialog diveSitesDialog = new DiveSitesDialog();
            diveSitesDialog.show(mContext.getFragmentManager(), DiveSitesDialog.class.getSimpleName());
            diveSitesDialog.setMultiSelectEnable(false);
            diveSitesDialog.setSearchHint(context.getString(R.string.hint_search_dive_site));
            diveSitesDialog.setOnSelectionDoneListener(selectedItems -> {
                List<DiveSite> list = new ArrayList(selectedItems.values());
                if (list.size() > 0) {
                    mDiveSite = list.get(0);
                    if (mDiveSite != null) {
                        diveSiteEditText.setText(mDiveSite.getName());
                    }
                    notifyFilterChanged();
                }
            });
        });
        dateRangeLayout.setOnDateRangeChangeListener((startDate, endDate) -> {
            mStartDate = startDate.getTime();
            mEndDate = endDate.getTime();
            notifyFilterChanged();
        });
    }

    public void notifyFilterChanged() {
        if (mOnFilterChangeListener != null)
            mOnFilterChangeListener.onFilterChanged(mStartDate, mEndDate, mLocation, mDiveSite);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onDetachedFromWindow() {
        EventBus.getDefault().unregister(this);
        super.onDetachedFromWindow();
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onLocationChanged(LocationEvent event) {
        mLocation = event.getLocationAddress();
        locationEditText.setText(mLocation.getFullAddress());
        notifyFilterChanged();
    }

    public void setContext(DiveTymActivity context) {
        mContext = context;
    }

    public void showLocationFilter(boolean show) {
        locationEditText.setVisibility(show ? VISIBLE : GONE);
    }

    public void showSiteFilter(boolean show) {
        diveSiteEditText.setVisibility(show ? VISIBLE : GONE);
    }

    public Date getStartDate() {
        return dateRangeLayout.getStartCalendar().getTime();
    }

    public Date getEndDate() {
        return dateRangeLayout.getEndCalendar().getTime();
    }

    public void setOnFilterChangeListener(OnFilterChangeListener onFilterChangeListener) {
        mOnFilterChangeListener = onFilterChangeListener;
    }

    public void setLocation(LocationAddress location) {
        this.mLocation = location;
        if (mLocation != null) {
            locationEditText.setText(mLocation.getFullAddress());
        }
    }

    public void setDiveSite(DiveSite diveSite) {
        this.mDiveSite = diveSite;
        if (mDiveSite != null) {
            diveSiteEditText.setText(mDiveSite.getName());
        }
    }

    public void setStartDate(Date startDate) {
        this.mStartDate = startDate;
        Calendar calendar = Calendar.getInstance();
        if (mStartDate != null) {
            calendar.setTime(mStartDate);
        }
        dateRangeLayout.setStartCalendar(calendar);
    }

    public void setEndDate(Date endDate) {
        this.mEndDate = endDate;
        Calendar calendar = Calendar.getInstance();
        if (mEndDate != null) {
            calendar.setTime(mEndDate);
        }
        dateRangeLayout.setEndCalendar(calendar);
    }

    public interface OnFilterChangeListener {
        void onFilterChanged(Date startDate, Date endDate, @Nullable LocationAddress locationAddress, @Nullable DiveSite diveSite);
    }
}
