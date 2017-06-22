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
    private static final String TAG = ImageSlider.class.getSimpleName();
    private RobotoTextView titleText;
    private RobotoTextView textIndicator;
    private ViewPager viewPager;
    private SliderAdapter adapter;
    private CircleIndicator circleIndicator;

    private ViewPager.OnPageChangeListener onPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            titleText.setText(adapter.getPageTitle(position));
            textIndicator.setText(position + 1 + "/" + adapter.getCount());
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

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
        titleText = (RobotoTextView) findViewById(R.id.text_title);
        textIndicator = (RobotoTextView) findViewById(R.id.text_indicator_right_top);
        circleIndicator = (CircleIndicator) findViewById(R.id.indicator);

        adapter = new SliderAdapter(context);

        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(onPageChangeListener);
        circleIndicator.setViewPager(viewPager);
        adapter.registerDataSetObserver(circleIndicator.getDataSetObserver());
    }

    @Override
    protected void onDetachedFromWindow() {
        adapter.unregisterDataSetObserver(circleIndicator.getDataSetObserver());
        super.onDetachedFromWindow();
    }

    public <DataType extends ThumbnailEntity> void setDataList(List<DataType> dataList) {
        adapter.setDataList(dataList);
        onPageChangeListener.onPageSelected(viewPager.getCurrentItem());
    }

    public void showCircleIndicator(boolean show) {
        circleIndicator.setVisibility(show ? VISIBLE : GONE);
    }

    public void showTextIndicator(boolean show) {
        textIndicator.setVisibility(show ? VISIBLE : GONE);
    }
    public void showTitle(boolean show){
        titleText.setVisibility(show? VISIBLE:GONE);
    }
}
