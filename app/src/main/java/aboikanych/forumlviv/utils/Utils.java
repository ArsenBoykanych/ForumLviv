package aboikanych.forumlviv.utils;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import aboikanych.forumlviv.net.model.ShopService;

public class Utils {

    private static final String TAG_FAV = "fav";

    public static boolean isNetworkConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }

    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager =
                (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        View focusView = activity.getCurrentFocus();
        if (focusView != null) {
            inputMethodManager.hideSoftInputFromWindow(focusView.getWindowToken(), 0);
        }
    }

    public static boolean isInMall(){
        return false;
    }

    public static List<ShopService> getIEElement(){
        List<ShopService> ie = new ArrayList<>();
        ie.add(new ShopService("Туалет","https://firebasestorage.googleapis.com/v0/b/forumlviv-b5c2b.appspot.com/o/ie%2Ftoilet.png?alt=media&token=b5c8ca8d-4fbe-4be8-ba3f-fc76620ccd8a"));
        ie.add(new ShopService("Ліфт","https://firebasestorage.googleapis.com/v0/b/forumlviv-b5c2b.appspot.com/o/ie%2Flift.png?alt=media&token=5e3b4768-d934-4cdf-bd84-79108a6d2260"));
        ie.add(new ShopService("Ескалатор вниз","https://firebasestorage.googleapis.com/v0/b/forumlviv-b5c2b.appspot.com/o/ie%2Felevator-down.png?alt=media&token=3601e99f-7d4c-4390-92de-8a33a2af029e"));
        ie.add(new ShopService("Ескалатор вгору","https://firebasestorage.googleapis.com/v0/b/forumlviv-b5c2b.appspot.com/o/ie%2Felevator-up.png?alt=media&token=bb2e0a3f-3d8e-4072-bea7-86566871c291"));
        ie.add(new ShopService("Туалет для людей з обмеженими можливостями","https://firebasestorage.googleapis.com/v0/b/forumlviv-b5c2b.appspot.com/o/ie%2Fenabled.png?alt=media&token=e7ca29ab-e110-42e2-bf05-4b3bdaab5e3e"));
        ie.add(new ShopService("Кімната матері та дитини","https://firebasestorage.googleapis.com/v0/b/forumlviv-b5c2b.appspot.com/o/ie%2Fbaby.png?alt=media&token=210a6e5a-d53a-4813-8ac8-912d448dce20"));
        ie.add(new ShopService("Паркінг (Східна зона)","https://firebasestorage.googleapis.com/v0/b/forumlviv-b5c2b.appspot.com/o/ie%2Fparking-blue.png?alt=media&token=55a09dbf-0455-4318-a48f-dba164105bcc"));
        ie.add(new ShopService("Паркінг (Західна зона)","https://firebasestorage.googleapis.com/v0/b/forumlviv-b5c2b.appspot.com/o/ie%2Fparking-blue.png?alt=media&token=55a09dbf-0455-4318-a48f-dba164105bcc"));
        ie.add(new ShopService("Вхід","https://firebasestorage.googleapis.com/v0/b/forumlviv-b5c2b.appspot.com/o/ie%2Fent3.png?alt=media&token=1f0dae54-a137-46ae-9eeb-a0a17e29f729"));
        ie.add(new ShopService("Банкомат","https://firebasestorage.googleapis.com/v0/b/forumlviv-b5c2b.appspot.com/o/ie%2Fatm.png?alt=media&token=1b09b9ab-e061-4e01-be9e-5357cd6da649"));
        ie.add(new ShopService("Інформаційний центр","https://firebasestorage.googleapis.com/v0/b/forumlviv-b5c2b.appspot.com/o/ie%2Finfo.png?alt=media&token=8980525b-45da-473a-90b3-14a3599189f0"));
        ie.add(new ShopService("Центр управління","https://firebasestorage.googleapis.com/v0/b/forumlviv-b5c2b.appspot.com/o/ie%2Fmanagment.png?alt=media&token=25617232-dc9b-40b9-9d9f-f19062ce1cb7"));
        return ie;
    }

    public static void writeFav(Context context, List<String> fav) {
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putString(TAG_FAV, new Gson().toJson(fav))
                .apply();
    }

    public static List<String> readFav(Context context) {
        Type type = new TypeToken<ArrayList<String>>() {}.getType();
        return new Gson().fromJson(PreferenceManager.getDefaultSharedPreferences(context)
                .getString(TAG_FAV, ""), type);
    }
}
