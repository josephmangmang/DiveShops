package com.divetym.dive.adapters.base;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.divetym.dive.activities.base.DiveTymActivity;
import com.divetym.dive.models.ListPreview;

import java.util.List;

/**
 * Created by kali_root on 4/9/2017.
 */

public abstract class BaseRecyclerAdapter<VH extends RecyclerView.ViewHolder, DataType> extends RecyclerView.Adapter<VH> {

    protected DiveTymActivity mContext;
    protected List<DataType> mDataList;

    public interface ItemClickListener<DataType> {
        void onItemClick(DataType object, View view);

        void onActionClick(DataType object, View view);
    }

    public BaseRecyclerAdapter(DiveTymActivity context, List<DataType> dataList) {
        mContext = context;
        mDataList = dataList;
    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    protected DataType getItem(int position) {
        return mDataList.get(position);
    }

    protected ItemClickListener<DataType> mItemClickListener;


    public void setItemClickListener(ItemClickListener<DataType> itemClickListener) {
        this.mItemClickListener = itemClickListener;
    }

    public void setDataList(List<DataType> dataList) {
        if (mDataList != null) {
            mDataList.clear();
            mDataList.addAll(dataList);
        } else {
            mDataList = dataList;
        }
        notifyDataSetChanged();
    }
}
