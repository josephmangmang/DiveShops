package com.divetym.dive.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.divetym.dive.R;
import com.divetym.dive.activities.AddBoatActivity;
import com.divetym.dive.fragments.base.DiveTymFragment;
import com.divetym.dive.models.Boat;
import com.divetym.dive.models.response.BoatResponse;
import com.divetym.dive.rest.ApiClient;
import com.divetym.dive.view.ToastAlert;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by kali_root on 6/4/2017.
 */

public class AddBoatFragment extends DiveTymFragment {

    @BindView(R.id.edit_name)
    EditText mName;
    @BindView(R.id.edit_description)
    EditText mDescription;
    private Boat mBoat;
    private boolean edit;

    public static AddBoatFragment getInstance(Boat boat) {
        AddBoatFragment fragment = new AddBoatFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(AddBoatActivity.EXTRA_BOAT, boat);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        if (getArguments() != null) {
            mBoat = getArguments().getParcelable(AddBoatActivity.EXTRA_BOAT);
            if (mBoat != null) {
                mContext.setTitle(R.string.title_edit_boat);
            }
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_boat, container, false);
        ButterKnife.bind(this, view);
        if (mBoat != null) {
            edit = true;
            mName.setText(mBoat.getName());
            mDescription.setText(mBoat.getDescription());
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
        String name = mName.getText().toString();
        String description = mDescription.getText().toString();
        if (TextUtils.isEmpty(name)) {
            mName.setError(getString(R.string.error_field_required));
            return;
        } else if (TextUtils.isEmpty(description)) {
            mDescription.setError(getString(R.string.error_field_required));
            return;
        }
        if (edit) {
            ApiClient.getApiInterface().updateDiveShopBoat(mSessionManager.getDiveShopUid(), mBoat.getBoatId(), name, description)
                    .enqueue(new Callback<BoatResponse>() {
                        @Override
                        public void onResponse(Call<BoatResponse> call, Response<BoatResponse> response) {
                            handleResponse(response);
                        }

                        @Override
                        public void onFailure(Call<BoatResponse> call, Throwable t) {
                            Toast.makeText(mContext, "Error adding Boat: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        } else {
            ApiClient.getApiInterface().addDiveShopBoat(mSessionManager.getDiveShopUid(), name, description)
                    .enqueue(new Callback<BoatResponse>() {
                        @Override
                        public void onResponse(Call<BoatResponse> call, Response<BoatResponse> response) {
                            handleResponse(response);
                        }

                        @Override
                        public void onFailure(Call<BoatResponse> call, Throwable t) {
                            Toast.makeText(mContext, "Error adding Boat: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }

    private void handleResponse(Response<BoatResponse> response) {
        if (response.body() != null) {
            if (!response.body().isError()) {
                new ToastAlert(mContext)
                        .setMessage(response.body().getMessage())
                        .show();
                mContext.setResult(Activity.RESULT_OK);
                mContext.finish();
            } else {
                Toast.makeText(mContext, "Error adding Boat: " + response.body().getMessage(), Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(mContext, "Error adding Boat: " + response.raw(), Toast.LENGTH_SHORT).show();
        }
    }
}
