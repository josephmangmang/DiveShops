package com.divetym.dive.ui.fragments.diver;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.divetym.dive.BuildConfig;
import com.divetym.dive.R;
import com.divetym.dive.models.DiveShop;
import com.divetym.dive.models.response.DiveShopListResponse;
import com.divetym.dive.rest.ApiClient;
import com.divetym.dive.ui.activities.diver.DiverDiveShopActivity;
import com.divetym.dive.ui.adapters.DiveShopListAdapter;
import com.divetym.dive.ui.fragments.base.DiveTymListFragment;
import com.divetym.dive.ui.view.ToastAlert;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMapOptions;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by kali_root on 6/19/2017.
 */

public class DiveShopListFragment extends DiveTymListFragment<DiveShopListAdapter, DiveShop, DiveShopListResponse> {
    private static final String TAG = DiveShopListFragment.class.getSimpleName();
    public static int MAP_RADIUS_DIVE_SHOP = 20;
    LatLng address = new LatLng(0, 0);
    private String mQuery;
    private boolean reset;
    private OnListChangeListener mOnListChangeListener;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    protected View getFragmentLayout(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.fragment_dive_shop_list, container, false);
    }

    @Override
    protected void onFabClicked() {

    }

    @Override
    protected void initializeAdapter() {
        mAdapter = new DiveShopListAdapter(mContext, mDataList, mRecyclerView);
        mAdapter.setItemClickListener(this);
        mAdapter.setLoadMoreListener(this);
        showFab(false);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.search, menu);
        MenuItem searchItem = menu.findItem(R.id.menu_search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                mQuery = query;
                offset = 0;
                reset = true;
                requestData();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                mQuery = newText;
                if (TextUtils.isEmpty(mQuery)) {
                    offset = 0;
                    reset = true;
                    requestData();
                }
                return true;
            }
        });
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    protected void requestData() {
        ApiClient.getApiInterface().getDiveShops(address.latitude, address.longitude, MAP_RADIUS_DIVE_SHOP, offset, mQuery)
                .enqueue(new Callback<DiveShopListResponse>() {
                    @Override
                    public void onResponse(Call<DiveShopListResponse> call, Response<DiveShopListResponse> response) {
                        onRequestResponse(response.body());
                    }

                    @Override
                    public void onFailure(Call<DiveShopListResponse> call, Throwable t) {
                        showProgress(false);
                        if (BuildConfig.DEBUG) {
                            Log.e(TAG, call.request().toString());
                            t.printStackTrace();
                        }
                        Log.e(TAG, "getData onFailiure: " + t.getMessage());
                    }
                });
    }

    @Override
    protected void onRequestResponse(DiveShopListResponse response) {
        showProgress(false);
        if (response != null && !response.isError()) {
            if (reset) {
                mAdapter.setDataList(response.getDiveShops());
            } else {
                mAdapter.addData(response.getDiveShops());
            }
            if (mOnListChangeListener != null) {
                mOnListChangeListener.onListChange(mDataList, reset);
            }
            reset = !reset;
        } else if (response != null) {
            ToastAlert.makeText(mContext, response.getMessage(), ToastAlert.LENGTH_SHORT);
        }
        setEmpty(mDataList.size() == 0);
    }

    public void setOnListChangeListener(OnListChangeListener onListChangeListener) {
        this.mOnListChangeListener = onListChangeListener;
    }

    @Override
    public void onItemClick(DiveShop object, View view, int i) {
        DiverDiveShopActivity.launch(mContext, object.getDiveShopUid());
    }

    public interface OnListChangeListener {
        void onListChange(List list, boolean reset);
    }
}
