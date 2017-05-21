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
import com.divetym.dive.models.Boat;
import com.divetym.dive.models.DailyTrip;
import com.divetym.dive.models.DailyTripBoat;
import com.divetym.dive.models.DailyTripDiveSite;
import com.divetym.dive.models.DailyTripGuide;
import com.divetym.dive.models.DiveSite;
import com.divetym.dive.models.Guide;
import com.divetym.dive.models.response.DailyTripResponse;
import com.divetym.dive.rest.ApiClient;
import com.divetym.dive.utils.DateUtils;
import com.divetym.dive.view.ListAddMoreLayout;
import com.divetym.dive.view.ToastAlert;
import com.google.gson.Gson;

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

public class CreateTripFragment extends DiveTymFragment {
    public static final String TAG = CreateTripFragment.class.getSimpleName();
    @BindView(R.id.edit_time)
    EditText etTime;
    @BindView(R.id.edit_group_size)
    EditText etGroupSize;
    @BindView(R.id.edit_number_of_dive)
    EditText etNumberOfDive;
    @BindView(R.id.edit_price)
    EditText etPrice;
    @BindView(R.id.edit_price_note)
    EditText etPriceNote;
    @BindView(R.id.list_addmore_site)
    ListAddMoreLayout<DailyTripDiveSite> mAddMoreLayoutDiveSite;
    @BindView(R.id.list_addmore_boat)
    ListAddMoreLayout<DailyTripBoat> mAddMoreLayoutBoats;
    @BindView(R.id.list_addmore_guide)
    ListAddMoreLayout<DailyTripGuide> mAddMoreLayoutGuides;

    private Calendar selectedTime;

