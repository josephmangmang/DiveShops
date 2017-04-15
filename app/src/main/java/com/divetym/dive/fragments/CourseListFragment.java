package com.divetym.dive.fragments;

import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.divetym.dive.BuildConfig;
import com.divetym.dive.R;
import com.divetym.dive.activities.base.AuthenticatedActivity;
import com.divetym.dive.activities.base.DiveTymFragment;
import com.divetym.dive.adapters.CourseListAdapter;
import com.divetym.dive.adapters.base.BaseRecyclerAdapter;
import com.divetym.dive.interfaces.OnLoadMoreListener;
import com.divetym.dive.models.DiveShopCourse;
import com.divetym.dive.models.response.DiveShopCourseListResponse;
import com.divetym.dive.rest.ApiClient;
import com.divetym.dive.rest.ApiInterface;
import com.divetym.dive.view.ToastAlert;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by kali_root on 4/10/2017.
 */

public class CourseListFragment extends DiveTymFragment implements OnLoadMoreListener,
        BaseRecyclerAdapter.ItemClickListener<DiveShopCourse> {
    private static final String TAG = CourseListFragment.class.getSimpleName();
    @BindView(R.id.list)
    RecyclerView mRecyclerView;
    private CourseListAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;
    private List<DiveShopCourse> mCourses;
    private ApiInterface mApiService;
    private int mOffset = 0;

    public CourseListFragment() {

    }

    public static CourseListFragment getInstance(@Nullable ArrayList<DiveShopCourse> courses) {
        CourseListFragment fragment = new CourseListFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(DiveShopCourse.TAG, courses);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null) {
            mCourses = args.getParcelableArrayList(DiveShopCourse.TAG);
            if (mCourses == null) {
                mCourses = new ArrayList<>();
            }
            mOffset = mCourses.size();
        }
        mApiService = ApiClient.getApiInterface();
        mLayoutManager = new LinearLayoutManager(mContext);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_course_list, container, false);
        ButterKnife.bind(this, view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new CourseListAdapter(mContext, mCourses, mRecyclerView);
        mAdapter.setItemClickListener(this);
        mAdapter.setLoadMoreListener(this);
        mRecyclerView.setAdapter(mAdapter);
        if (mCourses.size() == 0) {
            loadCourseData();
        }
        return view;
    }

    private void loadCourseData() {
        String shopUid = mSessionManager.getDiveShopUid();
        if (shopUid == null) {
            new ToastAlert(mContext)
                    .setMessage(R.string.error_empty_account_details)
                    .show();
            ((AuthenticatedActivity) mContext).logOut();
        }
        mApiService.getDiveShopCourses(shopUid, mOffset)
                .enqueue(new Callback<DiveShopCourseListResponse>() {
                    @Override
                    public void onResponse(Call<DiveShopCourseListResponse> call, Response<DiveShopCourseListResponse> response) {
                        DiveShopCourseListResponse body = response.body();
                        Log.d(TAG, "getDiveShopCourses onResponse: " + response.toString());
                        if (body != null && !body.isError()) {
                            List<DiveShopCourse> courses = body.getCourses();
                            mAdapter.addData(courses);
                        }
                    }

                    @Override
                    public void onFailure(Call<DiveShopCourseListResponse> call, Throwable t) {
                        if (BuildConfig.DEBUG) {
                            Log.e(TAG, call.request().toString());
                            t.printStackTrace();
                        }
                        Log.e(TAG, "getDiveShopCourses onFailiure: " + t.getMessage());
                    }
                });
    }

    @Override
    public void onLoadMore(int totalItemCount) {
        Log.d(TAG, "onLoadMore: totalItemCount = " + totalItemCount);
        mOffset = totalItemCount;
        loadCourseData();
    }

    @Override
    public void onItemClick(DiveShopCourse object, View view) {
        Log.d(TAG, "onItemClick " + object.toString());
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.add(R.id.content, CourseDetailsFragment.getInstance(object), CourseDetailsFragment.TAG)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onActionClick(DiveShopCourse object, View view) {
        Log.d(TAG, "onActionClick " + object.toString());
    }
}
