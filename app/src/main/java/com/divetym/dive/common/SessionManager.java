package com.divetym.dive.common;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.divetym.dive.models.User;
import com.divetym.dive.rest.constants.ApiConstant;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by kali_root on 4/4/2017.
 */

public class SessionManager {

    private static final String TAG = SessionManager.class.getSimpleName();
    private static final String SESSION_PREF_NAME = "session_pref_name";
    private static SessionManager sSessionManager;
    private SharedPreferences mPreferences;
    private SharedPreferences.Editor mEditor;
    public static Map<String, User.AccountType> sAccountTypeMap;

    static {
        Log.d(TAG, "static block called");
        sAccountTypeMap = new HashMap<>(2);
        sAccountTypeMap.put(User.AccountType.Diver.name(), User.AccountType.Diver);
        sAccountTypeMap.put(User.AccountType.Dive_Shop.name(), User.AccountType.Dive_Shop);
    }

    private SessionManager(Context context) {
        this.mPreferences = context.getSharedPreferences(SESSION_PREF_NAME, Context.MODE_PRIVATE);
        this.mEditor = this.mPreferences.edit();
    }

    public static SessionManager getInstance(Context context) {
        if (sSessionManager == null) {
            sSessionManager = new SessionManager(context.getApplicationContext());
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

    public String getDiveShopUid() {
        return mPreferences.getString(ApiConstant.DIVE_SHOP_ID, null);
    }

    public String getDiverUid() {
        return mPreferences.getString(ApiConstant.DIVER_ID, null);
    }

    public String getAuthKey() {
        return mPreferences.getString(ApiConstant.AUTH_KEY, null);
    }

    public void setDiveShopUid(String shopUid) {
        mEditor.putString(ApiConstant.DIVE_SHOP_ID, shopUid).commit();
    }

    public void setDiverUid(String diverUid) {
        mEditor.putString(ApiConstant.DIVER_ID, diverUid).commit();
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
}
