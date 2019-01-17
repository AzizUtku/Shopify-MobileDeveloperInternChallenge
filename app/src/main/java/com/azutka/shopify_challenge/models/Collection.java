package com.azutka.shopify_challenge.models;

import java.io.Serializable;

public class Collection implements Serializable {

    private String id;
    private String handle;
    private String title;
    private String body;
    private String updated_at;
    private String published_at;
    private String sort_order;
    private String img_url;

    public Collection() {
    }

    public Collection(String id, String handle, String title, String body, String updated_at, String published_at, String sort_order, String img_url) {
        this.id = id;
        this.handle = handle;
        this.title = title;
        this.body = body;
        this.updated_at = updated_at;
        this.published_at = published_at;
        this.sort_order = sort_order;
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

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public String getPublished_at() {
        return published_at;
    }

    public void setPublished_at(String published_at) {
        this.published_at = published_at;
    }

    public String getSort_order() {
        return sort_order;
    }

    public void setSort_order(String sort_order) {
        this.sort_order = sort_order;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }
}
