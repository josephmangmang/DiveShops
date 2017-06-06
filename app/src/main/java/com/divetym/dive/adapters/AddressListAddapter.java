package com.divetym.dive.adapters;

import android.location.Address;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.divetym.dive.R;
import com.divetym.dive.activities.base.DiveTymActivity;
import com.divetym.dive.adapters.base.BaseRecyclerAdapter;
import com.divetym.dive.adapters.base.DiveTymViewHolder;
import com.divetym.dive.common.DiveShopAddress;

import java.util.List;
import java.util.Locale;

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
        holder.mData = address;
        holder.mItemClickListener = mItemClickListener;
        int maxIndex = address.getMaxAddressLineIndex() + 1;
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < maxIndex; i++) {
            builder.append(address.getAddressLine(i));
            builder.append(", ");
        }

        holder.title.setText(builder.toString());
    }
}

class AddressHolder extends DiveTymViewHolder<Address> {
    TextView title;

    public AddressHolder(DiveTymActivity context, View itemView) {
        super(context, itemView);
        title = (TextView) itemView.findViewById(R.id.text_title);
    }
}