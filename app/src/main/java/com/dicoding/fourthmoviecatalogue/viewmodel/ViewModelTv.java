package com.dicoding.fourthmoviecatalogue.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.dicoding.fourthmoviecatalogue.model.ModelTv;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class ViewModelTv extends ViewModel {

    private static final String API_KEY = "20ccb4a4efd5760cd22acfcc3bdbe734";
    private MutableLiveData<ArrayList<ModelTv>> listTv = new MutableLiveData<>();
    private String TAG = "ViewModel";

    public void setTv(){
        AsyncHttpClient client = new AsyncHttpClient();
        final ArrayList<ModelTv> listItems = new ArrayList<>();
        String url = "https://api.themoviedb.org/3/discover/tv?api_key="+API_KEY+"&language=en-US\n";
        Log.d(TAG, "1. setTV URL : " + url);

        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                Log.d(TAG, "2. onSuccess : " + responseBody);
                try {
                    String result = new String(responseBody);
                    JSONObject responseObject = new JSONObject(result);
                    JSONArray list = responseObject.getJSONArray("results");
                    Log.d("onSuccess", "3. onSuccess : result : " + result);

                    for (int i=0; i<list.length(); i++){
                        JSONObject tv = list.getJSONObject(i);
                        ModelTv modelTv = new ModelTv(tv);
                        listItems.add(modelTv);
                        Log.d(TAG, "4. onSuccess : tvData : " + tv);
                    }
                    listTv.postValue(listItems);
                } catch (JSONException e) {
                    Log.d(TAG, e.getMessage());
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.d(TAG, error.getMessage());
            }
        });
    }

    public LiveData<ArrayList<ModelTv>> getTv(){
        return listTv;
    }
}
