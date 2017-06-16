package com.divetym.dive;

import android.app.Application;

import com.divetym.dive.common.SessionManager;

/**
 * Created by kali_root on 4/5/2017.
 */

public class DiveTymApplication extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
        SessionManager.getInstance(this);
    }
}
