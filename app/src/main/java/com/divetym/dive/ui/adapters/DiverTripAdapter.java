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

public class DiverTripAdapter extends TripListAdapter {

    public DiverTripAdapter(DiveTymActivity context, List<DailyTrip> dataList, RecyclerView recyclerView) {
        super(context, dataList, recyclerView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, DailyTrip dailyTrip, int i) {
        super.onBindViewHolder(viewHolder, dailyTrip, i);
        TripHolder holder = (TripHolder) viewHolder;
        holder.divshopName.setVisibility(View.VISIBLE);
    }
}

