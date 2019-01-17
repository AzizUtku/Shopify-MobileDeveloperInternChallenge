package com.azutka.shopify_challenge;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.azutka.shopify_challenge.adapters.CollectionAdapter;
import com.azutka.shopify_challenge.interfaces.OnTaskCompletedListener;
import com.azutka.shopify_challenge.models.Collection;
import com.azutka.shopify_challenge.requests.GeneralRequest;
import com.azutka.shopify_challenge.utils.Constant;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private RecyclerView mRecycler;

    private List<Collection> mCollections;

    @SuppressWarnings(value="unchecked")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Set toolbar
        mToolbar = findViewById(R.id.main_toolbar);
        setSupportActionBar(mToolbar);

        mRecycler = findViewById(R.id.main_collections_recycler);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        mRecycler.setLayoutManager(layoutManager);

        mCollections = (ArrayList<Collection>) getIntent().getSerializableExtra("collectionsExtra");
        getCollections();
    }

    public void getCollections(){
        if(mCollections != null){

            if(mCollections.size() > 0){
                //TODO Dont forget error
                //mTxtError.setVisibility(View.GONE);
                mRecycler.setAdapter(new CollectionAdapter(mCollections));
                synchronized (mRecycler) {
                    mRecycler.notifyAll();
                }
            }
            else {
                //mTxtError.setVisibility(View.VISIBLE);
            }

        }
    }
}
