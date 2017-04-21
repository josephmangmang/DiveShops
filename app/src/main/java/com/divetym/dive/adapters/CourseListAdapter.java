package com.divetym.dive.adapters;

import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.divetym.dive.R;
import com.divetym.dive.activities.base.DiveTymActivity;
import com.divetym.dive.adapters.base.BaseRecyclerAdapter;
import com.divetym.dive.adapters.base.DiveTymViewHolder;
import com.divetym.dive.interfaces.OnLoadMoreListener;
import com.divetym.dive.models.DiveShopCourse;
import com.divetym.dive.view.RobotoTextView;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by kali_root on 4/10/2017.
 */

public class CourseListAdapter extends BaseRecyclerAdapter<RecyclerView.ViewHolder, DiveShopCourse> implements OnLoadMoreListener {

    private static final String TAG = CourseListAdapter.class.getSimpleName();
    private static final int VIEW_ITEM = 0;
    private static final int VIEW_PROG = 1;
    private int mVisibleThreshold = 5;
    private int mLastVisibleItem;
    private int mTotalItemCount;
    private boolean loading;
    private Handler mHandler;
    private OnLoadMoreListener mLoadMoreListener;
    private OnLoadMoreListener mAdapterLoadMoreListener;

    public CourseListAdapter(DiveTymActivity context, List<DiveShopCourse> courses) {
        this(context, courses, null);
    }

    public CourseListAdapter(DiveTymActivity context, List<DiveShopCourse> courses, RecyclerView recyclerView) {
        super(context, courses);
        mHandler = new Handler();
        if (recyclerView != null) {
            if (recyclerView.getLayoutManager() instanceof LinearLayoutManager) {
                final LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                mAdapterLoadMoreListener = this;
                recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                    @Override
                    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                        super.onScrolled(recyclerView, dx, dy);
                        if (dy > 0) {
                            mTotalItemCount = layoutManager.getItemCount();
                            mLastVisibleItem = layoutManager.findLastVisibleItemPosition();
                            if (!loading && mTotalItemCount <= (mLastVisibleItem + mVisibleThreshold)) {
                                if (mLoadMoreListener != null) {
                                    mAdapterLoadMoreListener.onLoadMore(mDataList.size());
                                }
                                loading = true;
                            }
                        }
                    }
                });
            }
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        RecyclerView.ViewHolder holder;
        if (viewType == VIEW_ITEM) {
            view = LayoutInflater.from(mContext).inflate(R.layout.view_item_course, parent, false);
            holder = new CourseHolder(mContext, view);
        } else {
            view = LayoutInflater.from(mContext).inflate(R.layout.view_item_progress, parent, false);
            holder = new LoadingHolder(mContext, view);
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == VIEW_ITEM) {
            DiveShopCourse course = getItem(position);
            CourseHolder itemHolder = (CourseHolder) holder;
            itemHolder.mData = course;
            itemHolder.mItemClickListener = mItemClickListener;
            itemHolder.setData(course.getName(), course.getDescription(), course.getPrice().toString(), course.getPhotoCoverUrl());
        } else {
            Log.w(TAG, "onBindViewHolder: progress view");
        }
    }

    @Override
    public int getItemViewType(int position) {
        return mDataList.get(position) != null ? VIEW_ITEM : VIEW_PROG;
    }


    public void setLoaded() {
        loading = false;
    }

    public void setLoadMoreListener(OnLoadMoreListener mLoadMoreListener) {
        this.mLoadMoreListener = mLoadMoreListener;
    }

    @Override
    public void onLoadMore(final int totalItemCount) {
        Log.d(TAG, "onLoadMore..");
        if (mDataList != null) {
            mDataList.add(null);
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    notifyItemInserted(mDataList.size() - 1);
                    mHandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if (mLoadMoreListener != null) {
                                mLoadMoreListener.onLoadMore(totalItemCount);
                            }
                        }
                    }, 700);
                }
            });
        }
    }

    public void removeProgress() {
        if (!loading) return;
        mDataList.remove(mDataList.size() - 1);
        notifyItemRemoved(mDataList.size());
    }

    public void addData(List<DiveShopCourse> courses) {
        removeProgress();
        mDataList.addAll(courses);
        notifyDataSetChanged();
        setLoaded();
    }
}

class CourseHolder extends DiveTymViewHolder<DiveShopCourse> {
    ImageView ivThumbnail;
    RobotoTextView tvTitle;
    RobotoTextView tvDescription;
    RobotoTextView tvPrice;
    Button btnAction;

    public CourseHolder(DiveTymActivity context, View view) {
        super(context, view);
        ivThumbnail = (ImageView) view.findViewById(R.id.image_thumbnail);
        tvTitle = (RobotoTextView) view.findViewById(R.id.text_title);
        tvDescription = (RobotoTextView) view.findViewById(R.id.text_description);
        tvPrice = (RobotoTextView) view.findViewById(R.id.text_price);
        btnAction = (Button) view.findViewById(R.id.button_book_now);
        btnAction.setOnClickListener(this);
        view.setOnClickListener(this);
    }

    public void setData(String title, String description, String price, String imgUrl) {
        tvTitle.setText(title);
        tvDescription.setText(description);
        tvPrice.setText(price);
        Picasso.with(mContext)
                .load(imgUrl)
                .placeholder(R.drawable.dummy_image_preview)
                .error(R.drawable.dummy_image_error)
                .into(ivThumbnail);
    }
}

class LoadingHolder extends DiveTymViewHolder {

    public LoadingHolder(DiveTymActivity context, View itemView) {
        super(context, itemView);
    }
}

