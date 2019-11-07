package com.dicoding.fourthmoviecatalogue.model;

import android.os.Parcel;
import android.os.Parcelable;

public class ModelFavorite implements Parcelable {

    private String title;
    private String description;
    private String poster_path;

    private int id;

    public ModelFavorite() {
    }

    public ModelFavorite(String title, String description, String poster_path, int id) {
        this.title = title;
        this.description = description;
        this.poster_path = poster_path;
        this.id = id;
    }

    protected ModelFavorite(Parcel in) {
        title = in.readString();
        description = in.readString();
        poster_path = in.readString();
        id = in.readInt();
    }

    public static final Creator<ModelFavorite> CREATOR = new Creator<ModelFavorite>() {
        @Override
        public ModelFavorite createFromParcel(Parcel in) {
            return new ModelFavorite(in);
        }

        @Override
        public ModelFavorite[] newArray(int size) {
            return new ModelFavorite[size];
        }
    };

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(title);
        parcel.writeString(description);
        parcel.writeString(poster_path);
        parcel.writeInt(id);
    }
}
