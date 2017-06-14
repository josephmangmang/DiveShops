package com.divetym.dive.ui.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.divetym.dive.R;
import com.divetym.dive.models.DailyTrip;
import com.divetym.dive.ui.activities.base.DiveTymActivity;
import com.divetym.dive.ui.adapters.base.DiveTymViewHolder;
import com.divetym.dive.ui.adapters.base.EndlessListAdapter;
import com.divetym.dive.ui.view.RobotoTextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by kali_root on 6/12/2017.
 */

public class DiverTripAdapter extends EndlessListAdapter<DailyTrip> {
    public DiverTripAdapter(DiveTymActivity context, List<DailyTrip> dataList, RecyclerView recyclerView) {
        super(context, dataList, recyclerView);
    }

    @Override
    protected RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.view_item_diver_trip, parent, false);
        return new DiverTripHolder(mContext, view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, DailyTrip object, int position) {
        DiverTripHolder holder = (DiverTripHolder) viewHolder;
        DailyTrip dailyTrip = getItem(position);
        holder.mItemClickListener = mItemClickListener;
        holder.mData = dailyTrip;
        holder.divshopName.setText(dailyTrip.getDiveShopName());
        holder.date.setText(dailyTrip.getDateOnly());
        holder.time.setText(dailyTrip.getTimeOnly());
        holder.price.setText("P" + dailyTrip.getPrice().toString());
        holder.diveSite.setText(dailyTrip.getDiveSiteNames());
        holder.spotLeft.setText(mContext.getResources()
                .getQuantityString(R.plurals.remaining_slots, dailyTrip.getRemainingSlot(), dailyTrip.getRemainingSlot()));
    }
}

class DiverTripHolder extends DiveTymViewHolder<DailyTrip> {
    @BindView(R.id.text_dive_shop)
    RobotoTextView divshopName;
    @BindView(R.id.text_date)
    RobotoTextView date;
    @BindView(R.id.text_time)
    RobotoTextView time;
    @BindView(R.id.text_price)
    RobotoTextView price;
    @BindView(R.id.text_dive_site)
    RobotoTextView diveSite;
    @BindView(R.id.text_spot_left)
    RobotoTextView spotLeft;

    public DiverTripHolder(DiveTymActivity context, View itemView) {
        super(context, itemView);
        ButterKnife.bind(this, itemView);
    }
}
