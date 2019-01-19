package com.azutka.shopify_challenge.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.azutka.shopify_challenge.R;
import com.azutka.shopify_challenge.adapters.CollectionAdapter;
import com.azutka.shopify_challenge.models.Collection;

import java.util.ArrayList;
import java.util.List;


public class CollectionsFragment extends Fragment implements SearchView.OnQueryTextListener {

    private View mView;

    private RecyclerView mRecycler;
    private List<Collection> mCollections;

    public CollectionsFragment() {
        // Required empty public constructor
        setHasOptionsMenu(true);
    }

    @SuppressWarnings(value="unchecked")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_collections, container, false);


        mRecycler = mView.findViewById(R.id.fragment_collections_recycler);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        mRecycler.setLayoutManager(layoutManager);

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            mCollections = (ArrayList<Collection>) bundle.getSerializable("collectionsExtra");
        }

        getCollections();

        return mView;
    }


    public void getCollections(){
        if(mCollections != null){

            if(mCollections.size() > 0){
                //TODO Dont forget error
                //mTxtError.setVisibility(View.GONE);
                mRecycler.setAdapter(new CollectionAdapter(getContext(), mCollections, false));
                synchronized (mRecycler) {
                    mRecycler.notifyAll();
                }
            }
            else {
                //mTxtError.setVisibility(View.VISIBLE);
            }

        }
    }



    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {

        if(!newText.isEmpty() && newText != null){
            List<Collection> collectionsSearch = new ArrayList<>();
            //collectionsSearch.add(null);
            CollectionAdapter collectionAdapterSearch = new CollectionAdapter(getContext(), collectionsSearch,true);

            for(Collection c: mCollections){

                try{
                    if (c.getBody().toLowerCase().trim().contains(newText.toLowerCase().trim())
                            || c.getTitle().toLowerCase().trim().contains(newText.toLowerCase().trim())
                            || c.getId().toLowerCase().trim().contains(newText.toLowerCase().trim()) ){

                        collectionsSearch.add(c);

                    }
                } catch(Exception e){

                }
            }

            mRecycler.setAdapter(collectionAdapterSearch);
            synchronized(mRecycler){
                mRecycler.notifyAll();
            }

        } else {

            mRecycler.setAdapter(new CollectionAdapter(getContext(), mCollections, false));
            synchronized(mRecycler){
                mRecycler.notifyAll();
            }

        }


        return false;
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        try {

            MenuItem menuItemSearch = menu.findItem(R.id.menu_main_item_search);

            SearchView searchView = (android.support.v7.widget.SearchView) menuItemSearch.getActionView();
            EditText searchEditText = searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text);
            searchEditText.setHintTextColor(getResources().getColor(R.color.search_hint));
            searchView.setOnQueryTextListener(this);

        } catch (Exception e){

        }
    }

}
