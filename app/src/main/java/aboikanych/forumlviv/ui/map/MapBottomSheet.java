package aboikanych.forumlviv.ui.map;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import aboikanych.forumlviv.R;
import aboikanych.forumlviv.net.model.ShopService;
import aboikanych.forumlviv.ui.shops_services.ShopDetailFragment;

public class MapBottomSheet extends BottomSheetDialogFragment {
    public final static String ARG_SHOP = "arg_shop_item";

    public static MapBottomSheet newInstance(@NonNull ShopService shopService) {
        Bundle args = new Bundle();
        args.putParcelable(ARG_SHOP, shopService);
        MapBottomSheet fragment = new MapBottomSheet();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_content, container);
        ShopDetailFragment fragment = ShopDetailFragment.newInstance((ShopService) getArguments().getParcelable(ARG_SHOP),null, true);
        getChildFragmentManager().beginTransaction().add(R.id.content, fragment).commit();
        return view;
    }


}
