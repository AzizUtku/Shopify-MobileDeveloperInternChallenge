package com.azutka.shopify_challenge.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.azutka.shopify_challenge.R;
import com.azutka.shopify_challenge.models.Product;
import com.azutka.shopify_challenge.models.Variant;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Product> mProducts;
    private boolean mSearch;
    private Context mContext;


    public ProductAdapter(Context context, List<Product> products, boolean search) {
        this.mContext = context;
        this.mProducts = products;
        this.mSearch = search;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View productView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_product, parent, false);
        ProductHolder holder = new ProductHolder(productView);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Product product = mProducts.get(position);

        ProductHolder productHolder = (ProductHolder) holder;

        productHolder.setType(product.getProduct_type());
        productHolder.setTitle(product.getTitle());
        productHolder.setDescription(product.getBody());
        productHolder.setImage(product.getImg_url());
        productHolder.setPrice(product.getVariants().get(0).getPrice());
        productHolder.setWeightUnit(product.getVariants().get(0).getWeight_unit());
        productHolder.setWeight(product.getVariants().get(0).getWeight());
        productHolder.setAvailable(product.getVariants().get(0).getInventory_quantity());
        productHolder.setVendor(product.getVendor());
        productHolder.setVariants(product.getVariants());
        productHolder.setPosition(0);

    }

    @Override
    public int getItemCount() {
        return mProducts.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public class ProductHolder extends RecyclerView.ViewHolder {

        private ImageView mImgProduct;
        private TextView mTxtType;
        private FloatingActionButton mFabFav;
        private TextView mTxtTitle;
        private TextView mTxtDescription;
        private TextView mTxtPrice;
        private TextView mTxtAmount;
        private TextView mTxtWeight;
        private TextView mTxtAvailable;
        private TextView mTxtVendor;
        private TextView mBtnAddToCart;
        private TextView mBtnIncrease;
        private TextView mBtnDecrease;
        private RelativeLayout mRltProduct;
        private TextView mTxtVariant;
        private TextView mBtnBack;
        private TextView mBtnNext;

        private int mInventoryQuantity;
        private int mAmount;
        private String mImgURL;
        private String mWeightUnit;
        private double mPrice;
        private int mCurrentPosition;
        private List<Variant> mVariants;

        private ProductHolder(View itemView) {
            super(itemView);

            mImgProduct = itemView.findViewById(R.id.product_img_product);
            mTxtType = itemView.findViewById(R.id.product_txt_type);
            mFabFav = itemView.findViewById(R.id.product_fab_fav);
            mTxtTitle = itemView.findViewById(R.id.product_txt_title);
            mTxtDescription = itemView.findViewById(R.id.product_txt_info);
            mTxtPrice = itemView.findViewById(R.id.product_txt_price);
            mTxtAmount = itemView.findViewById(R.id.product_txt_amount);
            mTxtWeight = itemView.findViewById(R.id.product_txt_weight);
            mTxtAvailable = itemView.findViewById(R.id.product_txt_available);
            mTxtVendor = itemView.findViewById(R.id.product_txt_vendor);
            mBtnAddToCart = itemView.findViewById(R.id.product_btn_add_cart);
            mBtnIncrease = itemView.findViewById(R.id.product_btn_increase);
            mBtnDecrease = itemView.findViewById(R.id.product_btn_decrease);
            mRltProduct = itemView.findViewById(R.id.product_header_rlt);
            mTxtVariant = itemView.findViewById(R.id.product_txt_variant);
            mBtnBack = itemView.findViewById(R.id.product_btn_back);
            mBtnNext = itemView.findViewById(R.id.product_btn_next);

            mBtnIncrease.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    increaseAmount();
                }
            });

            mBtnDecrease.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    decreaseAmount();
                }
            });

            mBtnNext.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setPosition(mCurrentPosition + 1);
                }
            });

            mBtnBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setPosition(mCurrentPosition - 1);
                }
            });

            mBtnAddToCart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Snackbar snackbar = Snackbar.make(mRltProduct,"This function does not work!", Snackbar.LENGTH_LONG);
                    snackbar.show();
                }
            });

            mFabFav.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Snackbar snackbar = Snackbar.make(mRltProduct,"This function does not work!", Snackbar.LENGTH_LONG);
                    snackbar.show();
                }
            });

        }

        public void setType(String type){
            mTxtType.setText(type);
        }

        public void setTitle(String title){
            mTxtTitle.setText(title);
        }

        public void setDescription(String description){
            mTxtDescription.setText(description);
        }

        public void setPrice(String price){
            mPrice = Double.valueOf(price);
            mTxtPrice.setText("Price: $" + price);
        }

        public void setAmount(int amount){
            mAmount = amount;
            mTxtAmount.setText("Amount: " + amount);
            mTxtPrice.setText("Price: $" + (amount * mPrice));
        }

        public void setWeightUnit(String weightUnit){
           mWeightUnit= weightUnit;
        }

        public void setWeight(String weight){
            mTxtWeight.setText("Weight: " + weight + " " + mWeightUnit);
        }

        public void setAvailable(int quantity){
            mInventoryQuantity = quantity;
            mTxtAvailable.setText(quantity + " piece available");
        }

        public void setVendor(String vendor){
            mTxtVendor.setText("By " + vendor);
        }

        public void setVariants(List<Variant> variants){
            this.mVariants = variants;
        }

        public void setPosition(int position){
            this.mCurrentPosition = position;
            if(mCurrentPosition < 0){
                mCurrentPosition = mVariants.size() - 1;
            } else if(mCurrentPosition > mVariants.size() - 1){
                mCurrentPosition = 0;
            }

            Variant variant = mVariants.get(mCurrentPosition);

            mTxtVariant.setText(variant.getTitle());
            setPrice(variant.getPrice());
            setAmount(1);
            setWeightUnit(variant.getWeight_unit());
            setWeight(variant.getWeight());
            setAvailable(variant.getInventory_quantity());


        }

        private void increaseAmount(){
            if(mAmount < mInventoryQuantity){
                mAmount++;
                setAmount(mAmount);
            }
        }

        private void decreaseAmount(){
            if(mAmount > 1){
                mAmount--;
                setAmount(mAmount);
            }
        }

        public void setImage(String imgURL){
            mImgURL = imgURL;
            Picasso.get().load(imgURL).placeholder(R.drawable.ic_aerodynamic).into(new Target() {
                @Override
                public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                    mImgProduct.setImageBitmap(bitmap);
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

                    GradientDrawable gd = new GradientDrawable(GradientDrawable.Orientation.TR_BL,new int[] {vibrantDark,vibrant});
                    gd.setCornerRadius(0f);

                    mRltProduct.setBackground(gd);

                }
            });
        }
    }
}
