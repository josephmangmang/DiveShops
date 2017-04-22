package com.divetym.dive.fragments;

import android.view.View;

import com.divetym.dive.adapters.SearchListAdapter;
import com.divetym.dive.adapters.base.BaseRecyclerAdapter;
import com.divetym.dive.fragments.base.DiveTymListFragment;
import com.divetym.dive.models.DiveSite;
import com.divetym.dive.models.response.DiveSiteListResponse;

import java.util.List;

/**
 * Created by kali_root on 4/21/2017.
 */

public class SearchListFragment extends DiveTymListFragment<SearchListAdapter, DiveSite, DiveSiteListResponse>
        implements BaseRecyclerAdapter.ItemClickListener<DiveSite> {

    @Override
    protected void initializeAdapter() {
        mAdapter = new SearchListAdapter(mContext, mDataList);
        mAdapter.setItemClickListener(this);
    }

    @Override
    protected void requestData() {
    // TODO: 4/21/2017 request search
    }

    @Override
    protected void onRequestResponse(DiveSiteListResponse response) {

    }


    @Override
    public void onItemClick(DiveSite object, View view) {

    }

    @Override
    public void onActionClick(DiveSite object, View view) {

    }
}
