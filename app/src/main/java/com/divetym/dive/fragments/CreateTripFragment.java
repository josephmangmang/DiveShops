package com.divetym.dive.fragments;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import com.divetym.dive.LocationTracker;
import com.divetym.dive.R;
import com.divetym.dive.dialog.BoatsDialog;
import com.divetym.dive.dialog.DiveSitesDialog;
import com.divetym.dive.dialog.GuidesDialog;
import com.divetym.dive.dialog.SearchListDialog;
import com.divetym.dive.fragments.base.DiveTymFragment;
import com.divetym.dive.models.Boat;
import com.divetym.dive.models.DailyTripGuide;
import com.divetym.dive.models.DiveSite;
import com.divetym.dive.utils.DateUtils;
import com.divetym.dive.view.ListAddMoreLayout;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

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
    ListAddMoreLayout<DiveSite> mAddMoreLayoutDiveSite;
    @BindView(R.id.list_addmore_boat)
    ListAddMoreLayout<Boat> mAddMoreLayoutBoats;
    @BindView(R.id.list_addmore_guide)
    ListAddMoreLayout<DailyTripGuide> mAddMoreLayoutGuides;

    private Calendar selectedTime = Calendar.getInstance();

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
            diveSitesDialog.setOnSelectionDoneListener(new SearchListDialog.OnSelectionDoneListener<DiveSite>() {
                @Override
                public void onSelectionDone(HashMap<Integer, DiveSite> selectedItems) {
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
            boatsDialog.setOnSelectionDoneListener(new SearchListDialog.OnSelectionDoneListener<Boat>() {
                @Override
                public void onSelectionDone(HashMap<Integer, Boat> selectedItems) {
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
            guidesDialog.setOnSelectionDoneListener(new SearchListDialog.OnSelectionDoneListener<Boat>() {
                @Override
                public void onSelectionDone(HashMap<Integer, Boat> selectedItems) {
                    mAddMoreLayoutBoats.addDataList(new ArrayList<>(selectedItems.values()));
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
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_trip, container, false);
        ButterKnife.bind(this, view);

        etTime.setText(DateUtils.formatDisplayDateTime(Calendar.getInstance().getTime()));
        etTime.setOnClickListener(mTimeOnClickListener);

        mAddMoreLayoutDiveSite.setTitle(getString(R.string.title_dive_sites));
        mAddMoreLayoutBoats.setTitle(getString(R.string.title_boats));
        mAddMoreLayoutGuides.setTitle(getString(R.string.title_guides));

        mAddMoreLayoutDiveSite.setAddClickListener(onDiveSiteAddClickListener);
        mAddMoreLayoutBoats.setAddClickListener(onBoatAddClickListener);
        mAddMoreLayoutGuides.setAddClickListener(onGuideAddClickListener);
        return view;
    }
}
