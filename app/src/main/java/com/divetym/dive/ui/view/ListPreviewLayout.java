package com.divetym.dive.ui.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

import com.divetym.dive.R;
import com.divetym.dive.ui.activities.base.DiveTymActivity;
import com.divetym.dive.ui.adapters.ListPreviewAdapter;
import com.divetym.dive.ui.adapters.base.BaseRecyclerAdapter;
import com.divetym.dive.models.ListPreview;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kali_root on 4/6/2017.
 */

public class ListPreviewLayout extends CardView {
    public static final String TAG = ListPreviewLayout.class.getSimpleName();
    private RecyclerView recyclerView;
    private RobotoTextView previewTitleTextView;
    private RobotoTextView previewMoreTextView;
    private View emptyItemView;
    private ListPreviewAdapter mAdapter;
    private List<ListPreview> mPreviews;
    private DiveTymActivity mContext;

    public ListPreviewLayout(Context context) {
        super(context);
        initialize(context, null);
    }

    public ListPreviewLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize(context, attrs);
    }

    public ListPreviewLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize(context, attrs);
    }

    private void initialize(Context context, AttributeSet attrs) {
        if (!isInEditMode()) {
            mContext = (DiveTymActivity) context;
        }
        inflate(context, R.layout.view_list_preview, this);
        previewTitleTextView = (RobotoTextView) findViewById(R.id.text_preview_title);
        previewMoreTextView = (RobotoTextView) findViewById(R.id.text_preview_more);
        emptyItemView = findViewById(R.id.empty_item);
        recyclerView = (RecyclerView) findViewById(R.id.list);

        if (attrs != null) {
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ListPreviewLayout);
            if(a.hasValue(R.styleable.ListPreviewLayout_title)){
                String s = a.getString(R.styleable.ListPreviewLayout_title);
                previewTitleTextView.setText(s);
            }
            a.recycle();
        }

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        mPreviews = new ArrayList<>();
        mAdapter = new ListPreviewAdapter(mContext, mPreviews);
        recyclerView.setAdapter(mAdapter);
    }


    public void setPreviewList(List<ListPreview> previews) {
        mPreviews = previews;
        mAdapter.setDataList(mPreviews);
        if (previews != null && previews.size() > 0) {
            emptyItemView.setVisibility(GONE);
        } else {
            emptyItemView.setVisibility(VISIBLE);
        }
    }

    public void setPreviewTitle(String title) {
        previewTitleTextView.setText(title);
    }

    public void setMoreClickListener(OnClickListener moreClickListener) {
        previewMoreTextView.setOnClickListener(moreClickListener);
    }

    public void setItemClickListener(BaseRecyclerAdapter.ItemClickListener itemClickListener) {
        mAdapter.setItemClickListener(itemClickListener);
    }

    public boolean isEmpty() {
        return mPreviews.isEmpty();
    }
}
