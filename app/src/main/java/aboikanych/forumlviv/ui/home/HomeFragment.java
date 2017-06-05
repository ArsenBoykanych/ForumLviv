package aboikanych.forumlviv.ui.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import aboikanych.forumlviv.R;
import aboikanych.forumlviv.base.BaseFragment;
import aboikanych.forumlviv.net.model.NPEModel;
import aboikanych.forumlviv.ui.home.mvp.NPEPresenter;
import aboikanych.forumlviv.ui.home.mvp.NPEPresenterImpl;
import aboikanych.forumlviv.ui.home.mvp.NPEView;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Boykanych on 05.06.2017.
 */

public class HomeFragment extends BaseFragment implements NPEView {

    @BindView(R.id.menuTabLayout)
    TabLayout topTabs;
    @BindView(R.id.viewPager)
    ViewPager viewPager;

    private NPEPresenter presenter;
    private PagerAdapter adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new NPEPresenterImpl();
        presenter.attachView(this);
        presenter.getNPE();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, view);
        createTabs();
        return view;
    }

    private void createTabs() {
        topTabs.addTab(topTabs.newTab().setText("Популярні"), true);
        topTabs.addTab(topTabs.newTab().setText("Події та Новини"));
        topTabs.addTab(topTabs.newTab().setText("Акції"));
    }

    @Override
    public void onNPELoaded(List<NPEModel> npeList) {
        adapter = new PagerAdapter(getActivity().getSupportFragmentManager(), npeList);
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(topTabs));
        topTabs.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }
}
