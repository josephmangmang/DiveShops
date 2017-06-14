package com.divetym.dive.ui.adapters;

import android.location.Address;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.divetym.dive.R;
import com.divetym.dive.ui.activities.base.DiveTymActivity;
import com.divetym.dive.ui.adapters.base.BaseRecyclerAdapter;
import com.divetym.dive.ui.adapters.base.DiveTymViewHolder;
import com.divetym.dive.models.LocationAddress;
import com.google.android.gms.maps.model.LatLng;

import java.util.List;

/**
 * Created by kali_root on 6/5/2017.
 */

public class AddressListAddapter extends BaseRecyclerAdapter<AddressHolder, Address> {

    public AddressListAddapter(DiveTymActivity context, List<Address> dataList) {
        super(context, dataList);
    }

    @Override
    public AddressHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.view_item_address, parent, false);
        return new AddressHolder(mContext, view);
    }

    @Override
    public void onBindViewHolder(AddressHolder holder, int position) {
        Address address = getItem(position);
        holder.mItemClickListener = mItemClickListener;
        int maxIndex = address.getMaxAddressLineIndex() + 1;
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < maxIndex; i++) {
            if (address.getAddressLine(i).equals("Unnamed Road")) {
                continue;
            }
            builder.append(address.getAddressLine(i));
            builder.append(", ");
        }
        builder.delete(builder.length() - 2, builder.length());
        holder.fullAddress = builder.toString();
        holder.title.setText(holder.fullAddress);
        holder.mData = new LocationAddress(holder.fullAddress, new LatLng(address.getLatitude(), address.getLongitude()));
    }
}

class AddressHolder extends DiveTymViewHolder<LocationAddress> {
    TextView title;
    String fullAddress;

    public AddressHolder(DiveTymActivity context, View itemView) {
        super(context, itemView);
        title = (TextView) itemView.findViewById(R.id.text_title);
    }
}