package com.divetym.dive.dialog;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.divetym.dive.R;
import com.divetym.dive.activities.base.DiveTymActivity;
import com.divetym.dive.adapters.SearchDialogAdapter;
import com.divetym.dive.adapters.base.BaseRecyclerAdapter;
import com.divetym.dive.interfaces.ItemClickListener;
import com.divetym.dive.interfaces.OnLoadMoreListener;
import com.divetym.dive.models.common.ThumbnailEntity;
import com.divetym.dive.rest.ApiClient;
import com.divetym.dive.rest.ApiInterface;
import com.divetym.dive.view.RobotoTextView;
import com.divetym.dive.view.ToastAlert;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by kali_root on 5/10/2017.
 */

public abstract class SearchListDialog<DataType extends ThumbnailEntity, ResponseType extends com.divetym.dive.models.response.Response>
        extends DialogFragment implements OnLoadMoreListener, BaseRecyclerAdapter.ItemClickListener<DataType>,
        BaseRecyclerAdapter.MultiSelectListener {
    public static final String TAG = SearchListDialog.class.getSimpleName();
    public static final String BUNDLE_DATA_LIST = "bundle_data_list";
    protected static final int REQUEST_METHOD_SEARCH = 90;
    protected static final int REQUEST_METHOD_LIST = 90;

    @BindView(R.id.image_btn_close)
    ImageButton btnClose;
    @BindView(R.id.image_btn_search)
    ImageButton btnSearch;
    @BindView(R.id.edit_search)
    EditText etSearch;
    @BindView(R.id.button_done)
    Button mDone;
    @BindView(R.id.list)
    RecyclerView mRecyclerView;

    private List<DataType> mDataList;
    private SearchDialogAdapter mAdapter;
    private OnSelectionDoneListener mOnSelectionDoneListener;
    protected ApiInterface mApiService;
    protected String mLastSearchQuery;
    protected int mLastRequestMethod;
    protected int mOffset = 0;
    private String mSearchHint;

    public interface OnSelectionDoneListener<DataType> {
        void onSelectionDone(HashMap<Integer, DataType> selectedItems);
    }

    private View.OnClickListener btnCloseClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            onCloseClicked();
        }
    };

    private View.OnClickListener btnSearchListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            mLastSearchQuery = etSearch.getText().toString();
            onSearchClicked(mLastSearchQuery);
        }
    };

    private TextWatcher searchQueryChangedListener = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            btnSearch.setVisibility(charSequence.length() > 0 ? View.VISIBLE : View.INVISIBLE);
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };
    private View.OnClickListener doneClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (mOnSelectionDoneListener != null) {
                mOnSelectionDoneListener.onSelectionDone(mAdapter.getSelectedItems());
            }
            dismiss();
        }
    };

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mApiService = ApiClient.getApiInterface();
        mDataList = new ArrayList<>();
        mAdapter = new SearchDialogAdapter((DiveTymActivity) getActivity(), mDataList, mRecyclerView);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_search_list, container, false);
        ButterKnife.bind(this, view);
        etSearch.setHint(mSearchHint);
        etSearch.addTextChangedListener(searchQueryChangedListener);
        etSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_SEARCH) {
                    btnSearch.performClick();
                    return true;
                }
                return false;
            }
        });
        btnClose.setOnClickListener(btnCloseClickListener);
        btnSearch.setOnClickListener(btnSearchListener);
        mDone.setOnClickListener(doneClickListener);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);
        mAdapter.setLoadMoreListener(this);
        mAdapter.setMultiSelectListener(this);
        mAdapter.setItemClickListener(this);
        mRecyclerView.setAdapter(mAdapter);
        onFirstRequestData();
        return view;
    }

    private void onFirstRequestData() {
        mLastRequestMethod = REQUEST_METHOD_LIST;
        mOffset = 0;
        requestData();
    }

    protected void onCloseClicked() {
        dismiss();
    }

    public void setSearchHint(String hint) {
        mSearchHint = hint;
    }

    public void setDataList(List dataList) {
        mDataList = dataList;
        mAdapter.disableMultiSelectMode(true);
        mAdapter.setDataList(mDataList);
    }

    public void setOnSelectionDoneListener(OnSelectionDoneListener onSelectionDoneListener) {
        mOnSelectionDoneListener = onSelectionDoneListener;
    }

    protected abstract void onSearchClicked(String query);

    protected abstract void requestData();

    protected abstract void handleResponse(ResponseType response);

    protected abstract void searchData(String query);

    protected void handleResponseError(String message) {
        new ToastAlert(getActivity())
                .setMessage(message)
                .show();
    }

    @Override
    public void onLoadMore(int totalItemCount) {
        mOffset = totalItemCount;
        requestData();
    }

    @Override
    public void onItemClick(DataType object, View view, int position) {
        mAdapter.toggleSelection(position, object);
    }

    @Override
    public void onActionClick(DataType object, View view) {

    }

    @Override
    public void onMultiSelectStateChanged(boolean enabled) {
        Log.d(TAG, "onMultiSelectStateChanged");
    }

    @Override
    public void onItemAdded(int id) {
        Log.d(TAG, "onItemAdded : " + id);
    }

    @Override
    public void onItemRemoved(int id) {
        Log.d(TAG, "onItemRemoved : " + id);
    }
}
