package aboikanych.forumlviv.base;

import android.support.annotation.StringRes;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import aboikanych.forumlviv.R;
import aboikanych.forumlviv.base.mvp.BaseView;
import aboikanych.forumlviv.utils.Utils;

public class BaseActivity extends AppCompatActivity implements BaseView {

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void networkError() {
        if (Utils.isNetworkConnected(this)) {
            Toast.makeText(this, R.string.error_server_unavailable, Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, R.string.error_internet_connection, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void httpError(Exception e) {

    }

    public void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    public void showToast(@StringRes int resId) {
        Toast.makeText(this, resId, Toast.LENGTH_SHORT).show();
    }

    public void showSnackbar(View view, @StringRes int resId) {
        Snackbar.make(view, resId, Snackbar.LENGTH_LONG).show();
    }

    public void showSnackbar(View view, String message) {
        Snackbar.make(view, message, Snackbar.LENGTH_LONG).show();
    }
}

