package com.divetym.dive.adapters.base;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.divetym.dive.activities.base.DiveTymActivity;
import com.divetym.dive.models.ListPreview;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by kali_root on 4/9/2017.
 */

public abstract class BaseRecyclerAdapter<VH extends RecyclerView.ViewHolder, DataType> extends RecyclerView.Adapter<VH> {

    protected DiveTymActivity mContext;
    protected List<DataType> mDataList;
    protected HashMap<Integer, DataType> mSelectedItems = new HashMap<>();

    public interface ItemClickListener<DataType> {
        void onItemClick(DataType object, View view, int position);

        void onActionClick(DataType object, View view);
    }

    public interface MultiSelectListener {
        void onMultiSelectStateChanged(boolean enabled);

        void onItemAdded(int id);

        void onItemRemoved(int id);
    }
    public BaseRecyclerAdapter(DiveTymActivity context){
        this(context, new ArrayList<DataType>());
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
    protected MultiSelectListener mMultiSelectListener;

    public void setItemClickListener(ItemClickListener<DataType> itemClickListener) {
        this.mItemClickListener = itemClickListener;
    }

    public void setMultiSelectListener(MultiSelectListener multiSelectListener) {
        this.mMultiSelectListener = multiSelectListener;
    }

    public boolean isSelected(int id) {
        return mSelectedItems.containsKey(id);
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

    public boolean isInMultiSelectMode() {
        return mSelectedItems.size() > 0;
    }

    public HashMap<Integer, DataType> getSelectedItems() {
        return mSelectedItems;
    }

    public void disableMultiSelectMode(boolean requestCallback) {
        if (isInMultiSelectMode()) {
            mSelectedItems.clear();
            notifyDataSetChanged();

            if (requestCallback && mMultiSelectListener != null) {
                mMultiSelectListener.onMultiSelectStateChanged(false);
            }
        }
    }

    public void setSelected(int id, DataType object) {
        if (!mSelectedItems.containsKey(id)) {
            mSelectedItems.put(id, object);
            notifyDataSetChanged();

            if (mMultiSelectListener != null) {
                mMultiSelectListener.onItemAdded(id);

                if (mSelectedItems.size() == 1) {
                    mMultiSelectListener.onMultiSelectStateChanged(true);
                }
            }
        }
    }

    public void setUnselected(int id) {
        if (mSelectedItems.containsKey(id)) {
            mSelectedItems.remove(id);
            notifyDataSetChanged();

            if (mMultiSelectListener != null) {
                mMultiSelectListener.onItemRemoved(id);

                if (mSelectedItems.size() == 0) {
                    mMultiSelectListener.onMultiSelectStateChanged(false);
                }
            }
        }
    }

    public void toggleSelection(int id, DataType object) {
        if (isSelected(id)) {
            setUnselected(id);
        } else {
            setSelected(id, object);
        }
    }
}
