package com.divetym.dive.ui.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.divetym.dive.GlideApp;
import com.divetym.dive.R;
import com.divetym.dive.models.Guide;
import com.divetym.dive.ui.activities.base.DiveTymActivity;
import com.divetym.dive.ui.adapters.base.BaseRecyclerAdapter;
import com.divetym.dive.ui.adapters.base.DiveTymViewHolder;
import com.divetym.dive.ui.view.RobotoTextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by kali_root on 6/15/2017.
 */

public class GuidePreviewAdapter extends BaseRecyclerAdapter<GuidePreviewAdapter.GuidePreviewHolder, Guide> {

    public GuidePreviewAdapter(DiveTymActivity context) {
        super(context);
    }

    @Override
    public GuidePreviewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.view_item_guide_preview, parent, false);
        return new GuidePreviewHolder(mContext, view);
    }

    @Override
    public void onBindViewHolder(GuidePreviewHolder holder, int position) {
        Guide guide = getItem(position);
        holder.mData = guide;
        holder.position = position;
        holder.mItemClickListener = mItemClickListener;
        holder.name.setText(guide.getName());
        GlideApp.with(mContext)
                .load(guide.getImageUrl())
                .placeholder(R.drawable.dummy_image_preview)
                .error(R.drawable.dummy_image_error)
                .thumbnail(0.1f)
                .dontAnimate()
                .into(holder.avatar);
    }

    class GuidePreviewHolder extends DiveTymViewHolder<Guide> {
        @BindView(R.id.image_avatar)
        CircleImageView avatar;
        @BindView(R.id.text_name)
        RobotoTextView name;

        public GuidePreviewHolder(DiveTymActivity context, View itemView) {
            super(context, itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
