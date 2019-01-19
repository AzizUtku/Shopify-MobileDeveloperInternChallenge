package com.azutka.shopify_challenge.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.NonNull;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.azutka.shopify_challenge.DetailsActivity;
import com.azutka.shopify_challenge.R;
import com.azutka.shopify_challenge.models.Collection;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import java.util.List;

public class CollectionAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Collection> mCollections;
    private boolean mSearch;
    private Context mContext;

    public CollectionAdapter(Context context, List<Collection> collections, boolean search) {
        this.mContext = context;
        this.mCollections = collections;
        this.mSearch = search;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if(viewType == 0 && !mSearch){

            View headerView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_recycler_header, parent,false);
            HeaderHolder holder = new HeaderHolder(headerView);
            return holder;

        } else {

            View collectionView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_collection, parent, false);
            CollectionHolder holder = new CollectionHolder(collectionView);
            return holder;

        }


    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(position > 0 || mSearch) {
            Collection collection = mCollections.get(position);

            CollectionHolder collectionHolder = (CollectionHolder) holder;

            collectionHolder.setTitle(collection.getTitle());
            collectionHolder.setDescription(collection.getBody());
            collectionHolder.setImage(collection.getImg_url());
            collectionHolder.setID(collection.getId());
        }
    }

    @Override
    public int getItemCount() {
        return mCollections.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public class CollectionHolder extends RecyclerView.ViewHolder {

        private RelativeLayout mRltMiddle;
        private RelativeLayout mRltMain;
        private TextView mTxtTitle;
        private TextView mTxtDescription;
        private ImageView mImgCollection;

        private String mImgURL;
        private String mTitle;
        private String mDescription;
        private String mID;

        private CollectionHolder(View itemView) {
            super(itemView);
            mRltMiddle = itemView.findViewById(R.id.collection_rlt_middle);
            mRltMain = itemView.findViewById(R.id.collection_rlt_main);
            mTxtTitle = itemView.findViewById(R.id.collection_txt_title);
            mTxtDescription = itemView.findViewById(R.id.collection_txt_description);
            mImgCollection = itemView.findViewById(R.id.collection_img);

            mRltMain.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, DetailsActivity.class);
                    intent.putExtra("title", mTitle);
                    intent.putExtra("description", mDescription);
                    intent.putExtra("imageURL", mImgURL);
                    intent.putExtra("collectionID", mID);
                    mContext.startActivity(intent);
                }
            });
        }

        public void setID(String id) {
            mID = id;
        }

        public void setTitle(String title){
            mTitle = title;
            mTxtTitle.setText(title);
        }

        public void setDescription(String description){
            mDescription = description;
            mTxtDescription.setText(description);
            if(description.isEmpty()) {
                mTxtDescription.setVisibility(View.GONE);
            }
        }

        public void setImage(String imgURL){
            mImgURL = imgURL;
            Picasso.get().load(imgURL).placeholder(R.drawable.ic_aerodynamic).into(new Target() {
                @Override
                public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                    mImgCollection.setImageBitmap(bitmap);
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
                    gd.setCornerRadius(25f);

                    mRltMiddle.setBackground(gd);

                }
            });
        }
    }


    public class HeaderHolder extends RecyclerView.ViewHolder {

        public TextView mTxtInfo;

        public HeaderHolder(View itemView) {
            super(itemView);
            mTxtInfo = itemView.findViewById(R.id.row_recycler_txt_info);
        }
    }
}
