package aboikanych.forumlviv.ui.shops_services;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import aboikanych.forumlviv.R;
import aboikanych.forumlviv.base.BaseActivity;
import aboikanych.forumlviv.net.model.ShopService;

/**
 * Created by Boykanych on 05.06.2017.
 */

public class ShopDetailActivity extends BaseActivity {

    public final static String EXTRA_SHOP = "extra_shop";
    public final static String EXTRA_SHOP_FAV = "extra_shop_faw";

    private ShopService shopService;
    private List<String> favs;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_content);
        ShopDetailFragment fragment = ShopDetailFragment.newInstance((ShopService) getIntent().getParcelableExtra(EXTRA_SHOP),
                getIntent().getStringArrayListExtra(EXTRA_SHOP_FAV), false);
        getSupportFragmentManager().beginTransaction().add(R.id.content, fragment).commit();

        shopService = getIntent().getParcelableExtra(EXTRA_SHOP);
        favs = getIntent().getStringArrayListExtra(EXTRA_SHOP_FAV);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_fav, menu);
        if (favs != null) {
            if (favs.contains(shopService.getTitle())) {
                menu.getItem(0).setIcon(getResources().getDrawable(R.drawable.ic_fav_filled));
            }
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.fav:
                FirebaseAuth auth = FirebaseAuth.getInstance();
                if (auth.getCurrentUser() != null) {
                    if (favs == null) {
                        favs = new ArrayList<>();
                        favs.add(shopService.getTitle());
                        updateUserFaws();
                        item.setIcon(getResources().getDrawable(R.drawable.ic_fav_filled));
                        showToast(shopService.getTitle() + " додано до улюблених");

                    } else if (favs.contains(shopService.getTitle())) {
                        favs.remove(shopService.getTitle());
                        updateUserFaws();
                        showToast(shopService.getTitle() + " видалено з улюблених");
                        item.setIcon(getResources().getDrawable(R.drawable.ic_fav));
                    } else {
                        favs.add(shopService.getTitle());
                        updateUserFaws();
                        item.setIcon(getResources().getDrawable(R.drawable.ic_fav_filled));
                        showToast(shopService.getTitle() + " додано до улюблених");
                    }
                } else {
                    showToast("Авторизуйтесь щоб додати до улюблених");
                    return false;
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private boolean updateUserFaws() {
        final FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseDatabase.getInstance().getReference().child("users").child(auth.getCurrentUser().getUid()).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                DatabaseReference userRef = FirebaseDatabase.getInstance().getReference().child("users").child(auth.getCurrentUser().getUid());
                for (String favString : favs) {
                    String tagKey = userRef.push().getKey();
                    userRef.child(tagKey).setValue(favString);
                }
            }
        });
        return true;
    }
}
