package aboikanych.forumlviv.ui.map;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.View;

import aboikanych.forumlviv.R;
import aboikanych.forumlviv.base.BaseActivity;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MapActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    public final static String EXTRA_MAP = "extra_map";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map_content);
        ButterKnife.bind(this);
        setupToolbar();
        MapFragment fragment = MapFragment.newInstance(getIntent().getStringExtra(EXTRA_MAP));
        getSupportFragmentManager().beginTransaction().add(R.id.content, fragment).commit();
        toolbar.bringToFront();
    }

    private void setupToolbar() {
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onBackPressed();
                }
            });
        }
    }
}
