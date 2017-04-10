package com.divetym.dive.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.divetym.dive.R;
import com.divetym.dive.activities.base.DiveTymActivity;
import com.divetym.dive.adapters.base.BaseRecyclerAdapter;
import com.divetym.dive.adapters.base.DiveTymViewHolder;
import com.divetym.dive.models.ListPreview;
import com.divetym.dive.view.RobotoTextView;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by kali_root on 4/6/2017.
 */

public class ListPreviewAdapter extends BaseRecyclerAdapter<ListPreviewHolder, ListPreview> {
    private static final String TAG = ListPreviewAdapter.class.getSimpleName();

    public ListPreviewAdapter(DiveTymActivity context, List<ListPreview> previewItems) {
        super(context, previewItems);
    }

    @Override
    public ListPreviewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_item_preview, parent, false);
        return new ListPreviewHolder(mContext, view);
    }

    @Override
    public void onBindViewHolder(ListPreviewHolder holder, int position) {
        ListPreview preview = getItem(position);
        holder.setData(preview.getTitle(), preview.getSubtitle(), preview.getAction(), preview.getImageUrl());
        holder.root.setOnClickListener(holder);
        holder.action.setOnClickListener(holder);
    }
}

class ListPreviewHolder extends DiveTymViewHolder {
    ImageView backgroundImage;
    RobotoTextView title;
    RobotoTextView subtitle;
    RobotoTextView action;
    View root;

    public ListPreviewHolder(DiveTymActivity context, View view) {
        super(context, view);
        root = view;
        title = (RobotoTextView) view.findViewById(R.id.text_preview_item_title);
        subtitle = (RobotoTextView) view.findViewById(R.id.text_preview_item_subtitle);
        backgroundImage = (ImageView) view.findViewById(R.id.image_preview_background);
        action = (RobotoTextView) view.findViewById(R.id.text_button_preview_item_action);
    }

    public void setData(String title, String subtitle, String action, String imageUrl) {
        this.title.setText(title);
        this.subtitle.setText(subtitle);
        this.action.setText(action);
        Picasso.with(mContext)
                .load(imageUrl)
                .error(R.drawable.dummy_image_preview)
                .placeholder(R.drawable.dummy_image_preview)
                .into(backgroundImage);
    }

}
