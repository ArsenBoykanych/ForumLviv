package aboikanych.forumlviv.ui.shops_services;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

import aboikanych.forumlviv.R;
import aboikanych.forumlviv.base.BaseActivity;
import aboikanych.forumlviv.net.model.ShopService;
import aboikanych.forumlviv.utils.Utils;

/**
 * Created by Boykanych on 05.06.2017.
 */

public class ShopDetailActivity extends BaseActivity {

    public final static String EXTRA_SHOP = "extra_shop";
    private ShopService shopService;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_content);
        ShopDetailFragment fragment = ShopDetailFragment.newInstance((ShopService)getIntent().getParcelableExtra(EXTRA_SHOP), false);
        getSupportFragmentManager().beginTransaction().add(R.id.content, fragment).commit();
        shopService = getIntent().getParcelableExtra(EXTRA_SHOP);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_fav, menu);
        List<String> favs = Utils.readFav(this);
        if(favs != null){
            if(favs.contains(shopService.getTitle())){
                menu.getItem(0).setIcon(getResources().getDrawable(R.drawable.ic_fav_filled));
            }
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.fav:
                List<String> favs = Utils.readFav(this);
                if(favs == null || !favs.contains(shopService.getTitle())){
                    if(favs == null) {
                        favs = new ArrayList<>();
                    }
                    favs.add(shopService.getTitle());
                    Utils.writeFav(this, favs);
                    item.setIcon(getResources().getDrawable(R.drawable.ic_fav_filled));
                    showToast(shopService.getTitle() + " додано до улюблених");
                }
                else if(favs.contains(shopService.getTitle())){
                    item.setIcon(getResources().getDrawable(R.drawable.ic_fav));
                    favs.remove(shopService.getTitle());
                    Utils.writeFav(this, favs);
                    showToast(shopService.getTitle() + " видалено з улюблених");
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
