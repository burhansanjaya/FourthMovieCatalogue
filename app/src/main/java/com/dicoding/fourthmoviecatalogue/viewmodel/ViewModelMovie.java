package com.dicoding.fourthmoviecatalogue.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.dicoding.fourthmoviecatalogue.model.ModelMovie;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class ViewModelMovie extends ViewModel {

    private static final String API_KEY = "20ccb4a4efd5760cd22acfcc3bdbe734";
    private MutableLiveData<ArrayList<ModelMovie>> listMoviss = new MutableLiveData<>();
    private String TAG = "ViewModel";

    public void setMovie(){
        AsyncHttpClient client = new AsyncHttpClient();
        final ArrayList<ModelMovie> listItems = new ArrayList<>();
        String url = "http://api.themoviedb.org/3/discover/movie?api_key="+API_KEY+"&language=en-US";
        Log.d(TAG, "1. SetMovies URL : " + url);

        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                Log.d(TAG, "2. onSuccess : " + responseBody);
                try {
                    String result = new String(responseBody);
                    JSONObject responseObject = new JSONObject(result);
                    JSONArray list = responseObject.getJSONArray("results");
                    Log.d("onSuccess", "3. onSuccess : result : " + result);

                    for (int i = 0; i < list.length(); i++){
                        JSONObject movie = list.getJSONObject(i);
                        ModelMovie modelMovie = new ModelMovie(movie);
                        listItems.add(modelMovie);
                        Log.d(TAG, "4. onSuccess : movieData : " + movie);
                    }
                    listMoviss.postValue(listItems);
                }catch (JSONException e){
                    Log.d(TAG, e.getMessage());
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.d(TAG, error.getMessage());
            }
        });
    }

    public LiveData<ArrayList<ModelMovie>> getMovies(){
        return listMoviss;
    }
}
