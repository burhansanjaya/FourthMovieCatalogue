package com.dicoding.fourthmoviecatalogue.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.dicoding.fourthmoviecatalogue.model.ModelFavorite;

import java.util.ArrayList;

import static android.provider.BaseColumns._ID;
import static com.dicoding.fourthmoviecatalogue.db.DatabaseContract.FilmColumns.DESCRIPTION;
import static com.dicoding.fourthmoviecatalogue.db.DatabaseContract.FilmColumns.POSTER_PATH;
import static com.dicoding.fourthmoviecatalogue.db.DatabaseContract.FilmColumns.TITLE;
import static com.dicoding.fourthmoviecatalogue.db.DatabaseContract.TABLE_NAME;

public class MovieHelper {

    private static final String DATABASE_TABLE = TABLE_NAME;
    private static DatabaseHelper databaseHelper;
    private static MovieHelper INSTANCE;

    private static SQLiteDatabase database;

    private MovieHelper(Context context) {
        databaseHelper = new DatabaseHelper(context);
    }

    public static MovieHelper getInstance(Context context){
        if (INSTANCE == null){
            synchronized (SQLiteOpenHelper.class){
                INSTANCE = new MovieHelper(context);
            }
        }
        return INSTANCE;
    }

    public void open() throws SQLException {
        database = databaseHelper.getWritableDatabase();
    }

    public void close(){
        databaseHelper.close();

        if (database.isOpen())
            database.close();
    }

    public Cursor queryAll(){
        return database.query(
                DATABASE_TABLE,
                null,
                null,
                null,
                null,
                null,
                _ID + " ASC"
        );
    }

    public Cursor queryById(String id){
        return database.query(
                DATABASE_TABLE,
                null,
                _ID + " = ?",
                new String[]{id},
                null,
                null,
                null,
                null
        );
    }

    public ArrayList<ModelFavorite> getAllFilm(){
        ArrayList<ModelFavorite> arrayList = new ArrayList<>();
        Cursor cursor = database.query(DATABASE_TABLE, null,
                null,
                null,
                null,
                null,
                _ID + "ASC",
                null);
        cursor.moveToFirst();
        ModelFavorite modelFavorite;

        if (cursor.getCount() > 0){
            do{
                modelFavorite = new ModelFavorite();
                modelFavorite.setId(cursor.getInt(cursor.getColumnIndexOrThrow(_ID)));
                modelFavorite.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(TITLE)));
                modelFavorite.setDescription(cursor.getString(cursor.getColumnIndexOrThrow(DESCRIPTION)));
                modelFavorite.setPoster_path(cursor.getString(cursor.getColumnIndexOrThrow(POSTER_PATH)));
            }while (!cursor.isAfterLast());
        }
        cursor.close();
        return arrayList;
    }

    public long insertFilm(ModelFavorite modelFavorite){
        ContentValues args = new ContentValues();
        args.put(TITLE, modelFavorite.getTitle());
        args.put(DESCRIPTION, modelFavorite.getDescription());
        args.put(POSTER_PATH, modelFavorite.getPoster_path());
        return database.insert(DATABASE_TABLE, null, args);
    }

    public int deleteFilm(int id){
        return database.delete(TABLE_NAME, _ID + "='" + id + "'", null);
    }
}
