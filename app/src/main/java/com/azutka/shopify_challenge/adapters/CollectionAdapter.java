package com.azutka.shopify_challenge.adapters;

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

import com.azutka.shopify_challenge.R;
import com.azutka.shopify_challenge.models.Collection;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import org.w3c.dom.Text;

import java.util.List;

public class CollectionAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private List<Collection> collections;

    public CollectionAdapter(List<Collection> collections) {
        this.collections = collections;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View collectionView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_collection, parent, false);
        CollectionHolder holder = new CollectionHolder(collectionView);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Collection collection = collections.get(position);

        CollectionHolder collectionHolder = (CollectionHolder) holder;

        collectionHolder.setTitle(collection.getTitle());
        collectionHolder.setDescription(collection.getBody());
        collectionHolder.setImage(collection.getImg_url());
    }

    @Override
    public int getItemCount() {
        return collections.size();
    }

    public class CollectionHolder extends RecyclerView.ViewHolder {

        private RelativeLayout mRltMain;
        private TextView mTxtTitle;
        private TextView mTxtDescription;
        private ImageView mImgCollection;

        private CollectionHolder(View itemView) {
            super(itemView);
            mRltMain = itemView.findViewById(R.id.collection_rlt_main);
            mTxtTitle = itemView.findViewById(R.id.collection_txt_title);
            mTxtDescription = itemView.findViewById(R.id.collection_txt_description);
            mImgCollection = itemView.findViewById(R.id.collection_img);
        }

        public void setTitle(String title){
            mTxtTitle.setText(title);
        }

        public void setDescription(String description){
            mTxtDescription.setText(description);
        }

        public void setImage(String imgURL){
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
                    int vibrantLight = p.getLightVibrantColor(defaultValue);
                    int vibrantDark = p.getDarkVibrantColor(defaultValue);
                    int muted = p.getMutedColor(defaultValue);
                    int mutedLight = p.getLightMutedColor(defaultValue);
                    int mutedDark = p.getDarkMutedColor(defaultValue);

                    GradientDrawable gd = new GradientDrawable(
                            GradientDrawable.Orientation.TR_BL,
                            new int[] {vibrant,vibrantLight});
                    gd.setCornerRadius(0f);

                    mRltMain.setBackground(gd);


                }
            });
        }
    }
}
