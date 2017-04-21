package com.divetym.dive.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.divetym.dive.R;
import com.divetym.dive.activities.base.DiveTymActivity;
import com.divetym.dive.adapters.base.DiveTymViewHolder;
import com.divetym.dive.adapters.base.EndlessListAdapter;
import com.divetym.dive.models.Boat;
import com.divetym.dive.view.RobotoTextView;
import com.squareup.picasso.Picasso;

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
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, Boat boat) {
        BoatHolder holder = (BoatHolder) viewHolder;
        holder.mData = boat;
        holder.mItemClickListener = mItemClickListener;
        holder.setData(boat.getName(), boat.getDescription(), boat.getImageUrl());
    }

}

class BoatHolder extends DiveTymViewHolder {
    ImageView ivThumbnail;
    RobotoTextView tvTitle;
    RobotoTextView tvDescription;

    public BoatHolder(DiveTymActivity context, View itemView) {
        super(context, itemView);
        ivThumbnail = (ImageView) itemView.findViewById(R.id.image_thumbnail);
        tvTitle = (RobotoTextView) itemView.findViewById(R.id.text_title);
        tvDescription = (RobotoTextView) itemView.findViewById(R.id.text_description);
        itemView.setOnClickListener(this);
    }

    public void setData(String title, String description, String imgUrl) {
        tvTitle.setText(title);
        tvDescription.setText(description);
        Picasso.with(mContext)
                .load(imgUrl)
                .placeholder(R.drawable.dummy_image_preview)
                .error(R.drawable.dummy_image_error)
                .into(ivThumbnail);
    }
}
