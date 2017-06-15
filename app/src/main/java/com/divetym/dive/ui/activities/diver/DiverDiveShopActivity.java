package com.divetym.dive.ui.activities.diver;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.ProgressBar;

import com.divetym.dive.BuildConfig;
import com.divetym.dive.R;
import com.divetym.dive.models.DiveShop;
import com.divetym.dive.models.common.ThumbnailEntity;
import com.divetym.dive.models.response.DiveShopResponse;
import com.divetym.dive.rest.ApiClient;
import com.divetym.dive.ui.activities.base.DiveTymActivity;
import com.divetym.dive.ui.fragments.diver.DiverDiveShopFragment;
import com.divetym.dive.ui.view.ImageSlider;
import com.divetym.dive.ui.view.ToastAlert;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by kali_root on 6/15/2017.
 */

public class DiverDiveShopActivity extends DiveTymActivity {
    public static final String EXTRA_DIVE_SHOP_UID = "com.divetym.dive.EXTRA_DIVE_SHOP_UID";
    public static final String EXTRA_DIVE_SHOP = "com.divetym.dive.EXTRA_DIVE_SHOP";

    @BindView(R.id.image_slider)
    ImageSlider imageSlider;
    @BindView(R.id.progress)
    ProgressBar progressBar;

    public static void launch(Context context, @NonNull String diveShopUid) {
        Intent intent = new Intent(context, DiverDiveShopActivity.class);
        intent.putExtra(EXTRA_DIVE_SHOP_UID, diveShopUid);
        context.startActivity(intent);
    }

    @OnClick(R.id.fab)
    public void onFavoriteButtonClick(View view) {
        ToastAlert.makeText(this, "You click favorite! ", ToastAlert.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diver_dive_shop);
        ButterKnife.bind(this);
        showBackButton(true);
        requestDivesShop();
    }

    private void requestDivesShop() {
        String shopUid = getIntent().getStringExtra(EXTRA_DIVE_SHOP_UID);
        ApiClient.getApiInterface().getDiveShop(shopUid)
                .enqueue(new Callback<DiveShopResponse>() {
                    @Override
                    public void onResponse(Call<DiveShopResponse> call, Response<DiveShopResponse> response) {
                        DiveShopResponse body = response.body();
                        if (body != null) {
                            if (body.isError()) {
                                showError();
                            }
                            setDiveShop(body.getDiveShop());
                        } else {
                            showError();
                        }
                    }

                    @Override
                    public void onFailure(Call<DiveShopResponse> call, Throwable t) {
                        if (BuildConfig.DEBUG) {
                            t.printStackTrace();
                        }
                        showError();
                    }
                });
    }

    private void setDiveShop(DiveShop diveShop) {
        if (diveShop != null) {
            progressBar.setVisibility(View.GONE);
            setTitle(diveShop.getName());
            List<ThumbnailEntity> shopImages = new ArrayList<>();
            shopImages.add(new ThumbnailEntity("", diveShop.getImageUrl()));
            imageSlider.setDataList(shopImages);
            initFragment(R.id.content, DiverDiveShopFragment.newInstance(diveShop));
        } else {
            showError();
        }
    }

    private void showError() {
        Snackbar.make(progressBar, R.string.error_something_wrong, Snackbar.LENGTH_LONG)
                .setAction(R.string.retry, view -> {
                    requestDivesShop();
                }).show();
    }
}
