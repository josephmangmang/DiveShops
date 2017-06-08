package com.divetym.dive.ui.dialog;

import android.app.DialogFragment;
import android.content.Context;
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
import android.widget.Toast;

import com.divetym.dive.R;
import com.divetym.dive.ui.activities.base.DiveTymActivity;
import com.divetym.dive.ui.adapters.SearchDialogAdapter;
import com.divetym.dive.ui.adapters.base.BaseRecyclerAdapter;
import com.divetym.dive.interfaces.OnLoadMoreListener;
import com.divetym.dive.models.common.ThumbnailEntity;
import com.divetym.dive.rest.ApiClient;
import com.divetym.dive.rest.ApiInterface;

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
    protected static final int REQUEST_METHOD_LIST = 91;

    @BindView(R.id.image_btn_close)
    ImageButton closeImageButton;
    @BindView(R.id.image_btn_search)
    ImageButton searchImageButton;
    @BindView(R.id.edit_search)
    EditText searchEditText;
    @BindView(R.id.button_done)
    Button doneButton;
    @BindView(R.id.list)
    RecyclerView mRecyclerView;

    private List<DataType> mDataList;
    private SearchDialogAdapter mAdapter;
    private OnSelectionDoneListener mOnSelectionDoneListener;
    protected ApiInterface mApiService;
    protected String mLastSearchQuery;
    protected int lastRequestMethod;
    protected int offset = 0;
    private String searchHint;
    private boolean multiSelectEnable;
    private Context mContext;

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
            mLastSearchQuery = searchEditText.getText().toString();
            onSearchClicked(mLastSearchQuery);
        }
    };

    private TextWatcher searchQueryChangedListener = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            searchImageButton.setVisibility(charSequence.length() > 0 ? View.VISIBLE : View.INVISIBLE);
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
        mContext = getActivity();
        mApiService = ApiClient.getApiInterface();
        mDataList = new ArrayList<>();
        mAdapter = new SearchDialogAdapter((DiveTymActivity) getActivity(), mDataList, mRecyclerView);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_search_list, container, false);
        ButterKnife.bind(this, view);
        searchEditText.setHint(searchHint);
        searchEditText.addTextChangedListener(searchQueryChangedListener);
        searchEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_SEARCH) {
                    searchImageButton.performClick();
                    return true;
                }
                return false;
            }
        });
        closeImageButton.setOnClickListener(btnCloseClickListener);
        searchImageButton.setOnClickListener(btnSearchListener);
        doneButton.setOnClickListener(doneClickListener);
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
        lastRequestMethod = REQUEST_METHOD_LIST;
        offset = 0;
        getData();
    }

    protected void onCloseClicked() {
        dismiss();
    }

    public void setSearchHint(String hint) {
        searchHint = hint;
    }

    public void setMultiSelectEnable(boolean multiSelectEnable) {
        this.multiSelectEnable = multiSelectEnable;
    }

    public void setDataList(List dataList) {
        mDataList = dataList;
        mAdapter.disableMultiSelectMode(true);
        mAdapter.setDataList(mDataList);
    }

    public void setOnSelectionDoneListener(OnSelectionDoneListener onSelectionDoneListener) {
        mOnSelectionDoneListener = onSelectionDoneListener;
    }

    protected void getData() {
        if (lastRequestMethod == REQUEST_METHOD_SEARCH) {
            searchData(mLastSearchQuery);
        } else {
            requestData();
        }
    }

    protected void onSearchClicked(String query) {
        lastRequestMethod = REQUEST_METHOD_SEARCH;
        offset = 0;
        searchData(query);
    }

    protected abstract void searchData(String query);

    protected abstract void requestData();

    protected abstract void handleResponse(ResponseType response);


    protected void handleResponseError(String message) {
        Log.e(TAG, "Response error: " + message);
        Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onLoadMore(int totalItemCount) {
        offset = totalItemCount;
        getData();
    }

    @Override
    public void onItemClick(DataType object, View view, int position) {
        if (!multiSelectEnable) {
            mAdapter.toggleSelection(0, object);
            doneButton.callOnClick();
        } else {
            mAdapter.toggleSelection(position, object);
        }
    }

    @Override
    public void onItemLongClick(DataType object, View view, int position) {

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
