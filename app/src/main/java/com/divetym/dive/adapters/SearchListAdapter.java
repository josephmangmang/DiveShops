package com.divetym.dive.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.divetym.dive.R;
import com.divetym.dive.activities.base.DiveTymActivity;
import com.divetym.dive.adapters.base.BaseRecyclerAdapter;
import com.divetym.dive.adapters.base.DiveTymViewHolder;
import com.divetym.dive.models.DiveSite;
import com.divetym.dive.view.RobotoTextView;

import java.util.List;

/**
 * Created by kali_root on 4/21/2017.
 */

public class SearchListAdapter extends BaseRecyclerAdapter<SearchHolder, DiveSite> {


    public SearchListAdapter(DiveTymActivity context, List<DiveSite> dataList) {
        super(context, dataList);
    }

    @Override
    public SearchHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.view_item_simple, parent, false);
        return new SearchHolder(mContext, view);
    }

    @Override
    public void onBindViewHolder(SearchHolder holder, int position) {
        holder.mData = getItem(position);
        holder.tvTitle.setText(holder.mData.getName());
    }
}

class SearchHolder extends DiveTymViewHolder<DiveSite> {

    RobotoTextView tvTitle;

    public SearchHolder(DiveTymActivity context, View itemView) {
        super(context, itemView);
        tvTitle = (RobotoTextView) itemView.findViewById(R.id.text_title);
    }
}
