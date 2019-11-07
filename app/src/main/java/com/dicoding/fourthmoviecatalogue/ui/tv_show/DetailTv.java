package com.dicoding.fourthmoviecatalogue.ui.tv_show;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.dicoding.fourthmoviecatalogue.R;
import com.dicoding.fourthmoviecatalogue.model.ModelTv;

public class DetailTv extends AppCompatActivity {

    public static String EXTRA_TV = "extra_tv";
    TextView tvTitle;
    TextView tvPopularity;
    TextView tvLanguage;
    TextView tvDescription;
    ImageView imgPoster;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_tv);

        tvTitle = findViewById(R.id.tv_title);
        tvDescription = findViewById(R.id.tv_description);
        tvPopularity = findViewById(R.id.tv_popularity);
        tvLanguage = findViewById(R.id.tv_language);
        imgPoster = findViewById(R.id.img_poster);
        progressBar = findViewById(R.id.progressBar);

        ModelTv modelTv = getIntent().getParcelableExtra(EXTRA_TV);

        tvTitle.setText(modelTv.getTitle());
        tvDescription.setText(modelTv.getDescription());
        tvLanguage.setText(modelTv.getLanguage());
        Glide.with(DetailTv.this)
                .load(modelTv.getPoster())
                .apply(new RequestOptions().override(350, 550))
                .into(imgPoster);

        showLoading(true);

        if (imgPoster != null) {
            showLoading(false);
        }
    }

    private void showLoading(Boolean state) {
        if (state) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_change_settings){
            Intent mIntent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
            startActivity(mIntent);
        }
        return super.onOptionsItemSelected(item);
    }
}
