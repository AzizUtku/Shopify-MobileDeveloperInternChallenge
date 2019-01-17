package com.azutka.shopify_challenge;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.azutka.shopify_challenge.adapters.CollectionAdapter;
import com.azutka.shopify_challenge.interfaces.OnTaskCompletedListener;
import com.azutka.shopify_challenge.models.Collection;
import com.azutka.shopify_challenge.requests.GeneralRequest;

import java.util.ArrayList;
import java.util.List;

public class StartActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        getCollections();
    }

    public void getCollections() {

        OnTaskCompletedListener<Collection> onTaskCompletedListener = new OnTaskCompletedListener<Collection>() {
            @Override
            public void onTaskCompleted(List<Collection> collections) {
                Intent mainIntent = new Intent(StartActivity.this, MainActivity.class);
                mainIntent.putExtra("collectionsExtra", (ArrayList<Collection>) collections);
                mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(mainIntent);
                finish();
            }
        };

        GeneralRequest<Collection> collectionRequest = new GeneralRequest<>(GeneralRequest.TYPE_COLLECTIONS);
        collectionRequest.setOnTaskCompletedListener(onTaskCompletedListener);
        collectionRequest.execute();

    }
}
