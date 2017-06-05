package aboikanych.forumlviv.ui.shops_services;

import android.os.Bundle;
import android.support.annotation.Nullable;
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
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Boykanych on 05.06.2017.
 */

public class ShopDetailFragment extends BaseFragment {

    public final static String ARG_SHOP = "shop_model";

    @BindView(R.id.hoursFrom)
    TextView hoursFrom;
    @BindView(R.id.hoursTo)
    TextView hoursTo;
    @BindView(R.id.description)
    TextView description;
    @BindView(R.id.image)
    ImageView image;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private ShopService shopService;

    public static ShopDetailFragment newInstance(ShopService shopService) {
        Bundle args = new Bundle();
        args.putParcelable(ARG_SHOP, shopService);
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
        View view = inflater.inflate(R.layout.fragment_shop_details, container, false);
        ButterKnife.bind(this, view);

        if(getArguments().getParcelable(ARG_SHOP) != null) {
            shopService = getArguments().getParcelable(ARG_SHOP);
            setupToolbar();
            Glide.with(getActivity())
                    .load(shopService.getImgUrl())
                    .into(image);
            description.setText(shopService.getDescription());
            hoursTo.setText(shopService.getHoursTo());
            hoursFrom.setText(shopService.getHoursFrom());
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
                        getActivity().onBackPressed();
                }
            });
        }
    }
}
