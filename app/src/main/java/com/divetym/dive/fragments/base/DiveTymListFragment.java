package com.divetym.dive.fragments.base;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
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
    public static final String EXTRA_ADD_BUTTON = "com.divetym.dive.EXTRA_ADD_BUTTON";

    @BindView(R.id.list)
    protected RecyclerView mRecyclerView;
    @BindView(R.id.text_empty)
    protected RobotoTextView mEmptyText;
    @BindView(R.id.progress)
    ContentLoadingProgressBar mProgressBar;
    @BindView(R.id.fab_add)
    protected FloatingActionButton mFab;
    protected Adapter mAdapter;
    protected LinearLayoutManager mLayoutManager;
    protected List<DataType> mDataList;
    protected ApiInterface mApiService;
    protected int offset = 0;
    protected String shopUid;
    protected boolean showAddButton;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null) {
            mDataList = args.getParcelableArrayList(EXTRA_LIST);
            showAddButton = args.getBoolean(EXTRA_ADD_BUTTON, false);
            if (mDataList == null) {
                mDataList = new ArrayList<>();
            }
            offset = mDataList.size();
        } else {
            mDataList = new ArrayList<>();
        }
        mApiService = ApiClient.getApiInterface();
        mLayoutManager = new LinearLayoutManager(mContext);
        shopUid = mSessionManager.getDiveShopUid();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        ButterKnife.bind(this, view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mFab.setVisibility(showAddButton ? View.VISIBLE : View.GONE);
        initializeAdapter();
        mRecyclerView.setAdapter(mAdapter);

        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onFabClicked();
            }
        });
        if (mDataList.size() == 0) {
            loadData();
        }
        return view;
    }

    protected abstract void onFabClicked();

    protected abstract void initializeAdapter();

    private void loadData() {
        if (shopUid == null) {
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
        offset = totalItemCount;
        loadData();
    }

    @Override
    public void onItemClick(DataType object, View view, int i) {

    }

    @Override
    public void onItemLongClick(DataType object, View view, int position) {

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

    public void showFab(boolean show) {
        if (show) {
            mFab.show();
        } else {
            mFab.hide();
        }
    }
}
