package com.dicoding.fourthmoviecatalogue.ui.movie;


import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.dicoding.fourthmoviecatalogue.R;
import com.dicoding.fourthmoviecatalogue.adapter.CardViewMovie;
import com.dicoding.fourthmoviecatalogue.model.ModelMovie;
import com.dicoding.fourthmoviecatalogue.viewmodel.ViewModelMovie;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class MovieFragment extends Fragment implements CardViewMovie.OnItemClickListener {

    private CardViewMovie adapter;
    public static String EXTRA_MOVIE = "EXTRA_MOVIE";
    private static TypedArray dataPoster;
    private ProgressBar progressBar;
    private ViewModelMovie viewModelMovie;

    public MovieFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_movie, container, false);

        viewModelMovie = ViewModelProviders.of(this).get(ViewModelMovie.class);
        viewModelMovie.getMovies().observe(this, getMovie);
        adapter = new CardViewMovie(new ArrayList<ModelMovie>());
        adapter.notifyDataSetChanged();

        RecyclerView rvMovie = rootView.findViewById(R.id.rv_movie);
        rvMovie.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvMovie.setAdapter(adapter);
        adapter.setOnItemClickListener(MovieFragment.this);

        progressBar = rootView.findViewById(R.id.progressBar);
        showLoading(true);
        viewModelMovie.setMovie();

        return rootView;
    }

    private Observer<ArrayList<ModelMovie>> getMovie = new Observer<ArrayList<ModelMovie>>() {
        @Override
        public void onChanged(ArrayList<ModelMovie> modelMovies) {
            if (modelMovies != null){
                adapter.setData(modelMovies);
                showLoading(false);
            }
        }
    };

    @Override
    public void OnItemClick(int position) {
        ModelMovie mMovie = adapter.getData().get(position);
        Intent moveDetail = new Intent(getActivity(), DetailMovie.class);
        moveDetail.putExtra(DetailMovie.EXTRA_MOVIE, mMovie);
        startActivity(moveDetail);
    }

    private void showLoading(Boolean state){
        if (state){
            progressBar.setVisibility(View.VISIBLE);
        }else{
            progressBar.setVisibility(View.GONE);
        }
    }
}
