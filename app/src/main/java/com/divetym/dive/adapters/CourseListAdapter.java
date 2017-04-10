package com.divetym.dive.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.divetym.dive.R;
import com.divetym.dive.activities.base.DiveTymActivity;
import com.divetym.dive.adapters.base.BaseRecyclerAdapter;
import com.divetym.dive.adapters.base.DiveTymViewHolder;
import com.divetym.dive.models.Course;
import com.divetym.dive.view.RobotoTextView;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by kali_root on 4/10/2017.
 */

public class CourseListAdapter extends BaseRecyclerAdapter<CourseHolder, Course> {

    public CourseListAdapter(DiveTymActivity context, List<Course> courses) {
        super(context, courses);
    }


    @Override
    public CourseHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_item_course, parent, false);
        return new CourseHolder(mContext, view);
    }

    @Override
    public void onBindViewHolder(CourseHolder holder, int position) {

    }

}

class CourseHolder extends DiveTymViewHolder<Course> {
    ImageView imgThumbnail;
    RobotoTextView tvTitle;
    RobotoTextView tvDescription;
    RobotoTextView tvPrice;
    Button btnAction;

    public CourseHolder(DiveTymActivity context, View view) {
        super(context, view);
        imgThumbnail = (ImageView) view.findViewById(R.id.image_thumbnails);
        tvTitle = (RobotoTextView) view.findViewById(R.id.text_title);
        tvDescription = (RobotoTextView) view.findViewById(R.id.text_description);
        tvPrice = (RobotoTextView) view.findViewById(R.id.text_price);
        btnAction = (Button) view.findViewById(R.id.button_book_now);
    }

    public void setData(String title, String description, String price, String imgUrl) {
        tvTitle.setText(title);
        tvDescription.setText(description);
        tvPrice.setText(price);
        Picasso.with(mContext)
                .load(imgUrl)
                .centerCrop()
                .placeholder(R.drawable.dummy_image_preview)
                .error(R.drawable.dummy_image_preview)
                .into(imgThumbnail);
    }
}
