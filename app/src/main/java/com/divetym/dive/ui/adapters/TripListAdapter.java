package com.divetym.dive.ui.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.divetym.dive.R;
import com.divetym.dive.ui.activities.base.DiveTymActivity;
import com.divetym.dive.ui.adapters.base.DiveTymViewHolder;
import com.divetym.dive.ui.adapters.base.EndlessListAdapter;
import com.divetym.dive.models.DailyTrip;
import com.divetym.dive.ui.view.RobotoTextView;

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
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, DailyTrip object, int i) {
        TripHolder holder = (TripHolder) viewHolder;
        holder.mData = object;
        holder.position = i;
        holder.mItemClickListener = mItemClickListener;
        holder.date.setText(object.getDateOnly());
        holder.diveSite.setText(object.getDiveSiteNames());
        holder.price.setText(object.getPrice().toString());
        holder.time.setText(object.getTimeOnly());
        if (isInMultiSelectMode()) {
            holder.selected.setVisibility(View.VISIBLE);
            if (isSelected(i)) {
                holder.selected.setImageResource(R.drawable.ic_selected_24);
                holder.selected.setAlpha(1f);
            } else {
                holder.selected.setImageResource(R.drawable.ic_unselected_24);
                holder.selected.setAlpha(0.5f);
            }
        } else {
            holder.selected.setVisibility(View.GONE);
        }
    }
}

class TripHolder extends DiveTymViewHolder<DailyTrip> {
    @BindView(R.id.text_date)
    RobotoTextView date;
    @BindView(R.id.text_dive_site)
    RobotoTextView diveSite;
    @BindView(R.id.text_price)
    RobotoTextView price;
    @BindView(R.id.text_time)
    RobotoTextView time;
    @BindView(R.id.image_selected)
    ImageView selected;

    public TripHolder(DiveTymActivity context, View itemView) {
        super(context, itemView);
        ButterKnife.bind(this, itemView);
        itemView.setOnClickListener(this);
    }
}
