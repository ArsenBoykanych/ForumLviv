package aboikanych.forumlviv.ui.map;

import android.content.Context;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import aboikanych.forumlviv.R;
import aboikanych.forumlviv.net.model.ShopService;

public class AutoCompleteShopAdapter extends ArrayAdapter<ShopService> {

    private final List<ShopService> list;
    public List<ShopService> filteredList = new ArrayList<>();
    private Context context;

    public AutoCompleteShopAdapter(Context context, List<ShopService> list) {
        super(context, 0, list);
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return filteredList.size();
    }

    @Override
    public Filter getFilter() {
        return new ShopFilter(this, list);
    }

    @Override
    public int getPosition(@Nullable ShopService item) {
        return super.getPosition(item);
    }

    @Nullable
    @Override
    public ShopService getItem(int position) {
        return filteredList.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item from filtered list.
        ShopService shopService = filteredList.get(position);

        // Inflate your custom row layout as usual.
        LayoutInflater inflater = LayoutInflater.from(getContext());
        convertView = inflater.inflate(R.layout.item_auto_complete, parent, false);

        TextView tvName = (TextView) convertView.findViewById(R.id.title);
        ImageView ivIcon = (ImageView) convertView.findViewById(R.id.image);
        tvName.setText(shopService.getTitle());
        if (shopService.getImgUrl() != null) {
            Glide.with(context)
                    .load(shopService.getImgUrl())
                    .into(ivIcon);
        }else {
            ivIcon.setVisibility(View.GONE);
        }
        return convertView;
    }
}
