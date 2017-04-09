package com.divetym.dive.ui.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.divetym.dive.R;
import com.divetym.dive.ui.models.ListPreview;
import com.divetym.dive.ui.view.RobotoTextView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by kali_root on 4/6/2017.
 */

public class ListPreviewAdapter extends RecyclerView.Adapter<ListPreviewHolder> {
    private static final String TAG = ListPreviewAdapter.class.getSimpleName();
    private List<ListPreview> mPreviewItems;

    public ListPreviewAdapter(List<ListPreview> previewItems) {
        mPreviewItems = previewItems;
    }

    @Override
    public ListPreviewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.d(TAG, "onCreateViewHolder: ");
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_item_preview, parent, false);
        return new ListPreviewHolder(parent.getContext(), view);
    }

    @Override
    public void onBindViewHolder(ListPreviewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder: " + mPreviewItems.get(position));
        ListPreview preview = mPreviewItems.get(position);
        holder.setData(preview.getTitle(), preview.getSubtitle(), preview.getAction(), preview.getImageUrl());
        holder.setActionListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: 4/9/2017 start preview item details
            }
        });
    }

    @Override
    public int getItemCount() {
        return mPreviewItems.size();
    }

    public void setPreviewList(List<ListPreview> previews) {
        if (mPreviewItems != null) {
            mPreviewItems.clear();
            mPreviewItems.addAll(previews);
        } else {
            mPreviewItems = previews;
        }
        notifyDataSetChanged();
    }
}

class ListPreviewHolder extends RecyclerView.ViewHolder {
    ImageView backgroundImage;
    RobotoTextView title;
    RobotoTextView subtitle;
    RobotoTextView action;
    Context context;

    public ListPreviewHolder(Context context, View view) {
        super(view);
        this.context = context;
        title = (RobotoTextView) view.findViewById(R.id.text_preview_item_title);
        subtitle = (RobotoTextView) view.findViewById(R.id.text_preview_item_subtitle);
        backgroundImage = (ImageView) view.findViewById(R.id.image_preview_background);
        action = (RobotoTextView) view.findViewById(R.id.text_button_preview_item_action);
    }

    public void setData(String title, String subtitle, String action, String imageUrl) {
        this.title.setText(title);
        this.subtitle.setText(subtitle);
        this.action.setText(action);
        Picasso.with(context)
                .load(imageUrl)
                .error(R.drawable.dummy_image_preview)
                .placeholder(R.drawable.dummy_image_preview)
                .into(backgroundImage);
    }

    public void setActionListener(View.OnClickListener actionListener) {
        action.setOnClickListener(actionListener);
    }
}
