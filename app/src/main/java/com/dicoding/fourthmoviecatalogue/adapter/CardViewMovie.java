package com.dicoding.fourthmoviecatalogue.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.dicoding.fourthmoviecatalogue.R;
import com.dicoding.fourthmoviecatalogue.model.ModelMovie;

import java.util.ArrayList;

public class CardViewMovie extends RecyclerView.Adapter<CardViewMovie.CardViewViewHolder> {

    private ArrayList<ModelMovie> mData;
    private OnItemClickListener mListener;

    public interface OnItemClickListener{
        void OnItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        mListener = listener;
    }

    public CardViewMovie(ArrayList<ModelMovie> listmovie){
        this.mData = listmovie;
    }

    public void setData(ArrayList<ModelMovie> items){
        mData.clear();
        this.mData.addAll(items);
        notifyDataSetChanged();
    }

    public void clearData(){
        mData.clear();
    }

    @NonNull
    @Override
    public CardViewViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_cardview_movie,
                viewGroup, false);
        return new CardViewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CardViewViewHolder holder, int i) {
        holder.bind(mData.get(i));
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class CardViewViewHolder extends RecyclerView.ViewHolder {

        ImageView imgPoster;
        TextView tvTitle, tvDescription;
        Button btnDetail;

        public CardViewViewHolder(@NonNull View itemView) {
            super(itemView);
            imgPoster = itemView.findViewById(R.id.img_item_poster);
            tvTitle = itemView.findViewById(R.id.tv_item_title);
            tvDescription = itemView.findViewById(R.id.tv_item_description);
            btnDetail = itemView.findViewById(R.id.btn_detail);

            btnDetail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mListener != null){
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            mListener.OnItemClick(position);
                        }
                    }
                }
            });
        }

        public void bind(ModelMovie modelMovie) {

            Glide.with(itemView.getContext())
                    .load(modelMovie.getPoster())
                    .apply(new RequestOptions().override(350, 550))
                    .into(imgPoster);
            tvTitle.setText(modelMovie.getTitle());
            tvDescription.setText(modelMovie.getDescription());
        }
    }

    public ArrayList<ModelMovie> getData(){
        return mData;
    }
}
