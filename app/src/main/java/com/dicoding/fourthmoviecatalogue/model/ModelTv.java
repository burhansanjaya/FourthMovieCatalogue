package com.dicoding.fourthmoviecatalogue.model;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

public class ModelTv implements Parcelable {

    private String title;
    private String popularity;
    private String imgUrl = "https://image.tmdb.org/t/p/original";
    private String language;
    private String poster;
    private String description;
    private int id;

    public ModelTv() {
    }

    public ModelTv (JSONObject object){
        try{
            int id = object.getInt("id");
            String title = object.getString("name");
            String release = object.getString("first_air_date");
            String description = object.getString("overview");
            String popularity = object.getString("popularity");
            String language = object.getString("original_language");
            String poster = imgUrl+object.getString("backdrop_path");

            this.id = id;
            this.title = title;
            this.description = description;
            this.popularity = popularity;
            this.language = language;
            this.poster = poster;
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPopularity() {
        return popularity;
    }

    public void setPopularity(String popularity) {
        this.popularity = popularity;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public static Creator<ModelTv> getCREATOR() {
        return CREATOR;
    }

    protected ModelTv(Parcel in) {
        title = in.readString();
        popularity = in.readString();
        imgUrl = in.readString();
        language = in.readString();
        poster = in.readString();
        description = in.readString();
        id = in.readInt();
    }

    public static final Creator<ModelTv> CREATOR = new Creator<ModelTv>() {
        @Override
        public ModelTv createFromParcel(Parcel in) {
            return new ModelTv(in);
        }

        @Override
        public ModelTv[] newArray(int size) {
            return new ModelTv[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(title);
        parcel.writeString(popularity);
        parcel.writeString(imgUrl);
        parcel.writeString(language);
        parcel.writeString(poster);
        parcel.writeString(description);
        parcel.writeInt(id);
    }
}
