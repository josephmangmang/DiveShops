package com.divetym.dive.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.divetym.dive.BuildConfig;
import com.divetym.dive.R;
import com.divetym.dive.fragments.common.ModifyTripFragment;
import com.divetym.dive.models.DailyTrip;
import com.divetym.dive.models.response.DailyTripResponse;
import com.divetym.dive.rest.ApiClient;
import com.divetym.dive.view.ToastAlert;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;
import static com.divetym.dive.activities.TripDetailsActivity.EXTRA_DAILY_TRIP;

/**
 * Created by kali_root on 5/22/2017.
 */

public class EditTripFragment extends ModifyTripFragment {


    public static EditTripFragment getInstance(DailyTrip dailyTrip) {
        EditTripFragment frament = new EditTripFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(BUNDLE_DAILY_TRIP, dailyTrip);
        frament.setArguments(bundle);
        return frament;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext.getSupportActionBar().setTitle(R.string.title_edit_trip);
    }

    @Override
    protected void setDefaultData() {
        setDefaultData(mDailyTrip.getCalendar(), mDailyTrip.getGroupSize(),
                mDailyTrip.getNumberOfDive(), mDailyTrip.getPrice().toString(),
                mDailyTrip.getPriceNote(), mDailyTrip.getBoats(), mDailyTrip.getSites(), mDailyTrip.getGuides());
    }

    @Override
    protected void saveDailyTrip(DailyTrip dailyTrip) {
        ApiClient.getApiInterface()
                .updateDailyTrip(mSessionManager.getDiveShopUid(), mDailyTrip.getDailyTripId(), dailyTrip)
                .enqueue(new Callback<DailyTripResponse>() {
                    @Override
                    public void onResponse(Call<DailyTripResponse> call, Response<DailyTripResponse> response) {
                        if (BuildConfig.DEBUG) {
                            Log.d(TAG, "onResponse: " + response.toString());
                        }
                        if (response.body() != null) {
                            if(response.body().isError()){
                                new ToastAlert(mContext)
                                        .setMessage(response.body().getMessage())
                                        .show();
                            }else{
                                new ToastAlert(mContext)
                                        .setMessage(R.string.toast_saved)
                                        .show();
                                Intent resturnIntent = new Intent();
                                resturnIntent.putExtra(EXTRA_DAILY_TRIP, response.body().getDailyTrip());
                                mContext.setResult(RESULT_OK, resturnIntent);
                                mContext.finish();
                            }

                        }
                    }

                    @Override
                    public void onFailure(Call<DailyTripResponse> call, Throwable t) {
                        if (BuildConfig.DEBUG) {
                            t.printStackTrace();
                        }
                        Log.e(TAG, "onFailure: " + t.getMessage());
                    }
                });
    }
}
