package com.divetym.dive.ui.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.divetym.dive.GlideApp;
import com.divetym.dive.R;
import com.divetym.dive.ui.activities.base.DiveTymActivity;
import com.divetym.dive.ui.adapters.base.DiveTymViewHolder;
import com.divetym.dive.ui.adapters.base.EndlessListAdapter;
import com.divetym.dive.common.SessionManager;
import com.divetym.dive.interfaces.OnLoadMoreListener;
import com.divetym.dive.models.DiveShopCourse;
import com.divetym.dive.models.User;
import com.divetym.dive.ui.view.RobotoTextView;

import java.util.List;

/**
 * Created by kali_root on 4/10/2017.
 */

public class CourseListAdapter extends EndlessListAdapter<DiveShopCourse> implements OnLoadMoreListener {

    private static final String TAG = CourseListAdapter.class.getSimpleName();
    private String actionText = "";
    private boolean showActionButton;

    public CourseListAdapter(DiveTymActivity context, List<DiveShopCourse> dataList, RecyclerView recyclerView) {
        super(context, dataList, recyclerView);
    }

    @Override
    protected RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.view_item_course, parent, false);
        return new CourseHolder(mContext, view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, DiveShopCourse course, int i) {
        CourseHolder itemHolder = (CourseHolder) viewHolder;
        itemHolder.mData = course;
        itemHolder.mItemClickListener = mItemClickListener;
        itemHolder.setData(course.getName(), course.getDescription(), course.getPrice().toString(), course.getImageUrl());
        itemHolder.btnAction.setText(actionText);
        itemHolder.btnAction.setVisibility(showActionButton ? View.VISIBLE : View.GONE);
    }

    public void setActionButton(String actionText) {
        this.actionText = actionText;
    }

    public void showActionButton(boolean show) {
        this.showActionButton = show;
    }

    static class CourseHolder extends DiveTymViewHolder<DiveShopCourse> {
        ImageView thumbnail;
        RobotoTextView title;
        RobotoTextView description;
        RobotoTextView price;
        Button btnAction;

        public CourseHolder(DiveTymActivity context, View view) {
            super(context, view);
            thumbnail = (ImageView) view.findViewById(R.id.image_thumbnail);
            title = (RobotoTextView) view.findViewById(R.id.text_title);
            description = (RobotoTextView) view.findViewById(R.id.text_description);
            price = (RobotoTextView) view.findViewById(R.id.text_price);
            btnAction = (Button) view.findViewById(R.id.button_book_now);
            btnAction.setOnClickListener(this);
            view.setOnClickListener(this);
        }

        public void setData(String title, String description, String price, String imgUrl) {
            this.title.setText(title);
            this.description.setText(description);
            this.price.setText("PHP" + price);
            GlideApp.with(mContext)
                    .load(imgUrl)
                    .thumbnail(0.1f)
                    .placeholder(R.drawable.dummy_image_preview)
                    .error(R.drawable.dummy_image_error)
                    .into(thumbnail);
        }
    }
}

