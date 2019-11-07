package com.dicoding.fourthmoviecatalogue.model;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONObject;

public class ModelMovie implements Parcelable {

    private int id;
    private String title;
    private String description;
    private String vote_average;
    private String popularity;
    private String poster;
    private String language;
    private String imgUrl = "https://image.tmdb.org/t/p/w154";

    public ModelMovie() {
    }

    public ModelMovie(JSONObject object){
        try {
            int id = object.getInt("id");
            String title = object.getString("title");
            String release = object.getString("release_date");
            String description = object.getString("overview");
            String vote_average = object.getString("vote_average");
            String popularity = object.getString("popularity");
            String language = object.getString("original_language");
            String poster = imgUrl+object.getString("poster_path");

            this.id = id;
            this.title = title;
            this.vote_average = vote_average;
            this.popularity = popularity;
            this.language = language;
            this.description = description;
            this.poster = poster;

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected ModelMovie(Parcel in) {
        id = in.readInt();
        title = in.readString();
        description = in.readString();
        vote_average = in.readString();
        popularity = in.readString();
        poster = in.readString();
        language = in.readString();
        imgUrl = in.readString();
    }

    public static final Creator<ModelMovie> CREATOR = new Creator<ModelMovie>() {
        @Override
        public ModelMovie createFromParcel(Parcel in) {
            return new ModelMovie(in);
        }

        @Override
        public ModelMovie[] newArray(int size) {
            return new ModelMovie[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescriptionl(String descriptionl) {
        this.description = descriptionl;
    }

    public String getVote_average() {
        return vote_average;
    }

    public void setVote_average(String vote_average) {
        this.vote_average = vote_average;
    }

    public String getPopularity() {
        return popularity;
    }

    public void setPopularity(String popularity) {
        this.popularity = popularity;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(title);
        parcel.writeString(description);
        parcel.writeString(vote_average);
        parcel.writeString(popularity);
        parcel.writeString(poster);
        parcel.writeString(language);
        parcel.writeString(imgUrl);
    }
}
