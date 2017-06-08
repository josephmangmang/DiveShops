package com.divetym.dive.adapters;

import android.graphics.PorterDuff;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.divetym.dive.GlideApp;
import com.divetym.dive.R;
import com.divetym.dive.activities.base.DiveTymActivity;
import com.divetym.dive.adapters.base.DiveTymViewHolder;
import com.divetym.dive.adapters.base.EndlessListAdapter;
import com.divetym.dive.models.common.ThumbnailEntity;
import com.divetym.dive.view.RobotoTextView;

import java.util.List;

/**
 * Created by kali_root on 5/10/2017.
 */

public class SearchDialogAdapter<DataType extends ThumbnailEntity> extends EndlessListAdapter<DataType> {

    public SearchDialogAdapter(DiveTymActivity context, List dataList, RecyclerView recyclerView) {
        super(context, dataList, recyclerView);
    }

    @Override
    protected RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.view_item_search, parent, false);
        return new SearchItemHolder(mContext, view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, DataType object, int position) {
        SearchItemHolder holder = (SearchItemHolder) viewHolder;
        holder.mData = object;
        holder.position = position;
        holder.title.setText(object.getName());
        holder.mItemClickListener = mItemClickListener;
        GlideApp.with(mContext)
                .load(object.getImageUrl())
                .thumbnail(0.1f)
                .placeholder(R.drawable.dummy_image_preview)
                .error(R.drawable.dummy_image_error)
                .into(holder.thumbnail);
        if (isInMultiSelectMode()) {
            if (isSelected(position)) {
                holder.selected.setVisibility(View.VISIBLE);
            } else {
                holder.selected.setVisibility(View.GONE);
            }
        } else {
            holder.selected.setVisibility(View.GONE);
        }
    }

}

class SearchItemHolder extends DiveTymViewHolder {
    ImageView thumbnail;
    RobotoTextView title;
    ImageView selected;

    public SearchItemHolder(DiveTymActivity context, View itemView) {
        super(context, itemView);
        thumbnail = (ImageView) itemView.findViewById(R.id.image_thumbnail);
        title = (RobotoTextView) itemView.findViewById(R.id.text_title);
        selected = (ImageView) itemView.findViewById(R.id.image_selected);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            selected.setColorFilter(context.getResources().getColor(R.color.colorPrimary), PorterDuff.Mode.SRC_ATOP);
            selected.setAlpha(0.7f);
        } else {
            selected.setColorFilter(context.getColor(R.color.colorPrimary), PorterDuff.Mode.SRC_ATOP);
            selected.setAlpha(0.7f);
        }
        itemView.setOnClickListener(this);
    }
}
