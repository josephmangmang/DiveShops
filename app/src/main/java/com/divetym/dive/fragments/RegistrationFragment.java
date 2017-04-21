package com.divetym.dive.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.divetym.dive.R;
import com.divetym.dive.fragments.base.DiveTymFragment;
import com.divetym.dive.models.response.Response;
import com.divetym.dive.rest.ApiClient;
import com.divetym.dive.rest.ApiInterface;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;

/**
 * Created by kali_root on 4/5/2017.
 */

public class RegistrationFragment extends DiveTymFragment {
    private static final String TAG = RegistrationFragment.class.getSimpleName();
    @BindView(R.id.edit_email)
    EditText etEmail;
    @BindView(R.id.edit_password)
    EditText etPassword;
    private ApiInterface mApiService;
    private String mAccountType = null;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mApiService = ApiClient.getApiInterface();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @OnClick({R.id.radio_diver, R.id.radio_dive_shop})
    public void onAccountTypeChanged(RadioButton radioButton) {
        if (radioButton.isChecked()) {
            mAccountType = radioButton.getText().toString();
        }
    }

    @OnClick(R.id.btn_register)
    public void onRegisterClicked() {
        String email = etEmail.getText().toString();
        String password = etPassword.getText().toString();
        Log.d(TAG, "onRegisterClicked email: " + email + " pass: " + password);
        // Verify inputs
        if (TextUtils.isEmpty(email)) {
            etEmail.setError(getString(R.string.error_field_required));
            etEmail.requestFocus();
            return;
        } else if (TextUtils.isEmpty(password)) {
            etPassword.setError(getString(R.string.error_field_required));
            etPassword.requestFocus();
            return;
        } else if (mAccountType == null) {
            Toast.makeText(mContext, R.string.toast_specify_account_type, Toast.LENGTH_SHORT).show();
            return;
        }
        // Input Verified
        mApiService.register(email, password, mAccountType.toLowerCase().replace(' ', '_'))
                .enqueue(new Callback<Response>() {
                    @Override
                    public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                        Response respnse = response.body();
                        if (respnse != null) {
                            Log.d(TAG, "register response: " + respnse.toString());
                            showToastAlert(respnse.getMessage());
                            if (!respnse.isError()) {
                                mContext.onBackPressed();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<Response> call, Throwable t) {
                        Log.e(TAG, "register response: " + t.getMessage());
                        showToastAlert(t.getMessage());
                    }
                });
    }
}
