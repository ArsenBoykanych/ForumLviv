package aboikanych.forumlviv;

import android.app.Application;

import com.google.firebase.database.FirebaseDatabase;
import com.indoorway.android.location.sdk.IndoorwayLocationSdk;
import com.indoorway.android.map.sdk.IndoorwayMapSdk;

/**
 * Created by Boykanych on 22.05.2017.
 */

public class ForumApplication extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
        // define your api key
        String trafficApiKey = "bdcf3579-0c06-4ba5-b8dc-3f4bf8de197a";
        // sdk for map view and fetching map objects
        IndoorwayMapSdk.init(this, trafficApiKey);

        // sdk for indoor positioning
        IndoorwayLocationSdk.init(this, trafficApiKey);

        FirebaseDatabase.getInstance().setPersistenceEnabled(true);


    }
}
