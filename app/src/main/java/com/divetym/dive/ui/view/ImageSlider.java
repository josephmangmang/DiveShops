package com.divetym.dive.ui.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

import com.divetym.dive.R;
import com.divetym.dive.models.common.ThumbnailEntity;
import com.divetym.dive.ui.adapters.SliderAdapter;


import java.util.List;

import me.relex.circleindicator.CircleIndicator;

/**
 * Created by kali_root on 6/15/2017.
 */

public class ImageSlider extends RelativeLayout {
    private ViewPager viewPager;
    private SliderAdapter adapter;
    private CircleIndicator indicator;

    public ImageSlider(Context context) {
        super(context);
        init(context);
    }

    public ImageSlider(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ImageSlider(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        inflate(context, R.layout.view_image_slider, this);
        viewPager = (ViewPager) findViewById(R.id.view_pager);
        adapter = new SliderAdapter(context);
        viewPager.setAdapter(adapter);
        indicator = (CircleIndicator) findViewById(R.id.indicator);
        indicator.setViewPager(viewPager);
        adapter.registerDataSetObserver(indicator.getDataSetObserver());
    }

    @Override
    protected void onDetachedFromWindow() {
        adapter.unregisterDataSetObserver(indicator.getDataSetObserver());
        super.onDetachedFromWindow();
    }

    public <DataType extends ThumbnailEntity> void setDataList(List<DataType> dataList) {
        adapter.setDataList(dataList);
    }
}
