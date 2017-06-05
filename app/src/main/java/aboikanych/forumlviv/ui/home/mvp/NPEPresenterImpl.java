package aboikanych.forumlviv.ui.home.mvp;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import aboikanych.forumlviv.net.model.NPEModel;
import aboikanych.forumlviv.utils.Constants;

/**
 * Created by Boykanych on 05.06.2017.
 */

public class NPEPresenterImpl implements NPEPresenter {

    private NPEView view;
    private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

    @Override
    public void getNPE() {
        databaseReference.child(Constants.DATA_NPE).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<NPEModel> list = new ArrayList<>();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    list.add(postSnapshot.getValue(NPEModel.class));
                }
                view.onNPELoaded(list);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                view.baseError(databaseError.getMessage());
            }
        });
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
    public void attachView(NPEView view) {
        this.view = view;
    }
}
