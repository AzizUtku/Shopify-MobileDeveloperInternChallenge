package com.azutka.shopify_challenge;

import android.app.AlertDialog;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import com.azutka.shopify_challenge.fragments.CartFragment;
import com.azutka.shopify_challenge.fragments.CollectionsFragment;
import com.azutka.shopify_challenge.fragments.FavoritesFragment;
import com.azutka.shopify_challenge.models.Collection;
import com.azutka.shopify_challenge.utils.Constant;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Toolbar mToolbar;

    private List<Collection> mCollections;

    private BottomNavigationView mBottomNav;
    private Fragment mCurrentFragment;

    //Fragments
    private CollectionsFragment collectionsFragment;
    private FavoritesFragment favoritesFragment;
    private CartFragment cartFragment;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Set toolbar
        mToolbar = findViewById(R.id.main_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setIcon(getDrawable(R.drawable.ic_shopify_vector));

        mBottomNav = findViewById(R.id.main_bottom_nav);
        collectionsFragment = new CollectionsFragment();
        favoritesFragment = new FavoritesFragment();
        cartFragment = new CartFragment();

        mCollections = (ArrayList<Collection>) getIntent().getSerializableExtra("collectionsExtra");

        Bundle bundle = new Bundle();
        bundle.putSerializable("collectionsExtra", (ArrayList<Collection>) mCollections);
        collectionsFragment.setArguments(bundle);

        mCurrentFragment = collectionsFragment;
        setFragment( collectionsFragment,"collections");

        mBottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {

                    case R.id.menu_nav_collections:
                        switchFragment(collectionsFragment,"collections");
                        return true;
                    case R.id.menu_nav_favorites:
                        switchFragment(favoritesFragment,"favorites");
                        return true;
                    case R.id.menu_nav_cart:
                        switchFragment(cartFragment,"cart");
                        return true;
                    default:
                        switchFragment(collectionsFragment,"collections");
                        return true;

                }
            }
        });
    }

    private void switchFragment(Fragment fragment, String tag) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        if (getSupportFragmentManager().findFragmentByTag(tag) == null) {
            fragmentTransaction.add(R.id.main_frame, fragment, tag);
        }

        fragmentTransaction.hide(mCurrentFragment);
        fragmentTransaction.show(fragment);
        mCurrentFragment = fragment;
        fragmentTransaction.commit();
    }

    private void setFragment(Fragment fragment, String tag) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_frame, fragment, tag);
        fragmentTransaction.commit();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        //Set menu
        switch(item.getItemId()){
            case R.id.menu_main_about:
                AlertDialog.Builder dialogAbout = new AlertDialog.Builder(this);
                dialogAbout.setTitle("About");
                dialogAbout.setMessage("Developer: Aziz Utku Kağıtcı\nPhone: +905072672681\nEmail: azizutku@gmail.com\n\nVersion: " + Constant.version);
                dialogAbout.setPositiveButton("Okay", null );
                dialogAbout.show();
                break;
            default:
                break;
        }

        return true;
    }
}
