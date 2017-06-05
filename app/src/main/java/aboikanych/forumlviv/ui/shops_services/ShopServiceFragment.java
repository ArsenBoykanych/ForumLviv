package aboikanych.forumlviv.ui.shops_services;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import aboikanych.forumlviv.R;
import aboikanych.forumlviv.base.BaseFragment;
import aboikanych.forumlviv.net.model.ShopService;
import aboikanych.forumlviv.ui.shops_services.mvp.ShopServicePresenter;
import aboikanych.forumlviv.ui.shops_services.mvp.ShopServicePresenterImpl;
import aboikanych.forumlviv.ui.shops_services.mvp.ShopServiceView;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Boykanych on 05.06.2017.
 */

public class ShopServiceFragment extends BaseFragment implements ShopServiceView {

    private ShopServicePresenter presenter;
    private ShopServiceAdapter adapter;

    @BindView(R.id.shopRecycler)
    RecyclerView shopRecycler;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new ShopServicePresenterImpl();
        presenter.attachView(this);
        presenter.getShopsServices();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shop, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onShopsServicesLoaded(List<ShopService> shopsServices) {
        adapter = new ShopServiceAdapter(getActivity(), shopsServices);
        shopRecycler.setHasFixedSize(true);
        shopRecycler.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        shopRecycler.setAdapter(adapter);
    }
}
