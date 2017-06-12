package aboikanych.forumlviv.ui.shops_services;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import aboikanych.forumlviv.R;
import aboikanych.forumlviv.base.BaseActivity;
import aboikanych.forumlviv.base.BaseFragment;
import aboikanych.forumlviv.net.model.ShopService;
import aboikanych.forumlviv.ui.map.MapActivity;
import aboikanych.forumlviv.ui.map.MapBottomSheet;
import aboikanych.forumlviv.utils.ActionUtils;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Optional;

/**
 * Created by Boykanych on 05.06.2017.
 */

public class ShopDetailFragment extends BaseFragment {

    public final static String ARG_SHOP = "shop_model";
    private final static String ARG_TYPE_DIALOG = "dialog";

    @BindView(R.id.description)
    TextView description;
    @Nullable
    @BindView(R.id.image)
    ImageView image;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @Nullable
    @BindView(R.id.mapFab)
    FloatingActionButton mapFab;
    @BindView(R.id.phone)
    TextView phone;
    @BindView(R.id.website)
    TextView website;
    @BindView(R.id.shopCategory)
    TextView shopCategoryText;
    @BindView(R.id.openHoursDay)
    TextView openHoursDayText;
    @BindView(R.id.openHourTime)
    TextView openHourTimeText;


    private ShopService shopService;
    private boolean isDialog;

    public static ShopDetailFragment newInstance(ShopService shopService, boolean isDialog) {
        Bundle args = new Bundle();
        args.putParcelable(ARG_SHOP, shopService);
        args.putBoolean(ARG_TYPE_DIALOG, isDialog);
        ShopDetailFragment fragment = new ShopDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() == null) {
            throw new NullPointerException(ARG_SHOP + " can't be null");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        isDialog = getArguments().getBoolean(ARG_TYPE_DIALOG);
        View view = inflater.inflate(isDialog ? R.layout.fragment_shop_details_dialog : R.layout.fragment_shop_details, container, false);
        ButterKnife.bind(this, view);

        if (getArguments().getParcelable(ARG_SHOP) != null) {
            shopService = getArguments().getParcelable(ARG_SHOP);
            setupToolbar();
            if (!isDialog) {
                Glide.with(getActivity())
                        .load(shopService.getImgUrl())
                        .into(image);
            }
            description.setText(shopService.getDescription());
            phone.setText(shopService.getPhone() == null ? "+380322956994" : shopService.getPhone());
            website.setText(shopService.getWebLink() == null ? "lviv.multi.eu/ua" : shopService.getWebLink());

            openHoursDayText.setText("Понеділок - Неділя");
            openHourTimeText.setText(shopService.getHoursFrom() + " - " + shopService.getHoursTo());

            shopCategoryText.setText(shopService.getCategory());
        }
        return view;
    }

    private void setupToolbar() {
        ((BaseActivity) getActivity()).setSupportActionBar(toolbar);
        ActionBar actionBar = ((BaseActivity) getActivity()).getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(shopService.getTitle());
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (isDialog) {
                        ((MapBottomSheet) getParentFragment()).dismiss();
                    } else {
                        getActivity().onBackPressed();
                    }
                }
            });
        }
    }

    @Optional
    @OnClick({R.id.phone, R.id.website, R.id.mapFab})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.phone:
                ActionUtils.actionCall(getActivity(), shopService.getPhone() == null ? "+380322956994" : shopService.getPhone());
                break;
            case R.id.website:
                ActionUtils.actionBrowser(getActivity(), shopService.getWebLink() == null ? "lviv.multi.eu/ua" : shopService.getWebLink());
                break;
            case R.id.mapFab:
                Intent startAppIntent = new Intent(getActivity(), MapActivity.class);
                startAppIntent.putExtra(MapActivity.EXTRA_MAP, shopService.getTitle());
                startActivity(startAppIntent);
                break;
            default:
                throw new IllegalArgumentException("View id is not found");
        }
    }
}
