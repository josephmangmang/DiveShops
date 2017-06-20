package com.divetym.dive.ui.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;

import com.divetym.dive.GlideApp;
import com.divetym.dive.R;
import com.divetym.dive.models.DiveShop;
import com.divetym.dive.ui.activities.base.DiveTymActivity;
import com.divetym.dive.ui.adapters.base.DiveTymViewHolder;
import com.divetym.dive.ui.adapters.base.EndlessListAdapter;
import com.divetym.dive.ui.view.RobotoTextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by kali_root on 6/19/2017.
 */

public class DiveShopListAdapter extends EndlessListAdapter<DiveShop> {

    public DiveShopListAdapter(DiveTymActivity context, List<DiveShop> dataList, RecyclerView recyclerView) {
        super(context, dataList, recyclerView);
    }

    @Override
    protected RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.view_item_diveshop, parent, false);
        return new DiveShopHolder(mContext, view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, DiveShop object, int position) {
        DiveShopHolder holder = (DiveShopHolder) viewHolder;
        DiveShop shop = getItem(position);
        holder.mItemClickListener = mItemClickListener;
        holder.mData = shop;
        GlideApp.with(mContext)
                .load(shop.getImageUrl())
                .thumbnail(0.1f)
                .placeholder(R.drawable.dummy_image_preview)
                .error(R.drawable.dummy_image_error)
                .into(holder.thumbnail);
        holder.name.setText(shop.getName());
        holder.rating.setRating(10);// TODO: 6/19/2017 implement rating
        holder.ratingMark.setText("Excellent 10");// TODO: 6/19/2017  mplement rating remarks
        holder.address.setText(shop.getAddress());
        holder.price.setText(shop.getPricePerDive().toString());
        holder.currency.setText("PHP"); // TODO: 6/19/2017 Currency
    }

    class DiveShopHolder extends DiveTymViewHolder<DiveShop> {
        @BindView(R.id.text_name)
        RobotoTextView name;
        @BindView(R.id.rating)
        RatingBar rating;
        @BindView(R.id.text_rating_mark)
        RobotoTextView ratingMark;
        @BindView(R.id.text_address)
        RobotoTextView address;
        @BindView(R.id.text_price)
        RobotoTextView price;
        @BindView(R.id.text_price_currency)
        RobotoTextView currency;
        @BindView(R.id.image_thumbnail)
        ImageView thumbnail;

        public DiveShopHolder(DiveTymActivity context, View itemView) {
            super(context, itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
