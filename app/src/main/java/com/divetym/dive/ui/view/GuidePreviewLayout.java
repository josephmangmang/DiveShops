package com.divetym.dive.ui.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

import com.divetym.dive.R;
import com.divetym.dive.models.Guide;
import com.divetym.dive.ui.activities.base.DiveTymActivity;
import com.divetym.dive.ui.adapters.GuidePreviewAdapter;
import com.divetym.dive.ui.adapters.base.BaseRecyclerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kali_root on 6/15/2017.
 */

public class GuidePreviewLayout extends CardView {
    public static final String TAG = GuidePreviewLayout.class.getSimpleName();
    private RecyclerView recyclerView;
    private RobotoTextView previewTitleTextView;
    private RobotoTextView previewMoreTextView;
    private View emptyItemView;
    private GuidePreviewAdapter mAdapter;
    private DiveTymActivity mContext;

    public GuidePreviewLayout(Context context) {
        super(context);
        initialize(context, null);
    }

    public GuidePreviewLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize(context, attrs);
    }

    public GuidePreviewLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize(context, attrs);
    }

    private void initialize(Context context, AttributeSet attrs) {
        if (!isInEditMode()) {
            mContext = (DiveTymActivity) context;
        }
        inflate(context, R.layout.view_guides_preview, this);
        previewTitleTextView = (RobotoTextView) findViewById(R.id.text_preview_title);
        previewMoreTextView = (RobotoTextView) findViewById(R.id.text_preview_more);
        emptyItemView = findViewById(R.id.empty_item);
        recyclerView = (RecyclerView) findViewById(R.id.list);

        if (attrs != null) {
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.GuidePreviewLayout);
            if (a.hasValue(R.styleable.GuidePreviewLayout_title)) {
                String s = a.getString(R.styleable.GuidePreviewLayout_title);
                previewTitleTextView.setText(s);
            }
            a.recycle();
        }

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        mAdapter = new GuidePreviewAdapter(mContext);
        recyclerView.setAdapter(mAdapter);
    }


    public void setGuides(List guides) {
        mAdapter.setDataList(guides);
        if (guides != null && guides.size() > 0) {
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
        return mAdapter.isEmpty();
    }
}
