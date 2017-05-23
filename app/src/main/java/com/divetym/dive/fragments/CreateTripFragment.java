package com.divetym.dive.fragments;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import com.divetym.dive.BuildConfig;
import com.divetym.dive.R;
import com.divetym.dive.dialog.BoatsDialog;
import com.divetym.dive.dialog.DiveSitesDialog;
import com.divetym.dive.dialog.GuidesDialog;
import com.divetym.dive.dialog.SearchListDialog;
import com.divetym.dive.fragments.base.DiveTymFragment;
import com.divetym.dive.fragments.common.ModifyTripFragment;
import com.divetym.dive.models.DailyTrip;
import com.divetym.dive.models.DailyTripBoat;
import com.divetym.dive.models.DailyTripDiveSite;
import com.divetym.dive.models.DailyTripGuide;
import com.divetym.dive.models.response.DailyTripResponse;
import com.divetym.dive.rest.ApiClient;
import com.divetym.dive.utils.DateUtils;
import com.divetym.dive.view.ListAddMoreLayout;
import com.divetym.dive.view.ToastAlert;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
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
