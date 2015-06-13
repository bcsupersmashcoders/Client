package com.supersmashcoders.backtobackhackathon.models;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

public class ProductModel {
    private String id;
    private String name;
    private String productUrl;
    private String photoUrl;

    public ProductModel(String id, String name, String productUrl, String photoUrl) {
        this.id = id;
        this.name = name;
        this.productUrl = productUrl;
        this.photoUrl = photoUrl;
    }

    public static ProductModel of(JSONObject jsonEvent) {
        try {
            String id = jsonEvent.getString("id");
            String name = jsonEvent.getString("name");
            String productUrl = jsonEvent.getString("productURL");
            String photoUrl = jsonEvent.getString("photoURL");
            return new ProductModel(id, name, productUrl, photoUrl);
        } catch(JSONException e) {
            Log.e("JSON PARSE", "ERROR PARSING " + jsonEvent.toString());
            return null;
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProductUrl() {
        return productUrl;
    }

    public void setProductUrl(String productUrl) {
        this.productUrl = productUrl;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }
}
