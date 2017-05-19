package com.divetym.dive.adapters;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.divetym.dive.R;
import com.divetym.dive.activities.base.DiveTymActivity;
import com.divetym.dive.adapters.base.BaseRecyclerAdapter;
import com.divetym.dive.adapters.base.DiveTymViewHolder;
import com.divetym.dive.models.common.ThumbnailEntity;
import com.divetym.dive.view.RobotoTextView;
import com.squareup.picasso.Picasso;

import org.apache.commons.collections4.list.SetUniqueList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by kali_root on 5/7/2017.
 */

public class ListAddMoreAdapter<DataType extends ThumbnailEntity> extends BaseRecyclerAdapter<DiveTymViewHolder<DataType>, DataType> {
    private static final String TAG = ListAddMoreAdapter.class.getSimpleName();
    private static final int ITEM_VIEW = 1;
    private static final int ITEM_ADD_MORE = 2;
    private View.OnClickListener mAddClickListener;
    private SetUniqueList<DataType> mUniqueDataList;

    public ListAddMoreAdapter(DiveTymActivity context) {
        super(context);
        mUniqueDataList = SetUniqueList.setUniqueList(mDataList);
        mUniqueDataList.add(null);
    }

    @Override
    public DiveTymViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == ITEM_VIEW) {
            Log.d(TAG, "onCreateViewHolder ITEM_VIEW");
            View view = LayoutInflater.from(mContext).inflate(R.layout.view_item_list_add, parent, false);
            return new ListAddMoreHolder(mContext, view);
        } else {
            Log.d(TAG, "onCreateViewHolder ADDMORE_ITEM_VIEW");
            View view = LayoutInflater.from(mContext).inflate(R.layout.view_item_list_add_more, parent, false);
            return new AddMoreHolder(mContext, view);
        }
    }

    @Override
    public void onBindViewHolder(DiveTymViewHolder<DataType> viewHolder, final int position) {
        if (getItemViewType(position) == ITEM_VIEW) {
            Log.d(TAG, "onBindViewHolder ITEM_VIEW");
            ListAddMoreHolder holder = (ListAddMoreHolder) viewHolder;
            holder.mItemClickListener = mItemClickListener;
            holder.setData(mUniqueDataList.get(position));
            holder.remove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mUniqueDataList.remove(position);
                    notifyItemRemoved(position);
                    notifyItemRangeChanged(position, getItemCount());
                }
            });
        } else {
            Log.d(TAG, "onBindViewHolder ADDMORE_ITEM_VIEW");
            AddMoreHolder holder = (AddMoreHolder) viewHolder;
            holder.add.setOnClickListener(mAddClickListener);
        }
    }

    @Override
    public int getItemViewType(int position) {
        return mUniqueDataList.get(position) == null ? ITEM_ADD_MORE : ITEM_VIEW;
    }

    @Override
    public int getItemCount() {
        return mUniqueDataList.size();
    }

    @Override
    public void setDataList(List<DataType> dataList) {
        if (mUniqueDataList != null) {
            mUniqueDataList.clear();
            mUniqueDataList.addAll(dataList);
            mUniqueDataList.add(null);
        } else {
            mUniqueDataList.clear();
            mUniqueDataList.addAll(dataList);
            mUniqueDataList.add(null);
        }
        notifyDataSetChanged();
    }

    public void addData(DataType data) {
        mUniqueDataList.remove(null);
        mUniqueDataList.add(data);
        mUniqueDataList.add(null);
        notifyDataSetChanged();
    }

    public void addDataList(List<DataType> list) {
        mUniqueDataList.remove(null);
        for (DataType data : list) {
            mUniqueDataList.add(data);
        }
        mUniqueDataList.add(null);
        notifyDataSetChanged();
    }

    public void setAddClickListener(View.OnClickListener addClickListener) {
        this.mAddClickListener = addClickListener;
    }

    public List<DataType> getDataList(){
        List<DataType> list = new ArrayList<>(mDataList);
        list.remove(null);
        return list;
    }
}

class ListAddMoreHolder<DataType extends ThumbnailEntity> extends DiveTymViewHolder {
    ImageView thumbnail;
    ImageView remove;
    RobotoTextView name;

    public ListAddMoreHolder(DiveTymActivity context, View itemView) {
        super(context, itemView);
        thumbnail = (ImageView) itemView.findViewById(R.id.image_thumbnail);
        remove = (ImageView) itemView.findViewById(R.id.image_remove);
        name = (RobotoTextView) itemView.findViewById(R.id.text_name);
    }

    public void setData(DataType data) {
        name.setText(data.getName());
        Picasso.with(mContext)
                .load(data.getImageUrl())
                .error(R.drawable.dummy_image_error)
                .placeholder(R.drawable.dummy_image_preview)
                .into(thumbnail);
    }
}

class AddMoreHolder extends DiveTymViewHolder {
    ImageView add;

    public AddMoreHolder(DiveTymActivity context, View itemView) {
        super(context, itemView);
        add = (ImageView) itemView.findViewById(R.id.image_add_icon);
    }

}