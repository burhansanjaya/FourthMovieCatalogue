package com.dicoding.fourthmoviecatalogue.ui.favorite;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

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
public class MovieFavoriteFragment extends Fragment implements LoadMoviesCallback, MovieFavoriteAdapater.OnItemClickListener {

    private ProgressBar progressBar;
    private RecyclerView rvMovie;
    private MovieFavoriteAdapater adapater;
    private MovieHelper movieHelper;
    private int position;
    private ModelFavorite modelFavorite;
    private static final String EXTRA_STATE = "EXTRA_STATE";

    private final int ALERT_DIALOG_CLOSE = 10;
    public static final int RESULT_DELETE = 301;
    private final int ALERT_DIALOG_DELETE = 20;
    public static final String EXTRA_POSITION = "extra_position";

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
        adapater.setOnItemClickListener(this);

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
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == MovieFavoriteFragment.RESULT_DELETE){
            int position = data.getIntExtra(MovieFavoriteFragment.EXTRA_POSITION, 0);
            adapater.removeItem(position);
            showSnackbarMessage("Satu Item Berhasil Dihapus");
        }

        Log.d("ResultCode", String.valueOf(resultCode));
    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        super.startActivityForResult(intent, requestCode);
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

    @Override
    public void OnItemClick(int id) {
        showAlertDialog(ALERT_DIALOG_DELETE, id);
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

    private void showAlertDialog(int type, final int idmovie) {
        final boolean isDialogClose = type == ALERT_DIALOG_CLOSE;
        String dialogTitle, dialogMessage;

        if (isDialogClose) {
            dialogTitle = "Batal";
            dialogMessage = "Apakah anda ingin membatalkan perubahan pada form?";
        } else {
            dialogMessage = "Apakah anda yakin ingin menghapus item ini?";
            dialogTitle = "Hapus Note";
        }

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());

        alertDialogBuilder.setTitle(dialogTitle);
        alertDialogBuilder
                .setMessage(dialogMessage)
                .setCancelable(false)
                .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        if (isDialogClose) {
                            getActivity().finish();
                        } else {
                            long result = movieHelper.deleteFilm(idmovie);
                            if (result > 0) {
                                adapater.removeItem(position);
                                showSnackbarMessage("Satu Item Berhasil Dihapus");
                            } else {
                                Toast.makeText(getActivity(), "Gagal menghapus data", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                })
                .setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();

    }
}

interface LoadMoviesCallback{
    void preExecute();
    void postExecute(ArrayList<ModelFavorite> modelFavorites);
}
