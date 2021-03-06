package com.divetym.dive.ui.view;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import com.divetym.dive.R;
import com.divetym.dive.ui.activities.base.DiveTymActivity;
import com.divetym.dive.ui.adapters.ListAddMoreAdapter;
import com.divetym.dive.models.common.ThumbnailEntity;

import java.util.List;

/**
 * Created by kali_root on 5/7/2017.
 */

public class ListAddMoreLayout<DataType extends ThumbnailEntity> extends LinearLayout {
    private RecyclerView recyclerView;
    private RobotoTextView titleTextView;
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
        titleTextView = (RobotoTextView) findViewById(R.id.text_title);
        recyclerView = (RecyclerView) findViewById(R.id.list);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);

        mAdapter = new ListAddMoreAdapter(mContext);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(mAdapter);
    }

    public void setTitle(String title) {
        titleTextView.setText(title);
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
