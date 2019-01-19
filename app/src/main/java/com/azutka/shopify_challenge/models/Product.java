package com.azutka.shopify_challenge.models;

import java.util.ArrayList;

public class Product {

    private String id;
    private String handle;
    private String title;
    private String body;
    private String vendor;
    private String product_type;
    private String tags;
    private ArrayList<Variant> variants;
    private String img_url;

    public Product() {
    }

    public Product(String id, String handle, String title, String body, String vendor, String product_type, String tags, ArrayList<Variant> variants, String img_url) {
        this.id = id;
        this.handle = handle;
        this.title = title;
        this.body = body;
        this.vendor = vendor;
        this.product_type = product_type;
        this.tags = tags;
        this.variants = variants;
        this.img_url = img_url;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getHandle() {
        return handle;
    }

    public void setHandle(String handle) {
        this.handle = handle;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getVendor() {
        return vendor;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    public String getProduct_type() {
        return product_type;
    }

    public void setProduct_type(String product_type) {
        this.product_type = product_type;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public ArrayList<Variant> getVariants() {
        return variants;
    }

    public void setVariants(ArrayList<Variant> variants) {
        this.variants = variants;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }
}
