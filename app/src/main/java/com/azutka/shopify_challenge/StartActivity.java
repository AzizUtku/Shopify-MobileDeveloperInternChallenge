package com.azutka.shopify_challenge;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ProgressBar;

import com.azutka.shopify_challenge.interfaces.OnTaskCompletedListener;
import com.azutka.shopify_challenge.models.Collection;
import com.azutka.shopify_challenge.requests.GeneralRequest;
import com.azutka.shopify_challenge.utils.Constant;

import java.util.ArrayList;
import java.util.List;

public class StartActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        ProgressBar pb = findViewById(R.id.start_pb);
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if(networkInfo != null && networkInfo.isConnectedOrConnecting()){
            getCollections();
        } else {
            AlertDialog.Builder dialogAbout = new AlertDialog.Builder(this);
            dialogAbout.setCancelable(false);
            dialogAbout.setTitle("No connection");
            dialogAbout.setMessage("Internet connection is needed!");
            dialogAbout.setPositiveButton("Close", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            });
            dialogAbout.show();
        }
    }

    public void getCollections() {

        OnTaskCompletedListener<Collection> onTaskCompletedListener = new OnTaskCompletedListener<Collection>() {
            @Override
            public void onTaskCompleted(List<Collection> collections) {
                collections.add(0,null);
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
