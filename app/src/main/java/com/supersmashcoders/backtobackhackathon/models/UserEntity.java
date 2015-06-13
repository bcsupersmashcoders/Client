package com.supersmashcoders.backtobackhackathon.models;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class UserEntity implements Serializable {

    private Long id;
    private String username;

    private UserEntity(Long id, String username) {
        this.id = id;
        this.username = username;
    }

    public static UserEntity of(Long id, String username) {
        return new UserEntity(id, username);
    }

    public static UserEntity of(JSONObject jsonUser) {
        try {
            Long id = jsonUser.getLong("id");
            String username = jsonUser.getString("username");
            return new UserEntity(id, username);
        } catch (JSONException e) {
            Log.e("JSON PARSE", "ERROR PARSING " + jsonUser.toString());
            return null;
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public JSONObject asJSON() {
        JSONObject object = new JSONObject();
        try {
            object.put("username", getUsername());
        } catch (JSONException e) {
            Log.e("JSON PARSE", "Error converting object to JSON");
        }
        return object;
    }
}
