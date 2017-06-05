package aboikanych.forumlviv.ui.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import aboikanych.forumlviv.R;
import aboikanych.forumlviv.base.BaseFragment;
import aboikanych.forumlviv.net.model.NPEModel;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Boykanych on 06.06.2017.
 */

public class NPEFragment extends BaseFragment {

    public final static String ARG_NPE = "shop_npe";

    @BindView(R.id.npeRecycler)
    RecyclerView npeRecycler;

    private List<NPEModel> npeList;
    private NPEAdapter adapter;

    public static NPEFragment newInstance(List<NPEModel> npeList) {
        Bundle args = new Bundle();
        args.putParcelableArrayList(ARG_NPE, (ArrayList) npeList);
        NPEFragment fragment = new NPEFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() == null) {
            throw new NullPointerException(ARG_NPE + " can't be null");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_npe, container, false);
        ButterKnife.bind(this, view);

        if (getArguments().getParcelableArrayList(ARG_NPE) != null) {
            npeList = getArguments().getParcelableArrayList(ARG_NPE);
        }

        adapter = new NPEAdapter(getActivity(), npeList);
        npeRecycler.setHasFixedSize(true);
        npeRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        npeRecycler.setAdapter(adapter);
        return view;
    }
}
