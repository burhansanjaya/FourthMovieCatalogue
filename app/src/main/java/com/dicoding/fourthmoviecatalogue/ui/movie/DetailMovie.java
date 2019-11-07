package com.dicoding.fourthmoviecatalogue.ui.movie;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.dicoding.fourthmoviecatalogue.R;
import com.dicoding.fourthmoviecatalogue.db.MovieHelper;
import com.dicoding.fourthmoviecatalogue.model.ModelFavorite;
import com.dicoding.fourthmoviecatalogue.model.ModelMovie;

public class DetailMovie extends AppCompatActivity implements View.OnClickListener{

    private boolean isFavorit = false;
    private ModelFavorite modelFavorite;
    private int position;
    private MovieHelper movieHelper;

    public static String EXTRA_MOVIE = "extra_movie";
    public static final String EXTRA_FILM_FAVORITE = "extra_film_favorite";
    public static final String EXTRA_POSTION = "extra_position";

    public static final int REQUEST_ADD = 100;
    public static final int RESULT_ADD = 101;

    TextView tvTitle;
    TextView tvDescription;
    TextView tvVote;
    TextView tvPopularity;
    TextView tvLanguage;
    ImageView imgPoster;
    ProgressBar progressBar;
    Button btn_favorite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_movie);

        tvTitle = findViewById(R.id.tv_title);
        tvDescription = findViewById(R.id.tv_description);
        tvVote = findViewById(R.id.tv_vote);
        tvPopularity = findViewById(R.id.tv_popularity);
        tvLanguage = findViewById(R.id.tv_language);
        imgPoster = findViewById(R.id.img_poster);
        progressBar = findViewById(R.id.progressBar);
        btn_favorite = findViewById(R.id.btn_favorite);

        btn_favorite.setOnClickListener(this);
        ModelMovie modelMovie = getIntent().getParcelableExtra(EXTRA_MOVIE);

        tvTitle.setText(modelMovie.getTitle());
        tvDescription.setText(modelMovie.getDescription());
        tvVote.setText(modelMovie.getVote_average());
        tvPopularity.setText(modelMovie.getPopularity());
        tvLanguage.setText(modelMovie.getLanguage());
        Glide.with(DetailMovie.this)
                .load(modelMovie.getPoster())
                .apply(new RequestOptions().override(350, 550))
                .into(imgPoster);
        showLoading(true);

        if (imgPoster != null){
            showLoading(false);
        }

        movieHelper = MovieHelper.getInstance(getApplicationContext());
        movieHelper.open();
        modelFavorite = getIntent().getParcelableExtra(EXTRA_FILM_FAVORITE);

        if (modelFavorite != null){
            position = getIntent().getIntExtra(EXTRA_POSTION, 0);
            isFavorit = true;
            btn_favorite.setVisibility(View.GONE);
        }else{
            modelFavorite = new ModelFavorite();
        }

        if (savedInstanceState != null){
            ModelMovie movie = getIntent().getParcelableExtra(EXTRA_MOVIE);

            tvTitle.setText(movie.getTitle());
            tvDescription.setText(movie.getDescription());
            imgPoster.setVisibility(View.VISIBLE);
            Glide.with(this)
                    .load(movie.getPoster())
                    .apply(RequestOptions.placeholderOf(R.color.colorPrimary))
                    .into(imgPoster);

            btn_favorite.setVisibility(View.VISIBLE);
        }else{
            final Handler handler = new Handler();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(1000);
                    }catch (Exception e){
                        e.printStackTrace();
                    }

                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            ModelMovie movie = getIntent().getParcelableExtra(EXTRA_MOVIE);

                            tvTitle.setText(movie.getTitle());
                            tvDescription.setText(movie.getDescription());
                            imgPoster.setVisibility(View.VISIBLE);

                            Glide.with(DetailMovie.this)
                                    .load(movie.getPoster())
                                    .apply(RequestOptions.placeholderOf(R.color.colorPrimary))
                                    .into(imgPoster);

                            btn_favorite.setVisibility(View.VISIBLE);

                            if (getSupportActionBar() != null){
                                getSupportActionBar().setTitle(movie.getTitle());
                            }
                        }
                    });
                }
            }).start();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
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

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_favorite){
            ModelMovie movie = getIntent().getParcelableExtra(EXTRA_MOVIE);
            String title = tvTitle.getText().toString().trim();
            String description = tvDescription.getText().toString().trim();
            String poster = movie.getPoster();

            modelFavorite.setTitle(title);
            modelFavorite.setDescription(description);
            modelFavorite.setPoster_path(poster);

            Intent intent = new Intent();
            intent.putExtra(EXTRA_FILM_FAVORITE, modelFavorite);
            intent.putExtra(EXTRA_POSTION, position);

            if (!isFavorit){
                long result = movieHelper.insertFilm(modelFavorite);
                if (result > 0){
                    setResult(RESULT_ADD, intent);
                    Toast.makeText(DetailMovie.this, "Data Favorit Movie Berhasil Ditambahakan", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }else{
                Toast.makeText(DetailMovie.this, "Data Favorite Movie Gagal Ditambahkan", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
