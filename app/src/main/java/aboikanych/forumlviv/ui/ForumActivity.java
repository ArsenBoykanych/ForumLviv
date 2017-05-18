package aboikanych.forumlviv.ui;

import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.view.WindowManager;

import aboikanych.forumlviv.R;
import aboikanych.forumlviv.base.BaseActivity;
import butterknife.BindView;
import butterknife.ButterKnife;

public class ForumActivity extends BaseActivity {

    private final static int TAB_HOME = 0;
    private final static int TAB_MAP = 1;
    private final static int TAB_SHOPS = 2;
    private final static int TAB_SUITS = 3;
    private final static int TAB_OTHER = 4;

    private final static String FRAGMENT_TAG = "TOP_FRAGMENT";

    @BindView(R.id.menuTabLayout)
    TabLayout topTabs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
        setContentView(R.layout.activity_forum);
        ButterKnife.bind(this);

        setupTabs();
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
        // Fragment fragment;
        switch (position) {
            case TAB_HOME:

                break;
            case TAB_MAP:

                break;
            case TAB_SHOPS:

                break;
            case TAB_SUITS:

                break;
            case TAB_OTHER:

                break;
            default:
                throw new IllegalArgumentException("Tab id is not found");
        }
        // loadFragment(fragment);
    }

    private void createTabs() {
        topTabs.addTab(topTabs.newTab().setIcon(R.drawable.ic_home), true);
        topTabs.addTab(topTabs.newTab().setIcon(R.drawable.ic_home));
        topTabs.addTab(topTabs.newTab().setIcon(R.drawable.ic_home));
        topTabs.addTab(topTabs.newTab().setIcon(R.drawable.ic_home));
        topTabs.addTab(topTabs.newTab().setIcon(R.drawable.ic_home));
    }

    private void loadFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentContainer, fragment, FRAGMENT_TAG)
                .commit();
    }

/*    private void populateNavigationView() {
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null && user.getPhotoUrl() != null) {
            Glide.with(this)
                    .load(user.getPhotoUrl())
                    .fitCenter()
                    .into(logo);

            email.setText(
                    TextUtils.isEmpty(user.getEmail()) ? "No email" : user.getEmail());
            name.setText(
                    TextUtils.isEmpty(user.getDisplayName()) ? "No display name" : user.getDisplayName());
        }
    }*/
}
