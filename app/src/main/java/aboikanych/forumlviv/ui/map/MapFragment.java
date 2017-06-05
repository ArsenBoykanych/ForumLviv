package aboikanych.forumlviv.ui.map;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.indoorway.android.map.sdk.view.IndoorwayMapView;

import aboikanych.forumlviv.R;
import aboikanych.forumlviv.base.BaseFragment;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Boykanych on 05.06.2017.
 */

public class MapFragment extends BaseFragment {

    @BindView(R.id.mapView)
    IndoorwayMapView indoorwayMapView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map, container, false);
        ButterKnife.bind(this, view);

        indoorwayMapView.loadMap("HG7hpkd1FFw", "3RLhAnntR34");

        return view;
    }
}
