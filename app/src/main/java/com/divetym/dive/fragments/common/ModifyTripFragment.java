package com.divetym.dive.fragments.common;

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

import com.divetym.dive.R;
import com.divetym.dive.dialog.BoatsDialog;
import com.divetym.dive.dialog.DiveSitesDialog;
import com.divetym.dive.dialog.GuidesDialog;
import com.divetym.dive.dialog.SearchListDialog;
import com.divetym.dive.fragments.base.DiveTymFragment;
import com.divetym.dive.models.DailyTrip;
import com.divetym.dive.models.DailyTripBoat;
import com.divetym.dive.models.DailyTripDiveSite;
import com.divetym.dive.models.DailyTripGuide;
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

/**
 * Created by kali_root on 5/8/2017.
 */

public abstract class ModifyTripFragment extends DiveTymFragment {
    public static final String TAG = ModifyTripFragment.class.getSimpleName();
    public static final String BUNDLE_DAILY_TRIP = "bundle_daily_trip";
    @BindView(R.id.edit_time)
    EditText mTimeEditText;
    @BindView(R.id.edit_group_size)
    EditText mGroupSizeEditText;
    @BindView(R.id.edit_number_of_dive)
    EditText mNumberOfDiveEditText;
    @BindView(R.id.edit_price)
    EditText mPriceEditText;
    @BindView(R.id.edit_price_note)
    EditText mPriceNoteEditText;
    @BindView(R.id.list_addmore_site)
    ListAddMoreLayout<DailyTripDiveSite> mAddMoreLayoutDiveSite;
    @BindView(R.id.list_addmore_boat)
    ListAddMoreLayout<DailyTripBoat> mAddMoreLayoutBoats;
    @BindView(R.id.list_addmore_guide)
    ListAddMoreLayout<DailyTripGuide> mAddMoreLayoutGuides;
    protected DailyTrip mDailyTrip;

    private Calendar mSelectedTime;

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
            diveSitesDialog.setMultiSelectEnable(true);
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
            boatsDialog.setMultiSelectEnable(true);
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
            guidesDialog.setMultiSelectEnable(true);
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
            int y = mSelectedTime.get(Calendar.YEAR);
            int m = mSelectedTime.get(Calendar.MONTH);
            int d = mSelectedTime.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(mContext, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker datePicker, int nY, int nM, int nD) {
                    mSelectedTime.set(nY, nM, nD);
                    int hour = mSelectedTime.get(Calendar.HOUR_OF_DAY);
                    int minute = mSelectedTime.get(Calendar.MINUTE);

                    TimePickerDialog timePickerDialog = new TimePickerDialog(mContext, new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker timePicker, int nH, int nM) {
                            mSelectedTime.set(
                                    mSelectedTime.get(Calendar.YEAR),
                                    mSelectedTime.get(Calendar.MONTH),
                                    mSelectedTime.get(Calendar.DAY_OF_MONTH),
                                    nH,
                                    nM);
                            mTimeEditText.setText(DateUtils.formatDisplayDateTime(mSelectedTime.getTime()));
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
        Bundle args = getArguments();
        mDailyTrip = args == null ? null : (DailyTrip) args.getParcelable(BUNDLE_DAILY_TRIP);
        if (mDailyTrip == null) {
            mDailyTrip = new DailyTrip();
        }
        mContext.getSupportActionBar().setTitle(R.string.title_edit_trip);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_trip, container, false);
        ButterKnife.bind(this, view);
        mSelectedTime = Calendar.getInstance();
        mTimeEditText.setText(DateUtils.formatDisplayDateTime(mSelectedTime.getTime()));

        mAddMoreLayoutDiveSite.setTitle(getString(R.string.title_dive_sites));
        mAddMoreLayoutBoats.setTitle(getString(R.string.title_boats));
        mAddMoreLayoutGuides.setTitle(getString(R.string.title_guides));

        mTimeEditText.setOnClickListener(mTimeOnClickListener);
        mAddMoreLayoutDiveSite.setAddClickListener(onDiveSiteAddClickListener);
        mAddMoreLayoutBoats.setAddClickListener(onBoatAddClickListener);
        mAddMoreLayoutGuides.setAddClickListener(onGuideAddClickListener);
        setDefaultData();
        return view;
    }

    protected void setDefaultData() {
    }

    protected void setDefaultData(Calendar time, int groupSize, int numberOfDive, String price, String priceNote) {
        setDefaultData(time, groupSize, numberOfDive, price, priceNote, null, null, null);
    }

    protected void setDefaultData(Calendar time, int groupSize, int numberOfDive, String price, String priceNote,
                                  List<DailyTripBoat> boats, List<DailyTripDiveSite> diveSites, List<DailyTripGuide> guides) {
        mSelectedTime = time;
        mTimeEditText.setText(DateUtils.formatDisplayDateTime(mSelectedTime.getTime()));
        mGroupSizeEditText.setText(String.valueOf(groupSize));
        mNumberOfDiveEditText.setText(String.valueOf(numberOfDive));
        mPriceEditText.setText(String.valueOf(price));
        mPriceNoteEditText.setText(priceNote);

        if (boats != null) {
            mAddMoreLayoutBoats.setDataList(boats);
        }
        if (diveSites != null) {
            mAddMoreLayoutDiveSite.setDataList(diveSites);
        }
        if (guides != null) {
            mAddMoreLayoutGuides.setDataList(guides);
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.create_save, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_save) {
            onSaveClicked();
        }
        return false;
    }

    private void onSaveClicked() {
        boolean error = false;
        String errorMessage = getString(R.string.error_field_required);

        String dateTime = DateUtils.formatServerDateTime(mSelectedTime.getTime());
        String groupSize = mGroupSizeEditText.getText().toString();
        String numberOfDive = mNumberOfDiveEditText.getText().toString();
        String price = mPriceEditText.getText().toString();
        String priceNote = mPriceNoteEditText.getText().toString();

        if (TextUtils.isEmpty(dateTime)) {
            error = true;
            mTimeEditText.setError(errorMessage);
        }
        if (TextUtils.isEmpty(groupSize)) {
            error = true;
            mGroupSizeEditText.setError(errorMessage);
        }
        if (TextUtils.isEmpty(numberOfDive)) {
            error = true;
            mNumberOfDiveEditText.setError(errorMessage);
        }
        if (TextUtils.isEmpty(price)) {
            error = true;
            mPriceEditText.setError(errorMessage);
        }
        if (TextUtils.isEmpty(priceNote)) {
            error = true;
            mPriceNoteEditText.setError(errorMessage);
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
            mDailyTrip.setDate(dateTime);
            mDailyTrip.setNumberOfDive(Integer.parseInt(numberOfDive));
            mDailyTrip.setGroupSize(Integer.parseInt(groupSize));
            mDailyTrip.setPrice(new BigDecimal(price));
            mDailyTrip.setPriceNote(priceNote);
            mDailyTrip.setBoats(boats);
            mDailyTrip.setDiveSites(diveSites);
            mDailyTrip.setGuides(guides);
            saveDailyTrip(mDailyTrip);
        }
    }

    protected abstract void saveDailyTrip(DailyTrip dailyTrip);
}
