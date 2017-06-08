package com.divetym.dive.ui.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.divetym.dive.R;
import com.divetym.dive.ui.activities.AddCourseActivity;
import com.divetym.dive.ui.activities.base.DetailsActivity;
import com.divetym.dive.ui.dialog.CourseDialog;
import com.divetym.dive.ui.dialog.SearchListDialog;
import com.divetym.dive.ui.fragments.base.DiveTymFragment;
import com.divetym.dive.models.Course;
import com.divetym.dive.models.DiveShopCourse;
import com.divetym.dive.models.response.DiveShopCourseResponse;
import com.divetym.dive.rest.ApiClient;
import com.divetym.dive.ui.view.ToastAlert;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by kali_root on 5/25/2017.
 */

public class AddCourseFragment extends DiveTymFragment {
    @BindView(R.id.edit_course)
    EditText courseEditText;
    @BindView(R.id.edit_price)
    EditText priceEditText;
    private Course mCourse;
    private DiveShopCourse mDiveShopCourse;
    private boolean edit;

    public static AddCourseFragment getInstance(DiveShopCourse course) {
        AddCourseFragment fragment = new AddCourseFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(AddCourseActivity.EXTRA_COURSE, course);
        fragment.setArguments(bundle);
        return fragment;
    }

    @OnClick(R.id.edit_course)
    public void onSelectCourseClicked() {
        CourseDialog courseDialog = new CourseDialog();
        courseDialog.show(mContext.getFragmentManager(), CourseDialog.class.getSimpleName());
        courseDialog.setSearchHint(getString(R.string.hint_search_course));
        courseDialog.setOnSelectionDoneListener(new SearchListDialog.OnSelectionDoneListener<Course>() {

            @Override
            public void onSelectionDone(HashMap<Integer, Course> selectedItems) {
                mCourse = selectedItems.get(0);
                if (mCourse != null) {
                    courseEditText.setText(mCourse.getName());
                }
            }
        });
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        if (getArguments() != null) {
            mDiveShopCourse = getArguments().getParcelable(AddCourseActivity.EXTRA_COURSE);
            if (mDiveShopCourse != null) {
                mContext.setTitle(R.string.title_edit_course);
            }
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_course, container, false);
        ButterKnife.bind(this, view);
        if (mDiveShopCourse != null) {
            edit = true;
            mCourse = mDiveShopCourse;
            courseEditText.setText(mDiveShopCourse.getName());
            priceEditText.setText(mDiveShopCourse.getPrice().toString());
        }
        return view;
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
        String price = priceEditText.getText().toString();
        if (mCourse == null) {
            courseEditText.setError(getString(R.string.error_field_required));
            return;
        } else if (TextUtils.isEmpty(price)) {
            priceEditText.setError(getString(R.string.error_field_required));
            return;
        }
        if (edit) {
            ApiClient.getApiInterface().updateDiveShopCourse(mSessionManager.getDiveShopUid(),
                    mDiveShopCourse.getDiveShopCourseId(), mCourse.getCourseId(), Double.parseDouble(price))
                    .enqueue(new Callback<DiveShopCourseResponse>() {
                        @Override
                        public void onResponse(Call<DiveShopCourseResponse> call, Response<DiveShopCourseResponse> response) {
                            handleResponse(response);
                        }

                        @Override
                        public void onFailure(Call<DiveShopCourseResponse> call, Throwable t) {
                            Toast.makeText(mContext, "Error adding Course: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        } else {
            ApiClient.getApiInterface().addDiveShopCourse(mSessionManager.getDiveShopUid(),
                    mCourse.getCourseId(), Double.parseDouble(price))
                    .enqueue(new Callback<DiveShopCourseResponse>() {
                        @Override
                        public void onResponse(Call<DiveShopCourseResponse> call, Response<DiveShopCourseResponse> response) {
                            handleResponse(response);
                        }

                        @Override
                        public void onFailure(Call<DiveShopCourseResponse> call, Throwable t) {
                            Toast.makeText(mContext, "Error adding Course: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }

    private void handleResponse(Response<DiveShopCourseResponse> response) {
        if (response.body() != null) {
            if (!response.body().isError()) {
                new ToastAlert(mContext)
                        .setMessage(response.body().getMessage())
                        .show();
                Intent resultIntent = new Intent();
                resultIntent.putExtra(DetailsActivity.EXTRA_DATA, response.body().getDiveShopCourse());
                mContext.setResult(Activity.RESULT_OK, resultIntent);
                mContext.finish();
            } else {
                Toast.makeText(mContext, "Error adding Course: " + response.body().getMessage(), Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(mContext, "Error adding Course: " + response.raw(), Toast.LENGTH_SHORT).show();
        }
    }
}
