package com.azutka.shopify_challenge;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.azutka.shopify_challenge.adapters.ProductAdapter;
import com.azutka.shopify_challenge.interfaces.OnTaskCompletedListener;
import com.azutka.shopify_challenge.models.Product;
import com.azutka.shopify_challenge.requests.GeneralRequest;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.ArrayList;
import java.util.List;

public class DetailsActivity extends AppCompatActivity implements SearchView.OnQueryTextListener{

    private Toolbar mToolbar;
    private RelativeLayout mRltToolbar;
    private ImageView mImgToolbar;
    private TextView mTxtDescriptionToolbar;

    private String mImgURL;
    private String mCollectionID;

    private RecyclerView mRecycler;
    private ProgressBar mPb;
    private List<Product> mProducts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        mToolbar = findViewById(R.id.details_toolbar);
        setSupportActionBar(mToolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        mRltToolbar = findViewById(R.id.details_rlt_toolbar);
        mImgToolbar = findViewById(R.id.details_img_toolbar);
        mTxtDescriptionToolbar = findViewById(R.id.details_txt_description_toolbar);
        TextView txtToolbarTitle = findViewById(R.id.toolbar_title);

        String title = getIntent().getStringExtra("title");
        String description = getIntent().getStringExtra("description");
        mImgURL = getIntent().getStringExtra("imageURL");
        mCollectionID = getIntent().getStringExtra("collectionID");

        txtToolbarTitle.setText(title);
        mTxtDescriptionToolbar.setText(description);

        mRecycler = findViewById(R.id.details_recycler);
        mPb = findViewById(R.id.details_pb);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        mRecycler.setLayoutManager(layoutManager);

        if(description.isEmpty())
            mTxtDescriptionToolbar.setVisibility(View.GONE);

        setImage();

        getProducts();


    }

    public void getProducts() {

        OnTaskCompletedListener<String> onTaskCompletedListener = new OnTaskCompletedListener<String>() {
            @Override
            public void onTaskCompleted(List<String> productIDs) {

                StringBuilder stringBuilder = new StringBuilder();
                for(String id : productIDs){
                    stringBuilder.append(id).append(',');
                }
                if(stringBuilder.length() > 0){
                    stringBuilder.deleteCharAt(stringBuilder.length() - 1);
                }


                String ids = stringBuilder.toString();

                OnTaskCompletedListener<Product> onTaskCompletedListener = new OnTaskCompletedListener<Product>() {
                    @Override
                    public void onTaskCompleted(List<Product> products) {
                        mProducts = products;
                        if(mProducts != null){

                            if(mProducts.size() > 0){
                                //TODO Dont forget error
                                //mTxtError.setVisibility(View.GONE);
                                mRecycler.setAdapter(new ProductAdapter(getApplicationContext(), mProducts, false));
                                mPb.setVisibility(View.GONE);
                                synchronized (mRecycler) {
                                    mRecycler.notifyAll();
                                }
                            }
                            else {
                                //mTxtError.setVisibility(View.VISIBLE);
                            }

                        }
                    }
                };

                GeneralRequest<Product> collectionRequest = new GeneralRequest<>(GeneralRequest.TYPE_PRODUCT_DETAILS, ids);
                collectionRequest.setOnTaskCompletedListener(onTaskCompletedListener);
                collectionRequest.execute();

            }
        };

        GeneralRequest<Product> collectionRequest = new GeneralRequest<>(GeneralRequest.TYPE_PRODUCTS, mCollectionID);
        collectionRequest.setOnTaskCompletedListener(onTaskCompletedListener);
        collectionRequest.execute();

    }


    public void setImage(){
        Picasso.get().load(mImgURL).placeholder(R.drawable.ic_aerodynamic).into(new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                mImgToolbar.setImageBitmap(bitmap);
                setBackgroundGradient(bitmap);
            }

            @Override
            public void onBitmapFailed(Exception e, Drawable errorDrawable) {

            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {

            }
        });
    }

    private void setBackgroundGradient(Bitmap bitmap){
        Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
            public void onGenerated(Palette p) {

                int defaultValue = 0x000000;
                int vibrant = p.getVibrantColor(defaultValue);
                int vibrantDark = p.getDarkVibrantColor(defaultValue);

                GradientDrawable gd = new GradientDrawable(GradientDrawable.Orientation.TR_BL, new int[] {vibrantDark,vibrant});
                gd.setCornerRadius(0f);

                mRltToolbar.setBackground(gd);
            }
        });
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {

        if(!newText.isEmpty() && newText != null){
            List<Product> productSearch = new ArrayList<>();
            ProductAdapter productAdapterSearch = new ProductAdapter(getApplicationContext(), productSearch,true);

            for(Product p: mProducts){

                try{
                    if (p.getBody().toLowerCase().trim().contains(newText.toLowerCase().trim())
                            || p.getTitle().toLowerCase().trim().contains(newText.toLowerCase().trim())
                            || p.getId().toLowerCase().trim().contains(newText.toLowerCase().trim())
                            || p.getTags().toLowerCase().trim().contains(newText.toLowerCase().trim())
                            || p.getVendor().toLowerCase().trim().contains(newText.toLowerCase().trim())){

                        productSearch.add(p);

                    }
                } catch(Exception e){

                }
            }

            mRecycler.setAdapter(productAdapterSearch);
            synchronized(mRecycler){
                mRecycler.notifyAll();
            }

        } else {

            mRecycler.setAdapter(new ProductAdapter(getApplicationContext(), mProducts, false));
            synchronized(mRecycler){
                mRecycler.notifyAll();
            }

        }



        return false;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.cart_with_search_menu, menu);
        try{
            MenuItem menuItemSearch = menu.findItem(R.id.cart_search_menu_item_search);

            SearchView searchView = (SearchView) menuItemSearch.getActionView();
            EditText edtSearch = searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text);
            edtSearch.setHintTextColor(getResources().getColor(R.color.search_hint));
            searchView.setOnQueryTextListener(this);


        } catch(Exception e){

        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.cart_search_menu_item_cart:
                Snackbar snackbar = Snackbar.make(mRltToolbar,"This function does not work!", Snackbar.LENGTH_LONG);
                snackbar.show();
                break;

        }
        return super.onOptionsItemSelected(item);
    }

}
