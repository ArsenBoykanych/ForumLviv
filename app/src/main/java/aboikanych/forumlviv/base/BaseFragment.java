package aboikanych.forumlviv.base;

import android.support.v4.app.Fragment;
import android.util.Log;
import android.widget.Toast;

import aboikanych.forumlviv.R;
import aboikanych.forumlviv.base.mvp.BaseView;
import aboikanych.forumlviv.utils.Utils;

/**
 * Created by Boykanych on 05.06.2017.
 */

public class BaseFragment extends Fragment implements BaseView {
    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void networkError() {
        if (Utils.isNetworkConnected(getActivity())) {
            Toast.makeText(getActivity(), R.string.error_server_unavailable, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getActivity(), R.string.error_internet_connection, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void httpError(Exception e) {

    }

    @Override
    public void baseError(String errorDescription) {
        Log.e("ForumLviv", errorDescription);
    }

    public void showToast(String message) {
        ((BaseActivity) getActivity()).showToast(message);
    }

    public void showToast(int resId) {
        ((BaseActivity) getActivity()).showToast(resId);
    }
}
