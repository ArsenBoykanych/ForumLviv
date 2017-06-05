package aboikanych.forumlviv.ui.shops_services;

import android.os.Bundle;
import android.support.annotation.Nullable;

import aboikanych.forumlviv.R;
import aboikanych.forumlviv.base.BaseActivity;
import aboikanych.forumlviv.net.model.ShopService;

/**
 * Created by Boykanych on 05.06.2017.
 */

public class ShopDetailActivity extends BaseActivity {

    public final static String EXTRA_SHOP = "extra_shop";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_content);
        ShopDetailFragment fragment = ShopDetailFragment.newInstance((ShopService)getIntent().getParcelableExtra(EXTRA_SHOP));
        getSupportFragmentManager().beginTransaction().add(R.id.content, fragment).commit();
    }
}
