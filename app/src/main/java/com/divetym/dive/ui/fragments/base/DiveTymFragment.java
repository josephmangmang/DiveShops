package com.divetym.dive.ui.fragments.base;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.divetym.dive.ui.activities.base.DiveTymActivity;
import com.divetym.dive.common.SessionManager;
import com.divetym.dive.ui.view.ToastAlert;

/**
 * Created by kali_root on 3/28/2017.
 */

public class DiveTymFragment extends Fragment {
    protected DiveTymActivity mContext;
    protected SessionManager mSessionManager;

    public DiveTymFragment() {
        mSessionManager = SessionManager.getInstance(mContext);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = (DiveTymActivity) context;
    }

    protected void showToastAlert(String message) {
        new ToastAlert(mContext)
                .setMessage(message)
                .show();
    }
}
