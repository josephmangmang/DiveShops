package com.divetym.dive.ui.common;

import android.content.Context;
import android.content.SharedPreferences;

import com.divetym.dive.ui.models.User;
import com.divetym.dive.ui.rest.constants.ApiConstant;

/**
 * Created by kali_root on 4/4/2017.
 */

public class SessionManager {

    private static final String SESSION_PREF_NAME = "session_pref_name";
    private static SessionManager sSessionManager;
    private Context mContext;
    private SharedPreferences mPreferences;
    private SharedPreferences.Editor mEditor;

    private SessionManager(Context context) {
        this.mContext = context;
        this.mPreferences = context.getSharedPreferences(SESSION_PREF_NAME, Context.MODE_PRIVATE);
        this.mEditor = this.mPreferences.edit();
    }

    public SessionManager getInstance(Context context) {
        if (sSessionManager == null) {
            sSessionManager = new SessionManager(context);
        }
        return sSessionManager;
    }

    public boolean isLogin() {
        return getAuthKey() != null && getEmail() != null;
    }

    public boolean login(User user) {
        setUserUid(user.getUserUid());
        setAccountType(user.getAccountType());
        setAuthKey(user.getAuthKey());
        setCreatedTime(user.getCreatedTime());
        setEmail(user.getEmail());
        return save();
    }

    public void logout() {
        mEditor.clear();
        mEditor.commit();
    }

    private void setEmail(String email) {
        mEditor.putString(ApiConstant.EMAIL, email);
    }

    private void setCreatedTime(String createdTime) {
        mEditor.putString(ApiConstant.CREATE_TIME, createdTime);
    }

    private void setAuthKey(String authKey) {
        mEditor.putString(ApiConstant.AUTH_KEY, authKey);
    }

    private void setUserUid(String userUid) {
        mEditor.putString(ApiConstant.USER_ID, userUid);
    }

    private boolean save() {
        return mEditor.commit();
    }

    public void setAccountType(User.AccountType accountType) {
        mEditor.putString(ApiConstant.ACCOUNT_TYPE, accountType.name());
    }

    public User getUser() {
        User user = new User(getUid(), getEmail(), getCreatedTime(), getAccountType());
        user.setAuthKey(getAuthKey());
        return user;
    }

    public String getUid() {
        return mPreferences.getString(ApiConstant.USER_ID, null);
    }

    public String getEmail() {
        return mPreferences.getString(ApiConstant.EMAIL, null);
    }

    public String getCreatedTime() {
        return mPreferences.getString(ApiConstant.CREATE_TIME, null);
    }

    public User.AccountType getAccountType() {
        try {
            return User.AccountType.valueOf(mPreferences.getString(ApiConstant.ACCOUNT_TYPE, null));
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    public String getAuthKey() {
        return mPreferences.getString(ApiConstant.AUTH_KEY, null);
    }
}
