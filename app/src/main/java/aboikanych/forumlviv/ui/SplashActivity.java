package aboikanych.forumlviv.ui;

import android.content.Intent;
import android.os.Bundle;

import aboikanych.forumlviv.base.BaseActivity;

public class SplashActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = new Intent(this, AuthActivity.class);
        startActivity(intent);
        finish();
    }
}
