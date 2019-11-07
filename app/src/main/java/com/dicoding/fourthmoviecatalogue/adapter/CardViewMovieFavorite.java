package com.dicoding.fourthmoviecatalogue.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.dicoding.fourthmoviecatalogue.R;
import com.dicoding.fourthmoviecatalogue.model.ModelFavorite;
import com.dicoding.fourthmoviecatalogue.model.ModelMovie;

import java.util.ArrayList;

public class CardViewMovieFavorite extends RecyclerView.Adapter<CardViewMovieFavorite.MovieFavoriteViewHolder> {

    private ArrayList<ModelFavorite> listMovieFavorite = new ArrayList<>();
    private Activity activity;
    private ArrayList<ModelFavorite> mData;
    private OnItemClickListener mListener;

    public interface OnItemClickListener{
        void OnItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        mListener = listener;
    }

    public CardViewMovieFavorite(Activity activity) {
        this.activity = activity;
    }

    public ArrayList<ModelFavorite> getListMovieFavorite() {
        return listMovieFavorite;
    }

    public void setListMovieFavorite(ArrayList<ModelFavorite> listNotes) {

        if (listMovieFavorite.size() > 0) {
            this.listMovieFavorite.clear();
        }
        this.listMovieFavorite.addAll(listNotes);

        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MovieFavoriteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movie_favorite, parent, false);
        return new MovieFavoriteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieFavoriteViewHolder holder, int position) {
        holder.tvTitle.setText(listMovieFavorite.get(position).getTitle());
        holder.tvDescription.setText(listMovieFavorite.get(position).getDescription());
        Glide.with(holder.imagePoster)
                .load(listMovieFavorite.get(position).getPoster_path())
                .apply(new RequestOptions().override(350, 550))
                .into(holder.imagePoster);
    }

    @Override
    public int getItemCount() {
        return listMovieFavorite.size();
    }

    public class MovieFavoriteViewHolder extends RecyclerView.ViewHolder {
        final TextView tvTitle, tvDescription;
        final ImageView imagePoster;
        public MovieFavoriteViewHolder(View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_item_title);
            tvDescription = itemView.findViewById(R.id.tv_item_description);
            imagePoster = itemView.findViewById(R.id.img_item_poster);
        }
    }

    //public ArrayList<ModelMovie> getData(){
        //return mData;
    //}
}
