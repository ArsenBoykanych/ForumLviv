package aboikanych.forumlviv.ui.home;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import aboikanych.forumlviv.R;
import aboikanych.forumlviv.base.BaseActivity;
import aboikanych.forumlviv.net.model.NPEModel;
import aboikanych.forumlviv.utils.ActionUtils;
import butterknife.BindView;
import butterknife.ButterKnife;

public class NPEDetailActivity extends BaseActivity {

    public final static String EXTRA_NPE = "extra_npe";
    private static final int REQUEST_SHARING = 7;

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.description)
    TextView description;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.publishDate)
    TextView publishDate;
    @BindView(R.id.eventDate)
    TextView eventDate;
    @BindView(R.id.eventView)
    View eventView;
    @BindView(R.id.image)
    ImageView image;

    private NPEModel npeModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_npe_detail);
        ButterKnife.bind(this);
        npeModel = getIntent().getParcelableExtra(EXTRA_NPE);
        setupToolbar();
        if (npeModel.getType().equals("EVENTS")) {
            eventView.setVisibility(View.VISIBLE);
            eventDate.setText(npeModel.getDateFrom().substring(0,5).replace("-",".")
                    + " - " +
                    npeModel.getDateTo().substring(0,5).replace("-","."));
        }
        description.setText(npeModel.getDescription());
        title.setText(npeModel.getTitle());
        publishDate.setText(npeModel.getDatePublish().replace("-","."));
        Glide.with(this)
                .load(npeModel.getImgUrl())
                .placeholder(R.mipmap.placeholder)
                .into(image);
        if (npeModel.getType().equals("EVENTS")) {
            toolbar.setSubtitle("Подія");
        } else if (npeModel.getType().equals("NEWS")) {
            toolbar.setSubtitle("Новина");
        } else {
            toolbar.setSubtitle("Акція");
        }
    }

    private void setupToolbar() {
        toolbar.setSubtitleTextColor(Color.WHITE);
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setTitle(npeModel.getTitle());
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onBackPressed();
                }
            });
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_share, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.share:
                startActivityForResult(ActionUtils.getActionShare(this, String.format("%s%n%s", npeModel.getTitle(),
                        Html.fromHtml(npeModel.getDescription()).toString())), REQUEST_SHARING);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_SHARING) {
            showToast("Ви успішно поділились новиною");
        }
    }

}
