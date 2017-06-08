package com.divetym.dive.interfaces;

import android.view.View;

import com.divetym.dive.ui.adapters.base.BaseRecyclerAdapter;

/**
 * Created by kali_root on 5/11/2017.
 */

public class ItemClickListener<DataType> implements BaseRecyclerAdapter.ItemClickListener<DataType> {

    @Override
    public void onItemClick(DataType object, View view, int i) {
        
    }

    @Override
    public void onItemLongClick(DataType object, View view, int position) {

    }

    @Override
    public void onActionClick(DataType object, View view) {

    }
}
