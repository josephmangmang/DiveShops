package com.divetym.dive.ui.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.divetym.dive.R;
import com.divetym.dive.ui.view.RobotoTextView;

import java.util.Collections;
import java.util.List;

/**
 * Created by kali_root on 4/6/2017.
 */

public class SimpleListAdapter extends RecyclerView.Adapter<SimpleViewHolder> {
    private List<String> items = Collections.EMPTY_LIST;

    public SimpleListAdapter(List<String> items) {
        this.items = items;
    }

    @Override
    public SimpleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_1, parent, false);
        return new SimpleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SimpleViewHolder holder, int position) {
        holder.tvTitle.setText(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}

class SimpleViewHolder extends RecyclerView.ViewHolder {
    RobotoTextView tvTitle;

    public SimpleViewHolder(View itemView) {
        super(itemView);
        tvTitle = (RobotoTextView) itemView.findViewById(R.id.text);
    }
}
