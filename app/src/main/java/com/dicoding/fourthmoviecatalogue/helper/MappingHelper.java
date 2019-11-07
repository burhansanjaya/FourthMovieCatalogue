package com.dicoding.fourthmoviecatalogue.helper;

import android.database.Cursor;

import com.dicoding.fourthmoviecatalogue.db.DatabaseContract;
import com.dicoding.fourthmoviecatalogue.model.ModelFavorite;

import java.util.ArrayList;

public class MappingHelper {

    public static ArrayList<ModelFavorite> mapCursorToArrayList(Cursor moviesCursor){
        ArrayList<ModelFavorite> moviesList = new ArrayList<>();

        while (moviesCursor.moveToNext()){
            int id = moviesCursor.getInt(moviesCursor.getColumnIndexOrThrow(DatabaseContract.FilmColumns._ID));
            String title = moviesCursor.getString(moviesCursor.getColumnIndexOrThrow(DatabaseContract.FilmColumns.TITLE));
            String description = moviesCursor.getString(moviesCursor.getColumnIndexOrThrow(DatabaseContract.FilmColumns.DESCRIPTION));
            String imgPoster = moviesCursor.getString(moviesCursor.getColumnIndexOrThrow(DatabaseContract.FilmColumns.POSTER_PATH));
            moviesList.add(new ModelFavorite(title, description, imgPoster, id));
        }

        return moviesList;
    }
}
