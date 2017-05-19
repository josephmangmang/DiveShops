package com.divetym.dive.view;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.divetym.dive.R;
import com.divetym.dive.activities.base.DiveTymActivity;
import com.divetym.dive.adapters.ListAddMoreAdapter;
import com.divetym.dive.models.common.ThumbnailEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kali_root on 5/7/2017.
 */

public class ListAddMoreLayout<DataType extends ThumbnailEntity> extends LinearLayout {
    private RecyclerView mRecyclerView;
    private RobotoTextView mTitle;
    private ListAddMoreAdapter mAdapter;
    private DiveTymActivity mContext;

    public ListAddMoreLayout(Context context) {
        super(context);
        init(context);
    }


    public ListAddMoreLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ListAddMoreLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        if (!isInEditMode()) {
            mContext = (DiveTymActivity) context;
        }
        inflate(context, R.layout.view_list_add_more, this);
        mTitle = (RobotoTextView) findViewById(R.id.text_title);
        mRecyclerView = (RecyclerView) findViewById(R.id.list);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);

        mAdapter = new ListAddMoreAdapter(mContext);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(mAdapter);
    }

    public void setTitle(String title) {
        mTitle.setText(title);
    }

    public void setDataList(List<DataType> list) {
        mAdapter.setDataList(list);
    }

    public void addDataList(List<DataType> list) {
        mAdapter.addDataList(list);
    }

    public void addData(DataType object) {
        mAdapter.addData(object);
    }

    public void setAddClickListener(View.OnClickListener addClickListener) {
        mAdapter.setAddClickListener(addClickListener);
    }

    public List<DataType> getDataList() {
        return mAdapter.getDataList();
    }
}
