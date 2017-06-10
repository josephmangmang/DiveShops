package com.divetym.dive.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.divetym.dive.BuildConfig;
import com.divetym.dive.R;
import com.divetym.dive.event.DailyTripEvent;
import com.divetym.dive.ui.fragments.common.ModifyTripFragment;
import com.divetym.dive.models.DailyTrip;
import com.divetym.dive.models.response.DailyTripResponse;
import com.divetym.dive.rest.ApiClient;
import com.divetym.dive.ui.view.ToastAlert;

import org.greenrobot.eventbus.EventBus;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by kali_root on 5/8/2017.
 */

public class CreateTripFragment extends ModifyTripFragment {
    public static final String TAG = CreateTripFragment.class.getSimpleName();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext.getSupportActionBar().setTitle(R.string.title_create_trip);
    }


    @Override
    protected void saveDailyTrip(DailyTrip dailyTrip) {
        ApiClient.getApiInterface()
                .addDailyTrip(mSessionManager.getDiveShopUid(), dailyTrip)
                .enqueue(new Callback<DailyTripResponse>() {
                    @Override
                    public void onResponse(Call<DailyTripResponse> call, Response<DailyTripResponse> response) {
                        Log.d(TAG, "onResponse: " + response.toString());
                        DailyTripResponse tripResponse = response.body();
                        if (tripResponse != null) {
                            Log.d(TAG, "DailyTripResponse: " + tripResponse.toString());
                            new ToastAlert(mContext)
                                    .setMessage(tripResponse.getMessage())
                                    .show();
                            if (!tripResponse.isError()) {
                                EventBus.getDefault().postSticky(new DailyTripEvent(tripResponse.getDailyTrip()));
                                mContext.finish();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<DailyTripResponse> call, Throwable t) {
                        if (BuildConfig.DEBUG) {
                            t.printStackTrace();
                        }
                        new ToastAlert(mContext)
                                .setMessage(t.getMessage())
                                .show();
                    }
                });
    }
}
