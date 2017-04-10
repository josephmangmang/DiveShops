package com.divetym.dive.ui.view;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;

import com.divetym.dive.R;
import com.divetym.dive.ui.activities.base.DiveTymActivity;
import com.divetym.dive.ui.adapters.ListPreviewAdapter;
import com.divetym.dive.ui.models.ListPreview;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kali_root on 4/6/2017.
 */

public class ListPreviewLayout extends RelativeLayout {
    public static final String TAG = ListPreviewLayout.class.getSimpleName();
    private RecyclerView mRecyclerView;
    private RobotoTextView tvPreviewTitle;
    private RobotoTextView tvPreviewMore;
    private ListPreviewAdapter mAdapter;
    private List<ListPreview> mPreviews;
    private DiveTymActivity mContext;

    public ListPreviewLayout(Context context) {
        super(context);
        initialize(context);
    }

    public ListPreviewLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize(context);
    }

    public ListPreviewLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize(context);
    }

    private void initialize(Context context) {
        mContext = (DiveTymActivity) context;
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.view_list_preview, this, true);
        tvPreviewTitle = (RobotoTextView) findViewById(R.id.text_preview_title);
        tvPreviewMore = (RobotoTextView) findViewById(R.id.text_preview_more);
        mRecyclerView = (RecyclerView) findViewById(R.id.list);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(layoutManager);
        mPreviews = new ArrayList<>();
        mAdapter = new ListPreviewAdapter(mContext, mPreviews);
        mRecyclerView.setAdapter(mAdapter);
    }


    public void setPreviewList(List<ListPreview> previews) {
        mPreviews = previews;
        mAdapter.setDataList(mPreviews);
    }

    public void setPreviewTitle(String title) {
        tvPreviewTitle.setText(title);
    }

    public void setMoreClickListener(OnClickListener moreClickListener) {
        tvPreviewMore.setOnClickListener(moreClickListener);
    }

}
