package com.divetym.dive.ui.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.divetym.dive.BuildConfig;
import com.divetym.dive.R;
import com.divetym.dive.event.BoatEvent;
import com.divetym.dive.event.BoatListEvent;
import com.divetym.dive.event.DiveShopCourseEvent;
import com.divetym.dive.event.DiveShopCourseListEvent;
import com.divetym.dive.event.DiveShopEvent;
import com.divetym.dive.event.GuideEvent;
import com.divetym.dive.event.GuideListEvent;
import com.divetym.dive.ui.activities.BoatDetailsActivity;
import com.divetym.dive.ui.activities.BoatListActivity;
import com.divetym.dive.ui.activities.CourseListActivity;
import com.divetym.dive.ui.activities.CourseDetailsActivity;
import com.divetym.dive.ui.activities.DiveShopTripActivity;
import com.divetym.dive.ui.activities.DiveShopActivity;
import com.divetym.dive.ui.activities.EditDiveShopActivity;
import com.divetym.dive.ui.activities.GuideDetailsActivity;
import com.divetym.dive.ui.activities.GuideListActivity;
import com.divetym.dive.ui.activities.base.AuthenticatedActivity;
import com.divetym.dive.ui.fragments.base.DiveTymFragment;
import com.divetym.dive.ui.adapters.base.BaseRecyclerAdapter;
import com.divetym.dive.interfaces.ItemClickListener;
import com.divetym.dive.models.DiveShop;
import com.divetym.dive.models.ListPreview;
import com.divetym.dive.models.response.DiveShopResponse;
import com.divetym.dive.rest.ApiClient;
import com.divetym.dive.rest.ApiInterface;
import com.divetym.dive.ui.view.ListPreviewLayout;
import com.divetym.dive.ui.view.RobotoTextView;
import com.divetym.dive.ui.view.ToastAlert;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by kali_root on 4/5/2017.
 */

public class DiveShopFragment extends DiveTymFragment {
    private static final String TAG = DiveShopFragment.class.getSimpleName();
    public static final int REQUEST_UPDATE = 1;
    public static final String EXTRA_DIVE_SHOP = "com.divetym.dive.EXTRA_DIVE_SHOP_UID";
    @BindView(R.id.text_description)
    RobotoTextView descriptionTextView;
    @BindView(R.id.text_price_per_dive)
    RobotoTextView pricePerDiveTextView;
    @BindView(R.id.preview_courses)
    ListPreviewLayout mPreviewCourses;
    @BindView(R.id.preview_boats)
    ListPreviewLayout mPreviewBoats;
    @BindView(R.id.preview_guides)
    ListPreviewLayout mPreviewGuides;
    @BindView(R.id.text_special_service)
    RobotoTextView specialServiceTextView;
    private ApiInterface mApiService;
    private DiveShop mDiveShop;
    private int selectedItemPosition;

