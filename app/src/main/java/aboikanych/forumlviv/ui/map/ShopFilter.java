package aboikanych.forumlviv.ui.map;

import android.widget.Filter;

import java.util.ArrayList;
import java.util.List;

import aboikanych.forumlviv.net.model.ShopService;

public class ShopFilter  extends Filter {

    private AutoCompleteShopAdapter adapter;
    private List<ShopService> originalList;
    private List<ShopService> filteredList;

    public ShopFilter(AutoCompleteShopAdapter adapter, List<ShopService> originalList) {
        super();
        this.adapter = adapter;
        this.originalList = originalList;
        this.filteredList = new ArrayList<>();
    }

    @Override
    protected FilterResults performFiltering(CharSequence constraint) {
        filteredList.clear();
        final FilterResults results = new FilterResults();

        if (constraint == null || constraint.length() == 0) {
            filteredList.addAll(originalList);
        } else {
            final String filterPattern = constraint.toString().toLowerCase().trim();

            // Your filtering logic goes in here
            for (final ShopService item : originalList) {
                if (item.getTitle().toLowerCase().contains(filterPattern.toLowerCase())) {
                    filteredList.add(item);
                }
            }
        }
        results.values = filteredList;
        results.count = filteredList.size();
        return results;
    }

    @Override
    protected void publishResults(CharSequence constraint, FilterResults results) {
        adapter.filteredList.clear();
        adapter.filteredList.addAll((List) results.values);
        adapter.notifyDataSetChanged();
    }
}
