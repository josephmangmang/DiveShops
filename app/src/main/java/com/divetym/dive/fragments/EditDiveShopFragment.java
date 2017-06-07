package com.divetym.dive.fragments;

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
import com.divetym.dive.activities.SearchMapActivity;
import com.divetym.dive.models.DiveShopAddress;
import com.divetym.dive.fragments.base.DiveTymFragment;
import com.divetym.dive.models.DiveShop;
import com.divetym.dive.models.response.Response;
import com.divetym.dive.rest.ApiClient;
import com.google.android.gms.maps.model.LatLng;

import java.math.BigDecimal;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;

import static android.app.Activity.RESULT_OK;
import static com.divetym.dive.fragments.DiveShopFragment.EXTRA_DIVE_SHOP;

/**
 * Created by kali_root on 6/5/2017.
 */

public class EditDiveShopFragment extends DiveTymFragment {
    private static final String TAG = EditDiveShopFragment.class.getSimpleName();
    public static final int REQUEST_SELECT_LOCATION = 1;
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
    @BindView(R.id.edit_address)
    EditText mAddressLocation;
    private DiveShopAddress mDiveShopAddress;
    private DiveShop mDiveShop;

    @OnClick(R.id.edit_address)
    public void changedLocation() {
        Intent intent = new Intent(mContext, SearchMapActivity.class);
        intent.putExtra(DiveShopAddress.EXTRA_DIVE_SHOP_ADDRESS, mDiveShopAddress);
        startActivityForResult(intent, REQUEST_SELECT_LOCATION);
    }

    public static EditDiveShopFragment newInstance(DiveShop diveShop) {
        EditDiveShopFragment fragment = new EditDiveShopFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(EXTRA_DIVE_SHOP, diveShop);
        fragment.setArguments(bundle);
        return fragment;
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
        if (mDiveShop != null) {
            try {
                mName.setText(mDiveShop.getName());
                mAddressLocation.setText(mDiveShop.getAddress());
                mContactNumber.setText(mDiveShop.getContactNumber());
                mPricePerDive.setText(mDiveShop.getPricePerDive().toString());
                mDescription.setText(mDiveShop.getDescription());
                mSpecialService.setText(mDiveShop.getSpecialService());
                mDiveShopAddress = new DiveShopAddress(mDiveShop.getAddress(), new LatLng(mDiveShop.getLatitiude(), mDiveShop.getLongitude()));
            } catch (NullPointerException e) {
                Log.e(TAG, "Diveshop: " + e.getMessage());
            }
        }
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_SELECT_LOCATION) {
            if (resultCode == RESULT_OK) {
                if (data == null) return;
                mDiveShopAddress = data.getParcelableExtra(DiveShopAddress.EXTRA_DIVE_SHOP_ADDRESS);
                mAddressLocation.setText(mDiveShopAddress.getFullAddress());
                mAddressLocation.setSelection(mAddressLocation.length());
            }
        }
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
        if (mDiveShopAddress != null) {
            mDiveShop.setAddress(mDiveShopAddress.getFullAddress());
            mDiveShop.setLatitiude(mDiveShopAddress.getLatLng().latitude);
            mDiveShop.setLongitude(mDiveShopAddress.getLatLng().longitude);
        } else if (mDiveShop.getAddress() == null) {
            Toast.makeText(mContext, "No address selected", Toast.LENGTH_SHORT).show();
            return;
        }
        mDiveShop.setName(name);
        mDiveShop.setContactNumber(contactNumber);
        mDiveShop.setPricePerDive(new BigDecimal(pricePerDive));
        mDiveShop.setDescription(description);
        mDiveShop.setSpecialService(specialService);

        ApiClient.getApiInterface().updateDiveShop(mSessionManager.getDiveShopUid(), mDiveShop)
                .enqueue(new Callback<Response>() {
                    @Override
                    public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                        if (response.body() != null) {
                            if (!response.body().isError()) {
                                Intent resultData = new Intent();
                                resultData.putExtra(EXTRA_DIVE_SHOP, mDiveShop);
                                mContext.setResult(RESULT_OK, resultData);
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
