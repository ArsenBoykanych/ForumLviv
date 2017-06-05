package aboikanych.forumlviv.ui.home;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

import aboikanych.forumlviv.net.model.NPEModel;
import aboikanych.forumlviv.utils.Constants;

/**
 * Created by Boykanych on 06.06.2017.
 */

public class PagerAdapter extends FragmentStatePagerAdapter {

    private final static int TAB_ALL = 0;
    private final static int TAB_NEWS_EVENTS = 1;
    private final static int TAB_PROMOTIONS = 2;

    List<NPEModel> npeList;

    public PagerAdapter(FragmentManager fm, List<NPEModel> npeList) {
        super(fm);
        this.npeList = npeList;
    }

    @Override
    public Fragment getItem(int position) {
        NPEFragment fragment;
        List<NPEModel> filteredNPEList = new ArrayList<>();
        switch (position) {
            case TAB_ALL:
                fragment = NPEFragment.newInstance(npeList);
                break;
            case TAB_NEWS_EVENTS:
                for (NPEModel npeModel : npeList) {
                    if (npeModel.getType().equals(Constants.NPE_EVENTS) ||
                            npeModel.getType().equals(Constants.NPE_NEWS)) {
                        filteredNPEList.add(npeModel);
                    }
                }
                fragment = NPEFragment.newInstance(filteredNPEList);
                break;
            case TAB_PROMOTIONS:
                for (NPEModel npeModel : npeList) {
                    if (npeModel.getType().equals(Constants.NPE_PROMOTIONS)) {
                        filteredNPEList.add(npeModel);
                    }
                }
                fragment = NPEFragment.newInstance(filteredNPEList);
                break;
            default:
                throw new IllegalArgumentException("Tab id is not found");
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return 3;
    }
}