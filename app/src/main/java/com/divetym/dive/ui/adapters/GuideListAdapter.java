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
import com.divetym.dive.models.Guide;
import com.divetym.dive.ui.view.RobotoTextView;

import java.util.List;

/**
 * Created by kali_root on 5/25/2017.
 */

public class GuideListAdapter extends EndlessListAdapter<Guide> {
    public GuideListAdapter(DiveTymActivity context, List<Guide> dataList, RecyclerView recyclerView) {
        super(context, dataList, recyclerView);
    }

    @Override
    protected RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.view_item_guide, parent, false);
        return new GuideHolder(mContext, view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, Guide object, int position) {
        GuideHolder holder = (GuideHolder) viewHolder;
        holder.mData = object;
        holder.mItemClickListener = mItemClickListener;
        holder.setData(object.getName(), object.getDescription(), object.getImageUrl());
    }

    static class GuideHolder extends DiveTymViewHolder<Guide> {
        ImageView thumbnail;
        RobotoTextView title;
        RobotoTextView description;

        public GuideHolder(DiveTymActivity context, View itemView) {
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
}

