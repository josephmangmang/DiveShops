package com.divetym.dive.ui.activities.base;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.divetym.dive.ui.common.SessionManager;
import com.divetym.dive.ui.view.ToastAlert;

/**
 * Created by kali_root on 3/28/2017.
 */

public class DiveTymBaseFragment extends Fragment {
    protected DiveTymActivity mContext;
    protected SessionManager mSessionManager;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = (DiveTymActivity) context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSessionManager = SessionManager.getInstance(mContext);
    }
    protected void showToastAlert(String message) {
        new ToastAlert(mContext)
                .setMessage(message)
                .show();
    }
}
