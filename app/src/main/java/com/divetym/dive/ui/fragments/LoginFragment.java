package com.divetym.dive.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.divetym.dive.R;
import com.divetym.dive.common.SessionManager;
import com.divetym.dive.event.SessionEvent;
import com.divetym.dive.models.response.UserResponse;
import com.divetym.dive.rest.ApiClient;
import com.divetym.dive.ui.fragments.base.DiveTymFragment;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by kali_root on 3/27/2017.
 */

public class LoginFragment extends DiveTymFragment {
    private static final String TAG = LoginFragment.class.getSimpleName();
    @BindView(R.id.edit_email)
    EditText emailEditText;
    @BindView(R.id.edit_password)
    EditText passwordEditText;

    @OnClick({R.id.button_login, R.id.button_signup})
    public void onButtonClick(View view) {
        String email = emailEditText.getText().toString();
        String password = passwordEditText.getText().toString();
        boolean error = false;
        if (TextUtils.isEmpty(email)) {
            emailEditText.setError(getString(R.string.error_field_required));
            emailEditText.requestFocus();
            error = true;
        } else if (TextUtils.isEmpty(password)) {
            passwordEditText.setError(getString(R.string.error_field_required));
            passwordEditText.requestFocus();
            error = true;
        }
        if (!error) {
            if (view.getId() == R.id.button_login) {
                login(email, password);
            } else {
                register(email, password);
            }
        }
    }

    private void register(String email, String password) {
        ApiClient.getApiInterface().register(email, password, "diver")
                .enqueue(new Callback<com.divetym.dive.models.response.Response>() {
                    @Override
                    public void onResponse(Call<com.divetym.dive.models.response.Response> call, retrofit2.Response<com.divetym.dive.models.response.Response> response) {
                        com.divetym.dive.models.response.Response respnse = response.body();
                        if (respnse != null) {
                            Log.d(TAG, "register response: " + respnse.toString());
                            showToastAlert(respnse.getMessage());
                        }
                    }

                    @Override
                    public void onFailure(Call<com.divetym.dive.models.response.Response> call, Throwable t) {
                        Log.e(TAG, "register response: " + t.getMessage());
                        showToastAlert(t.getMessage());
                    }
                });
    }

    private void login(String email, String password) {
        ApiClient.getApiInterface().login(email, password).enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                UserResponse userResponse = response.body();
                if (userResponse != null) {
                    Log.i(TAG, "login response: " + userResponse.getMessage());
                    if (userResponse.isError()) {
                        showToastAlert(userResponse.getMessage());
                    } else {
                        SessionManager.getInstance(mContext).login(userResponse.getUser());
                        EventBus.getDefault().postSticky(new SessionEvent());
                        mContext.switchScreen();
                    }
                }
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                Log.e(TAG, "login response: " + t.getMessage());
                showToastAlert(t.getMessage());
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        mContext.setTitle(R.string.title_login);
        ButterKnife.bind(this, view);
        return view;
    }

}
