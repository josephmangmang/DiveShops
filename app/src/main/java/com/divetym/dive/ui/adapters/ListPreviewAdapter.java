package com.divetym.dive.ui.adapters;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.divetym.dive.GlideApp;
import com.divetym.dive.R;
import com.divetym.dive.ui.activities.base.DiveTymActivity;
import com.divetym.dive.ui.adapters.base.BaseRecyclerAdapter;
import com.divetym.dive.ui.adapters.base.DiveTymViewHolder;
import com.divetym.dive.models.ListPreview;
import com.divetym.dive.ui.view.RobotoTextView;
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
        holder.position = position;
        holder.mData = preview;
        holder.mItemClickListener = mItemClickListener;
        holder.setData(preview.getTitle(), preview.getSubtitle(), preview.getAction(), preview.getImageUrl());
    }
}

class ListPreviewHolder extends DiveTymViewHolder<ListPreview> {
    ImageView thumbnail;
    RobotoTextView title;
    RobotoTextView subtitle;
    RobotoTextView btnAction;

    public ListPreviewHolder(DiveTymActivity context, View view) {
        super(context, view);
        title = (RobotoTextView) view.findViewById(R.id.text_preview_item_title);
        subtitle = (RobotoTextView) view.findViewById(R.id.text_preview_item_subtitle);
        thumbnail = (ImageView) view.findViewById(R.id.image_preview_background);
        btnAction = (RobotoTextView) view.findViewById(R.id.text_button_preview_item_action);
        btnAction.setOnClickListener(this);
        view.setOnClickListener(this);
    }

    public void setData(String title, String subtitle, String action, String imageUrl) {
        this.title.setText(title);
        this.subtitle.setText(subtitle);
        this.btnAction.setText(action);
        if(TextUtils.isEmpty(action)){
            this.btnAction.setVisibility(View.GONE);
        }
        GlideApp.with(mContext)
                .load(imageUrl)
                .thumbnail(0.1f)
                .error(R.drawable.dummy_image_error)
                .placeholder(R.drawable.dummy_image_preview)
                .into(thumbnail);
    }

}
