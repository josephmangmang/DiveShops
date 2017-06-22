package com.divetym.dive.ui.adapters;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.divetym.dive.GlideApp;
import com.divetym.dive.R;
import com.divetym.dive.models.common.ThumbnailEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kali_root on 6/15/2017.
 */

public class SliderAdapter<DataType extends ThumbnailEntity> extends PagerAdapter {
    private List<DataType> dataList;
    private LayoutInflater mInflater;
    private Context context;

    public SliderAdapter(Context context) {
        this(context, null);
    }

    public SliderAdapter(Context context, List<DataType> dataList) {
        this.context = context;
        mInflater = LayoutInflater.from(context);
        if (dataList == null) {
            this.dataList = new ArrayList<>();
        } else {
            this.dataList = dataList;
        }
    }

    @Override
    public int getCount() {
        return dataList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return dataList.size() > 0 ? dataList.get(position).getName() : "";
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ImageView view = (ImageView) mInflater.inflate(R.layout.view_slider, container, false);
        DataType data = dataList.get(position);
        GlideApp.with(context)
                .load(data.getImageUrl())
                .thumbnail(0.1f)
                .placeholder(R.drawable.dummy_image_preview)
                .error(R.drawable.dummy_image_error)
                .into(view);
        container.addView(view);
        return view;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    public void setDataList(List<DataType> dataList) {
        this.dataList = dataList;
        notifyDataSetChanged();
    }

}
