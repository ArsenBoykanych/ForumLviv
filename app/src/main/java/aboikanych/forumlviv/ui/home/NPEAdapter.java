package aboikanych.forumlviv.ui.home;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import aboikanych.forumlviv.R;
import aboikanych.forumlviv.net.model.NPEModel;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Boykanych on 06.06.2017.
 */

public class NPEAdapter extends RecyclerView.Adapter<NPEAdapter.NPEHolder> {

    private Context context;
    private List<NPEModel> itemsList;

    public NPEAdapter(Context context, List<NPEModel> itemsList) {
        this.context = context;
        this.itemsList = itemsList;
    }

    @Override
    public NPEAdapter.NPEHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_npe, parent, false);
        return new NPEHolder(v);
    }

    @Override
    public void onBindViewHolder(NPEAdapter.NPEHolder holder, int position) {
        NPEModel npeModel = itemsList.get(position);
        holder.title.setText(npeModel.getTitle());
        if (npeModel.getDatePublish() == null || npeModel.getDatePublish().equals("")) {
            holder.date.setVisibility(View.GONE);
        } else {
            holder.date.setText(npeModel.getDatePublish());
        }
        Glide.with(context)
                .load(npeModel.getImgUrl())
                .into(holder.image);
    }

    @Override
    public int getItemCount() {
        return itemsList.size();
    }

    public class NPEHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.image)
        ImageView image;
        @BindView(R.id.title)
        TextView title;
        @BindView(R.id.date)
        TextView date;

        public NPEHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Intent startAppIntent = new Intent(context, NPEDetailActivity.class);
            startAppIntent.putExtra(NPEDetailActivity.EXTRA_NPE, itemsList.get(getAdapterPosition()));
            context.startActivity(startAppIntent);
        }
    }
}
