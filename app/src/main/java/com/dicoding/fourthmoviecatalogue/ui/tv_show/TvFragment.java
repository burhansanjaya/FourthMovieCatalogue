package com.dicoding.fourthmoviecatalogue.ui.tv_show;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.dicoding.fourthmoviecatalogue.R;
import com.dicoding.fourthmoviecatalogue.adapter.CardViewTv;
import com.dicoding.fourthmoviecatalogue.model.ModelTv;import com.dicoding.fourthmoviecatalogue.viewmodel.ViewModelTv;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class TvFragment extends Fragment implements CardViewTv.OnItemClickListener {
    private CardViewTv adapter;
    public static String EXTRA_TV = "EXTRA_TV";
    private ProgressBar progressBar;
    private ViewModelTv viewModelTv;

    public TvFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_tv, container, false);

        viewModelTv = ViewModelProviders.of(this).get(ViewModelTv.class);
        viewModelTv.getTv().observe(this, getTv);
        adapter = new CardViewTv(new ArrayList<ModelTv>());
        adapter.notifyDataSetChanged();

        RecyclerView rvTv = rootView.findViewById(R.id.rv_tv);
        rvTv.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvTv.setAdapter(adapter);
        adapter.setOnItemClickListener(TvFragment.this);

        progressBar = rootView.findViewById(R.id.progressBar);
        showLoading(true);
        viewModelTv.setTv();

        return rootView;
    }

    private Observer<ArrayList<ModelTv>> getTv = new Observer<ArrayList<ModelTv>>() {
        @Override
        public void onChanged(@Nullable ArrayList<ModelTv> modelTvs) {
            if (modelTvs != null){
                adapter.setData(modelTvs);
                showLoading(false);
            }
        }
    };

    private void showLoading(Boolean state){
        if (state){
            progressBar.setVisibility(View.VISIBLE);
        }else{
            progressBar.setVisibility(View.GONE);
        }
    }


    @Override
    public void OnItemClick(int position) {
        ModelTv mTv = adapter.getData().get(position);
        Intent moveDetail = new Intent(getActivity(), DetailTv.class);
        moveDetail.putExtra(DetailTv.EXTRA_TV, mTv);
        startActivity(moveDetail);
    }
}
