package com.divetym.dive.adapters.base;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.divetym.dive.R;
import com.divetym.dive.activities.base.DiveTymActivity;

/**
 * Created by kali_root on 4/10/2017.
 */

public class DiveTymViewHolder<DataType> extends RecyclerView.ViewHolder implements View.OnClickListener {
    private static final String TAG = DiveTymViewHolder.class.getSimpleName();
    public BaseRecyclerAdapter.ItemClickListener<DataType> mItemClickListener;
    public DataType mData;
    protected DiveTymActivity mContext;

    public DiveTymViewHolder(DiveTymActivity context, View itemView) {
        super(itemView);
        mContext = context;
    }

    @Override
    public void onClick(View view) {
        Log.d(TAG, "onClick");
        if (mItemClickListener != null) {
            if (view.getId() == R.id.root) {
                // this is a root of item view
                mItemClickListener.onItemClick(mData, view);
            } else {
                // this is a action button
                mItemClickListener.onActionClick(mData, view);
            }
        }
    }
}
