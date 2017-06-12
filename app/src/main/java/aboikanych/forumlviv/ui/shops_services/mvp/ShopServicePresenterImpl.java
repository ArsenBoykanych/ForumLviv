package aboikanych.forumlviv.ui.shops_services.mvp;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import aboikanych.forumlviv.net.model.ShopService;
import aboikanych.forumlviv.utils.Constants;

/**
 * Created by Boykanych on 05.06.2017.
 */

public class ShopServicePresenterImpl implements ShopServicePresenter {

    private ShopServiceView view;
    private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

    @Override
    public void getShopsServices() {
        databaseReference.child(Constants.DATA_SHOPS).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<ShopService> shopServiceList = new ArrayList<>();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    shopServiceList.add(postSnapshot.getValue(ShopService.class));
                }
                view.onShopsServicesLoaded(shopServiceList);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                view.baseError(databaseError.getMessage());
            }
        });
    }

    @Override
    public void getFavShopsServices() {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        if (auth.getCurrentUser() != null) {
            databaseReference.child(Constants.DATA_USERS).child(auth.getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    List<String> favShops = new ArrayList<>();
                    if (dataSnapshot.exists()) {
                        for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                            favShops.add(postSnapshot.getValue(String.class));
                        }
                        view.onFavShopsServicesLoaded(favShops);
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    view.baseError(databaseError.getMessage());
                }
            });
        } else {
            view.onFavShopsServicesLoaded(null);
        }
    }

    @Override
    public void onResume() {

    }

    @Override
    public void onPause() {

    }

    @Override
    public void onDestroy() {
        view = null;
    }

    @Override
    public void attachView(ShopServiceView view) {
        this.view = view;
    }
}
