package aboikanych.forumlviv.ui.main;

import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import aboikanych.forumlviv.R;
import aboikanych.forumlviv.base.BaseActivity;
import aboikanych.forumlviv.ui.home.HomeFragment;
import aboikanych.forumlviv.ui.map.MapFragment;
import aboikanych.forumlviv.ui.shops_services.ShopServiceFragment;
import butterknife.BindView;
import butterknife.ButterKnife;

public class ForumActivity extends BaseActivity {

    private final static int TAB_HOME = 0;
    private final static int TAB_MAP = 1;
    private final static int TAB_SHOPS = 2;
    private final static int TAB_SUITS = 3;
    private final static int TAB_OTHER = 4;

    private final static String FRAGMENT_TAG = "TOP_FRAGMENT";

    private int[] forumLogoColors;
    private int logoColorPosition = 0;
    private Handler handler;


    @BindView(R.id.menuTabLayout)
    TabLayout topTabs;
    @BindView(R.id.forumLogoCircle)
    ImageView forumLogoCircle;
    @BindView(R.id.fragmentContainer)
    View fragmentContainer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
        setContentView(R.layout.activity_forum);
        ButterKnife.bind(this);
        setupLogoAnimation();
        setupTabs();
    }

    private void setupLogoAnimation() {
        forumLogoColors = getResources().getIntArray(R.array.forum_logo_colors);
        handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                forumLogoCircle.setColorFilter(new PorterDuffColorFilter(forumLogoColors[logoColorPosition++], PorterDuff.Mode.SRC_IN));
                if (logoColorPosition == 4) {
                    logoColorPosition = 0;
                }
                handler.postDelayed(this, 3000);
            }
        }, 3000);
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
        Fragment fragment = null;
        switch (position) {
            case TAB_HOME:
                fragment = new HomeFragment();
                break;
            case TAB_MAP:
                fragment = new MapFragment();
                break;
            case TAB_SHOPS:
                fragment = new ShopServiceFragment();
                break;
            case TAB_SUITS:

                break;
            case TAB_OTHER:

                break;
            default:
                throw new IllegalArgumentException("Tab id is not found");
        }
        if (fragment != null) {
            loadFragment(fragment);
        }
    }

    private void createTabs() {
        topTabs.addTab(topTabs.newTab().setIcon(R.drawable.ic_home), true);
        topTabs.addTab(topTabs.newTab().setIcon(R.drawable.ic_map));
        topTabs.addTab(topTabs.newTab().setIcon(R.drawable.ic_shops));
        topTabs.addTab(topTabs.newTab().setIcon(R.drawable.ic_basket));
        topTabs.addTab(topTabs.newTab().setIcon(R.drawable.ic_more));
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