    private View.OnClickListener onDiveSiteAddClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Log.d(TAG, "onDiveSiteAddClickListener");
            if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(
                        getActivity(),
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION},
                        0);
            }
            DiveSitesDialog diveSitesDialog = new DiveSitesDialog();
            diveSitesDialog.show(mContext.getFragmentManager(), DiveSitesDialog.class.getSimpleName());
            diveSitesDialog.setSearchHint(getString(R.string.hint_search_dive_site));
            diveSitesDialog.setOnSelectionDoneListener(new SearchListDialog.OnSelectionDoneListener<DailyTripDiveSite>() {
                @Override
                public void onSelectionDone(HashMap<Integer, DailyTripDiveSite> selectedItems) {
                    mAddMoreLayoutDiveSite.addDataList(new ArrayList<>(selectedItems.values()));
                }
            });
        }
    };
    private View.OnClickListener onBoatAddClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Log.d(TAG, "onBoatAddClickListener");
            BoatsDialog boatsDialog = new BoatsDialog();
            boatsDialog.show(mContext.getFragmentManager(), BoatsDialog.TAG);
            boatsDialog.setSearchHint(getString(R.string.hint_search_boat));
            boatsDialog.setOnSelectionDoneListener(new SearchListDialog.OnSelectionDoneListener<DailyTripBoat>() {
                @Override
                public void onSelectionDone(HashMap<Integer, DailyTripBoat> selectedItems) {
                    mAddMoreLayoutBoats.addDataList(new ArrayList<>(selectedItems.values()));
                }
            });

        }
    };
    private View.OnClickListener onGuideAddClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Log.d(TAG, "onGuideAddClickListener");
            GuidesDialog guidesDialog = new GuidesDialog();
            guidesDialog.show(mContext.getFragmentManager(), BoatsDialog.TAG);
            guidesDialog.setSearchHint(getString(R.string.hint_search_guide));
            guidesDialog.setOnSelectionDoneListener(new SearchListDialog.OnSelectionDoneListener<DailyTripGuide>() {
                @Override
                public void onSelectionDone(HashMap<Integer, DailyTripGuide> selectedItems) {
                    mAddMoreLayoutGuides.addDataList(new ArrayList<>(selectedItems.values()));
                }
            });
        }
    };
    private View.OnClickListener mTimeOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            final Calendar calendar = Calendar.getInstance();
            int y = calendar.get(Calendar.YEAR);
            int m = calendar.get(Calendar.MONTH);
            int d = calendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(mContext, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker datePicker, int nY, int nM, int nD) {
                    selectedTime.set(nY, nM, nD);
                    int hour = calendar.get(Calendar.HOUR_OF_DAY);
                    int minute = calendar.get(Calendar.MINUTE);

                    TimePickerDialog timePickerDialog = new TimePickerDialog(mContext, new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker timePicker, int nH, int nM) {
                            selectedTime.set(
                                    selectedTime.get(Calendar.YEAR),
                                    selectedTime.get(Calendar.MONTH),
                                    selectedTime.get(Calendar.DAY_OF_MONTH),
                                    nH,
                                    nM);
                            etTime.setText(DateUtils.formatDisplayDateTime(selectedTime.getTime()));
                        }
                    }, hour, minute, false);
                    timePickerDialog.show();
                }
            }, y, m, d);
            datePickerDialog.show();
        }
    };

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_trip, container, false);
        ButterKnife.bind(this, view);
        selectedTime = Calendar.getInstance();
        etTime.setText(DateUtils.formatDisplayDateTime(selectedTime.getTime()));
        etTime.setOnClickListener(mTimeOnClickListener);

        mAddMoreLayoutDiveSite.setTitle(getString(R.string.title_dive_sites));
        mAddMoreLayoutBoats.setTitle(getString(R.string.title_boats));
        mAddMoreLayoutGuides.setTitle(getString(R.string.title_guides));

        mAddMoreLayoutDiveSite.setAddClickListener(onDiveSiteAddClickListener);
        mAddMoreLayoutBoats.setAddClickListener(onBoatAddClickListener);
        mAddMoreLayoutGuides.setAddClickListener(onGuideAddClickListener);
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_create_save, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_save) {
            saveDailyTrip();
        }
        return false;
    }

    private void saveDailyTrip() {
        boolean error = false;
        String errorMessage = getString(R.string.error_field_required);

        String dateTime = DateUtils.formatServerDateTime(selectedTime.getTime());
        String groupSize = etGroupSize.getText().toString();
        String numberOfDive = etNumberOfDive.getText().toString();
        String price = etPrice.getText().toString();
        String priceNote = etPriceNote.getText().toString();

        if (TextUtils.isEmpty(dateTime)) {
            error = true;
            etTime.setError(errorMessage);
        }
        if (TextUtils.isEmpty(groupSize)) {
            error = true;
            etGroupSize.setError(errorMessage);
        }
        if (TextUtils.isEmpty(numberOfDive)) {
            error = true;
            etNumberOfDive.setError(errorMessage);
        }
        if (TextUtils.isEmpty(price)) {
            error = true;
            etPrice.setError(errorMessage);
        }
        if (TextUtils.isEmpty(priceNote)) {
            error = true;
            etPriceNote.setError(errorMessage);
        }

        List<DailyTripBoat> boats = mAddMoreLayoutBoats.getDataList();
        List<DailyTripDiveSite> diveSites = mAddMoreLayoutDiveSite.getDataList();
        List<DailyTripGuide> guides = mAddMoreLayoutGuides.getDataList();

        if (!error && diveSites != null && diveSites.size() == 0) {
            error = true;
            new ToastAlert(mContext)
                    .setMessage(R.string.error_empty_dive_sites)
                    .show();
        }
        if (!error) {
            DailyTrip dailyTrip = new DailyTrip();
            dailyTrip.setDate(dateTime);
            dailyTrip.setNumberOfDive(Integer.parseInt(numberOfDive));
            dailyTrip.setGroupSize(Integer.parseInt(groupSize));
            dailyTrip.setPrice(new BigDecimal(price));
            dailyTrip.setPriceNote(priceNote);
            dailyTrip.setBoats(boats);
            dailyTrip.setDiveSites(diveSites);
            dailyTrip.setGuides(guides);

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
}
