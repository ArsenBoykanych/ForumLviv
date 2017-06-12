package aboikanych.forumlviv.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

public class ActionUtils {

    public static Intent getActionShare(Context context, String url) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_SUBJECT, "Forum Lviv");
        intent.putExtra(Intent.EXTRA_TEXT, url);
        return Intent.createChooser(intent, "Forum Lviv");
    }

    public static void actionCall(Context context, String phoneNumber) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + phoneNumber));
        context.startActivity(intent);
    }

    public static void actionBrowser(Context context, String website) {
        if (!website.contains("http")) {
            website = "http://" + website;
        }
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(website));
        context.startActivity(intent);
    }
}
