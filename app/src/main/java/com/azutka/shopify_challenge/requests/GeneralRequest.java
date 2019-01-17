package com.azutka.shopify_challenge.requests;

import android.os.AsyncTask;
import android.util.Log;

import com.azutka.shopify_challenge.interfaces.OnTaskCompletedListener;
import com.azutka.shopify_challenge.models.Collection;
import com.azutka.shopify_challenge.utils.Constant;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class GeneralRequest<T> extends AsyncTask<Void, Void, String> {

    private static final String TAG = "GeneralRequest";

    public static final int TYPE_COLLECTIONS = 0;

    private int mType;
    private String mUrl;
    private OnTaskCompletedListener<T> onTaskCompletedListener;
    private OkHttpClient mClient;
    private List<T> list;

    public GeneralRequest(int type){
        mType = type;
        if(type == TYPE_COLLECTIONS){
            mUrl = Constant.URL_COLLECTIONS;
        }
    }

    public void setOnTaskCompletedListener(OnTaskCompletedListener onTaskCompletedListener){
        this.onTaskCompletedListener = onTaskCompletedListener;
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        mClient = new OkHttpClient();
        list = new ArrayList<>();
    }

    @Override
    protected String doInBackground(Void... voids) {

        try {
            Request request = new Request.Builder()
                    .url( mUrl)
                    .header("Content-Type", "application/json; charset=utf-8")
                    .build();
            Response response = mClient.newCall(request).execute();
            return response.body().string();
        } catch (Exception e){
            return null;
        }

    }


    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        Log.d(TAG, "onPostExecute:" + s);

        list = getList(s, mType);

        if(onTaskCompletedListener != null){
            onTaskCompletedListener.onTaskCompleted(list);
        }

    }

    @SuppressWarnings (value="unchecked")
    public List<T> getList(String json, int type) {

        List<T> newList = new ArrayList<>();

        if(type == TYPE_COLLECTIONS){
            try {

                JSONObject jsonObject = new JSONObject(json);

                JSONArray jsonArray = jsonObject.getJSONArray("custom_collections");

                for(int i = 0; i < jsonArray.length(); i++){
                    String id = jsonArray.getJSONObject(i).getString("id");
                    String handle = jsonArray.getJSONObject(i).getString("handle");
                    String title = jsonArray.getJSONObject(i).getString("title");
                    String body = jsonArray.getJSONObject(i).getString("body_html");
                    String updated_at = jsonArray.getJSONObject(i).getString("updated_at");
                    String published_at = jsonArray.getJSONObject(i).getString("published_at");
                    String sort_order = jsonArray.getJSONObject(i).getString("sort_order");
                    String img_url = jsonArray.getJSONObject(i).getJSONObject("image").getString("src");

                    Collection collection = new Collection(id,handle,title,body,updated_at,published_at,sort_order,img_url);

                    newList.add((T) collection);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

            return newList;

        }

        return newList;
    }
}
