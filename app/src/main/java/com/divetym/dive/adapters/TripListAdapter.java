package com.divetym.dive.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.divetym.dive.R;
import com.divetym.dive.activities.base.DiveTymActivity;
import com.divetym.dive.adapters.base.DiveTymViewHolder;
import com.divetym.dive.adapters.base.EndlessListAdapter;
import com.divetym.dive.models.DailyTrip;
import com.divetym.dive.view.RobotoTextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by kali_root on 4/21/2017.
 */

public class TripListAdapter extends EndlessListAdapter<DailyTrip> {
    public TripListAdapter(DiveTymActivity context, List<DailyTrip> dataList, RecyclerView recyclerView) {
        super(context, dataList, recyclerView);
    }

    @Override
    protected RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.view_item_trip, parent, false);
        return new TripHolder(mContext, view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, DailyTrip object) {
        TripHolder holder = (TripHolder) viewHolder;
        holder.mData = object;
        holder.mItemClickListener = mItemClickListener;
        holder.tvDate.setText(object.getDateOnly());
        holder.tvDiveSite.setText(object.getDiveSites());
        holder.tvPrice.setText(object.getPrice().toString());
        holder.tvTime.setText(object.getTimeOnly());
    }
}

class TripHolder extends DiveTymViewHolder<DailyTrip> {
    @BindView(R.id.text_date)
    RobotoTextView tvDate;
    @BindView(R.id.text_dive_site)
    RobotoTextView tvDiveSite;
    @BindView(R.id.text_price)
    RobotoTextView tvPrice;
    @BindView(R.id.text_time)
    RobotoTextView tvTime;

    public TripHolder(DiveTymActivity context, View itemView) {
        super(context, itemView);
        ButterKnife.bind(this, itemView);
        itemView.setOnClickListener(this);
    }
}
