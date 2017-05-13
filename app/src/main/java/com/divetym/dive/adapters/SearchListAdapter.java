package com.divetym.dive.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.divetym.dive.R;
import com.divetym.dive.activities.base.DiveTymActivity;
import com.divetym.dive.adapters.base.DiveTymViewHolder;
import com.divetym.dive.adapters.base.EndlessListAdapter;
import com.divetym.dive.models.DiveSite;
import com.divetym.dive.view.RobotoTextView;

import java.util.List;

/**
 * Created by kali_root on 4/21/2017.
 */

public class SearchListAdapter extends EndlessListAdapter<DiveSite> {


    public SearchListAdapter(DiveTymActivity context, List<DiveSite> dataList, RecyclerView recyclerView) {
        super(context, dataList, recyclerView);
    }

    @Override
    protected RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.view_item_simple, parent, false);
        return new SearchHolder(mContext, view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, DiveSite object, int position) {
        SearchHolder holder = (SearchHolder) viewHolder;
        holder.mData = object;
        holder.mItemClickListener = mItemClickListener;
        holder.title.setText(holder.mData.getName());
    }
}

class SearchHolder extends DiveTymViewHolder<DiveSite> {

    RobotoTextView title;

    public SearchHolder(DiveTymActivity context, View itemView) {
        super(context, itemView);
        title = (RobotoTextView) itemView.findViewById(R.id.text_title);
        itemView.setOnClickListener(this);
    }
}
