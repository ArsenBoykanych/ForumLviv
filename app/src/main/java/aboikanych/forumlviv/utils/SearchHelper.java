package aboikanych.forumlviv.utils;

import android.widget.Filter;

import java.util.ArrayList;
import java.util.List;

import aboikanych.forumlviv.net.model.ShopService;

public class SearchHelper {

    public interface OnFindSuggestionsListener {
        void onResults(List<ShopService> results);
    }

    public static void findSuggestions(final List<ShopService> shopServices, String query, final int limit,
                                       final OnFindSuggestionsListener listener) {
        new Filter() {

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {

                List<ShopService> suggestionList = new ArrayList<>();
                if (!(constraint == null || constraint.length() == 0)) {

                    for (ShopService suggestion : shopServices) {
                        if (suggestion.getTitle().toUpperCase()
                                .contains(constraint.toString().toUpperCase())) {

                            suggestionList.add(suggestion);
                            if (limit != -1 && suggestionList.size() == limit) {
                                break;
                            }
                        }
                    }
                }

                FilterResults results = new FilterResults();
                results.values = suggestionList;
                results.count = suggestionList.size();
                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {

                if (listener != null) {
                    listener.onResults((List<ShopService>) results.values);
                }
            }
        }.filter(query);

    }
}