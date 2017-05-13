package com.divetym.dive.adapters.base;

import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.divetym.dive.R;
import com.divetym.dive.activities.base.DiveTymActivity;
import com.divetym.dive.interfaces.OnLoadMoreListener;
import com.divetym.dive.models.DiveShopCourse;

import java.util.List;

/**
 * Created by kali_root on 4/19/2017.
 */

public abstract class EndlessListAdapter<DataType> extends BaseRecyclerAdapter<RecyclerView.ViewHolder, DataType>
        implements OnLoadMoreListener {
    public static final String TAG = EndlessListAdapter.class.getSimpleName();
    private static final int VIEW_ITEM = 0;
    private static final int VIEW_PROG = 1;
    private int mVisibleThreshold = 5;
    private int mLastVisibleItem;
    private int mTotalItemCount;
    private boolean loading;
    private Handler mHandler;
    private OnLoadMoreListener mLoadMoreListener;
    private OnLoadMoreListener mAdapterLoadMoreListener;

    public EndlessListAdapter(DiveTymActivity context, List<DataType> dataList, RecyclerView recyclerView) {
        super(context, dataList);
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
        RecyclerView.ViewHolder holder;
        if (viewType == VIEW_ITEM) {
            holder = onCreateViewHolder(parent);
        } else {
            View view = LayoutInflater.from(mContext).inflate(R.layout.view_item_progress, parent, false);
            holder = new LoadingHolder(view);
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == VIEW_ITEM) {
            onBindViewHolder(holder, getItem(position), position);
        } else {
            Log.w(TAG, "onBindViewHolder: progress view");
        }
    }

    protected abstract RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent);


    public abstract void onBindViewHolder(RecyclerView.ViewHolder viewHolder, DataType object, int position);

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

    public void addData(List<DataType> courses) {
        removeProgress();
        mDataList.addAll(courses);
        notifyDataSetChanged();
        setLoaded();
    }

    public void replaceData(List<DataType> data){
        removeProgress();
        mDataList.clear();
        mDataList.addAll(data);
        notifyDataSetChanged();
        setLoaded();

    }
    class LoadingHolder extends RecyclerView.ViewHolder {

        public LoadingHolder(View itemView) {
            super(itemView);
        }
    }
}
