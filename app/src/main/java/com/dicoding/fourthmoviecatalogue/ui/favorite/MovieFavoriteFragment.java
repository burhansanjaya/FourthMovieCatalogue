package com.dicoding.fourthmoviecatalogue.ui.favorite;


import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.dicoding.fourthmoviecatalogue.R;
import com.dicoding.fourthmoviecatalogue.adapter.MovieFavoriteAdapater;
import com.dicoding.fourthmoviecatalogue.db.MovieHelper;
import com.dicoding.fourthmoviecatalogue.helper.MappingHelper;
import com.dicoding.fourthmoviecatalogue.model.ModelFavorite;
import com.dicoding.fourthmoviecatalogue.model.ModelMovie;
import com.google.android.material.snackbar.Snackbar;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class MovieFavoriteFragment extends Fragment implements LoadMoviesCallback {

    private ProgressBar progressBar;
    private RecyclerView rvMovie;
    private MovieFavoriteAdapater adapater;
    private MovieHelper movieHelper;
    private static final String EXTRA_STATE = "EXTRA_STATE";

    public MovieFavoriteFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_movie_favorite, container, false);

        progressBar = rootView.findViewById(R.id.progressBar);
        rvMovie = rootView.findViewById(R.id.rv_movie);
        rvMovie.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvMovie.setHasFixedSize(true);
        adapater = new MovieFavoriteAdapater(getActivity());
        rvMovie.setAdapter(adapater);

        movieHelper = MovieHelper.getInstance(getContext());
        movieHelper.open();

        new LoadMoviesAsync(movieHelper, this).execute();

        if (savedInstanceState == null){
            new LoadMoviesAsync(movieHelper, this).execute();
        }else{
            ArrayList<ModelFavorite> list = savedInstanceState.getParcelableArrayList(EXTRA_STATE);
            if (list != null){
                adapater.setListMovieFavorite(list);
            }
        }

        return rootView;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(EXTRA_STATE, adapater.getListMovieFavorite());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        movieHelper.close();
    }

    private void showSnackbarMessage(String message){
        Snackbar.make(rvMovie, message, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void preExecute() {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                progressBar.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public void postExecute(ArrayList<ModelFavorite> modelFavorites) {
        progressBar.setVisibility(View.INVISIBLE);
        if (modelFavorites.size() > 0){
            adapater.setListMovieFavorite(modelFavorites);
        }else{
            adapater.setListMovieFavorite(new ArrayList<ModelFavorite>());
            showSnackbarMessage("Tidak Ada Data Saat Ini");
        }
    }

    private static class LoadMoviesAsync extends AsyncTask<Void, Void, ArrayList<ModelFavorite>>{

        private final WeakReference<MovieHelper> weakMovieHelper;
        private final WeakReference<LoadMoviesCallback> weakCallback;

        private LoadMoviesAsync(MovieHelper movieHelper, LoadMoviesCallback callback){
            weakMovieHelper = new WeakReference<>(movieHelper);
            weakCallback = new WeakReference<>(callback);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            weakCallback.get().preExecute();
        }

        @Override
        protected ArrayList<ModelFavorite> doInBackground(Void... voids) {
            Cursor dataCursor = weakMovieHelper.get().queryAll();
            return MappingHelper.mapCursorToArrayList(dataCursor);
        }

        @Override
        protected void onPostExecute(ArrayList<ModelFavorite> modelFavorites) {
            super.onPostExecute(modelFavorites);

            weakCallback.get().postExecute(modelFavorites);
        }
    }
}

interface LoadMoviesCallback{
    void preExecute();
    void postExecute(ArrayList<ModelFavorite> modelFavorites);
}
