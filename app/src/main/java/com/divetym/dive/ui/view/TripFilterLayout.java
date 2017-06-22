package com.divetym.dive.ui.view;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.divetym.dive.BuildConfig;
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

public class TripFilterLayout extends LinearLayout {
    @BindView(R.id.layout_date_range)
    DateRangeLayout dateRangeLayout;
    @BindView(R.id.text_location)
    ClearableTextView locationText;
    @BindView(R.id.text_dive_site)
    ClearableTextView diveSiteText;
    @BindView(R.id.card_filter)
    CardView filterCard;
    @BindView(R.id.action_button)
    Button actionButton;

    private DiveTymActivity mContext;
    private LocationAddress mLocation;
    private DiveSite mDiveSite;
    private Date mStartDate;
    private Date mEndDate;
    private OnFilterChangeListener mOnFilterChangeListener;
    private OnActionClickListener onActionClickListener;

    public TripFilterLayout(Context context) {
        super(context);
        init(context, null);
    }

    public TripFilterLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        inflate(context, R.layout.view_trip_filter, this);
        ButterKnife.bind(this, this);
        if (context instanceof DiveTymActivity) {
            mContext = (DiveTymActivity) context;
        } else if (context instanceof ContextWrapper) {
            try {
                Context baseContext = ((ContextWrapper) context).getBaseContext();
                mContext = (DiveTymActivity) baseContext;
            } catch (ClassCastException e) {
                if (BuildConfig.DEBUG) {
                    e.printStackTrace();
                }
            }
        }
        if (attrs != null) {
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.TripFilterLayout);
            if (a.hasValue(R.styleable.TripFilterLayout_show_filter_button)) {
                boolean show = a.getBoolean(R.styleable.TripFilterLayout_show_filter_button, true);
                filterCard.setVisibility(show ? VISIBLE : GONE);
            }
            if (a.hasValue(R.styleable.TripFilterLayout_action_text)) {
                CharSequence text = a.getText(R.styleable.TripFilterLayout_action_text);
                actionButton.setText(text);
            }
        }
        mStartDate = getStartDate();
        mEndDate = getEndDate();
        locationText.setOnClearListener(() -> {
            mLocation = null;
            notifyFilterChanged();
        });
        diveSiteText.setOnClearListener(() -> {
            mDiveSite = null;
            notifyFilterChanged();
        });

        actionButton.setOnClickListener(view -> {
            if (onActionClickListener != null) {
                onActionClickListener
                        .onActionClick(
                                mStartDate,
                                mEndDate,
                                mLocation,
                                mDiveSite);
            }
        });
        locationText.setOnClickListener(view1 -> {
            if (mContext != null) {
                SearchMapActivity.launch(mContext, 1);
            }
        });

        diveSiteText.setOnClickListener(view1 -> {
            DiveSitesDialog diveSitesDialog = new DiveSitesDialog();
            diveSitesDialog.show(mContext.getFragmentManager(), DiveSitesDialog.class.getSimpleName());
            diveSitesDialog.setMultiSelectEnable(false);
            diveSitesDialog.setSearchHint(context.getString(R.string.hint_search_dive_site));
            diveSitesDialog.setOnSelectionDoneListener(selectedItems -> {
                List<DiveSite> list = new ArrayList(selectedItems.values());
                if (list.size() > 0) {
                    mDiveSite = list.get(0);
                    if (mDiveSite != null) {
                        diveSiteText.setText(mDiveSite.getName());
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
        locationText.setText(mLocation.getFullAddress());
        EventBus.getDefault().removeStickyEvent(event);
        notifyFilterChanged();
    }

    public void setContext(DiveTymActivity context) {
        mContext = context;
    }

    public void showLocationFilter(boolean show) {
        locationText.setVisibility(show ? VISIBLE : GONE);
    }

    public void showSiteFilter(boolean show) {
        diveSiteText.setVisibility(show ? VISIBLE : GONE);
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

    public void setOnActionClickListener(OnActionClickListener onActionClickListener) {
        this.onActionClickListener = onActionClickListener;
    }

    public void setLocation(LocationAddress location) {
        this.mLocation = location;
        if (mLocation != null) {
            locationText.setText(mLocation.getFullAddress());
        }
    }

    public void setDiveSite(DiveSite diveSite) {
        this.mDiveSite = diveSite;
        if (mDiveSite != null) {
            diveSiteText.setText(mDiveSite.getName());
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
    public int getHightForPeek(){
       return filterCard.getHeight();
    }
    public void setFilterButtonClickListener(OnClickListener clickListener) {
        findViewById(R.id.button_filter).setOnClickListener(clickListener);
    }

    public interface OnFilterChangeListener {
        void onFilterChanged(Date startDate, Date endDate, @Nullable LocationAddress locationAddress, @Nullable DiveSite diveSite);
    }

    public interface OnActionClickListener {
        void onActionClick(Date startDate, Date endDate, @Nullable LocationAddress locationAddress, @Nullable DiveSite diveSite);
    }
}