    private View.OnClickListener mPreviewGuideMoreClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            GuideListActivity.launch(mContext, mDiveShop.getGuides(), true);
        }
    };

    @OnClick(R.id.button_view_daily_trips)
    public void onViewDailyTrip() {
        startActivity(new Intent(mContext, DiveShopTripActivity.class));
    }

    private View.OnClickListener mPreviewCoursesMoreClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            CourseListActivity.launch(mContext, mDiveShop.getCourses(), true);
        }
    };
    private View.OnClickListener mPreviewBoatsMoreClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            BoatListActivity.launch(mContext, mDiveShop.getBoats(), true);
        }
    };
    private BaseRecyclerAdapter.ItemClickListener mCourseItemClickListener = new ItemClickListener<ListPreview>() {
        @Override
        public void onItemClick(ListPreview object, View view, int i) {
            Log.d(TAG, "onItemClick " + object.toString());
            selectedItemPosition = object.getPosition();
            CourseDetailsActivity.launch(mContext, mDiveShop.getCourses().get(selectedItemPosition));
        }
    };

    private BaseRecyclerAdapter.ItemClickListener mBoatItemClickListener = new ItemClickListener<ListPreview>() {
        @Override
        public void onItemClick(ListPreview object, View view, int i) {
            Log.d(TAG, "onItemClick " + object.toString());
            selectedItemPosition = object.getPosition();
            BoatDetailsActivity.launch(mContext, mDiveShop.getBoats().get(selectedItemPosition));
        }

    };
    private BaseRecyclerAdapter.ItemClickListener mGuideItemClickListener = new ItemClickListener<ListPreview>() {
        public void onItemClick(ListPreview object, View view, int i) {
            Log.d(TAG, "onItemClick " + object.toString());
            selectedItemPosition = object.getPosition();
            GuideDetailsActivity.launch(mContext, mDiveShop.getGuides().get(selectedItemPosition));
        }
    };
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        mApiService = ApiClient.getApiInterface();
        mDiveShop = new DiveShop();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dive_shop_profile, container, false);
        ButterKnife.bind(this, view);

        mPreviewCourses.setMoreClickListener(mPreviewCoursesMoreClickListener);
        mPreviewCourses.setItemClickListener(mCourseItemClickListener);
        mPreviewBoats.setItemClickListener(mBoatItemClickListener);
        mPreviewBoats.setMoreClickListener(mPreviewBoatsMoreClickListener);
        mPreviewGuides.setItemClickListener(mGuideItemClickListener);
        mPreviewGuides.setMoreClickListener(mPreviewGuideMoreClickListener);

        loadDiveShopData();
        return view;
    }

    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onDiveShopChanged(DiveShopEvent event) {
        Log.d(TAG, "onDiveShopChanged: ");
        mDiveShop = event.getDiveShop();
        setDiveshop(mDiveShop);
        removeStickyEvent(event);
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onBoatListChanged(BoatListEvent event) {
        Log.d(TAG, "onBoatListChanged: " + event);
        mDiveShop.setBoats(event.getBoats());
        mPreviewBoats.setPreviewList(mDiveShop.getBoatPreviews(true));
        removeStickyEvent(event);
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onCourseListChanged(DiveShopCourseListEvent event) {
        Log.d(TAG, "onCourseListChanged: " + event);
        mDiveShop.setCourses(event.getDiveShopCourses());
        mPreviewCourses.setPreviewList(mDiveShop.getCoursePreviews(true));
        removeStickyEvent(event);
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onGuideListChanged(GuideListEvent event) {
        Log.d(TAG, "onGuideListChanged: " + event);
        mDiveShop.setGuides(event.getGuides());
        mPreviewGuides.setPreviewList(mDiveShop.getGuidePreviews(true));
        removeStickyEvent(event);
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onDataChanged(BoatEvent event) {
        mDiveShop.getBoats().set(selectedItemPosition, event.getBoat());
        mPreviewBoats.setPreviewList(mDiveShop.getBoatPreviews(true));
        removeStickyEvent(event);
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onDataChanged(DiveShopCourseEvent event) {
        mDiveShop.getCourses().set(selectedItemPosition, event.getDiveShopCourse());
        mPreviewCourses.setPreviewList(mDiveShop.getCoursePreviews(true));
        removeStickyEvent(event);
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onDataChanged(GuideEvent event) {
        mDiveShop.getGuides().set(selectedItemPosition, event.getGuide());
        mPreviewGuides.setPreviewList(mDiveShop.getGuidePreviews(true));
        removeStickyEvent(event);
    }

    private void removeStickyEvent(Object event) {
        EventBus.getDefault().removeStickyEvent(event);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.edit, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_edit) {
            onEditClicked();
        }
        return false;
    }

    private void onEditClicked() {
        EditDiveShopActivity.launch(mContext, mDiveShop);
    }


    private void loadDiveShopData() {
        String uid = mSessionManager.getUid();
        if (uid == null) {
            new ToastAlert(mContext)
                    .setMessage(R.string.error_empty_account_details)
                    .show();
            ((AuthenticatedActivity) mContext).logOut();
            return;
        }
        String shopUid = mSessionManager.getDiveShopUid();
        if (shopUid != null) {
            mApiService.getDiveShop(shopUid)
                    .enqueue(new Callback<DiveShopResponse>() {
                        @Override
                        public void onResponse(Call<DiveShopResponse> call, Response<DiveShopResponse> response) {
                            Log.d(TAG, "onResponse: " + response.toString());
                            DiveShopResponse body = response.body();
                            if (body != null && !body.isError()) {
                                setDiveshop(body.getDiveShop());
                            }
                        }

                        @Override
                        public void onFailure(Call<DiveShopResponse> call, Throwable t) {
                            Log.e(TAG, "Failed getting user account: " + t.getMessage());
                            if (BuildConfig.DEBUG) {
                                Log.e(TAG, call.request().toString());
                                t.printStackTrace();
                            }
                            showToastAlert(t.getMessage());
                        }
                    });
        } else {
            mApiService.getDiveShopByUid(uid)
                    .enqueue(new Callback<DiveShopResponse>() {
                        @Override
                        public void onResponse(Call<DiveShopResponse> call, Response<DiveShopResponse> response) {
                            Log.d(TAG, "onResponse: " + response.toString());
                            DiveShopResponse body = response.body();
                            if (body != null && !body.isError()) {
                                setDiveshop(body.getDiveShop());
                            }
                        }

                        @Override
                        public void onFailure(Call<DiveShopResponse> call, Throwable t) {
                            Log.e(TAG, "Failed getting user account: " + t.getMessage());
                            if (BuildConfig.DEBUG) {
                                Log.e(TAG, call.request().toString());
                                t.printStackTrace();
                            }
                            showToastAlert(t.getMessage());
                        }
                    });
        }
    }

    private void setDiveshop(DiveShop diveShop) {
        Log.d(TAG, "Dive_Shop: " + diveShop.toString());
        mDiveShop = diveShop;
        mSessionManager.setDiveShopUid(diveShop.getDiveShopUid());

        mContext.setTitle(diveShop.getName());
        mContext.setSubtitle(diveShop.getAddress());
        DiveShopActivity diveShopActivity = ((DiveShopActivity) mContext);
        diveShopActivity.setToolbarTitle(diveShop.getName());
        diveShopActivity.setToolbarSubtitle(diveShop.getAddress());
        diveShopActivity.setToolbarBackground(diveShop.getImageUrl());
        descriptionTextView.setText(diveShop.getDescription());
        pricePerDiveTextView.setText(diveShop.getPricePerDive().toString());
        specialServiceTextView.setText(diveShop.getSpecialService());

        mPreviewCourses.setPreviewTitle(getString(R.string.title_courses));
        mPreviewCourses.setPreviewList(diveShop.getCoursePreviews(false));

        mPreviewBoats.setPreviewTitle(getString(R.string.title_boats));
        mPreviewBoats.setPreviewList(diveShop.getBoatPreviews(false));

        mPreviewGuides.setPreviewTitle(getString(R.string.title_guides));
        mPreviewGuides.setPreviewList(diveShop.getGuidePreviews(false));
    }
}
