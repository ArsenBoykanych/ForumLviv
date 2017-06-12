package aboikanych.forumlviv.ui.shops_services;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import aboikanych.forumlviv.R;
import aboikanych.forumlviv.base.BaseFragment;
import aboikanych.forumlviv.net.model.ShopService;
import aboikanych.forumlviv.ui.calbacks.ForumActivityCallbacks;
import aboikanych.forumlviv.ui.shops_services.mvp.ShopServicePresenter;
import aboikanych.forumlviv.ui.shops_services.mvp.ShopServicePresenterImpl;
import aboikanych.forumlviv.ui.shops_services.mvp.ShopServiceView;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Boykanych on 05.06.2017.
 */

public class ShopServiceFragment extends BaseFragment implements ShopServiceView {

    private static final int TAB_ALL = 0;
    private static final int TAB_FAVOURITES = 1;

    private ShopServicePresenter presenter;
    private ShopServiceAdapter adapter;

    ForumActivityCallbacks callbacks;

    @BindView(R.id.shopRecycler)
    RecyclerView shopRecycler;
    @BindView(R.id.menuTabLayout)
    TabLayout topTabs;

    private List<ShopService> shopsServices;
    private List<String> favs;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        callbacks = (ForumActivityCallbacks) context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new ShopServicePresenterImpl();
        presenter.attachView(this);
        presenter.getShopsServices();
        presenter.getFavShopsServices();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shop, container, false);
        ButterKnife.bind(this, view);
        setupTabs();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        callbacks.setSearchVisibility(true);
        presenter.getFavShopsServices();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        callbacks.setSearchVisibility(false);
    }

    @Override
    public void onShopsServicesLoaded(List<ShopService> shopsServices) {
        callbacks.listOfShopsToSearch(shopsServices);
        this.shopsServices = shopsServices;
        adapter = new ShopServiceAdapter(getActivity(), this.shopsServices);
        shopRecycler.setHasFixedSize(true);
        shopRecycler.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        adapter.setFavItemsList(favs);
        shopRecycler.setAdapter(adapter);
    }

    @Override
    public void onFavShopsServicesLoaded(List<String> shopsServices) {
        favs = shopsServices;
        if (favs != null) {
            adapter.setFavItemsList(favs);
            adapter.notifyDataSetChanged();
            shopRecycler.invalidate();
        }
    }

    private void setupTabs() {
        topTabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                handleTabClick(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        createTabs();
    }

    private void handleTabClick(int position) {
        switch (position) {
            case TAB_ALL:
                adapter = new ShopServiceAdapter(getActivity(), shopsServices);
                adapter.setFavItemsList(favs);
                shopRecycler.setAdapter(adapter);
                shopRecycler.invalidate();
                break;
            case TAB_FAVOURITES:
                adapter = new ShopServiceAdapter(getActivity(), updateFav());
                adapter.setFavItemsList(favs);
                shopRecycler.setAdapter(adapter);
                shopRecycler.invalidate();
                break;
            default:
                throw new IllegalArgumentException("Tab id is not found");
        }
    }

    @NonNull
    private List<ShopService> updateFav() {
        List<ShopService> favShopsServices = new ArrayList<>();
        if (favs != null) {
            for (String item : favs) {
                for (ShopService itemShop : shopsServices) {
                    if (item.contains(itemShop.getTitle())) {
                        favShopsServices.add(itemShop);
                    }
                }
            }
        }
        return favShopsServices;
    }

    private void createTabs() {
        topTabs.addTab(topTabs.newTab().setText("Усі"), true);
        topTabs.addTab(topTabs.newTab().setText("Улюблені"));
    }
}
