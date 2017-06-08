package com.divetym.dive.ui.fragments;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.divetym.dive.R;
import com.divetym.dive.ui.activities.DiveShopActivity;
import com.divetym.dive.ui.fragments.base.DiveTymFragment;
import com.divetym.dive.common.SessionManager;
import com.divetym.dive.models.response.UserResponse;
import com.divetym.dive.rest.ApiClient;
import com.divetym.dive.rest.ApiInterface;

import butterknife.BindView;
import butterknife.ButterKnife;
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
    @BindView(R.id.button_login)
    Button loginButton;
    @BindView(R.id.button_signup)
    Button signUpButton;

    private ApiInterface mApiService;

    private View.OnClickListener mLoginButtonClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
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
                login(email, password);
            }
        }
    };

    private View.OnClickListener mSignUpButtonClick = view -> getFragmentManager().beginTransaction()
            .replace(R.id.content, new RegistrationFragment())
            .addToBackStack(null)
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            .commit();

    private void login(String email, String password) {
        mApiService.login(email, password).enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                UserResponse userResponse = response.body();
                if (userResponse != null) {
                    Log.i(TAG, "login response: " + userResponse.getMessage());
                    if (userResponse.isError()) {
                        showToastAlert(userResponse.getMessage());
                    } else {
                        SessionManager.getInstance(mContext).login(userResponse.getUser());
                        startActivity(new Intent(mContext, DiveShopActivity.class));
                        mContext.finish();
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


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mApiService = ApiClient.getApiInterface();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        ButterKnife.bind(this, view);
        initializeResources();
        return view;
    }

    private void initializeResources() {
        loginButton.setOnClickListener(mLoginButtonClick);
        signUpButton.setOnClickListener(mSignUpButtonClick);
    }

}