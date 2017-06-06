package com.divetym.dive.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.divetym.dive.R;
import com.divetym.dive.common.DiveShopAddress;
import com.divetym.dive.fragments.base.DiveTymFragment;
import com.divetym.dive.interfaces.LocationChangedListener;
import com.divetym.dive.models.DiveShop;
import com.divetym.dive.models.response.Response;
import com.divetym.dive.rest.ApiClient;
import com.divetym.dive.view.ToastAlert;

import java.math.BigDecimal;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import xyz.sahildave.widget.SearchViewLayout;

import static com.divetym.dive.fragments.DiveShopFragment.EXTRA_DIVE_SHOP;

/**
 * Created by kali_root on 6/5/2017.
 */

public class EditDiveShopFragment extends DiveTymFragment {
    private static final String TAG = EditDiveShopFragment.class.getSimpleName();
    @BindView(R.id.edit_name)
    EditText mName;
    @BindView(R.id.edit_contact_number)
    EditText mContactNumber;
    @BindView(R.id.edit_price_per_dive)
    EditText mPricePerDive;
    @BindView(R.id.edit_description)
    EditText mDescription;
    @BindView(R.id.edit_special_service)
    EditText mSpecialService;
    @BindView(R.id.search_view_container)
    SearchViewLayout mSearchViewLayout;
    private DiveShopAddress mAddress;

    private DiveShop mDiveShop;
    private LocationChangedListener mLocationChangedListener = new LocationChangedListener() {
        @Override
        public void onLocationChanged(DiveShopAddress address) {
            mAddress = address;
            mSearchViewLayout.setCollapsedHint(mAddress.getFullAddress());
            mSearchViewLayout.setExpandedText(mAddress.getFullAddress());
            mSearchViewLayout.moveExpandedTextCursorToEnd();
        }
    };

    public static EditDiveShopFragment newInstance(DiveShop diveShop) {
        EditDiveShopFragment fragment = new EditDiveShopFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(EXTRA_DIVE_SHOP, diveShop);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        if (getArguments() != null) {
            mDiveShop = getArguments().getParcelable(EXTRA_DIVE_SHOP);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dive_shop_profile_edit, container, false);
        ButterKnife.bind(this, view);
        SearchMap searchMap = new SearchMap();
        searchMap.setLocationChangedListener(mLocationChangedListener);
        mSearchViewLayout.setExpandedContentFragment(mContext, searchMap);
        mSearchViewLayout.setHint(getString(R.string.hint_search_address));
        mSearchViewLayout.handleToolbarAnimation(mContext.getToolbar());
        if (mDiveShop != null) {
            try {
                mName.setText(mDiveShop.getName());
                mSearchViewLayout.setCollapsedHint(mDiveShop.getAddress());
                mSearchViewLayout.setExpandedText(mDiveShop.getAddress());
                mContactNumber.setText(mDiveShop.getContactNumber());
                mPricePerDive.setText(mDiveShop.getPricePerDive().toString());
                mDescription.setText(mDiveShop.getDescription());
                mSpecialService.setText(mDiveShop.getSpecialService());
            } catch (NullPointerException e) {
                Log.e(TAG, "Diveshop: " + e.getMessage());
            }
        }
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.create_save, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_save) {
            onSaveClicked();
        }
        return false;
    }

    private void onSaveClicked() {
        if (mDiveShop == null) return;
        String name = mName.getText().toString();
        String contactNumber = mContactNumber.getText().toString();
        String pricePerDive = mPricePerDive.getText().toString();
        String description = mDescription.getText().toString();
        String specialService = mSpecialService.getText().toString();
        if (TextUtils.isEmpty(name)) {
            mName.setError(getString(R.string.error_field_required));
            mName.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(pricePerDive)) {
            mPricePerDive.setError(getString(R.string.error_field_required));
            mPricePerDive.requestFocus();
            return;
        }
        if (mAddress != null) {
            mDiveShop.setAddress(mAddress.getFullAddress());
            mDiveShop.setLatitiude(mAddress.getLatitude());
            mDiveShop.setLongitude(mAddress.getLongitude());
        } else if (mDiveShop.getAddress() == null) {
            Toast.makeText(mContext, "No address selected", Toast.LENGTH_SHORT).show();
            return;
        }
        mDiveShop.setName(name);
        mDiveShop.setContactNumber(contactNumber);
        mDiveShop.setPricePerDive(new BigDecimal(pricePerDive));
        mDiveShop.setDescription(description);
        mDiveShop.setSpecialService(specialService);

        ApiClient.getApiInterface().updateDiveShop(mDiveShop.getDiveShopUid(), mDiveShop)
                .enqueue(new Callback<Response>() {
                    @Override
                    public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                        if (response.body() != null) {
                            if (!response.body().isError()) {
                                Intent resultData = new Intent();
                                resultData.putExtra(EXTRA_DIVE_SHOP, mDiveShop);
                                mContext.setResult(Activity.RESULT_OK, resultData);
                                mContext.finish();
                            } else {
                                Toast.makeText(mContext, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<Response> call, Throwable t) {
                        Toast.makeText(mContext, t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
