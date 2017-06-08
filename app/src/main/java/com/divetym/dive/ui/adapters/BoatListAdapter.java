package com.divetym.dive.ui.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.divetym.dive.GlideApp;
import com.divetym.dive.R;
import com.divetym.dive.ui.activities.base.DiveTymActivity;
import com.divetym.dive.ui.adapters.base.DiveTymViewHolder;
import com.divetym.dive.ui.adapters.base.EndlessListAdapter;
import com.divetym.dive.models.Boat;
import com.divetym.dive.ui.view.RobotoTextView;

import java.util.List;

/**
 * Created by kali_root on 4/15/2017.
 */

public class BoatListAdapter extends EndlessListAdapter<Boat> {


    public BoatListAdapter(DiveTymActivity context, List<Boat> dataList, RecyclerView recyclerView) {
        super(context, dataList, recyclerView);
    }

    @Override
    protected RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.view_item_boat, parent, false);
        return new BoatHolder(mContext, view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, Boat boat, int i) {
        BoatHolder holder = (BoatHolder) viewHolder;
        holder.mData = boat;
        holder.mItemClickListener = mItemClickListener;
        holder.setData(boat.getName(), boat.getDescription(), boat.getImageUrl());
    }

}

class BoatHolder extends DiveTymViewHolder {
    ImageView thumbnail;
    RobotoTextView title;
    RobotoTextView description;

    public BoatHolder(DiveTymActivity context, View itemView) {
        super(context, itemView);
        thumbnail = (ImageView) itemView.findViewById(R.id.image_thumbnail);
        title = (RobotoTextView) itemView.findViewById(R.id.text_title);
        description = (RobotoTextView) itemView.findViewById(R.id.text_description);
        itemView.setOnClickListener(this);
    }

    public void setData(String title, String description, String imgUrl) {
        this.title.setText(title);
        this.description.setText(description);
        GlideApp.with(mContext)
                .load(imgUrl)
                .thumbnail(0.1f)
                .placeholder(R.drawable.dummy_image_preview)
                .error(R.drawable.dummy_image_error)
                .into(thumbnail);
    }
}
