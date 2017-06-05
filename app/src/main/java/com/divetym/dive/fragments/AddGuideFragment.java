package com.divetym.dive.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
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
import com.divetym.dive.activities.AddGuideActivity;
import com.divetym.dive.activities.base.DetailsActivity;
import com.divetym.dive.fragments.base.DiveTymFragment;
import com.divetym.dive.models.Guide;
import com.divetym.dive.models.response.BoatResponse;
import com.divetym.dive.models.response.GuideResponse;
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

public class AddGuideFragment extends DiveTymFragment {
    @BindView(R.id.edit_name)
    EditText mName;
    @BindView(R.id.edit_description)
    EditText mDescription;
    private Guide mGuide;
    private boolean edit;

    public static AddGuideFragment getInstance(Guide guide) {
        AddGuideFragment fragment = new AddGuideFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(AddGuideActivity.EXTRA_GUIDE, guide);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        if (getArguments() != null) {
            mGuide = getArguments().getParcelable(AddGuideActivity.EXTRA_GUIDE);
            if (mGuide != null) {
                edit = true;
                mContext.setTitle(R.string.title_edit_guide);
            }
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_guide, container, false);
        ButterKnife.bind(this, view);
        if (mGuide != null) {
            mName.setText(mGuide.getName());
            mDescription.setText(mGuide.getDescription());
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
            ApiClient.getApiInterface().updateGuide(mSessionManager.getDiveShopUid(), mGuide.getGuideId(), name, description)
                    .enqueue(new Callback<GuideResponse>() {
                        @Override
                        public void onResponse(Call<GuideResponse> call, Response<GuideResponse> response) {
                            handleResponse(response);
                        }

                        @Override
                        public void onFailure(Call<GuideResponse> call, Throwable t) {
                            Toast.makeText(mContext, "Error adding Boat: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        } else {
            ApiClient.getApiInterface().addGuide(mSessionManager.getDiveShopUid(), name, description)
                    .enqueue(new Callback<GuideResponse>() {
                        @Override
                        public void onResponse(Call<GuideResponse> call, Response<GuideResponse> response) {
                            handleResponse(response);
                        }

                        @Override
                        public void onFailure(Call<GuideResponse> call, Throwable t) {
                            Toast.makeText(mContext, "Error adding Boat: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }

    private void handleResponse(Response<GuideResponse> response) {
        if (response.body() != null) {
            if (!response.body().isError()) {
                new ToastAlert(mContext)
                        .setMessage(response.body().getMessage())
                        .show();
                Intent resultIntent = new Intent();
                resultIntent.putExtra(DetailsActivity.EXTRA_DATA, response.body().getGuide());
                mContext.setResult(Activity.RESULT_OK, resultIntent);
                mContext.finish();
            } else {
                Toast.makeText(mContext, "Error adding Boat: " + response.body().getMessage(), Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(mContext, "Error adding Boat: " + response.raw(), Toast.LENGTH_SHORT).show();
        }
    }
}
