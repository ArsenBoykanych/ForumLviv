package aboikanych.forumlviv.ui.main;

import android.content.Intent;
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
import android.widget.TextView;

import com.arlib.floatingsearchview.FloatingSearchView;
import com.arlib.floatingsearchview.suggestions.SearchSuggestionsAdapter;
import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion;
import com.bumptech.glide.Glide;

import java.util.List;

import aboikanych.forumlviv.R;
import aboikanych.forumlviv.base.BaseActivity;
import aboikanych.forumlviv.net.model.ShopService;
import aboikanych.forumlviv.ui.calbacks.ForumActivityCallbacks;
import aboikanych.forumlviv.ui.home.HomeFragment;
import aboikanych.forumlviv.ui.map.MapFragment;
import aboikanych.forumlviv.ui.more.MoreFragment;
import aboikanych.forumlviv.ui.shops_services.ShopDetailActivity;
import aboikanych.forumlviv.ui.shops_services.ShopServiceFragment;
import aboikanych.forumlviv.utils.SearchHelper;
import aboikanych.forumlviv.utils.Utils;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ForumActivity extends BaseActivity implements ForumActivityCallbacks {

    private final static int TAB_HOME = 0;
    private final static int TAB_MAP = 1;
    private final static int TAB_SHOPS = 2;
    private final static int TAB_SUITS = 3;
    private final static int TAB_OTHER = 4;

    private final static String FRAGMENT_TAG = "TOP_FRAGMENT";

    @BindView(R.id.menuTabLayout)
    TabLayout topTabs;
    @BindView(R.id.forumLogoCircle)
    ImageView forumLogoCircle;
    @BindView(R.id.searchButton)
    ImageView searchButton;
    @BindView(R.id.fragmentContainer)
    View fragmentContainer;
    @BindView(R.id.searchView)
    FloatingSearchView searchView;

    private int[] forumLogoColors;
    private int logoColorPosition = 0;
    private Handler handler;
    public List<ShopService> listOfShops;

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
        setupFloatingSearch();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Utils.hideSoftKeyboard(this);
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
                fragment = new MoreFragment();
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
        topTabs.addTab(topTabs.newTab().setIcon(R.drawable.ic_map_white));
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

    @OnClick(R.id.searchButton)
    public void onSearchClick() {
        searchView.setVisibility(View.VISIBLE);
        searchButton.setVisibility(View.GONE);
        searchView.setSearchFocused(true);
    }

    @Override
    public void setSearchVisibility(boolean visible) {
        searchButton.setVisibility(visible ? View.VISIBLE : View.GONE);
    }

    @Override
    public void listOfShopsToSearch(List<ShopService> searchServiceList) {
        listOfShops = searchServiceList;
    }

    private void setupFloatingSearch() {
        searchView.setOnQueryChangeListener(new FloatingSearchView.OnQueryChangeListener() {

            @Override
            public void onSearchTextChanged(String oldQuery, final String newQuery) {

                if (!oldQuery.equals("") && newQuery.equals("")) {
                    searchView.clearSuggestions();
                } else {
                    searchView.showProgress();
                    if (listOfShops != null) {
                        SearchHelper.findSuggestions(listOfShops, newQuery, 5,
                                new SearchHelper.OnFindSuggestionsListener() {

                                    @Override
                                    public void onResults(List<ShopService> results) {
                                        searchView.swapSuggestions(results);
                                        searchView.hideProgress();
                                    }
                                });
                    }
                }
            }
        });

        searchView.setOnSearchListener(new FloatingSearchView.OnSearchListener() {
            @Override
            public void onSuggestionClicked(final SearchSuggestion searchSuggestion) {
                Intent startAppIntent = new Intent(ForumActivity.this, ShopDetailActivity.class);
                startAppIntent.putExtra(ShopDetailActivity.EXTRA_SHOP, searchSuggestion);
                startActivity(startAppIntent);
                closeSearchView();
            }

            @Override
            public void onSearchAction(String query) {
            }
        });

        searchView.setOnHomeActionClickListener(new FloatingSearchView.OnHomeActionClickListener() {
            @Override
            public void onHomeClicked() {
                closeSearchView();
            }
        });

        searchView.setOnBindSuggestionCallback(new SearchSuggestionsAdapter.OnBindSuggestionCallback() {
            @Override
            public void onBindSuggestion(View suggestionView, ImageView leftIcon,
                                         TextView textView, SearchSuggestion item, int itemPosition) {
                ShopService shopService = (ShopService) item;
                Glide.with(ForumActivity.this)
                        .load(((ShopService) item).getImgUrl())
                        .fitCenter()
                        .into(leftIcon);

                textView.setText(shopService.getTitle());
            }

        });
    }

    private void closeSearchView() {
        searchView.setVisibility(View.GONE);
        searchButton.setVisibility(View.VISIBLE);
        searchView.clearQuery();
    }
}
