package com.divetym.dive.fragments.base;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.divetym.dive.R;
import com.divetym.dive.activities.base.AuthenticatedActivity;
import com.divetym.dive.adapters.base.BaseRecyclerAdapter;
import com.divetym.dive.adapters.base.BaseRecyclerAdapter.ItemClickListener;
import com.divetym.dive.fragments.CourseListFragment;
import com.divetym.dive.interfaces.OnLoadMoreListener;
import com.divetym.dive.models.response.Response;
import com.divetym.dive.rest.ApiClient;
import com.divetym.dive.rest.ApiInterface;
import com.divetym.dive.view.RobotoTextView;
import com.divetym.dive.view.ToastAlert;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by kali_root on 4/15/2017.
 */

public abstract class DiveTymListFragment<Adapter extends BaseRecyclerAdapter, DataType extends Parcelable, Rspnse extends Response>
        extends DiveTymFragment implements OnLoadMoreListener, ItemClickListener<DataType> {
    private static final String TAG = CourseListFragment.class.getSimpleName();
    public static final String EXTRA_LIST = "com.divetym.dive.EXTRA_LIST";

    @BindView(R.id.list)
    protected RecyclerView mRecyclerView;
    @BindView(R.id.text_empty)
    protected RobotoTextView mEmptyText;
    @BindView(R.id.progress)
    ContentLoadingProgressBar mProgressBar;
    protected Adapter mAdapter;
    protected LinearLayoutManager mLayoutManager;
    protected List<DataType> mDataList;
    protected ApiInterface mApiService;
    protected int mOffset = 0;
    protected String mShopUid;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null) {
            mDataList = args.getParcelableArrayList(EXTRA_LIST);
            if (mDataList == null) {
                mDataList = new ArrayList<>();
            }
            mOffset = mDataList.size();
        } else {
            mDataList = new ArrayList<>();
        }
        mApiService = ApiClient.getApiInterface();
        mLayoutManager = new LinearLayoutManager(mContext);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        ButterKnife.bind(this, view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(mLayoutManager);
        initializeAdapter();
        mRecyclerView.setAdapter(mAdapter);
        if (mDataList.size() == 0) {
            loadData();
        }
        return view;
    }

    protected abstract void initializeAdapter();

    private void loadData() {
        mShopUid = mSessionManager.getDiveShopUid();
        if (mShopUid == null) {
            new ToastAlert(mContext)
                    .setMessage(R.string.error_empty_account_details)
                    .show();
            ((AuthenticatedActivity) mContext).logOut();
            return;
        }
        showProgress(true);
        requestData();
    }

    protected abstract void requestData();

    protected abstract void onRequestResponse(Rspnse response);

    protected void setEmpty(boolean empty) {
        mEmptyText.setVisibility(empty ? View.VISIBLE : View.GONE);
    }

    protected void setEmptyTextMessage(String message) {
        mEmptyText.setText(message);
    }

    @Override
    public void onLoadMore(int totalItemCount) {
        Log.d(TAG, "onLoadMore: totalItemCount = " + totalItemCount);
        mOffset = totalItemCount;
        loadData();
    }

    @Override
    public void onItemClick(DataType object, View view) {

    }

    @Override
    public void onActionClick(DataType object, View view) {

    }

    public void showProgress(boolean show) {
        if (show) {
            mProgressBar.show();
        } else {
            mProgressBar.hide();
        }
    }
}
