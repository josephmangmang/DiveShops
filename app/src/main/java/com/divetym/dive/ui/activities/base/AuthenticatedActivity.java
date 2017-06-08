package com.divetym.dive.ui.activities.base;

import android.content.Intent;

import com.divetym.dive.ui.activities.LoginActivity;
import com.divetym.dive.common.SessionManager;

/**
 * Created by kali_root on 4/4/2017.
 */

public class AuthenticatedActivity extends DiveTymActivity {

    @Override
    protected void onResume() {
        super.onResume();
        checkAuthentication();
    }

    private void checkAuthentication() {
        if (!SessionManager.getInstance(this).isLogin()) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
        }
    }

    public void logOut() {
        SessionManager.getInstance(this).logout();
        startActivity(LoginActivity.class);
        finish();
    }
}
