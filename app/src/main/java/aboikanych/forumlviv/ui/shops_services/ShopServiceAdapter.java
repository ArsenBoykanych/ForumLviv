package aboikanych.forumlviv.ui.shops_services;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.List;

import aboikanych.forumlviv.R;
import aboikanych.forumlviv.net.model.ShopService;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Boykanych on 05.06.2017.
 */

public class ShopServiceAdapter extends RecyclerView.Adapter<ShopServiceAdapter.ShopServiceHolder> {

    private Context context;
    private List<ShopService> itemsList;

    public ShopServiceAdapter(Context context, List<ShopService> itemsList) {
        this.context = context;
        this.itemsList = itemsList;
    }

    @Override
    public ShopServiceAdapter.ShopServiceHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_shop, parent, false);
        return new ShopServiceHolder(v);
    }

    @Override
    public void onBindViewHolder(ShopServiceAdapter.ShopServiceHolder holder, int position) {
        ShopService shopService = itemsList.get(position);
        holder.title.setText(shopService.getTitle());
        Glide.with(context)
                .load(shopService.getImgUrl())
                .into(holder.image);
    }

    @Override
    public int getItemCount() {
        return itemsList.size();
    }

    public class ShopServiceHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.image)
        ImageView image;
        @BindView(R.id.title)
        TextView title;

        public ShopServiceHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Toast.makeText(context, itemsList.get(getAdapterPosition()).getTitle(), Toast.LENGTH_SHORT).show();
            Intent startAppIntent = new Intent(context, ShopDetailActivity.class);
            startAppIntent.putExtra(ShopDetailActivity.EXTRA_SHOP, itemsList.get(getAdapterPosition()));
            context.startActivity(startAppIntent);

        }
    }
}
